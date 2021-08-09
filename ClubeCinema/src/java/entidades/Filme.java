/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author guga_
 */
package entidades;

public class Filme {
    
    private String título;
    private String diretor;
    private String ano;
    private String gênero;
    private String oscarMelhorFilme;
    
    public Filme(){
        
    }
    public String toString(boolean sem_diretor){
        if(sem_diretor) return ano + " - " + título + " - " + oscarMelhorFilme;
        else return ano + " - " + título + " - " + diretor + " - " + oscarMelhorFilme;
    }

    public String getTítulo() {
        return título;
    }

    public void setTítulo(String título) {
        this.título = título;
    }

    public String getDiretor() {
        return diretor;
    }

    public void setDiretor(String diretor) {
        this.diretor = diretor;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getGênero() {
        return gênero;
    }

    public void setGênero(String gênero) {
        this.gênero = gênero;
    }

    public String getOscarMelhorFilme() {
        return oscarMelhorFilme;
    }

    public void setOscarMelhorFilme(String oscarMelhorFilme) {
        this.oscarMelhorFilme = oscarMelhorFilme;
    }

      
}
