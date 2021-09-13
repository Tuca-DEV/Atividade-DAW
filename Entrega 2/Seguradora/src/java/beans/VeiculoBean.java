
package beans;

import entities.Veiculo;
import java.io.Serializable;
import util.RequestParameters;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import services.VeiculoService;


@Named(value = "veiculoBean")
@ViewScoped
public class VeiculoBean implements Serializable {
    @EJB
    private VeiculoService veiculoService;
    
    @Inject
    private RequestParameters parameters;
    private boolean consultado;
    private Veiculo value;
    
    @PostConstruct
    public void init() {
        String id = parameters.get("id");
        if (id == null) value = new Veiculo();
        else value = veiculoService.find(Long.valueOf(id));
    }
    
    public Veiculo getValue() { return value; }
    public void setValue(Veiculo value) { this.value = value; }
    public boolean isConsultado() {return consultado;}
    public void setConsultado(boolean consultado) { this.consultado = consultado; }  
    
    public void reset() { value = new Veiculo(); }
    public void inserir() {
        consultado = false;
        reset(); 
    }
    public void consultar(Veiculo value) { 
        consultado = true;
        setValue(value);
    }
    
    public List<Veiculo> getAll() { return veiculoService.getAll(); }
    
    public String save() {
        if(getAll().isEmpty()){
            value.setPlaca(value.getPlaca().toUpperCase());
            veiculoService.create(value);
            reset();
            return null;
        }else{
            for (Veiculo veiculo : getAll()) {
                if(value.getPlaca().equals(veiculo.getPlaca())){
                    return null;
                }
            }
            value.setPlaca(value.getPlaca().toUpperCase());
            veiculoService.create(value);
            reset();
            return null;
        }
    }
    
    public String update() {
        veiculoService.edit(value);
        return null;
    }
    
    public String delete() {
        veiculoService.remove(value);
        return null;
    }
    
}
