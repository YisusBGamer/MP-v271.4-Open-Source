package net.swordie.ms.client.character.cards;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "monsterbookcards")
public class MonsterBookCard implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "bookid")
    private int bookid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookid() {
        return bookid;
    }

    public void setBookid(int bookid) {
        this.bookid = bookid;
    }

}