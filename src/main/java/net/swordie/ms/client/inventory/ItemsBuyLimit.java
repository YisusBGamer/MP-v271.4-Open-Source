package net.swordie.ms.client.inventory;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "itemsbuylimit")
public class ItemsBuyLimit implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "shopitemid")
    private long shopitemid;

    @Column(name = "charid")
    private int charid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getShopitemid() {
        return shopitemid;
    }

    public void setShopitemid(long shopitemid) {
        this.shopitemid = shopitemid;
    }

    public int getCharid() {
        return charid;
    }

    public void setCharid(int charid) {
        this.charid = charid;
    }

}