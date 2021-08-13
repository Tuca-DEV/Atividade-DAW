package beans;

import entidades.Veiculo;
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

@Named(value = "seguradoraDB")
@RequestScoped

public class SeguradoraDB {
    
    @Resource(lookup = "java:/SeguradoraDS")
    private DataSource veiculoDataSource;
    
    private ArrayList<Veiculo> veiculos;
    private Veiculo veiculo;
    private String marcaSelecionado;
    private String donoSelecionado;
    private boolean cadastrar;
    private boolean pesquisar;
    private ArrayList<String> mensagensErro;
    
    @PostConstruct
    public void init(){
        veiculos = new ArrayList();
        veiculo = new Veiculo();
        mensagensErro = new ArrayList();
        cadastrar = false;
        pesquisar = false;
    }

    public DataSource getVeiculoDataSource() {
        return veiculoDataSource;
    }

    public void setFilmeDataSource(DataSource veiculoDataSource) {
        this.veiculoDataSource = veiculoDataSource;
    }

    public String getDonoSelecionado() {
        return donoSelecionado;
    }

    public void setDonoSelecionado(String donoSelecionado) {
        this.donoSelecionado = donoSelecionado;
    }

    
    public ArrayList<String> getMensagensErro() {
        return mensagensErro;
    }

    public void setMensagensErro(ArrayList<String> mensagensErro) {
        this.mensagensErro = mensagensErro;
    }
    
    public String getMarcaSelecionado() {
        return marcaSelecionado;
    }

