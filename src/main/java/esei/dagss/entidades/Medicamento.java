/*
 Práctica Java EE 7, DAGSS 2013/14 (ESEI, U. de Vigo)
 */
package esei.dagss.entidades;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Entity
public class Medicamento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Size(min = 0, max = 50)
    @Column(length = 50, nullable = false)
    String nombre;

    @Size(min = 0, max = 50)
    @Column(length = 50, nullable = false)
    String principioActivo;

    @Size(min = 0, max = 50)
    @Column(length = 50, nullable = false)
    String fabricante;

    @ManyToOne
    Familia familia;

    @Min(1)
    Integer numeroDosis;

    public Medicamento() {
    }

    public Medicamento(String nombre, String principioActivo, String fabricante, Familia familia, Integer numeroDosis) {
        this.nombre = nombre;
        this.principioActivo = principioActivo;
        this.fabricante = fabricante;
        this.familia = familia;
        this.numeroDosis = numeroDosis;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrincipioActivo() {
        return principioActivo;
    }

    public void setPrincipioActivo(String principioActivo) {
        this.principioActivo = principioActivo;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public Familia getFamilia() {
        return familia;
    }

    public void setFamilia(Familia familia) {
        this.familia = familia;
    }

    public Integer getNumeroDosis() {
        return numeroDosis;
    }

    public void setNumeroDosis(Integer numeroDosis) {
        this.numeroDosis = numeroDosis;
    }
    
    
}
