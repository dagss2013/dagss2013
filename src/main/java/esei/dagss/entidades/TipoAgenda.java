/*
 Práctica Java EE 7, DAGSS 2013/14 (ESEI, U. de Vigo)
 */
package esei.dagss.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Entity
public class TipoAgenda implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Size(min = 0, max = 50)
    @Column(length = 50, nullable = false)
    String descripcion;

    @Enumerated(EnumType.STRING)
    TipoTurno tipoTurno;

    @Temporal(TemporalType.TIME)
    Date horaInico;

    @Temporal(TemporalType.TIME)
    Date horaFin;

    @Min(value = 1)
    Integer duracionCita;

    @Version
    Long version;

    public TipoAgenda() {
    }

    public TipoAgenda(String descripcion, TipoTurno tipoturno, Date horaInico, Date horaFin, Integer duracionCita) {
        this.descripcion = descripcion;
        this.tipoTurno = tipoturno;
        this.horaInico = horaInico;
        this.horaFin = horaFin;
        this.duracionCita = duracionCita;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TipoTurno getTipoTurno() {
        return tipoTurno;
    }

    public void setTipoTurno(TipoTurno turno) {
        this.tipoTurno = turno;
    }

    public Date getHoraInico() {
        return horaInico;
    }

    public void setHoraInico(Date horaInico) {
        this.horaInico = horaInico;
    }

    public Date getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Date horaFin) {
        this.horaFin = horaFin;
    }

    public Integer getDuracionCita() {
        return duracionCita;
    }

    public void setDuracionCita(Integer duracionCita) {
        this.duracionCita = duracionCita;
    }

}
