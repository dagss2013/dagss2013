/*
 Proyecto Java EE, DAGSS-2013
 */
package esei.dagss.daos;

import esei.dagss.entidades.Medico;
import esei.dagss.entidades.Paciente;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

@Stateless
@LocalBean
public class MedicoDAO extends GenericoDAO<Medico> {

    public Medico buscarPorDNI(String dni) {
        Query q = em.createQuery("SELECT object(m) FROM Medico AS m "
                + "  WHERE m.dni = :dni");
        q.setParameter("dni", dni);

        return filtrarResultadoUnico(q);
    }

    public Medico buscarPorNumeroColegiado(String numeroColegiado) {
        Query q = em.createQuery("SELECT object(m) FROM Medico AS m "
                + "  WHERE m.numeroColegiado = :dni");
        return filtrarResultadoUnico(q);
    }


    public List<Medico> buscarTodos() {
        Query q = em.createQuery("SELECT object(m) FROM Medico AS m");
        return q.getResultList();
    }

    public Long contarTodos() {
        Query q = em.createQuery("SELECT object(m) FROM Medico AS m");
        return (Long) q.getSingleResult();
    }

    public List<Paciente> buscarPorNombre(String patron) {
        Query q = em.createQuery("SELECT object(m) FROM Medico AS m "
                + "  WHERE (m.nombre LIKE :patron) OR "
                + "        (m.apellidos LIKE :patron)");
        q.setParameter("patron","%"+patron+"%");        
        return q.getResultList();
    }

    public List<Paciente> buscarPorCentroSalud(Long idCentroSalud) {
        Query q = em.createQuery("SELECT object(m) FROM Medico AS m "
                + "  WHERE m.centroSalud.id = :idCentroSalud");
        q.setParameter("idCentroSalud",idCentroSalud);        
        return q.getResultList();
    }

    // Otros
}
