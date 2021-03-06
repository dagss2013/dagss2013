/*
 Proyecto Java EE, DAGSS-2013
 */
package esei.dagss.controladores.administrador;

import esei.dagss.daos.CentroSaludDAO;
import esei.dagss.entidades.CentroSalud;
import esei.dagss.entidades.Direccion;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

/**
 *
 * @author dagss
 */
@Named(value = "centroSaludControlador")
@SessionScoped
public class CentroSaludControlador implements Serializable {

    @Inject
    CentroSaludDAO centroSaludDAO;

    List<CentroSalud> centrosSalud;
    CentroSalud centroSaludEnEdicion;
    Boolean esNuevo;

    /**
     * Creates a new instance of CentroSaludControlador
     */
    public CentroSaludControlador() {
    }

    @PostConstruct
    public void inicializar() {
        centrosSalud = centroSaludDAO.buscarTodos();
    }

    public List<CentroSalud> getCentrosSalud() {
        return centrosSalud;
    }

    public void setCentrosSalud(List<CentroSalud> centrosSalud) {
        this.centrosSalud = centrosSalud;
    }

    public CentroSalud getCentroSaludEnEdicion() {
        return centroSaludEnEdicion;
    }

    public void setCentroSaludEnEdicion(CentroSalud centroSaludEnEdicion) {
        this.centroSaludEnEdicion = centroSaludEnEdicion;
    }

    public Boolean isEsNuevo() {
        return esNuevo;
    }

    public void setEsNuevo(Boolean esNuevo) {
        this.esNuevo = esNuevo;
    }

    public void doEliminar(CentroSalud centroSalud) {
        centroSaludDAO.eliminar(centroSalud);
        centrosSalud = centroSaludDAO.buscarTodos();
    }

    public void doNuevo() {
        centroSaludEnEdicion = new CentroSalud();
        centroSaludEnEdicion.setDireccion(new Direccion());
        esNuevo = true;
    }

    public void doEditar(CentroSalud centroSalud) {
        centroSaludEnEdicion = centroSalud;   // Otra alternativa: volver a refrescarlos desde el DAO
        esNuevo = false;
    }

    public void doGuardar() {
        if (esNuevo) {
            // Crea un nuevo centro de salud
            centroSaludDAO.crear(centroSaludEnEdicion);
        } else {
            // Actualiza un centro de salud
            centroSaludDAO.actualizar(centroSaludEnEdicion);
        }
        // Actualiza lista de centros de salud a mostrar
        centrosSalud = centroSaludDAO.buscarTodos();
    }

    public void doCancelar() {
        esNuevo = false;
        centroSaludEnEdicion = new CentroSalud();
    }

    public String doVolver() {

        return "../index?faces-redirect=true";
    }

}
