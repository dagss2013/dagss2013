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
    @EJB
    private AdministradorDAO administradorDAO;

    /**
     * Creates a new instance of GestionAdministradoresControlador
     */
    public GestionAdministradoresControlador() {
    }
    
    @PostConstruct
    private void inicializarGestionAdministradores() {
        administradores = administradorDAO.busarTodos();
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
    
    
    
}
