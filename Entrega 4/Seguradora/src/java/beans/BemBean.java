
package beans;

import entities.Bem;
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
import services.BemService;


@Named(value = "bemBean")
@ViewScoped
public class BemBean implements Serializable {
    @EJB
    private BemService bemService;
    
    @Inject
    private RequestParameters parameters;
    
    private Bem value;
    private SelectItem selectItem;
    private boolean consultado;
    private List<Bem> bensFiltrados;
    
    @PostConstruct
    public void init() {
        String id = parameters.get("id");
        if (id == null) value = new Bem();
        else value = bemService.find(Long.valueOf(id));
    }
    
    public List<Bem> completaBens(String prefixo) {
        return bemService.filter(prefixo);
    }
    
    public Bem getValue() { return value; }
    public void setValue(Bem value) { this.value = value; }
    
    public boolean isConsultado() { return consultado; }
    public void setConsultado(boolean consultado) { this.consultado = consultado; }

    public SelectItem getSelectItem() { return selectItem; }
    public void setSelectItem(SelectItem selectItem) { this.selectItem = selectItem; }

    public List<Bem> getBensFiltrados() { return bensFiltrados; }
    public void setBensFiltrados(List<Bem> bensFiltrados) { this.bensFiltrados = bensFiltrados; }
    
    public void reset() { value = new Bem(); }
    public void inserir() { 
        consultado = false;
        reset(); 
    }
    public void consultar(Bem value) { 
        consultado = true;
        setValue(value);  
        
    }                       
    
    public SelectItem[] getTipos(boolean filtrar) {
        SelectItem[] items;
        int length = Bem.Tipo.values().length;
        int n = 0;
        if (filtrar) {
            items = new SelectItem[length + 1];
            items[0] = new SelectItem("", "");
            n++;
        } else items = new SelectItem[length];
        for(Bem.Tipo tipo : Bem.Tipo.values()) {
            items[n++] = new SelectItem(tipo, tipo.getLabel());
        }    
        return items;
    }
    
    public SelectItem[] getSegurosAtivos() {
        SelectItem[] items;
        items = new SelectItem[3];
        
        items[0] = new SelectItem("", "");
        items[1] = new SelectItem(Boolean.TRUE.toString(), "Sim");
        items[2] = new SelectItem(Boolean.FALSE.toString(), "Não");
        
        return items;
    }
    public String onFlowProcess(FlowEvent event) { return event.getNewStep(); }
    
    
    public List<Bem> getAll() { return bemService.getAll(); }
    
    public String save() {
        bemService.create(value);
        reset();
        return null;
    }
    
    public String update() {
        bemService.edit(value);
        return null;
    }
    
    public String delete() {
        bemService.remove(value);
        return null;
    }
    
   
}
