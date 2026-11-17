package net.swordie.ms.client.trunk;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "employeetrunk")
public class EmployeeTrunk implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}