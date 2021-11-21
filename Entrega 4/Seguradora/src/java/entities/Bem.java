
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


@Entity
public class Bem implements Serializable, PersistentEntity {
    
    public enum Tipo {
        veiculo("Veículo"), imovel("Imóvel"), vida("Vida");
        
        private final String label;
        
        private Tipo(String label) { this.label = label; }
        public String getLabel() { return this.label; }
    }
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    
    @Enumerated(EnumType.STRING)
    private Tipo tipo;
    private String mensalidade;
    private boolean seguroAtivo; 
    @OneToMany(mappedBy = "bem")
    private List<Seguro> seguros;
    @ManyToOne
    private Cliente cliente;
    private String titulo; // ex: Vc001
    
    //Getters 

    public String getTitulo() {
        return titulo;
    }
    
    
    public Long getId() { return id; }
  
    public Tipo getTipo() { return tipo; }
    
    public String getMensalidade() { return mensalidade; }
        
    public boolean isSeguroAtivo() { return seguroAtivo; }
    
    public List<Seguro> getSeguros() {
        return seguros;
    }
    
    public Cliente getCliente() {
        return cliente;
    }
    
    
    //Setters
    
    
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public void setId(Long id) {
        this.id = id;
    }
    
    public void setTipo(Tipo tipo) { this.tipo = tipo; }
    
    public void setMensalidade(String mensalidade) { this.mensalidade = mensalidade; }
    
    public void setSeguroAtivo(boolean seguroAtivo) { this.seguroAtivo = seguroAtivo; }
    
    public void setSeguros(List<Seguro> seguros) {
        this.seguros = seguros;
    }  
    
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }  
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bem)) {
            return false;
        }
        Bem other = (Bem) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Bem[ id=" + id + " ]";
    }
    
}
