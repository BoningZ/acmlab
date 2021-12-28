package org.fatmansoft.teach.controllers;

import org.fatmansoft.teach.models.*;
import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.payload.response.DataResponse;
import org.fatmansoft.teach.repository.*;
import org.fatmansoft.teach.util.CommonMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/interview")
public class InterviewController {
    @Autowired
    MarkRepository markRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    IntervieweeRepository intervieweeRepository;
    @Autowired
    UserTypeRepository userTypeRepository;
    @Autowired
    StudentRepository studentRepository;

    @PostMapping("/submitMarks")
    @PreAuthorize("hasRole('USER') ")
    public DataResponse submitMarks(@Valid @RequestBody DataRequest dataRequest){
        Integer userId= CommonMethod.getUserId();
        User user=userRepository.findByUserId(userId).get();
        Student student=user.getStudent();
        List list=dataRequest.getList("marks");
        for(int i=0;i<list.size();i++){
            Map m=(Map)list.get(i);
            Interviewee interviewee=intervieweeRepository.getById((Integer)m.get("iid"));
            Integer mark=(Integer)m.get("mark");
            if(mark==0)continue;
            String remark=(String)m.get("remark");
            Mark mrk;
            try{mrk=markRepository.findByStudentAndInterviewee(student,interviewee).get();
                mrk.setMark(mark); mrk.setRemark(remark);}
            catch (NoSuchElementException e){mrk=new Mark(student,interviewee,mark,remark);}
            markRepository.save(mrk);
        }
        return CommonMethod.getReturnMessageOK();
    }

    @PostMapping("/getMarks")
    @PreAuthorize("hasRole('USER') ")
    public DataResponse getMarks(@Valid @RequestBody DataRequest dataRequest){
        Integer userId= CommonMethod.getUserId();
        User user=userRepository.findByUserId(userId).get();
        Student student=user.getStudent();
        Integer grade=dataRequest.getInteger("grade");

        List list;
        if(dataRequest.getString("sid")!=null) {
            list =new ArrayList();
            try {list.add(intervieweeRepository.getBySid(dataRequest.getString("sid")).get());}
            catch (NoSuchElementException e){return CommonMethod.getReturnMessageError("该学号未被注册为面试者！");}
        }
        else if(grade!=null)list=intervieweeRepository.getAllByGrade(grade);
        else list=intervieweeRepository.findAll();

        List dataList=new ArrayList();
        for(int i=0;i<list.size();i++){
            Interviewee interviewee=(Interviewee)list.get(i);
            Map m=new HashMap();
            m.put("iid",interviewee.getId());
            m.put("sid",interviewee.getSid());
            m.put("name",interviewee.getName());
            m.put("introduction",interviewee.getIntroduction());
            String remark=""; Integer mark=null;
            if(markRepository.existsByStudentAndInterviewee(student,interviewee)){
                Mark mrk=markRepository.findByStudentAndInterviewee(student,interviewee).get();
                remark=mrk.getRemark(); mark= mrk.getMark();
            }
            m.put("mark",mark); m.put("remark",remark);
            dataList.add(m);
        }
        return CommonMethod.getReturnData(dataList);
    }

    @PostMapping("/getInterviewees")
    @PreAuthorize("hasRole('ADMIN') ")
    public DataResponse getInterviewees(@Valid @RequestBody DataRequest dataRequest){
        List list;
        Integer grade=dataRequest.getInteger("grade");
        if(dataRequest.getString("sid")!=null) {
            list =new ArrayList();
            try {list.add(intervieweeRepository.getBySid(dataRequest.getString("sid")).get());}
            catch (NoSuchElementException e){return CommonMethod.getReturnMessageError("该学号未被注册为面试者！");}
        }
        else if(grade!=null)list=intervieweeRepository.getAllByGrade(grade);
        else list=intervieweeRepository.findAll();
        List dataList=new ArrayList();
        for(int i=0;i<list.size();i++){
            Interviewee interviewee=(Interviewee)list.get(i);
            Map m=new HashMap();
            m.put("iid",interviewee.getId());
            m.put("sid",interviewee.getSid());
            m.put("name",interviewee.getName());
            m.put("introduction",interviewee.getIntroduction());
            List detail=new ArrayList();
            Integer sumMark=0;
            List l=markRepository.findMarksByInterviewee(interviewee);
            for(int j=0;j<l.size();j++){
                Map m1=new HashMap();
                Mark mrk=(Mark)l.get(j);
                Student student=mrk.getStudent();
                sumMark+=mrk.getMark();
                m1.put("mark",mrk.getMark());
                m1.put("remark",mrk.getRemark());
                m1.put("name",student.getXing()+student.getMing());
                detail.add(m1);
            }
            if(l.isEmpty())m.put("mark",0);
            else m.put("mark",(double)sumMark/l.size());
            m.put("detail",detail);
            dataList.add(m);
        }
        return CommonMethod.getReturnData(dataList);
    }

    @Transactional
    @PostMapping("/formalize")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse formalize(@Valid @RequestBody DataRequest dataRequest){
        Interviewee interviewee=intervieweeRepository.getById(dataRequest.getInteger("iid"));

        markRepository.deleteMarksByInterviewee(interviewee);

        Student student=new Student(interviewee);
        studentRepository.save(student);

        User u=userRepository.findByInterviewee(interviewee).get();
        u.setInterviewee(null);
        u.setUserType(userTypeRepository.findByName(EUserType.ROLE_USER));
        u.setStudent(student);
        userRepository.save(u);

        intervieweeRepository.delete(interviewee);

        return CommonMethod.getReturnMessageOK();
    }
}
