package org.fatmansoft.teach.models;

import javax.persistence.*;

@Entity
@Table(	name = "application",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "id"),
        })
public class Application {//志愿填报
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Boolean isPersonal;

    @ManyToOne
    @JoinColumn(name="teamId")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "studentId")
    private Student student;

    private Integer appList;//状压
    private Integer season;

    public Application(Team team, Integer season) {
        this.team = team;
        this.season = season;
    }

    public Application() {}

    public Integer getSeason() {
        return season;
    }

    public void setSeason(Integer season) {
        this.season = season;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getPersonal() {
        return isPersonal;
    }

    public void setPersonal(Boolean personal) {
        isPersonal = personal;
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

    public Integer getAppList() {
        return appList;
    }

    public void setAppList(Integer appList) {
        this.appList = appList;
    }




}
