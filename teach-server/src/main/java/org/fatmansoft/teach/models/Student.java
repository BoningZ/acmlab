package org.fatmansoft.teach.models;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(	name = "student",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "id"),
        })
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Email
    private String email;

    private String sid;
    private String xing;
    private String ming;
    private String firstName;
    private String lastName;
    private Boolean sex;//1男 0女
    private String tel;
    private Integer grade;//e.g. 2020
    private String college;
    private String major;
    private String TShirt;
    private Boolean provided;
    private String idCard;

    private String introduction;
    private String icon;

    public Student(){}
    public Student(Interviewee interviewee){
        this.setEmail(interviewee.getEmail());
        this.setGrade(interviewee.getGrade());
        this.setIcon(interviewee.getIcon());
        this.setIntroduction(interviewee.getIntroduction());
        this.setSid(interviewee.getSid());
    }
    public String getFullName(){
        return xing+ming+"  "+lastName+" "+firstName;
    }
    public String getChineseName(){
        return xing+ming;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Boolean participateIn(Team team){ return (team.getCaptain().equals(this)||team.getMember1().equals(this)||team.getMember2().equals(this)); }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getXing() {
        return xing;
    }

    public void setXing(String xing) {
        this.xing = xing;
    }

    public String getMing() {
        return ming;
    }

    public void setMing(String ming) {
        this.ming = ming;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getTShirt() {
        return TShirt;
    }

    public void setTShirt(String TShirt) {
        this.TShirt = TShirt;
    }

    public Boolean getProvided() {
        return provided;
    }

    public void setProvided(Boolean provided) {
        this.provided = provided;
    }
}
