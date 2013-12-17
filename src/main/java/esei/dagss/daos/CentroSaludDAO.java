/*
 Proyecto Java EE, DAGSS-2013
 */

package esei.dagss.daos;

import esei.dagss.entidades.CentroSalud;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

@Stateless
@LocalBean
public class CentroSaludDAO extends GenericoDAO<CentroSalud>{

    public List<CentroSalud> buscarTodos() {
        Query q = em.createQuery("SELECT object(cs) FROM CentroSalud AS cs ");
        return q.getResultList();                
    }
    
}
