/*
 Proyecto Java EE, DAGSS-2013
 */

package esei.dagss.controladores.medico;

import esei.dagss.daos.CitaDAO;
import esei.dagss.entidades.Cita;
import esei.dagss.entidades.Medico;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author mauro
 */
@Named(value = "gestionCitasMedicoControlador")
@SessionScoped
public class GestionCitasMedicoControlador {
    private Medico medicoActual;
    private List<Cita> listaCitas;
    
    @EJB
    private CitaDAO citaDAO;

    
    /**
     * Creates a new instance of GestionAdministradoresControlador
     */
    public GestionCitasMedicoControlador() {
    }
    
    @PostConstruct
    public void cargarCitas()
    {
        this.listaCitas = citaDAO.buscarCitasHoy(this.medicoActual.getId());
        System.out.println("ListaCitas -> " + this.listaCitas.toString());
        System.err.println("ListaCitas -> " + this.listaCitas.toString());
    }
    
    public Medico getMedicoActual() {
        return medicoActual;
    }

    public void setMedicoActual(Medico medicoActual) {
        this.medicoActual = medicoActual;
    }

    public List<Cita> getListaCitas() {
        return listaCitas;
    }

    public void setListaCitas(List<Cita> listaCitas) {
        this.listaCitas = listaCitas;
    }
}
