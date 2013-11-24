/*
 Proyecto Java EE, DAGSS-2013
 */
package esei.dagss.controladores.paciente;

import esei.dagss.controladores.autenticacion.AutenticacionControlador;
import esei.dagss.daos.MedicoDAO;
import esei.dagss.daos.PacienteDAO;
import esei.dagss.entidades.Medico;
import esei.dagss.entidades.Paciente;
import esei.dagss.entidades.TipoUsuario;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 *
 * @author ribadas
 */
@Named(value = "pacienteControlador")
@SessionScoped
public class PacienteControlador implements Serializable {

    private Paciente pacienteActual;
    private String dni;
    private String numeroTarjetaSanitaria;
    private String numeroSeguridadSocial;
    private String password;

    @Inject
    private AutenticacionControlador autenticacionControlador;

    @EJB
    private PacienteDAO pacienteDAO;

    /**
     * Creates a new instance of AdministradorControlador
     */
    public PacienteControlador() {
    }

    public Paciente getPacienteActual() {
        return pacienteActual;
    }

    public void setPacienteActual(Paciente pacienteActual) {
        this.pacienteActual = pacienteActual;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNumeroTarjetaSanitaria() {
        return numeroTarjetaSanitaria;
    }

    public void setNumeroTarjetaSanitaria(String numeroTarjetaSanitaria) {
        this.numeroTarjetaSanitaria = numeroTarjetaSanitaria;
    }

    public String getNumeroSeguridadSocial() {
        return numeroSeguridadSocial;
    }

    public void setNumeroSeguridadSocial(String numeroSeguridadSocial) {
        this.numeroSeguridadSocial = numeroSeguridadSocial;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String doLogin() {
        String destino = null;
        if (((dni == null) && (numeroSeguridadSocial == null)  && (numeroTarjetaSanitaria == null))
                || (password == null)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No se ha indicado suficientes datos de autenticación", ""));
        } else {
            Paciente paciente = null;
            if (dni != null) {
                paciente = pacienteDAO.buscarPorDNI(dni);
            }
            if ((paciente == null) && (numeroSeguridadSocial != null)) {
                paciente = pacienteDAO.buscarPorNumeroSeguridadSocial(numeroSeguridadSocial);            
            }
            if ((paciente == null) && (numeroTarjetaSanitaria != null)) {
                paciente = pacienteDAO.buscarPorTarjetaSanitaria(numeroTarjetaSanitaria);
            }
            
            if (paciente == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No existe ningún paciente con los datos indicados", ""));
            } else {
                if (autenticacionControlador.autenticarUsuario(paciente.getId(), password,
                                                               TipoUsuario.PACIENTE.getEtiqueta().toLowerCase())) {
                    pacienteActual = paciente;
                    destino = "privado/index";
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Credenciales de acceso incorrectas", ""));
                }
            }
        }
        return destino;
    }
}
