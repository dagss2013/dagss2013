/*
 Proyecto Java EE, DAGSS-2013
 */
package esei.dagss.daos;

import esei.dagss.entidades.Administrador;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

@Stateless
@LocalBean
public class AdministradorDAO extends GenericoDAO<Administrador> {

    public Administrador buscarPorLogin(String login) {
        Query q = em.createQuery("SELECT object(a) FROM Administrador AS a "
                               + "  WHERE a.login = :login");
        q.setParameter("login", login);

        return filtrarResultadoUnico(q);
    }

    public List<Administrador> buscarTodos() {
        Query q = em.createQuery("SELECT object(a) FROM Administrador AS a ");
        return q.getResultList();
    }
}
