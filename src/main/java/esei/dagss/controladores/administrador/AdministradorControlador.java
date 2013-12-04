/*
 Proyecto Java EE, DAGSS-2013
 */
package esei.dagss.controladores.administrador;

import esei.dagss.controladores.autenticacion.AutenticacionControlador;
import esei.dagss.daos.AdministradorDAO;
import esei.dagss.entidades.Administrador;
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
@Named(value = "administradorControlador")
@SessionScoped
public class AdministradorControlador implements Serializable {

    private List<Administrador> administradores;
    private Administrador administradorActual;
    private String login;
    private String password;

    @Inject
    private AutenticacionControlador autenticacionControlador;

    @EJB
    private AdministradorDAO administradorDAO;

    /**
     * Creates a new instance of AdministradorControlador
     */
    public AdministradorControlador() {
    }

    @PostConstruct
    public void inicializar() {
        administradores = administradorDAO.buscarTodos();
        if ((administradores != null) && (administradores.size() > 0)) {
            administradorActual = administradores.get(0);
        }
    }

    public List<Administrador> getAdministradores() {
        return administradores;
    }

    public void setAdministradores(List<Administrador> administradores) {
        this.administradores = administradores;
    }

    public Administrador getAdminsitradorActual() {
        return administradorActual;
    }

    public void setAdminsitradorActual(Administrador adminsitradorActual) {
        this.administradorActual = adminsitradorActual;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Administrador getAdministradorActual() {
        return administradorActual;
    }

    public void setAdministradorActual(Administrador administradorActual) {
        this.administradorActual = administradorActual;
    }


    public String doLogin() {
        String destino = null;
        if ((login == null) || (password == null)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No se ha indicado un nombre de usuario o una contraseña", ""));
        } else {
            Administrador administrador = administradorDAO.buscarPorLogin(login);
            if (administrador == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No existe el usuario " + login, ""));
            } else {
                if (autenticacionControlador.autenticarUsuario(administrador.getId(), password,
                                                               TipoUsuario.ADMINISTRADOR.getEtiqueta().toLowerCase())) {
                    administradorActual = administrador;
                    destino = "privado/index";
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Credenciales de acceso incorrectas", ""));
                }

            }
        }
        return destino;
    }
    
    public String doGestionAdministradores() {
    
        return "administradores/listaAdministradores";
    }
    
    public String doGestionCentrosSalud() {
    
        return "centrossalud/listaCentrosSalud";
    }
    
    public String doGestionTiposAgenda() {
    
        return "centrossalud/listaTiposAgenda";
    }
    
    public String doGestionMedicos() {
    
        return "medicos/listaMedicos";
    }

    public String doGestionPacientes() {
    
        return "pacientes/listaPacientes";
    }    

    public String doGestionFarmacias() {
    
        return "farmacias/listaFarmacias";
    }    
    
    public String doGestionMedicamentos() {
    
        return "medicamentos/listaMedicamentos";
    }    
    
    
}
