package org.fatmansoft.teach.controllers;

import org.fatmansoft.teach.models.Contest;
import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.payload.response.DataResponse;
import org.fatmansoft.teach.repository.ContestRepository;
import org.fatmansoft.teach.repository.ContestTypeRepository;
import org.fatmansoft.teach.repository.ProcessRepository;
import org.fatmansoft.teach.util.CommonMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/contest")
public class ContestController {
    @Autowired
    ContestRepository contestRepository;
    @Autowired
    ContestTypeRepository contestTypeRepository;
    @Autowired
    ProcessRepository processRepository;


    @PostMapping("/getContestInfo")
    @PreAuthorize("hasRole('ADMIN') ")
    public DataResponse getContestInfo(@Valid @RequestBody DataRequest dataRequest){
        Integer id=dataRequest.getInteger("id");
        if(id==null)return CommonMethod.getReturnMessageError("比赛将在提交后保存");
        try{
            Contest contest=contestRepository.findById(id).get();
            return CommonMethod.getReturnData(CommonMethod.objectToMap(contest));
        }catch (Exception e){return CommonMethod.getReturnMessageError("该比赛不存在！");}
    }

    @PostMapping("/getContestList")
    @PreAuthorize("hasRole('ADMIN') ")
    public DataResponse getContestList(@Valid @RequestBody DataRequest dataRequest){
        String typeName=dataRequest.getString("type");
        Integer season= dataRequest.getInteger("season");
        List dataList;
        if(typeName!=null&&season!=null)
            dataList=contestRepository.getContestBySeasonAndContestType(season,contestTypeRepository.findByName(typeName).get());
        else if(typeName!=null)
            dataList=contestRepository.getContestByContestType(contestTypeRepository.findByName(typeName).get());
        else if(season!=null)
            dataList=contestRepository.getContestBySeason(season);
        else dataList=contestRepository.findAll();

        List l=new ArrayList();
        for(int i=0;i<dataList.size();i++){
            Contest c= (Contest) dataList.get(i);
            Map m=CommonMethod.objectToMap(c);
            m.put("registered",processRepository.existsByContest(c));
            l.add(m);
        }
        return CommonMethod.getReturnData(l);
    }

    @PostMapping("/submitContest")
    @PreAuthorize("hasRole('ADMIN') ")
    public DataResponse submitContest(@Valid @RequestBody DataRequest dataRequest){
        Map form = dataRequest.getMap("form");
        Integer id=(Integer)form.get("id");
        Contest contest;
        try{contest=contestRepository.getById(id);}
        catch (Exception e){contest=new Contest();}
        contest.setAddr((String)form.get("addr"));
        contest.setQq((String)form.get("qq"));
        contest.setQuota((Integer)form.get("quota"));
        contest.setDemand((String) form.get("demand"));
        contest.setSeason((Integer)form.get("season"));
        contest.setContestDate(CommonMethod.String2Date((String)form.get("contestDate")));
        contest.setStartReg(CommonMethod.String2Date((String)form.get("startReg")));
        contest.setEndReg(CommonMethod.String2Date((String)form.get("endReg")));
        contest.setContestType(contestTypeRepository.findByName((String)form.get("contestType")).get());

        //生成赛季中编号
        List<Contest> cList=contestRepository.getContestBySeason(contest.getSeason());
        int max=0;
        for(int i=0;i<cList.size();i++){Contest c=cList.get(i);if(max<c.getRankInSeason())max=c.getRankInSeason();}
        contest.setRankInSeason(max+1);

        contestRepository.save(contest);
        return CommonMethod.getReturnMessageOK();
    }

    @PostMapping("/getContestTypes")
    @PreAuthorize("hasRole('ADMIN') ")
    public DataResponse getContestTypes(@Valid @RequestBody DataRequest dataRequest){
        List l=contestTypeRepository.findAll();
        List dataList=new ArrayList();
        for(int i=0;i<l.size();i++)dataList.add(CommonMethod.objectToMap(l.get(i)));
        return CommonMethod.getReturnData(dataList);
    }

}
