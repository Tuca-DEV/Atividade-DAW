
package services;

import entities.Seguro;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;

@Stateless
public class SeguroService extends EntityService<Seguro> {
    public SeguroService() { super(Seguro.class); }

    @Override
    protected EntityManager getEntityManager() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
