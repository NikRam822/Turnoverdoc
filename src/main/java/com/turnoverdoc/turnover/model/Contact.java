package com.turnoverdoc.turnover.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "contacts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "messanger")
    private String messenger;

    @OneToOne(mappedBy = "contact")
    private Order order;

    public Contact(String phone, String email, String messenger, Order addedOrder) {
        this.phone = phone;
        this.email = email;
        this.messenger = messenger;
        this.order = addedOrder;
    }
}
