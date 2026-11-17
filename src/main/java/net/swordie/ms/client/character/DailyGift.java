package net.swordie.ms.client.character;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "dailygift")
public class DailyGift implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "accountID")
    private int accountid;

    @Column(name = "date")
    private long date;

    @Column(name = "dateComplete")
    private int datecomplete;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccountid() {
        return accountid;
    }

    public void setAccountid(int accountid) {
        this.accountid = accountid;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getDatecomplete() {
        return datecomplete;
    }

    public void setDatecomplete(int datecomplete) {
        this.datecomplete = datecomplete;
    }

}