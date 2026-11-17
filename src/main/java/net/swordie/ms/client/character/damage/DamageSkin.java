package net.swordie.ms.client.character.damage;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "damageskins")
public class DamageSkin implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "charid")
    private int charid;

    @Column(name = "damageskinid")
    private int damageskinid;

    @Column(name = "itemid")
    private int itemid;

    @Column(name = "notsave")
    private int notsave;

    @Column(name = "activatetime")
    private java.time.LocalDateTime activatetime;

    @Column(name = "description")
    private String description;

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

    public int getDamageskinid() {
        return damageskinid;
    }

    public void setDamageskinid(int damageskinid) {
        this.damageskinid = damageskinid;
    }

    public int getItemid() {
        return itemid;
    }

    public void setItemid(int itemid) {
        this.itemid = itemid;
    }

    public int getNotsave() {
        return notsave;
    }

    public void setNotsave(int notsave) {
        this.notsave = notsave;
    }

    public java.time.LocalDateTime getActivatetime() {
        return activatetime;
    }

    public void setActivatetime(java.time.LocalDateTime activatetime) {
        this.activatetime = activatetime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}