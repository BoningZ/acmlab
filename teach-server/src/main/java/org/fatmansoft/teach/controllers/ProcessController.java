package org.fatmansoft.teach.controllers;

import org.fatmansoft.teach.models.*;
import org.fatmansoft.teach.models.Process;
import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.payload.response.DataResponse;
import org.fatmansoft.teach.repository.ContestRepository;
import org.fatmansoft.teach.repository.ProcessRepository;
import org.fatmansoft.teach.repository.TeamRepository;
import org.fatmansoft.teach.repository.UserRepository;
import org.fatmansoft.teach.util.CommonMethod;
import org.fatmansoft.teach.util.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.relational.core.sql.In;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/process")
public class ProcessController {
    private String rURL,cURL,fURL;

    @Autowired
    ContestRepository contestRepository;
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    ProcessRepository processRepository;
    @Autowired
    UserRepository userRepository;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN') ")
    public DataResponse createProcess(@Valid @RequestBody DataRequest dataRequest){
        Integer id=dataRequest.getInteger("id");
        Contest contest=contestRepository.getById(id);
        if(processRepository.existsByContest(contest))return CommonMethod.getReturnMessageError("该比赛已注册过！");
        List tList=dataRequest.getList("checked");
        for(Object o:tList){
            Team t=teamRepository.getById((Integer)o);
            if(countExceed(t,contest,contest.getSeason()))
                return CommonMethod.getReturnMessageError("队伍\""+t.getFullName()+"\"于"+contest.getSeason()+"赛季已参加两次"+contest.getContestType().toString()+"比赛");
        }
        for(Object o:tList){
            Team t=teamRepository.getById((Integer)o);
            Process process=new Process(t,contest);
            processRepository.save(process);
        }
        return CommonMethod.getReturnMessageOK();
    }

    @PostMapping("/getList")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public DataResponse getProcessList(@Valid @RequestBody DataRequest dataRequest) {
        Integer userId= CommonMethod.getUserId();
        User user=userRepository.findByUserId(userId).get();
        List pList;
        if(user.getUserType().getName().equals(EUserType.ROLE_USER)) {
            pList=new ArrayList();
            Student s=user.getStudent();
            List tList = teamRepository.findTeamsByCaptainOrMember1OrMember2(s,s,s);
            for(Object o:tList){
                Team t=(Team)o;
                pList.addAll(processRepository.findProcessesByTeam(t));
            }
        }else pList=processRepository.findAll();
        pList.sort((o1, o2) -> {
            if(o1.equals(o2))return 0;
            return ((Process)o1).getProcess()>((Process)o2).getProcess()?1:-1;});
        List dataList=new ArrayList();
        for(Object o:pList){
            Process p=(Process)o;
            Map m=CommonMethod.objectToMap(p);
            m.put("active", Const.toActive(p.getProcess()));
            m.put("tName",p.getTeam().getFullName());

            Contest c=p.getContest();
            m.put("demand",c.getDemand());
            m.put("qq",c.getQq());
            m.put("contestDate",c.getContestDate());
            m.put("endReg",c.getEndReg());
            dataList.add(m);
        }
        return CommonMethod.getReturnData(dataList);
    }

    @PostMapping("/push")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public DataResponse pushProcess(@Valid @RequestBody DataRequest dataRequest) {
        String op=dataRequest.getString("op");
        Integer id=dataRequest.getInteger("id");
        Process p=processRepository.getById(id);
        p.setProcess(p.getProcess()|Const.Str2Int.get(op));
        processRepository.save(p);
        return CommonMethod.getReturnMessageOK();
    }

