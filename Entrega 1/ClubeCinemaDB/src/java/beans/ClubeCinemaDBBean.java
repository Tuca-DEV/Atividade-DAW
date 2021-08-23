/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entidades.Filme;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.faces.model.SelectItem;
import javax.sql.DataSource;

@Named(value = "clubeCinemaDBBean")
@RequestScoped

public class ClubeCinemaDBBean {
    
    @Resource(lookup = "java:/ClubeCinemaDBDS")
    private DataSource filmeDataSource;
    
    private ArrayList<Filme> filmes;
    private Filme filme;
    private String diretorSelecionado;
    private boolean cadastrar;
    private boolean pesquisar;
    private ArrayList<String> mensagensErro;
    
    @PostConstruct
    public void init(){
        filmes = new ArrayList();
        filme = new Filme();
        mensagensErro = new ArrayList();
        cadastrar = false;
        pesquisar = false;
    }

    public DataSource getFilmeDataSource() {
        return filmeDataSource;
    }

    public void setFilmeDataSource(DataSource filmeDataSource) {
        this.filmeDataSource = filmeDataSource;
    }

    public ArrayList<String> getMensagensErro() {
        return mensagensErro;
    }

    public void setMensagensErro(ArrayList<String> mensagensErro) {
        this.mensagensErro = mensagensErro;
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

    public ArrayList<Filme> getFilmes() throws SQLException {
        getFilmesDB();
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
    
    
    public String cadastrarFilme() throws SQLException{
        saveFilmeDB();
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
    
    private Collection<String> getDiretores() {
        HashMap<String, String> diretores = new HashMap();
        for (Filme filme: filmes) {
            String diretor = filme.getDiretor();
            if (diretores.get(diretor) == null) diretores.put(diretor,diretor);
        }
        return diretores.values();
    }
    
    public Connection getConnectionDB(){
        Connection conexão = null;
        if(filmeDataSource == null){
            mensagensErro.add("DataSource não acessível");
            return null;
        }
        try{conexão = filmeDataSource.getConnection();}
        catch (SQLException exception) {mensagensErro.add(exception.getMessage());}
        return conexão;
    }
    
    public void saveFilmeDB() throws SQLException {
        Connection conexão = getConnectionDB();
        if(conexão == null) return;
        PreparedStatement comando = null;
        try {
            comando = conexão.prepareStatement
                    ("INSERT INTO Filmes (Titulo, Diretor, Ano, Genero, OscarMelhorFilme)"
                    + "VALUES (?, ?, ?, ?, ?)");
            comando.setString(1, filme.getTítulo());
            comando.setString(2, filme.getDiretor());
            comando.setString(3, filme.getAno());
            comando.setString(4, filme.getGênero());
            comando.setString(5, filme.getOscarMelhorFilme());
            comando.executeUpdate();
            comando.close();       
        } catch (SQLException exception) {
            if (comando != null) comando.close();
            mensagensErro.add(exception.getMessage());
        }
        conexão.close();
        return;       
    }
    
    public String getFilmesDB() throws SQLException {
        String próxima_página = "";
        ArrayList<Filme> filmes = new ArrayList();
        Connection conexão = getConnectionDB();
        if(conexão == null) return "";
        PreparedStatement comando = null;
        ResultSet consultas = null;
        try {
            comando = conexão.prepareStatement
                    ("SELECT Titulo, Diretor, Ano, Genero, OscarMelhorFilme FROM Filmes ORDER BY Ano DESC ");
            consultas = comando.executeQuery();
            while(consultas.next()){
                Filme filme = new Filme(consultas.getString("Titulo"), consultas.getString("Diretor"),
                    consultas.getString("Ano"), consultas.getString("Genero"),
                    consultas.getString("OscarMelhorFilme"));
                filmes.add(filme);
            }
            consultas.close();
            comando.close();
            próxima_página = "index";
            
        } catch (SQLException exception){
            if(consultas != null) consultas.close();
            if(comando != null) comando.close();
            mensagensErro.add(exception.getMessage());
        }
        conexão.close();
        this.filmes = filmes;
        return próxima_página;
    }
    
    public ArrayList<String> getDiretoresDB() throws SQLException {
        ArrayList<String> diretores = new ArrayList();
        Connection conexão = getConnectionDB();
        if(conexão == null) return diretores;
        PreparedStatement comando = null;
        ResultSet consultas = null;
        
        try{
            comando = conexão.prepareStatement("SELECT Diretor FROM Filmes ORDER BY Diretor");
            consultas = comando.executeQuery();
            while(consultas.next()){
                String diretor = consultas.getString("Diretor");
                int total_diretores = diretores.size();
                if(total_diretores == 0 || (!diretor.equals(diretores.get(total_diretores - 1))))
                    diretores.add(diretor);
            }
            consultas.close();
            comando.close();
        } catch (SQLException exception){
            if (consultas != null) consultas.close();
            if (comando != null) comando.close();
            mensagensErro.add(exception.getMessage());
            
        }
        conexão.close();
        return diretores;
                
    }
    
    public ArrayList<SelectItem> getDiretoresItens() throws SQLException {
        ArrayList<SelectItem> itens = new ArrayList();
        for(String diretor: getDiretoresDB()){
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
    
    public ArrayList<String> getInfoFilmesDiretor() throws SQLException{
        getFilmesDiretorDB();
        ArrayList<String> info_filmes_diretor = new ArrayList();
        for(Filme filme: filmes){
            String diretor = filme.getDiretor();
            if(diretor.equals(diretorSelecionado)){
                boolean inserido = false;
                String ano_filme = filme.getAno();
                for(int n = 0; n < info_filmes_diretor.size(); n++){
                    String ano = info_filmes_diretor.get(n).split(" - ")[0];
                    if(ano_filme.compareTo(ano) < 0) continue;
                    info_filmes_diretor.add(n, filme.toString(true));
                    inserido = true;
                    break;
                }
                if(!inserido) info_filmes_diretor.add(filme.toString(true));
            }
        }
        return info_filmes_diretor;
    }
    
    public void getFilmesDiretorDB() throws SQLException{
        ArrayList<Filme> filmes = new ArrayList();
        Connection conexão = getConnectionDB();
        if(conexão == null) return;
        PreparedStatement comando = null;
        ResultSet consultas = null;
        try {
            comando = conexão.prepareStatement("SELECT * FROM Filmes WHERE Diretor = ? ORDER BY Ano DESC");
            comando.setString(1, diretorSelecionado);
            consultas = comando.executeQuery();
            while(consultas.next()){
                Filme filme = new Filme(consultas.getString("Titulo"), consultas.getString("Diretor"),
                    consultas.getString("Ano"), consultas.getString("Genero"),
                    consultas.getString("OscarMelhorFilme"));
                filmes.add(filme);
            }
            consultas.close();
            comando.close();
            this.filmes = filmes;
            
        } catch (SQLException exception){
            if (consultas != null) consultas.close();
            if (comando != null) comando.close();
            mensagensErro.add(exception.getMessage());
        }
        conexão.close();
        return;
    }
    
}    
