package org.fatmansoft.teach.models;


import javax.persistence.*;

@Entity
@Table(	name = "ranking",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "id"),
        })
public class Ranking {//排位分数
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


    private Integer penalty;//罚时
    private Integer solved;//状压 按题目

    private Integer season;
    private Integer countInSeason;

    public Integer getScore(){
        int cnt=0;
        for(int i=0;i<=20;i++)cnt+=(solved&(1<<i))>0?1:0;
        return cnt;
    }

    public Ranking(){}

    public Ranking(Team team, Integer solved, Integer season, Integer countInSeason,Integer penalty) {
        this.team = team;
        this.solved = solved;
        this.season = season;
        this.countInSeason = countInSeason;
        this.penalty=penalty;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }



    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Integer getPenalty() {
        return penalty;
    }

    public void setPenalty(Integer penalty) {
        this.penalty = penalty;
    }



    public Integer getSolved() {
        return solved;
    }

    public void setSolved(Integer solved) {
        this.solved = solved;
    }

    public Integer getSeason() {
        return season;
    }

    public void setSeason(Integer season) {
        this.season = season;
    }

    public Integer getCountInSeason() {
        return countInSeason;
    }

    public void setCountInSeason(Integer countInSeason) {
        this.countInSeason = countInSeason;
    }


}
