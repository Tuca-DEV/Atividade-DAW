
package services;

import entities.Cliente;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;

@Stateless
public class ClienteService extends EntityService<Cliente> {
    public ClienteService() { super(Cliente.class); }

    public List<Cliente> filter(String prefix) {
        prefix += "%";
        Query query = em.createQuery
            ("SELECT cliente from Cliente cliente WHERE cliente.nome LIKE :prefix");
        query.setParameter("prefix", prefix);
        return query.getResultList();
    }
    
    @Override
    protected EntityManager getEntityManager() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
