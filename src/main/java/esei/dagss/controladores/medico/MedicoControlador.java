/*
 Proyecto Java EE, DAGSS-2013
 */
package esei.dagss.controladores.medico;

import esei.dagss.controladores.autenticacion.AutenticacionControlador;
import esei.dagss.daos.CitaDAO;
import esei.dagss.daos.MedicoDAO;
import esei.dagss.entidades.Cita;
import esei.dagss.entidades.Medico;
import esei.dagss.entidades.TipoUsuario;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 *
 * @author ribadas
 */
@Named(value = "medicoControlador")
@SessionScoped
public class MedicoControlador implements Serializable {

    private Medico medicoActual;
    private String dni;
    private String numeroColegiado;
    private String password;
    private List<Cita> listaCitas;

    @Inject
    private AutenticacionControlador autenticacionControlador;
    @Inject
    private CitaDAO citaDAO;
    
    @EJB
    private MedicoDAO medicoDAO;

    @PostConstruct
    public void inicializaCitas()
    {
        listaCitas = citaDAO.buscarCitasHoy(medicoActual.getId());
    }
    
    /**
     * Creates a new instance of AdministradorControlador
     */
    public MedicoControlador() {
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNumeroColegiado() {
        return numeroColegiado;
    }

    public void setNumeroColegiado(String numeroColegiado) {
        this.numeroColegiado = numeroColegiado;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Medico getMedicoActual() {
        return medicoActual;
    }

    public void setMedicoActual(Medico medicoActual) {
        this.medicoActual = medicoActual;
    }

    public String doLogin() {
        String destino = null;
        if (((dni == null) && (numeroColegiado == null)) || (password == null)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No se ha indicado un DNI ó un número de colegiado o una contraseña", ""));
        } else {
            Medico medico = null;
            if (dni != null) {
                medico = medicoDAO.buscarPorDNI(dni);
            }
            if ((medico == null) && (numeroColegiado != null)) {
                medico = medicoDAO.buscarPorNumeroColegiado(numeroColegiado);
            }
            if (medico == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No existe ningún médico con los datos indicados", ""));
            } else {
                if (autenticacionControlador.autenticarUsuario(medico.getId(), password,
                        TipoUsuario.MEDICO.getEtiqueta().toLowerCase())) {
                    medicoActual = medico;
                    destino = "privado/index";
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Credenciales de acceso incorrectas", ""));
                }
            }
        }
        return destino;
    }
}
