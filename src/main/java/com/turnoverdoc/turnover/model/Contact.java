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

    @Column(name = "messanger")
    private String messenger;

    @OneToOne(mappedBy = "contact")
    private Order order;

    @Column(name = "messenger_type")
    private MessengerType messengerType;

    public Contact(String phone, String messenger, Order addedOrder, MessengerType messengerType) {
        this.phone = phone;
        this.messenger = messenger;
        this.order = addedOrder;
        this.messengerType = messengerType;
    }
}
