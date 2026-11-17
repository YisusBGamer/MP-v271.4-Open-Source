package net.swordie.ms.client.character;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "hexastats")
public class HexaStat implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "charid")
    private int charid;

    @Column(name = "index")
    private int index;

    @Column(name = "coreid")
    private int coreid;

    @Column(name = "stat0")
    private int stat0;

    @Column(name = "level0")
    private int level0;

    @Column(name = "stat1")
    private int stat1;

    @Column(name = "level1")
    private int level1;

    @Column(name = "stat2")
    private int stat2;

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

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getCoreid() {
        return coreid;
    }

    public void setCoreid(int coreid) {
        this.coreid = coreid;
    }

    public int getStat0() {
        return stat0;
    }

    public void setStat0(int stat0) {
        this.stat0 = stat0;
    }

    public int getLevel0() {
        return level0;
    }

    public void setLevel0(int level0) {
        this.level0 = level0;
    }

    public int getStat1() {
        return stat1;
    }

    public void setStat1(int stat1) {
        this.stat1 = stat1;
    }

    public int getLevel1() {
        return level1;
    }

    public void setLevel1(int level1) {
        this.level1 = level1;
    }

    public int getStat2() {
        return stat2;
    }

    public void setStat2(int stat2) {
        this.stat2 = stat2;
    }

}