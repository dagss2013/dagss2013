/*
 Proyecto Java EE, DAGSS-2013
 */

package esei.dagss.controladores.autenticacion;

import esei.dagss.daos.UsuarioDAO;
import esei.dagss.entidades.TipoUsuario;
import esei.dagss.entidades.Usuario;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author ribadas
 */
@Named(value = "autenticacionControlador")
@SessionScoped
public class AutenticacionControlador implements Serializable {
    private boolean usuarioAutenticado;
    private TipoUsuario tipoUsuario;
    private Usuario usuario; 
    
    @EJB
    private UsuarioDAO usuarioDAO;
        
    
    public AutenticacionControlador() {
    }

    public boolean isUsuarioAutenticado() {
        return usuarioAutenticado;
    }

    public void setUsuarioAutenticado(boolean usuarioAutenticado) {
        this.usuarioAutenticado = usuarioAutenticado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public boolean autenticarUsuario(Long idUsuario, String passwordEnClaro, String tipo) {
        if (usuarioDAO.autenticarUsuario(idUsuario, passwordEnClaro, tipo)) {
            usuarioAutenticado = true;
            usuario = usuarioDAO.buscarPorId(idUsuario);
            tipoUsuario = usuario.getTipoUsuario();
            return true;
        }
        else {
            usuarioAutenticado = false;
            usuario = null;
            tipoUsuario = null;            
            return false;
        }
    }
    
    public String doLogOut() {
        usuarioDAO.actualizarUltimoAcceso(usuario.getId());
        usuarioAutenticado = false;
        usuario = null;
        tipoUsuario = null;
        
        // Finalizar la sesion        
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        
        // Volver a la página principal
        return "/index";
    }
    
}
