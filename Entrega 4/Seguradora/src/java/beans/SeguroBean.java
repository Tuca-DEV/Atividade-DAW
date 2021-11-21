
package beans;

import entities.Seguro;
import java.io.Serializable;
import util.RequestParameters;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.event.FlowEvent;
import services.SeguroService;


@Named(value = "seguroBean")
@ViewScoped
public class SeguroBean implements Serializable {
    @EJB
    private SeguroService seguroService;
    
    @Inject
    private RequestParameters parameters;
    
    private Seguro value;
    private boolean consultado;
    private List<Seguro> segurosFiltrados;
    
    @PostConstruct
    public void init() {
        String id = parameters.get("id");
        if (id == null) value = new Seguro();
        else value = seguroService.find(Long.valueOf(id));
    }
    
    public Seguro getValue() { return value; }
    public void setValue(Seguro value) { this.value = value; }
    
    public boolean isConsultado() { return consultado; }
    public void setConsultado(boolean consultado) { this.consultado = consultado; }

    public List<Seguro> getSegurosFiltrados() { return segurosFiltrados; }
    public void setSegurosFiltrados(List<Seguro> segurosFiltrados) { 
        this.segurosFiltrados = segurosFiltrados;
    }
    
    
    
    public void reset() { 
        value = new Seguro(); 
    }
    public void inserir() { 
        reset(); 
        consultado = false;
    }
    public void consultar(Seguro value) { 
        setValue(value); 
        consultado = true;
    }
    
    public String onFlowProcess(FlowEvent event) { return event.getNewStep(); }
    
    public List<Seguro> getAll() { return seguroService.getAll(); }
    
    public String save() {
        seguroService.create(value);
        reset();
        return null;
    }
    
    public String update() {
        seguroService.edit(value);
        return null;
    }
    
    public String delete() {
        seguroService.remove(value);
        return null;
    }
    
}
