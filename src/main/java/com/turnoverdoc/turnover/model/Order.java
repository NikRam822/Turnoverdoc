package com.turnoverdoc.turnover.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "passport_path")
    private String passportPath;

    @Column(name = "p45_path")
    private String p45Path;

    @Column(name = "p60_path")
    private String p60Path;

    @Column(name = "p80_path")
    private String p80Path;

    @Column(name = "contract_path")
    private String contractPath;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

    @OneToOne
    @JoinColumn(name = "contact_id", referencedColumnName = "id")
    private Contact contact;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id")
    @NotFound(action = NotFoundAction.IGNORE)
    private User user;

    @DateTimeFormat(pattern = "dd-MM-yyyy hh:mm:ss")
    @Column(name = "date")
    private Timestamp timestampDate;


    public Order(String passportPath, String p45Path, String p60Path, String p80Path, String contractPath) {
        this.passportPath = passportPath;
        this.p45Path = p45Path;
        this.p60Path = p60Path;
        this.p80Path = p80Path;
        this.contractPath = contractPath;
    }
}