    public void setMarcaSelecionado(String marcaSelecionado) {
        this.marcaSelecionado = marcaSelecionado;
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

    public ArrayList<Veiculo> getVeiculos() throws SQLException {
        getVeiculosDB();
        return veiculos;
    }

    public void setVeiculos(ArrayList<Veiculo> veiculos) {
        this.veiculos = veiculos;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }
    
    
    public String cadastrarVeiculo() throws SQLException{
        saveVeiculoDB();
        reset();
        return "index";
    }
    
    public void pesquisarVeiculo(){
        pesquisar = true;
        cadastrar = false;
    }
    
    public void reset(){
        veiculo = new Veiculo();
        cadastrar = true;
        pesquisar = false;
    }
    
    
    public Connection getConnectionDB(){
        Connection conexão = null;
        if(veiculoDataSource == null){
            mensagensErro.add("DataSource não acessível");
            return null;
        }
        try{conexão = veiculoDataSource.getConnection();}
        catch (SQLException exception) {mensagensErro.add(exception.getMessage());}
        return conexão;
    }
    
    public void saveVeiculoDB() throws SQLException {
        Connection conexão = getConnectionDB();
        if(conexão == null) return;
        PreparedStatement comando = null;
        try {
            comando = conexão.prepareStatement
                    ("INSERT INTO veiculos (Dono, Placa, Marca, Ano, Modelo) "
                    + "VALUES (?, ?, ?, ?, ?)");
            comando.setString(1, veiculo.getDono());
            comando.setString(2, veiculo.getPlaca());
            comando.setString(3, veiculo.getMarca());
            comando.setString(4, veiculo.getAno());
            comando.setString(5, veiculo.getModelo());
            comando.executeUpdate();
            comando.close();       
        } catch (SQLException exception) {
            if (comando != null) comando.close();
            mensagensErro.add(exception.getMessage());
        }
        conexão.close();
        return;       
    }
    
    public String getVeiculosDB() throws SQLException {
        String próxima_página = "";
        ArrayList<Veiculo> veiculos = new ArrayList();
        Connection conexão = getConnectionDB();
        if(conexão == null) return "";
        PreparedStatement comando = null;
        ResultSet consultas = null;
        try {
            comando = conexão.prepareStatement
                    ("SELECT Dono, Placa, Marca, Ano, Modelo FROM veiculos ORDER BY Marca DESC ");
            consultas = comando.executeQuery();
            while(consultas.next()){
                Veiculo veiculo = new Veiculo(consultas.getString("Dono"), consultas.getString("Placa"),
                    consultas.getString("Marca"), consultas.getString("Ano"),
                    consultas.getString("Modelo"));
                veiculos.add(veiculo);
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
        this.veiculos = veiculos;
        return próxima_página;
    }
   
    public ArrayList<String> getMarcasDB() throws SQLException {
        ArrayList<String> marcas = new ArrayList();
        Connection conexão = getConnectionDB();
        if(conexão == null) return marcas;
        PreparedStatement comando = null;
        ResultSet consultas = null;
        
        try{
            comando = conexão.prepareStatement("SELECT Marca FROM veiculos ORDER BY Marca");
            consultas = comando.executeQuery();
            while(consultas.next()){
                String marca = consultas.getString("Marca");
                int total_marcas = marcas.size();
                if(total_marcas == 0 || (!marca.equals(marcas.get(total_marcas - 1))))
                    marcas.add(marca);
            }
            consultas.close();
            comando.close();
        } catch (SQLException exception){
            if (consultas != null) consultas.close();
            if (comando != null) comando.close();
            mensagensErro.add(exception.getMessage());
            
        }
        conexão.close();
        return marcas;
                
    }
    
    public ArrayList<String> getDonosDB() throws SQLException {
        ArrayList<String> donos = new ArrayList();
        Connection conexão = getConnectionDB();
        if(conexão == null) return donos;
        PreparedStatement comando = null;
        ResultSet consultas = null;
        
        try{
            comando = conexão.prepareStatement("SELECT Dono FROM veiculos ORDER BY Dono");
            consultas = comando.executeQuery();
            while(consultas.next()){
                String dono = consultas.getString("Dono");
                int total_donos = donos.size();
                if(total_donos == 0 || (!dono.equals(donos.get(total_donos - 1))))
                    donos.add(dono);
            }
            consultas.close();
            comando.close();
        } catch (SQLException exception){
            if (consultas != null) consultas.close();
            if (comando != null) comando.close();
            mensagensErro.add(exception.getMessage());
            
        }
        conexão.close();
        return donos;
                
    }
    
    public ArrayList<SelectItem> getDonosItens() throws SQLException {
        ArrayList<SelectItem> itens = new ArrayList();
        for(String dono: getDonosDB()){
            boolean inserido = false;
            for (int n = 0; n < itens.size(); n++){
                if(dono.compareTo(itens.get(n).getLabel()) > 0) continue;
                itens.add(n, new SelectItem(dono, dono));
                inserido = true;
                break;
            }
            if(!inserido) itens.add(new SelectItem(dono, dono));
        }
        return itens;
    }
    
    public ArrayList<SelectItem> getMarcasItens() throws SQLException {
        ArrayList<SelectItem> itens = new ArrayList();
        for(String marca: getMarcasDB()){
            boolean inserido = false;
            for (int n = 0; n < itens.size(); n++){
                if(marca.compareTo(itens.get(n).getLabel()) > 0) continue;
                itens.add(n, new SelectItem(marca, marca));
                inserido = true;
                break;
            }
            if(!inserido) itens.add(new SelectItem(marca, marca));
        }
        return itens;
    }
    
    public ArrayList<String> getInfoVeiculosFiltro() throws SQLException{
        getVeiculosMarcaDonoDB();
        ArrayList<String> info_veiculos_filtro = new ArrayList();
        for(Veiculo veiculo: veiculos){
            String marca = veiculo.getMarca();
            String dono = veiculo.getDono();
            if(marca.equals(marcaSelecionado) && dono.equals(donoSelecionado)){
                boolean inserido = false;
                String ano_veiculo = veiculo.getAno();
                for(int n = 0; n < info_veiculos_filtro.size(); n++){
                    String ano = info_veiculos_filtro.get(n).split(" - ")[0];
                    if(ano_veiculo.compareTo(ano) < 0) continue;
                    info_veiculos_filtro.add(n, veiculo.toString());
                    inserido = true;
                    break;
                }
                if(!inserido) info_veiculos_filtro.add(veiculo.toString());
            }
        }
        return info_veiculos_filtro;
    }
    
    public void getVeiculosMarcaDonoDB() throws SQLException{
        ArrayList<Veiculo> veiculos = new ArrayList();
        Connection conexão = getConnectionDB();
        if(conexão == null) return;
        PreparedStatement comando = null;
        ResultSet consultas = null;
        try {
            comando = conexão.prepareStatement("SELECT * FROM veiculos WHERE Marca = ? AND Dono = ? ORDER BY Marca");
            comando.setString(1, marcaSelecionado);
            comando.setString(2, donoSelecionado);
            consultas = comando.executeQuery();
            while(consultas.next()){
                Veiculo veiculo = new Veiculo(consultas.getString("Dono"), consultas.getString("Marca"),
                    consultas.getString("Placa"), consultas.getString("Ano"),
                    consultas.getString("Modelo"));
                veiculos.add(veiculo);
            }
            consultas.close();
            comando.close();
            this.veiculos = veiculos;
            
        } catch (SQLException exception){
            if (consultas != null) consultas.close();
            if (comando != null) comando.close();
            mensagensErro.add(exception.getMessage());
        }
        conexão.close();
        return;
    }
    
}    

