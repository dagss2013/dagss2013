/*
 Proyecto Java EE, DAGSS-2013
 */
package esei.dagss.daos;

import esei.dagss.entidades.TipoUsuario;
import esei.dagss.entidades.Usuario;
import java.util.Calendar;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;
import org.jasypt.util.password.BasicPasswordEncryptor;

@Stateless
@LocalBean
public class UsuarioDAO extends GenericoDAO<Usuario> {


    public List<Usuario> buscarTodos() {
        Query q = em.createQuery("SELECT object(u) FROM Usuario as u");
        return q.getResultList();
    }

    public int contador() {
        Query q = em.createQuery("SELECT count(u) FROM Usuario as u");
        return q.getFirstResult();
    }

    // Métodos adicionales para control de usuarios
    public boolean autenticarUsuario(Long idUsuario, String passwordPlano, String tipo) {
        Usuario usuario;
        boolean resultado = false;
        
        usuario = buscarPorId(idUsuario);
        
        if (usuario != null) {
            if (comprobarTipo(usuario, tipo)) {
                if (comprobarPassword(passwordPlano, usuario.getPassword())) {
                    resultado = true;
                }
            }
        }

        return resultado;
    }

    public Usuario actualizarPassword(long idUsuario, String passwordPlano) {
        Usuario usuario = buscarPorId(idUsuario);

        BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
        String passwordEncriptado = passwordEncryptor.encryptPassword(passwordPlano);

        usuario.setPassword(passwordEncriptado);
        return actualizar(usuario);
    }

    public Usuario actualizarUltimoAcceso(long idUsuario) {
        Usuario usuario = buscarPorId(idUsuario);
        usuario.setUltimoAcceso(Calendar.getInstance().getTime());  // Tiempo actual
        return actualizar(usuario);
    }

    public boolean existeUsuario(Long idUsuario) {
        return (buscarPorId(idUsuario) != null);
    }

    private boolean comprobarTipo(Usuario usuario, String tipo) {
        return usuario.getTipoUsuario().getEtiqueta().equalsIgnoreCase(tipo);
    }

    private boolean comprobarPassword(String passwordPlano, String passwordEncriptado) {
        BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();

        return (passwordEncryptor.checkPassword(passwordPlano, passwordEncriptado));
    }
}
