package net.swordie.ms.client.trunk;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "eventnametag")
public class EventNameTag implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "charid")
    private int charid;

    @Column(name = "activeRed")
    private int activered;

    @Column(name = "activeBlue")
    private int activeblue;

    @Column(name = "activeYellow")
    private int activeyellow;

    @Column(name = "activeGreen")
    private int activegreen;

    @Column(name = "activePurple")
    private int activepurple;

    @Column(name = "sRed")
    private String sred;

    @Column(name = "sBlue")
    private String sblue;

    @Column(name = "sYellow")
    private String syellow;

    @Column(name = "sGreen")
    private String sgreen;

    @Column(name = "sPurple")
    private String spurple;

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

    public int getActivered() {
        return activered;
    }

    public void setActivered(int activered) {
        this.activered = activered;
    }

    public int getActiveblue() {
        return activeblue;
    }

    public void setActiveblue(int activeblue) {
        this.activeblue = activeblue;
    }

    public int getActiveyellow() {
        return activeyellow;
    }

    public void setActiveyellow(int activeyellow) {
        this.activeyellow = activeyellow;
    }

    public int getActivegreen() {
        return activegreen;
    }

    public void setActivegreen(int activegreen) {
        this.activegreen = activegreen;
    }

    public int getActivepurple() {
        return activepurple;
    }

    public void setActivepurple(int activepurple) {
        this.activepurple = activepurple;
    }

    public String getSred() {
        return sred;
    }

    public void setSred(String sred) {
        this.sred = sred;
    }

    public String getSblue() {
        return sblue;
    }

    public void setSblue(String sblue) {
        this.sblue = sblue;
    }

    public String getSyellow() {
        return syellow;
    }

    public void setSyellow(String syellow) {
        this.syellow = syellow;
    }

    public String getSgreen() {
        return sgreen;
    }

    public void setSgreen(String sgreen) {
        this.sgreen = sgreen;
    }

    public String getSpurple() {
        return spurple;
    }

    public void setSpurple(String spurple) {
        this.spurple = spurple;
    }

}