
package services;

import entities.Bem;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;

@Stateless
public class BemService extends EntityService<Bem> {
    public BemService() { super(Bem.class); }

    @Override
    protected EntityManager getEntityManager() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
