
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Pattern;


@Entity
public class Cliente implements Serializable, PersistentEntity {
    
    public enum EstadoCivil {
        solteiro("solteiro(a)"), casado("casado(a)"), divorciado("divorciado(a)"), viuvo("viuvo(a)");

        private final String label;
        
        private EstadoCivil(String label) { this.label = label; }
        public String getLabel() { return this.label; }
    }
    
    public enum Estado {
        ac("AC"), al("AL"), ap("AP"), am("AM"), ba("BA"), ce("CE"), df("DF"), es("ES"), go("GO"), ma("MA"), mt("MT"),
        ms("MS"), mg("MG"), pa("PA"), pb("PB"), pr("PR"), pe("PE"), pi("PI"), rj("RJ"), rn("RN"), rs("RS"), ro("RO"),
        rr("RR"), sc("SC"), sp("SP"), se("SE"), to("TO");

        private final String label;
        
        private Estado(String label) { this.label = label; }
        public String getLabel() { return this.label; }
    }
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @OneToMany(mappedBy = "cliente")
    private List<Bem> bens;
    
    @Pattern(regexp = "(\\d{3})\\.(\\d{3})\\.(\\d{3})\\-(\\d{2})")
    private String cpf;
    @Enumerated(EnumType.STRING)
    private EstadoCivil estadoCivil;
    private String nome;
    private String sexo;
    
    @Pattern(regexp = "\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*")
    private String email;
    @Pattern(regexp = "((\\(\\d{2}\\) ?)|(\\d{2}-))?(\\d{5}|\\d{4})-\\d{4}")
    private String telefone;
    @Pattern(regexp = "^\\d{5}-\\d{3}$")
    private String CEP;
    @Enumerated(EnumType.STRING)
    private Estado estado;
    private String rua;
    private String cidade;
    
    
    //Getters 

    public List<Bem> getBens() {
        return bens;
    }
  
    public Long getId() { return id; }

    public String getCpf() {  return cpf; }

    public EstadoCivil getEstadoCivil() { return estadoCivil; }
    
    public String getNome() { return nome; }

    public String getSexo() { return sexo; }
    
    public String getEmail() { return email; }
    
    public String getTelefone() { return telefone; }
    
    public String getCEP() { return CEP; }

    public String getRua() { return rua; }

    public String getCidade() { return cidade; }
    
    public Estado getEstado() { return estado; }
    
    //Setters
    public void setBens(List<Bem> bens) {
        this.bens = bens;
    }
    
    public void setId(Long id) { this.id = id; }
    
    public void setCpf(String cpf) { this.cpf = cpf; }
    
    public void setEstadoCivil(EstadoCivil estadoCivil) { this.estadoCivil = estadoCivil; }
        
    public void setNome(String nome) { this.nome = nome; }
    
    public void setSexo(String sexo) { this.sexo = sexo; }
    
    public void setEmail(String email) { this.email = email; }
        
    public void setTelefone(String telefone) { this.telefone = telefone; }
        
    public void setCEP(String CEP) { this.CEP = CEP; }

    public void setRua(String rua) { this.rua = rua; }
    
    public void setCidade(String cidade) { this.cidade = cidade; }
    
    public void setEstado(Estado estado) { this.estado = estado; }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Cliente[ id=" + id + " ]";
    }
    
}
