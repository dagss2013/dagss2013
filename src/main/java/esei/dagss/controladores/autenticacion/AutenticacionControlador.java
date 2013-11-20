/*
 Proyecto Java EE, DAGSS-2013
 */

package esei.dagss.controladores.autenticacion;

import esei.dagss.entidades.Usuario;
import java.io.Serializable;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author ribadas
 */
@Named(value = "autenticacionControlador")
@SessionScoped
public class AutenticacionControlador implements Serializable {
    private boolean usuarioAutenticado;
    private Usuario usuario; 
    private String identificadorUsuario;
        
    
    /**
     * Creates a new instance of AutenticacionControlador
     */
    public AutenticacionControlador() {
    }
    
}
