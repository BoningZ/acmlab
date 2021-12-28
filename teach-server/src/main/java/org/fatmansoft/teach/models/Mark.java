package org.fatmansoft.teach.models;


import javax.persistence.*;

@Entity
@Table(	name = "mark",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "id"),
        })
public class Mark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "studentId")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "intervieweeId")
    private Interviewee interviewee;

    private Integer mark;
    private String remark;//点评 记录

    public Mark(Student student, Interviewee interviewee, Integer mark, String remark) {
        this.student = student;
        this.interviewee = interviewee;
        this.mark = mark;
        this.remark = remark;
    }
    public Mark(){}

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

    public Interviewee getInterviewee() {
        return interviewee;
    }

    public void setInterviewee(Interviewee interviewee) {
        this.interviewee = interviewee;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
