package net.swordie.ms.client.inventory;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "unseenequips")
public class UnseenEquip implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "alid")
    private int alid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAlid() {
        return alid;
    }

    public void setAlid(int alid) {
        this.alid = alid;
    }

}