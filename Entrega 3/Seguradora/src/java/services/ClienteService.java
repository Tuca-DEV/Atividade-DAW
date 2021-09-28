
package services;

import entities.Cliente;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;

@Stateless
public class ClienteService extends EntityService<Cliente> {
    public ClienteService() { super(Cliente.class); }

    @Override
    protected EntityManager getEntityManager() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
