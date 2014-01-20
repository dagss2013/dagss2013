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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
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

    private static final int DURACION_CITA = 15;
    
    private Paciente pacienteActual;
    private String password1;
    private String password2;
    private List<Cita> citas;
    
    private Cita         citaEnEdicion;
    private List<String> huecosCita;
    
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
    
    public Cita getCitaEnEdicion() {
        return citaEnEdicion;
    }
    
    public void setCitaEnEdicion(Cita citaEnEdicion) {
        this.citaEnEdicion = citaEnEdicion;
    }
    
    public List<String> getHuecosCita() {
        return huecosCita;
    }
    
    public void setHuecosCita(List<String> huecosCita) {
        this.huecosCita = huecosCita;
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
    
    public String doNuevaCita()
    {
        citaEnEdicion = new Cita();
        citaEnEdicion.setPaciente(pacienteActual);
        citaEnEdicion.setMedico(pacienteActual.getMedico());
        return "seleccionarFechaCita";
    }
    
    public String doSeleccionarFechaCita()
    {        
        return "seleccionarHoraCita";
    }
    
    public String doSeleccionarHoraCita()
    {
        // FIXME: codigo horrible, y ademas no funciona

        List<Date> huecos = new LinkedList<>();
        List<Cita> noHuecos = citaDAO.buscarPorMedicoYFecha(
            citaEnEdicion.getMedico().getId(),
            citaEnEdicion.getFecha()
        );
        
        Calendar calendar = Calendar.getInstance();
        Date inicio = citaEnEdicion.getMedico().getTipoAgenda().getHoraInico();
        Date fin    = citaEnEdicion.getMedico().getTipoAgenda().getHoraFin();

        for (calendar.setTime(inicio);
             calendar.before(fin);  // <== problema aqui, no entra al bucle
             calendar.add(Calendar.MINUTE, DURACION_CITA)) {
            huecos.add(calendar.getTime());
            System.err.println(calendar.getTime());
        }
        
        for (Cita c : noHuecos) {
            if (huecos.contains(c.getHora()))
                huecos.remove(c.getHora());
        }
        
        DateFormat formatter = new SimpleDateFormat("HH:mm");
        
        this.huecosCita = new LinkedList<>();
        for (Date d : huecos) huecosCita.add(formatter.format(d));
        
        // TODO: implementar seleccion por usuario y almacenamiento de cita
        return "seleccionarHoraCita";
    }

}
