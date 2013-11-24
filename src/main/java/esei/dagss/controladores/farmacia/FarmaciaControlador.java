/*
 Proyecto Java EE, DAGSS-2013
 */
package esei.dagss.controladores.farmacia;

import esei.dagss.controladores.autenticacion.AutenticacionControlador;
import esei.dagss.daos.FarmaciaDAO;
import esei.dagss.entidades.Farmacia;
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
@Named(value = "farmaciaControlador")
@SessionScoped
public class FarmaciaControlador implements Serializable {

    private Farmacia farmaciaActual;
    private String nif;
    private String password;

    @Inject
    private AutenticacionControlador autenticacionControlador;

    @EJB
    private FarmaciaDAO farmaciaDAO;

    /**
     * Creates a new instance of AdministradorControlador
     */
    public FarmaciaControlador() {
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Farmacia getFarmaciaActual() {
        return farmaciaActual;
    }

    public void setFarmaciaActual(Farmacia farmaciaActual) {
        this.farmaciaActual = farmaciaActual;
    }

    public String doLogin() {
        String destino = null;
        if ((nif == null) || (password == null)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No se ha indicado un nif o una contraseña", ""));
        } else {
            Farmacia farmacia = farmaciaDAO.buscarPorNIF(nif);
            if (farmacia == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No existe una farmacia con el NIF " + nif, ""));
            } else {
                if (autenticacionControlador.autenticarUsuario(farmacia.getId(), password,
                                                               TipoUsuario.FARMACIA.getEtiqueta().toLowerCase())) {
                    farmaciaActual = farmacia;
                    destino = "privado/index";
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Credenciales de acceso incorrectas", ""));
                }

            }
        }
        return destino;
    }
}
