/*
 Proyecto Java EE, DAGSS-2013
 */
package esei.dagss.daos;


import esei.dagss.entidades.Farmacia;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

@Stateless
@LocalBean
public class FarmaciaDAO extends GenericoDAO<Farmacia> {

    public Farmacia buscarPorNIF(String nif) {
        Query q = em.createQuery("SELECT object(f) FROM Farmacia AS f "
                + "  WHERE f.nif = :nif");
        q.setParameter("nif", nif);

        return filtrarResultadoUnico(q);
    }


    public List<Farmacia> buscarTodos() {
        Query q = em.createQuery("SELECT object(f) FROM Farmacia AS f");
        return q.getResultList();
    }

    public Long contarTodos() {
        Query q = em.createQuery("SELECT count(f) FROM Farmacia AS f");
        return (Long) q.getSingleResult();
    }

    public List<Farmacia> buscarPorNombre(String patron) {
        Query q = em.createQuery("SELECT object(f) FROM Farmacia AS f "
                + "  WHERE f.nombreFarmacia LIKE :patron");
        q.setParameter("patron","%"+patron+"%");        
        return q.getResultList();
    }

    public List<Farmacia> buscarPorLocalidad(String localidad) {
        Query q = em.createQuery("SELECT object(f) FROM Farmacia AS f "
                + "  WHERE f.direccion.localidad LIKE :patron");
        q.setParameter("patron","%"+localidad+"%");        
        return q.getResultList();
    }

    // Otros
}
