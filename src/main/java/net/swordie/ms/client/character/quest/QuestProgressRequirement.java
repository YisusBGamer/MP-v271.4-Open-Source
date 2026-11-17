package net.swordie.ms.client.character.quest;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "questprogressrequirements")
public class QuestProgressRequirement implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "charid")
    private int charid;

    @Column(name = "orderNum")
    private int ordernum;

    @Column(name = "progresstype")
    private String progresstype;

    @Column(name = "questid")
    private long questid;

    @Column(name = "unitid")
    private int unitid;

    @Column(name = "requiredcount")
    private int requiredcount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCharid() {
        return charid;
    }

    public void setCharid(int charid) {
        this.charid = charid;
    }

    public int getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(int ordernum) {
        this.ordernum = ordernum;
    }

    public String getProgresstype() {
        return progresstype;
    }

    public void setProgresstype(String progresstype) {
        this.progresstype = progresstype;
    }

    public long getQuestid() {
        return questid;
    }

    public void setQuestid(long questid) {
        this.questid = questid;
    }

    public int getUnitid() {
        return unitid;
    }

    public void setUnitid(int unitid) {
        this.unitid = unitid;
    }

    public int getRequiredcount() {
        return requiredcount;
    }

    public void setRequiredcount(int requiredcount) {
        this.requiredcount = requiredcount;
    }

}