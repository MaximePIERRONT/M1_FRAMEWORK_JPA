package fr.pantheonsorbonne.ufr27.miage.domain;

import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class TicketId implements Serializable {
    private static final long serialVersionUID = -6358958727466566034L;
    @Column(name = "idTrain", nullable = false)
    private Integer idTrain;
    @Column(name = "idPassenger", nullable = false)
    private Integer idPassenger;

    public Integer getIdPassenger() {
        return idPassenger;
    }

    public void setIdPassenger(Integer idPassenger) {
        this.idPassenger = idPassenger;
    }

    public Integer getIdTrain() {
        return idTrain;
    }

    public void setIdTrain(Integer idTrain) {
        this.idTrain = idTrain;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPassenger, idTrain);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TicketId entity = (TicketId) o;
        return Objects.equals(this.idPassenger, entity.idPassenger) &&
                Objects.equals(this.idTrain, entity.idTrain);
    }
}