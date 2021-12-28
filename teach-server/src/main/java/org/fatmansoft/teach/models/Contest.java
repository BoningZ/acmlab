package org.fatmansoft.teach.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Locale;

@Entity
@Table(	name = "contest",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "id"),
        })
public class Contest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="contestTypeId")
    private ContestType contestType;

    private Integer quota;//名额
    private Integer season;//赛季，同个赛季每支队伍只能参加两次同种比赛
    private Integer rankInSeason;//在当前赛季中的编号

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date contestDate;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date startReg;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date endReg;

    private String addr;
    private String qq;
    private String demand;//特殊要求 比如核酸、赛氪

    public String toString(){
        return contestType.toString()+" "+season+" "+addr;
    }

    public String getDemand() {
        return demand;
    }

    public void setDemand(String demand) {
        this.demand = demand;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ContestType getContestType() {
        return contestType;
    }

    public void setContestType(ContestType contestType) {
        this.contestType = contestType;
    }

    public Integer getQuota() {
        return quota;
    }

    public void setQuota(Integer quota) {
        this.quota = quota;
    }

    public Integer getSeason() {
        return season;
    }

    public void setSeason(Integer season) {
        this.season = season;
    }

    public Integer getRankInSeason() {
        return rankInSeason;
    }

    public void setRankInSeason(Integer rankInSeason) {
        this.rankInSeason = rankInSeason;
    }

    public Date getContestDate() {
        return contestDate;
    }

    public void setContestDate(Date contestDate) {
        this.contestDate = contestDate;
    }

    public Date getStartReg() {
        return startReg;
    }

    public void setStartReg(Date startReg) {
        this.startReg = startReg;
    }

    public Date getEndReg() {
        return endReg;
    }

    public void setEndReg(Date endReg) {
        this.endReg = endReg;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }
}
