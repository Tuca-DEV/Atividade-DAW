
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


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
    private String contratante;
    private String mensalidade;
    private boolean seguroAtivo; 
    
    //Getters 
    public Long getId() { return id; }
  
    public Tipo getTipo() { return tipo; }
    
    public String getContratante() { return contratante; }
    
    public String getMensalidade() { return mensalidade; }
        
    public boolean isSeguroAtivo() { return seguroAtivo; }
    
    //Setters
    public void setId(Long id) { this.id = id; }
    
    public void setTipo(Tipo tipo) { this.tipo = tipo; }
    
    public void setMensalidade(String mensalidade) { this.mensalidade = mensalidade; }
    
    public void setContratante(String contratante) { this.contratante = contratante; }
    
    public void setSeguroAtivo(boolean seguroAtivo) { this.seguroAtivo = seguroAtivo; }
    
    
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
