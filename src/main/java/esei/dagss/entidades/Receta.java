/*
 Práctica Java EE 7, DAGSS 2013/14 (ESEI, U. de Vigo)
 */
package esei.dagss.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.inject.Inject;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.validation.constraints.Min;
import javax.validation.constraints.Null;

@Entity
public class Receta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    Prescripcion prescripcion;

    @Min(1)
    Integer cantidad;

    @Temporal(javax.persistence.TemporalType.DATE)
    Date inicioValidez;

    @Temporal(javax.persistence.TemporalType.DATE)
    Date finValidez;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    EstadoReceta estadoReceta;

    @ManyToOne(optional = true)
    Farmacia farmacia;
    
    public Receta() {
    }

    public Receta(Prescripcion prescripcion, Integer cantidad, Date inicioValidez, Date finValidez, EstadoReceta estadoReceta) {
        this(prescripcion, cantidad, inicioValidez, finValidez, estadoReceta, null);
    }

    public Receta(Prescripcion prescripcion, Integer cantidad, Date inicioValidez, Date finValidez, EstadoReceta estadoReceta, Farmacia farmacia) {
        this.prescripcion = prescripcion;
        this.cantidad = cantidad;
        this.inicioValidez = inicioValidez;
        this.finValidez = finValidez;
        this.estadoReceta = estadoReceta;
        this.farmacia = farmacia;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Prescripcion getPrescripcion() {
        return prescripcion;
    }

    public void setPrescripcion(Prescripcion prescripcion) {
        this.prescripcion = prescripcion;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Date getInicioValidez() {
        return inicioValidez;
    }

    public void setInicioValidez(Date inicioValidez) {
        this.inicioValidez = inicioValidez;
    }

    public Date getFinValidez() {
        return finValidez;
    }

    public void setFinValidez(Date finValidez) {
        this.finValidez = finValidez;
    }

    public EstadoReceta getEstado() {
        return estadoReceta;
    }

    public void setEstado(EstadoReceta estado) {
        this.estadoReceta = estado;
    }

    public Farmacia getFarmacia() {
        return farmacia;
    }

    public void setFarmacia(Farmacia farmacia) {
        this.farmacia = farmacia;
    }
    
    

}
