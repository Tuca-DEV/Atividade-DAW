/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entidades.Filme;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.faces.model.SelectItem;

@Named(value = "clubeCinemaBean")
@SessionScoped

public class ClubeCinemaBean implements Serializable {
    private ArrayList<Filme> filmes;
    private Filme filme;
    private String diretorSelecionado;
    private boolean cadastrar;
    private boolean pesquisar;
    
    @PostConstruct
    public void init(){
        filmes = new ArrayList();
        filme = new Filme();
        cadastrar = false;
        pesquisar = false;
    }

    public String getDiretorSelecionado() {
        return diretorSelecionado;
    }

    public void setDiretorSelecionado(String diretorSelecionado) {
        this.diretorSelecionado = diretorSelecionado;
    }
    
    public boolean isCadastrar() {
        return cadastrar;
    }

    public void setCadastrar(boolean cadastrar) {
        this.cadastrar = cadastrar;
    }

    public boolean isPesquisar() {
        return pesquisar;
    }

    public void setPesquisar(boolean pesquisar) {
        this.pesquisar = pesquisar;
    }

    public ArrayList<Filme> getFilmes() {
        return filmes;
    }

    public void setFilmes(ArrayList<Filme> filmes) {
        this.filmes = filmes;
    }

    public Filme getFilme() {
        return filme;
    }

    public void setFilme(Filme filme) {
        this.filme = filme;
    }
    
    
    public String cadastrarFilme(){
        filmes.add(filme);
        reset();
        return "index";
    }
    
    public void pesquisarFilme(){
        pesquisar = true;
        cadastrar = false;
    }
    
    public void reset(){
        filme = new Filme();
        cadastrar = true;
        pesquisar = false;
    }
    
    public ArrayList<SelectItem> getDiretoresItens(){
        Collection<String> diretores = getDiretores();
        ArrayList<SelectItem> itens = new ArrayList();
        for(String diretor:getDiretores()){
            boolean inserido = false;
            for (int n = 0; n < itens.size(); n++){
                if(diretor.compareTo(itens.get(n).getLabel()) > 0) continue;
                itens.add(n, new SelectItem(diretor, diretor));
                inserido = true;
                break;
            }
            if(!inserido) itens.add(new SelectItem(diretor, diretor));
        }
        return itens;
    }
    
    private Collection<String> getDiretores() {
        HashMap<String, String> diretores = new HashMap();
        for (Filme filme: filmes) {
            String diretor = filme.getDiretor();
            if (diretores.get(diretor) == null) diretores.put(diretor,diretor);
        }
        return diretores.values();
    }
    
    public ArrayList<String> getInfoFilmesDiretor(){
        ArrayList<String> info_filmes_diretor = new ArrayList();
        for (Filme filme: filmes){
            String diretor = filme.getDiretor();
            if (diretor.equals(diretorSelecionado)) {
                boolean inserido = false;
                String ano_filme = filme.getAno();
                for (int n = 0; n < info_filmes_diretor.size(); n++){
                    String ano = info_filmes_diretor.get(n).split(" - ")[0];
                    if(ano_filme.compareTo(ano) < 0) continue;
                    info_filmes_diretor.add(n, filme.toString(true));
                    inserido = true;
                    break;
                }
                if (!inserido) info_filmes_diretor.add(filme.toString(true));
            }
        }
        return info_filmes_diretor;
    }

}
