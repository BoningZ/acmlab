package org.fatmansoft.teach.models;

import javax.persistence.*;

@Entity
@Table(	name = "process",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "id"),
        })
public class Process {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="teamId")
    private Team team;

    @ManyToOne
    @JoinColumn(name="studentId")
    private Student student;

    @ManyToOne
    @JoinColumn(name="contestId")
    private Contest contest;

    private String transport;
    private String hotel;

    private String feelings;//感想
    private Integer award;//1234，金银铜铁

    private Integer process;//状压 已注册 已完成特殊要求 已订酒店 已买车票 已上传成果 已提交感想 已提交发票 已报销
    //注册-> 缴费-> 特殊要求-> 机酒-> 奖项-> 文件上传（发票 奖状 感想）-> 报销-> 完成

    private String certificate;
    private String receipt;

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public Process() { }

    public Process(Team team, Contest contest) {
        this.team = team;
        this.contest = contest;
        this.process=0;
    }

    public Integer getId() {
        return id;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    public Integer getAward() {
        return award;
    }

    public void setAward(Integer award) {
        this.award = award;
    }

    public String getFeelings() {
        return feelings;
    }

    public void setFeelings(String feelings) {
        this.feelings = feelings;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Contest getContest() {
        return contest;
    }

    public void setContest(Contest contest) {
        this.contest = contest;
    }

    public Integer getProcess() {
        return process;
    }

    public void setProcess(Integer process) {
        this.process = process;
    }
}
