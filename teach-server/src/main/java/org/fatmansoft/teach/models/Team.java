package org.fatmansoft.teach.models;

import javax.persistence.*;

@Entity
@Table(	name = "team",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "id"),
        })
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String chinese;
    private String english;

    @ManyToOne
    @JoinColumn(name = "captain")
    private Student captain;

    @ManyToOne
    @JoinColumn(name = "member1")
    private Student member1;

    @ManyToOne
    @JoinColumn(name = "member2")
    private Student member2;

    private Boolean active;//是否参与排位


    public String getFullName(){return chinese+" "+english;}
    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChinese() {
        return chinese;
    }

    public void setChinese(String chinese) {
        this.chinese = chinese;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public Student getCaptain() {
        return captain;
    }

    public void setCaptain(Student captain) {
        this.captain = captain;
    }

    public Student getMember1() {
        return member1;
    }

    public void setMember1(Student member1) {
        this.member1 = member1;
    }

    public Student getMember2() {
        return member2;
    }

    public void setMember2(Student member2) {
        this.member2 = member2;
    }
}
