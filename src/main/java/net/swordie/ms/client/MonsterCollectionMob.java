package net.swordie.ms.client;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "monster_collection_mobs")
public class MonsterCollectionMob implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "collectionid")
    private int collectionid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCollectionid() {
        return collectionid;
    }

    public void setCollectionid(int collectionid) {
        this.collectionid = collectionid;
    }

}