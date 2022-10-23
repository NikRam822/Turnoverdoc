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

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

    @OneToOne(mappedBy = "order")
    private Contact contact;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;


    public Order(String passportPath, String p45Path, String p60Path, String p80Path, String contractPath) {
        this.passportPath = passportPath;
        this.p45Path = p45Path;
        this.p60Path = p60Path;
        this.p80Path = p80Path;
        this.contractPath = contractPath;
    }

    public void setPathFile(Order order, String fileName, String filePath) {
        switch (fileName) {
            case "p45":
                order.setP45Path(filePath);
                break;
            case "p60":
                order.setP60Path(filePath);
                break;
            case "p80":
                order.setP80Path(filePath);
                break;
            case "passport":
                order.setPassportPath(filePath);
                break;
            case "contract":
                order.setContractPath(filePath);
                break;
        }

    }
}