    @PostMapping("/refreshHT")
    @PreAuthorize("hasRole('USER')")
    public DataResponse refreshHT(@Valid @RequestBody DataRequest dataRequest) {
        Integer id=dataRequest.getInteger("id");
        String hotel=dataRequest.getString("hotel");
        String trans=dataRequest.getString("trans");
        Process p=processRepository.getById(id);
        if(hotel!=null&&!hotel.equals(""))p.setProcess(p.getProcess()|Const.Str2Int.get("hotel"));
        if(trans!=null&&!trans.equals(""))p.setProcess(p.getProcess()|Const.Str2Int.get("transport"));
        p.setTransport(trans); p.setHotel(hotel);
        processRepository.save(p);
        return CommonMethod.getReturnMessageOK();
    }

    @PostMapping("/refreshA")
    @PreAuthorize("hasRole('USER')")
    public DataResponse refreshA(@Valid @RequestBody DataRequest dataRequest) {
        Integer id=dataRequest.getInteger("id");
        Integer award=dataRequest.getInteger("award");
        Process p=processRepository.getById(id);
        if(award!=null&&!award.equals(0))p.setProcess(p.getProcess()|Const.Str2Int.get("award"));
        p.setAward(award);
        processRepository.save(p);
        return CommonMethod.getReturnMessageOK();
    }

    @PostMapping("/refreshFCR")
    @PreAuthorize("hasRole('USER')")
    public DataResponse refreshFCR(@Valid @RequestBody DataRequest dataRequest) {
        Integer id=dataRequest.getInteger("id");
        Process p=processRepository.getById(id);
        if(fURL!=null){p.setFeelings(fURL);fURL=null;p.setProcess(p.getProcess()|Const.Str2Int.get("feeling"));}
        if(cURL!=null){p.setCertificate(cURL);fURL=null;p.setProcess(p.getProcess()|Const.Str2Int.get("certificate"));}
        if(rURL!=null){p.setReceipt(rURL);fURL=null;p.setProcess(p.getProcess()|Const.Str2Int.get("receipt"));}
        processRepository.save(p);
        return CommonMethod.getReturnMessageOK();
    }




    @Value("${web.upload-path}")
    private String uploadPath;
    @Value("${server.port}")
    private String port;

    @PostMapping("/submitR")
    public DataResponse importR(MultipartFile file, HttpServletRequest req) throws IOException {
        InetAddress address = InetAddress.getLocalHost();

        String realPath = uploadPath+"receipts/";
        File folder = new File(realPath);
        if (!folder.exists()) folder.mkdirs();
        String oldName = file.getOriginalFilename();
        String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."));
        file.transferTo(new File(folder,newName));
        rURL="/api/download/getFile?fileFullName=receipts/"+newName;

        return CommonMethod.getReturnMessageOK();
    }
    @PostMapping("/submitC")
    public DataResponse importC(MultipartFile file, HttpServletRequest req) throws IOException {
        InetAddress address = InetAddress.getLocalHost();

        String realPath = uploadPath+"certificates/" ;
        File folder = new File(realPath);
        if (!folder.exists()) folder.mkdirs();
        String oldName = file.getOriginalFilename();
        String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."));
        file.transferTo(new File(folder,newName));
        cURL="/api/download/getFile?fileFullName=certificates/"+newName;

        return CommonMethod.getReturnMessageOK();
    }
    @PostMapping("/submitF")
    public DataResponse importF(MultipartFile file, HttpServletRequest req) throws IOException {
        InetAddress address = InetAddress.getLocalHost();

        String realPath = uploadPath+"feelings/" ;
        File folder = new File(realPath);
        if (!folder.exists()) folder.mkdirs();
        String oldName = file.getOriginalFilename();
        String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."));
        file.transferTo(new File(folder,newName));
        fURL="/api/download/getFile?fileFullName=feelings/"+newName;

        return CommonMethod.getReturnMessageOK();
    }

    private Boolean countExceed(Team team,Contest contest,Integer season){
        List pList=processRepository.findProcessesByTeam(team);
        int cnt=0;
        for(Object o:pList){
            Process p=(Process)o;
            if(!p.getContest().getSeason().equals(season))continue;
            if(p.getContest().getContestType().equals(contest.getContestType()))cnt++;
            if(cnt==2)return true;
        }
        return false;
    }




}
