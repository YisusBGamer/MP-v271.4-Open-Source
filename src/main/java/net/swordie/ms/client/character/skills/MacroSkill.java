package net.swordie.ms.client.character.skills;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "macroskills")
public class MacroSkill implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "ordercol")
    private int ordercol;

    @Column(name = "skillid")
    private int skillid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrdercol() {
        return ordercol;
    }

    public void setOrdercol(int ordercol) {
        this.ordercol = ordercol;
    }

    public int getSkillid() {
        return skillid;
    }

    public void setSkillid(int skillid) {
        this.skillid = skillid;
    }

}