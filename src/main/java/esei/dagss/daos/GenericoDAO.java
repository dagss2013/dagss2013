/*
 Proyecto Java EE, DAGSS-2013
 */
package esei.dagss.daos;

import esei.dagss.entidades.Paciente;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public abstract class GenericoDAO<T> {

    @PersistenceContext(unitName = "dagss2013-PU")
    protected EntityManager em;


    public T crear(T entidad) {
        em.persist(entidad); // Crea una nueva tupla en la BD con los datos de "entidad"
        // -> ademas genera su ID
        return entidad;
    }

    public T actualizar(T entidad) {
        return em.merge(entidad);   // Actualiza los datos de "entidad" en su correspondiente tupla BD
    }

    public void eliminar(T entidad) {
        em.remove(em.merge(entidad));  // Actualiza y elimina
    }

    public T buscarPorId(Object id) {
        // Identifica la clase real de las entidades gestionada por este objeto (T.class)
        Class<T> claseEntidad = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

        return em.find(claseEntidad, id);
    }

    // Metodos de utilidad comunes    
    public List<T> buscarQueryParametrizada(String consulta, Map<String, Object> parametros) {
        Query query = em.createQuery(consulta);
        for (Map.Entry<String, Object> parametro : parametros.entrySet()) {
            query.setParameter(parametro.getKey(), parametro.getValue());
        }
        return query.getResultList();
    }

    protected T filtrarResultadoUnico(Query query) {
        List<T> resultados = query.getResultList();
        if (resultados == null) {
            return null;  // No encontrado
        } else if (resultados.size() != 1) {
            return null; // No encontrado (hay más con el mismo login ¿?)
        } else {
            return resultados.get(0);  // Devuelve el encontrado
        }
    }
}
