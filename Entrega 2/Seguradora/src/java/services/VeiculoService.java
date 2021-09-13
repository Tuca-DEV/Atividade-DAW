
package services;

import entities.Veiculo;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;

@Stateless
public class VeiculoService extends EntityService<Veiculo> {
    public VeiculoService() { super(Veiculo.class); }

    @Override
    protected EntityManager getEntityManager() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
