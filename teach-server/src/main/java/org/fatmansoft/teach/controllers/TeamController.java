package org.fatmansoft.teach.controllers;

import org.fatmansoft.teach.models.EUserType;
import org.fatmansoft.teach.models.Student;
import org.fatmansoft.teach.models.Team;
import org.fatmansoft.teach.models.User;
import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.payload.response.DataResponse;
import org.fatmansoft.teach.repository.StudentRepository;
import org.fatmansoft.teach.repository.TeamRepository;
import org.fatmansoft.teach.repository.UserRepository;
import org.fatmansoft.teach.util.CommonMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/team")
public class TeamController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    StudentRepository studentRepository;

    @PostMapping("/getTeamList")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public DataResponse getTeamList(@Valid @RequestBody DataRequest dataRequest){
        Integer userId= CommonMethod.getUserId();
        User user=userRepository.findByUserId(userId).get();
        List dataList=new ArrayList();
        List tList;
        if(user.getUserType().getName().equals(EUserType.ROLE_USER)){
            Student s=user.getStudent();
            tList=teamRepository.findTeamsByCaptainOrMember1OrMember2(s,s,s);
        }else{
            String name=dataRequest.getString("name");
            Integer grade=dataRequest.getInteger("grade");
            if(name!=null)tList=teamRepository.findTeamsByChineseOrEnglish(name,name);//优先按名称查询
            else if(grade!=null){//再按包含某年级学生查询
                List sList=studentRepository.findStudentsByGrade(grade);
                tList=new ArrayList();
                for(int i=0;i<sList.size();i++){
                    Student s=(Student)sList.get(i);
                    tList.addAll(teamRepository.findTeamsByCaptainOrMember1OrMember2(s,s,s));
                }
                tList=new ArrayList(new LinkedHashSet(tList));//去重
            }else tList=teamRepository.findAll();//若均为空，返回全部队伍
        }
        for(int i=0;i<tList.size();i++){
            Team team=(Team)tList.get(i);
            Map m=CommonMethod.objectToMap(team);
            m.put("captain",CommonMethod.objectToMap(team.getCaptain()));
            m.put("member1",CommonMethod.objectToMap(team.getMember1()));
            m.put("member2",CommonMethod.objectToMap(team.getMember2()));
            dataList.add(m);
        }
        return CommonMethod.getReturnData(dataList);
    }

    @PostMapping("/getTeamInfo")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public DataResponse getTeamInfo(@Valid @RequestBody DataRequest dataRequest){
        Integer id=dataRequest.getInteger("id");
        if(id==null)return CommonMethod.getReturnMessageError("队伍将在成功提交后保存");
        Team team=teamRepository.findById(id).get();//不能用getById，否则是Object
        Map m=CommonMethod.objectToMap(team);
        m.put("captain",team.getCaptain().getId());
        m.put("member1",team.getMember1().getId());
        m.put("member2",team.getMember2().getId());
        return CommonMethod.getReturnData(m);
    }

    @PostMapping("/submitTeam")
    @PreAuthorize("hasRole('USER') ")
    public DataResponse submitTeam(@Valid @RequestBody DataRequest dataRequest){
        Map form=dataRequest.getMap("form");
        Integer id=(Integer)form.get("id");
        Team team;
        try{team=teamRepository.getById(id);}
        catch (Exception e){team=new Team();}
        team.setChinese((String)form.get("chinese"));
        team.setEnglish((String)form.get("english"));
        team.setActive((Boolean)form.get("active"));
        team.setCaptain(studentRepository.getById((Integer)form.get("captain")));
        team.setMember1(studentRepository.getById((Integer)form.get("member1")));
        team.setMember2(studentRepository.getById((Integer)form.get("member2")));
        teamRepository.save(team);
        return CommonMethod.getReturnMessageOK();
    }

    @PostMapping("/getStudentList")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public DataResponse getStudentList(@Valid @RequestBody DataRequest dataRequest){
        List list=studentRepository.findAll();
        List dataList=new ArrayList();
        for(int i=0;i<list.size();i++)
            dataList.add(CommonMethod.objectToMap(list.get(i)));
        return CommonMethod.getReturnData(dataList);
    }

    @PostMapping("/getAccess")
    @PreAuthorize("hasRole('USER') ")
    public DataResponse getAccess(@Valid @RequestBody DataRequest dataRequest){
        Integer id=dataRequest.getInteger("id");
        if(id==null)return CommonMethod.getReturnMessageError("队伍未创建，队伍保存后再次编辑可参与排位");
        Team team=teamRepository.getById(id);
        Boolean active=team.getActive();
        if(active){team.setActive(false);teamRepository.save(team);return CommonMethod.getReturnMessageOK();}
        List sList=new ArrayList();
        sList.add(team.getCaptain());sList.add(team.getMember1());sList.add(team.getMember2());
        List tList=new ArrayList();
        for (Object o : sList) {
            Student s = (Student) o;
            tList.addAll(teamRepository.findTeamsByCaptainOrMember1OrMember2(s, s, s));
        }
        tList=new ArrayList(new LinkedHashSet(tList));
        for(Object o:tList){
            Team t=(Team)o;
            if(t.getActive())
                return CommonMethod.getReturnMessageError("队员所在队伍\""+t.getChinese()+" "+t.getEnglish()+"\"已经参与本赛季排位！");
        }
        team.setActive(true);teamRepository.save(team);
        return CommonMethod.getReturnMessageOK();
    }
}
