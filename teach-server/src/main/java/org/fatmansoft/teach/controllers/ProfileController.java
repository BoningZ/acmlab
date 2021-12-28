package org.fatmansoft.teach.controllers;

import org.fatmansoft.teach.models.*;
import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.payload.response.DataResponse;
import org.fatmansoft.teach.repository.IntervieweeRepository;
import org.fatmansoft.teach.repository.StudentRepository;
import org.fatmansoft.teach.repository.TeacherRepository;
import org.fatmansoft.teach.repository.UserRepository;
import org.fatmansoft.teach.util.CommonMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.xml.ws.spi.http.HttpContext;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/teach")
public class ProfileController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    IntervieweeRepository intervieweeRepository;

    private String iconURL;

    @PostMapping("/getProfile")
    @PreAuthorize("hasRole('USER') or hasRole('INTERVIEWEE') or hasRole('ADMIN')")
    public DataResponse getProfile(@Valid @RequestBody DataRequest dataRequest){
        Integer userId= CommonMethod.getUserId();
        User user=userRepository.findByUserId(userId).get();
        Map m;
        if(user.getUserType().getName()== EUserType.ROLE_INTERVIEWEE)
            m=CommonMethod.objectToMap(user.getInterviewee());
        else if(user.getUserType().getName()== EUserType.ROLE_USER)
            m=CommonMethod.objectToMap(user.getStudent());
        else m=new HashMap();
        m.put("access",user.getUserType().getName()== EUserType.ROLE_ADMIN);
        return CommonMethod.getReturnData(m);
    }

    @PostMapping("/submitProfile")
    @PreAuthorize("hasRole('USER') or hasRole('INTERVIEWEE')")
    public DataResponse submitProfile(@Valid @RequestBody DataRequest dataRequest){
        Integer userId= CommonMethod.getUserId();
        User user;
        Optional<User> tmp = userRepository.findByUserId(userId);
        user = tmp.get();
        if(user.getUserType().getName()== EUserType.ROLE_INTERVIEWEE){
            Interviewee interviewee=user.getInterviewee();
            interviewee.setEmail(dataRequest.getString("email"));
            interviewee.setIntroduction(dataRequest.getString("introduction"));
            if(iconURL!=null){interviewee.setIcon(iconURL);iconURL=null;}
            intervieweeRepository.save(interviewee);
        }else{
            Student student=user.getStudent();
            student.setTShirt(dataRequest.getString("TShirt"));
            student.setCollege(dataRequest.getString("college"));
            student.setEmail(dataRequest.getString("email"));
            student.setFirstName(dataRequest.getString("firstName"));
            student.setLastName(dataRequest.getString("lastName"));
            student.setXing(dataRequest.getString("xing"));
            student.setMing(dataRequest.getString("ming"));
            student.setGrade(dataRequest.getInteger("grade"));
            student.setIntroduction(dataRequest.getString("introduction"));
            student.setProvided(dataRequest.getBoolean("provided"));
            student.setMajor(dataRequest.getString("major"));
            student.setSex(dataRequest.getBoolean("sex"));
            student.setSid(dataRequest.getString("sid"));
            student.setTel(dataRequest.getString("tel"));
            student.setIdCard(dataRequest.getString("idCard"));
            if(iconURL!=null){student.setIcon(iconURL);iconURL=null;}
            studentRepository.save(student);
        }
        return CommonMethod.getReturnMessageOK();
    }

    SimpleDateFormat sdf = new SimpleDateFormat("/yyyy/MM/dd/");
    @Value("${web.upload-path}")
    private String uploadPath;
    @Value("${server.port}")
    private String port;

    @PostMapping("/submitIcon")
    //@PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('INTERVIEWEE')")
    public DataResponse importData(MultipartFile file,HttpServletRequest req) throws IOException {
        InetAddress address = InetAddress.getLocalHost();

        String format = sdf.format(new Date());
        String realPath = uploadPath+"avatars/" /*+ format*/;
        File folder = new File(realPath);
        if (!folder.exists()) folder.mkdirs();
        String oldName = file.getOriginalFilename();
        String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."));
        file.transferTo(new File(folder,newName));//保存头像
        String url = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + "/upload" + format + newName;
        //System.out.println(url);
        //iconURL="http://"+address.getHostAddress() +":"+port+"/image/avatars/"+newName;
        iconURL="/api/download/getFile?fileFullName=avatars/"+newName;
        //System.out.println(iconURL);

        return CommonMethod.getReturnMessageOK();
    }

}

