package com.turnoverdoc.turnover.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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

    @Column(name = "status")
    private OrderStatus status;

    @OneToOne(mappedBy = "order")
    private Contact contact;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;
}
