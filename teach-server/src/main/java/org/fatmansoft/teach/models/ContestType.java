package org.fatmansoft.teach.models;

import javax.persistence.*;

@Entity
@Table(	name = "contest_type",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "id"),
        })
public class ContestType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;//e.g. CCPC ICPC 蓝桥杯 华为软挑
    private Boolean isTeam;

    public String toString(){return this.name;}

    public Boolean getTeam() {
        return isTeam;
    }

    public void setTeam(Boolean team) {
        isTeam = team;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
