/*
 Proyecto Java EE, DAGSS-2013
 */

package esei.dagss.controladores.paciente;

import esei.dagss.controladores.autenticacion.AutenticacionControlador;
import esei.dagss.daos.CitaDAO;
import esei.dagss.daos.PacienteDAO;
import esei.dagss.entidades.Cita;
import esei.dagss.entidades.EstadoCita;
import esei.dagss.entidades.Paciente;
import esei.dagss.entidades.Usuario;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.jasypt.util.password.BasicPasswordEncryptor;

/**
 *
 * @author Albertops
 */
@Named(value = "gestionPacienteControlador")
@SessionScoped
public class GestionPacienteControlador implements Serializable {
    
    private Paciente pacienteActual;
    private String password1;
    private String password2;
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

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }
    
    public List<Cita> getCitas() {
        return citas;
    }

    public void setCitas(List<Cita> citas) {
        this.citas = citas;
    }
    
    public String doCancelar(Cita cita) {
        if (cita.getEstado().equals(EstadoCita.PLANIFICADA)) {
            cita.setEstado(EstadoCita.ANULADA);
            citaDAO.actualizar(cita);
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Se ha cancelado la cita", ""));
        }

        return "index";
    }

    public String doGuardar() 
    {
        if (password1.equals(password2)) {
            if (!password1.equals("")) {
                BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
                String passwordEncriptado = passwordEncryptor.encryptPassword(password1);
                pacienteActual.setPassword(passwordEncriptado);
            }
            
            pacienteActual = pacienteDAO.actualizar(pacienteActual);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Se han guardado los cambios", ""));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Las contraseñas deben coincidir", ""));
        
            return "editarPaciente";
        }
        
        return "index";
    }
    
    public String doModificar() 
    {
        if (pacienteActual.getPassword().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Primero debe introducir una contraseña", ""));

            return "cambiarPassword";
        }
        
        return "editarPaciente";
    }
}
