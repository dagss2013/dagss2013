/*
 Práctica Java EE 7, DAGSS 2013/14 (ESEI, U. de Vigo)
 */
package esei.dagss.entidades;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Temporal;
import javax.persistence.Version;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)  // Una única tabla compartida por todas las  subclases
@DiscriminatorColumn(name = "TIPO_USUARIO",
        discriminatorType = DiscriminatorType.STRING,
        length = 20)
public abstract class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    Date fechaAlta;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    Date ultimoAcceso;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_USUARIO", length = 20)
    TipoUsuario tipoUsuario;

    @Column(length = 64)
    String password;

    @Version
    Long version;

    public Usuario() {
        this.fechaAlta = Calendar.getInstance().getTime();
        this.ultimoAcceso = Calendar.getInstance().getTime();
    }

    public Usuario(Date fechaAlta, Date ultimoAcceso, TipoUsuario tipoUsuario) {
        this(fechaAlta, ultimoAcceso, tipoUsuario, null);
    }

    public Usuario(Date fechaAlta, Date ultimoAcceso, TipoUsuario tipoUsuario, String password) {
        this.fechaAlta = fechaAlta;
        this.ultimoAcceso = ultimoAcceso;
        this.tipoUsuario = tipoUsuario;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Date getUltimoAcceso() {
        return ultimoAcceso;
    }

    public void setUltimoAcceso(Date ultimoAcceso) {
        this.ultimoAcceso = ultimoAcceso;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
