
/**
 *
 * @author guga_
 */
package entidades;

public class Veiculo {
    
    private String dono;
    private String placa;
    private String marca;
    private String ano;
    private String modelo;

    public Veiculo(String dono, String marca, String placa, String ano, String modelo) {
        this.dono = dono;
        this.placa = placa;
        this.marca = marca;
        this.ano = ano;
        this.modelo = modelo;
    }
    
    public Veiculo(){
        
    }
    
    @Override
    public String toString(){
        return  dono + " - " + placa + " - " + modelo;
    }

    public String getDono() {
        return dono;
    }

    public void setDono(String dono) {
        this.dono = dono;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

      
}
