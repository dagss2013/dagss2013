/*
 Proyecto Java EE, DAGSS-2013
 */

package esei.dagss.controladores.paciente;

import esei.dagss.controladores.autenticacion.AutenticacionControlador;
import esei.dagss.daos.CitaDAO;
import esei.dagss.daos.PacienteDAO;
import esei.dagss.daos.UsuarioDAO;
import esei.dagss.entidades.Administrador;
import esei.dagss.entidades.Cita;
import esei.dagss.entidades.Paciente;
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
 * @author Albertops
 */
@Named(value = "gestionPacienteControlador")
@SessionScoped
public class GestionPacienteControlador implements Serializable {
    
    private Paciente pacienteActual;
    private List<Cita> citas;
    
    @Inject
    private AutenticacionControlador autenticacionControlador;
    
    @EJB
    private PacienteDAO pacienteDAO;
    @EJB
    private CitaDAO citaDAO;

    /**
     * Creates a new instance of GestionPacienteControlador
     */
    public GestionPacienteControlador() {
    }
    
    @PostConstruct
    public void inicializarGestionPaciente()
    {   
        pacienteActual = pacienteDAO.buscarPorId(autenticacionControlador.getUsuario().getId());
        citas = citaDAO.buscarPorPacienteID(pacienteActual.getId());
    }
    
    public Paciente getPacienteActual() {
        return pacienteActual;
    }

    public void setPacienteActual(Paciente pacienteActual) {
        this.pacienteActual = pacienteActual;
    }

    public List<Cita> getCitas() {
        return citas;
    }

    public void setCitas(List<Cita> citas) {
        this.citas = citas;
    }
    
    public String doCancelar(Cita cita) {
        citas.remove(cita);
        citaDAO.eliminar(cita);

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Se ha cancelado la cita", ""));

        return "index";
    }
}
