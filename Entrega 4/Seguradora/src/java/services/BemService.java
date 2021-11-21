
package services;

import entities.Bem;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;

@Stateless
public class BemService extends EntityService<Bem> {
    public BemService() { super(Bem.class); }

    public List<Bem> filter(String prefix) {
        prefix += "%";
        Query query = em.createQuery
            ("SELECT bem from Bem bem WHERE bem.titulo LIKE :prefix");
        query.setParameter("prefix", prefix);
        return query.getResultList();
    }
    
    @Override
    protected EntityManager getEntityManager() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
