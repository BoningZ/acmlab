package org.fatmansoft.teach.controllers;

import java.awt.*;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.fatmansoft.teach.models.*;
import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.payload.response.DataResponse;
import org.fatmansoft.teach.repository.*;
import org.fatmansoft.teach.util.CommonMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.fatmansoft.teach.payload.request.LoginRequest;
import org.fatmansoft.teach.payload.request.SignupRequest;
import org.fatmansoft.teach.payload.response.JwtResponse;
import org.fatmansoft.teach.payload.response.MessageResponse;
import org.fatmansoft.teach.security.jwt.JwtUtils;
import org.fatmansoft.teach.security.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    UserTypeRepository userTypeRepository;

    @Autowired
    IntervieweeRepository intervieweeRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        User user=userRepository.findByUserName(userDetails.getUsername()).get();
        user.setLastLoginTime(new Date());
        user.setLoginCount(user.getLoginCount()+1);
        userRepository.save(user);



        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                roles.get(0)));
    }

    @PostMapping("/signup")
    public DataResponse registerUser(@Valid @RequestBody DataRequest signUpRequest) {
        if (userRepository.existsByUserName(signUpRequest.getString("username")))
            return CommonMethod.getReturnMessageError("该用户名已被占用！");
        if(intervieweeRepository.existsBySid(signUpRequest.getString("sid")))
            return CommonMethod.getReturnMessageError("该学号已被注册，若非本人操作请联系管理员！");

        // Create new user's account
        User user = new User(signUpRequest.getString("username"),
                encoder.encode(signUpRequest.getString("password")));
        user.setUserType(userTypeRepository.findByName(EUserType.ROLE_INTERVIEWEE));

        Interviewee interviewee=new Interviewee();
        interviewee.setGrade(signUpRequest.getInteger("grade"));
        interviewee.setName(signUpRequest.getString("name"));
        interviewee.setSid(signUpRequest.getString("sid"));
        user.setInterviewee(interviewee);




        intervieweeRepository.save(interviewee);
        userRepository.save(user);


        return CommonMethod.getReturnMessageOK();
    }

    @PostMapping("/changePassword")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('INTERVIEWEE')")
    public DataResponse changePassword(@Valid @RequestBody DataRequest dataRequest) {
        Integer userId= CommonMethod.getUserId();
        if(userId == null)
            return CommonMethod.getReturnMessageError("lang.comm.loginError");
        String oldPassword = dataRequest.getString("oldPassword");
        String newPassword = dataRequest.getString("newPassword");
        User u = userRepository.findById(userId).get();
        if(!encoder.matches(oldPassword, u.getPassword())) {
            return CommonMethod.getReturnMessageError("原密码错误！");
        }
        u.setPassword(encoder.encode(newPassword));
        userRepository.save(u);
        return CommonMethod.getReturnMessageOK();
    }


}
