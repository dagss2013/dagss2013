/*
 Proyecto Java EE, DAGSS-2013
 */
package esei.dagss.controladores.administrador;

import esei.dagss.daos.AdministradorDAO;
import esei.dagss.entidades.Administrador;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author dagss
 */
@Named(value = "gestionAdministradoresControlador")
@RequestScoped
public class GestionAdministradoresControlador {

    private List<Administrador> administradores;
    private Administrador administradorEnEdicion;
    private String password1;
    private String password2;

    @EJB
    private AdministradorDAO administradorDAO;

    /**
     * Creates a new instance of GestionAdministradoresControlador
     */
    public GestionAdministradoresControlador() {
    }

    @PostConstruct
    private void inicializarGestionAdministradores() {
        administradores = administradorDAO.buscarTodos();
        if ((administradores != null) && (!administradores.isEmpty())) {
            administradorEnEdicion = administradores.get(0);
        }
    }

    public List<Administrador> getAdministradores() {
        return administradores;
    }

    public void setAdministradores(List<Administrador> administradores) {
        this.administradores = administradores;
    }

    public Administrador getAdministradorEnEdicion() {
        return administradorEnEdicion;
    }

    public void setAdministradorEnEdicion(Administrador administradorEnEdicion) {
        this.administradorEnEdicion = administradorEnEdicion;
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

    public String doEditar(Administrador administradorSeleccionado) {
        if (administradorSeleccionado != null) {
            this.administradorEnEdicion = administradorSeleccionado;
            return "editarAdministrador";
        } else {
            return null;
        }
    }

    public String doEliminar(Administrador administradorSeleccionado) {
// lammar a dAO

        return "listaAdministradores";
    }

    public String doNuevo() {
        this.administradorEnEdicion = new Administrador();
        return "editarAdministrador";
    }

    public String doGuardar() {
        // llamar a DAO
        return "listaAdminsitradores";
    }

    public String doCancelar() {
        if ((administradores != null) && (!administradores.isEmpty())) {
            administradorEnEdicion = administradores.get(0);
        }
        return "listaAdminsitradores";
    }

}
