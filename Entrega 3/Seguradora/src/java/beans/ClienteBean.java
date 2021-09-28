
package beans;

import entities.Cliente;
import java.io.IOException;
import java.io.Serializable;
import util.RequestParameters;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.event.FlowEvent;
import services.ClienteService;


@Named(value = "clienteBean")
@ViewScoped
public class ClienteBean implements Serializable {
    @EJB
    private ClienteService clienteService;
    
    @Inject
    private RequestParameters parameters;
    
    private Cliente value;
    private SelectItem selectItem;
    private boolean consultado;
    private List<Cliente> clientesFiltrados;
    
    @PostConstruct
    public void init() {
        String id = parameters.get("id");
        if (id == null) value = new Cliente();
        else value = clienteService.find(Long.valueOf(id));
    }
    
    public Cliente getValue() { return value; }
    public void setValue(Cliente value) { this.value = value; }
    
    public boolean isConsultado() { return consultado; }
    public void setConsultado(boolean consultado) { this.consultado = consultado; }

    public SelectItem getSelectItem() { return selectItem; }
    public void setSelectItem(SelectItem selectItem) { this.selectItem = selectItem; }

    public List<Cliente> getClientesFiltrados() { return clientesFiltrados; }
    public void setClientesFiltrados(List<Cliente> clientesFiltrados) { this.clientesFiltrados = clientesFiltrados; }
    
    public void reset() { value = new Cliente(); }
    public void inserir() { 
        consultado = false;
        reset(); 
    }
    public void consultar(Cliente value) { 
        consultado = true;
        setValue(value);  
    }                       
    
    public SelectItem[] getEstadosCivis(boolean filtrar) {
        SelectItem[] items;
        int length = Cliente.EstadoCivil.values().length;
        int n = 0;
        if (filtrar) {
            items = new SelectItem[length + 1];
            items[0] = new SelectItem("", "");
            n++;
        } else items = new SelectItem[length];
        for(Cliente.EstadoCivil estado_civil : Cliente.EstadoCivil.values()) {
            items[n++] = new SelectItem(estado_civil, estado_civil.getLabel());
        }    
        return items;
    }
    
    public SelectItem[] getEstados(boolean filtrar) {
        SelectItem[] items;
        int length = Cliente.Estado.values().length;
        int n = 0;
        if (filtrar) {
            items = new SelectItem[length + 1];
            items[0] = new SelectItem("", "");
            n++;
        } else items = new SelectItem[length];
        for(Cliente.Estado estado : Cliente.Estado.values()) {
            items[n++] = new SelectItem(estado, estado.getLabel());
        }    
        return items;
    }
    
    public SelectItem[] getSexos(boolean filtrar) {
        SelectItem[] items;
        int length = 2;
        int n = 0;
        if (filtrar) {
            items = new SelectItem[length + 1];
            items[0] = new SelectItem("", "");
            items[1] = new SelectItem("feminino", "Feminino");
            items[2] = new SelectItem("masculino", "Masculino");
        } else {
            items = new SelectItem[length];
            items[0] = new SelectItem("feminino", "Feminino");
            items[1] = new SelectItem("masculino", "Masculino");
        }
        return items;
    }
    
    public String onFlowProcess(FlowEvent event) { return event.getNewStep(); }
    
    
    public List<Cliente> getAll() { return clienteService.getAll(); }
    
    public String save() {
        clienteService.create(value);
        reset();
        return null;
    }
    
    public String update() {
        clienteService.edit(value);
        return null;
    }
    
    public String delete() {
        clienteService.remove(value);
        return null;
    }
    
    
}
