/*
 Proyecto Java EE, DAGSS-2013
 */

package esei.dagss.daos;

import esei.dagss.entidades.Cita;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;


@Stateless
@LocalBean
public class CitaDAO  extends GenericoDAO<Cita>{
    
    
    public List<Cita> buscarCitasHoy(Long id)
    {
        Query q = em.createQuery("SELECT object(c) FROM Cita AS c WHERE c.medico.id = :id AND "
                + "c.fecha = DATE(NOW()) ORDER BY c.hora");
        q.setParameter("id", id);
        return q.getResultList();
    }
    
    public List<Cita> buscarPorPacienteID(Long id) {
        Query q = em.createQuery("SELECT object(c) FROM Cita AS c WHERE c.paciente.id = :id");
        q.setParameter("id", id);
        return q.getResultList();
    }
    
    public List<Cita> buscarPorMedicoId(Long id) {
        Query q = em.createQuery("SELECT object(c) FROM Cita AS c WHERE c.medico.id = :id");
        q.setParameter("id", id);
        return q.getResultList();
    }
    
    public List<Cita> buscarPorMedicoYFecha(Long medId, Date fecha) {
        Query q = em.createQuery("SELECT object(c) FROM Cita AS c WHERE c.medico.id = :id AND c.fecha = :date");
        q.setParameter("id", medId);
        q.setParameter("date", fecha);
        return q.getResultList();
    }

}
