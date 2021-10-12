package fr.pantheonsorbonne.ufr27.miage.domain;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;

@Entity
public class Ticket {
    @EmbeddedId
    private TicketId id;

    @Lob
    @Column(name = "fare", nullable = false)
    private Long fare;

    @Column(name = "currency", nullable = false, length = 3)
    private String currency;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getFare() {
        return fare;
    }

    public void setFare(Long fare) {
        this.fare = fare;
    }

    public TicketId getId() {
        return id;
    }

    public void setId(TicketId id) {
        this.id = id;
    }
}