package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import enums.BancoTabela;
import model.Pessoa;
import util.ConnectionFactory;

public class SecretariaDAO {
	private static SecretariaDAO uniqueInstance; //Singleton
	
	public SecretariaDAO() { }
	
	public static synchronized SecretariaDAO getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new SecretariaDAO();
		return uniqueInstance;
	}
	
	public int addSecretaria(Pessoa pessoa) {
		int id = 0;
		String sql;
		Connection conexao = null;
		try {
			conexao = ConnectionFactory.getConnection();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		sql = "INSERT INTO " + BancoTabela.PESSOA + "(nome,email,telefone) values (?,?,?)";
		
		ResultSet resultado = null;
		PreparedStatement prepareStatement = null;
        try {
        	conexao.setAutoCommit(false);

        	sql="SELECT nextval(pg_get_serial_sequence('pessoa','id_pessoa'))"; //Captura o proximo valor da sequencia
            prepareStatement = conexao.prepareStatement(sql);
            
            resultado = prepareStatement.executeQuery();
            resultado.next();
            
            id = resultado.getInt("nextval"); //pega o id da pessoa inserida
            
            //Insere a pessoa no banco
            sql = "INSERT INTO " + BancoTabela.PESSOA + "(id_pessoa, nome,email,telefone) values (?,?,?,?)";
            
            prepareStatement.close();
        	prepareStatement = conexao.prepareStatement(sql);
			
        	prepareStatement.setInt(1, id);
            prepareStatement.setString(2, pessoa.getNome());
            prepareStatement.setString(3, pessoa.getEmail());
            prepareStatement.setString(4, pessoa.getTelefone());
            
            prepareStatement.executeUpdate();

            //Insere o perfil da pessoa no banco
            sql = "INSERT INTO "+ BancoTabela.PERFIL_PESSOA + "(id_pessoa, id_perfil) values (?,?)";
            
            prepareStatement.close();
            prepareStatement = conexao.prepareStatement(sql);
            
            prepareStatement.setInt(1, id);
            prepareStatement.setInt(2, 3);
            
            prepareStatement.executeUpdate();
            
            conexao.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try { conexao.rollback(); } catch (SQLException e1) { e1.printStackTrace();}
        }
        finally {
        	try {resultado.close();} catch (SQLException e) {e.printStackTrace();}
        	try {prepareStatement.close();} catch (SQLException e) {e.printStackTrace();}
        	try {conexao.close();} catch (SQLException e) {e.printStackTrace();}
        }
        return id;
	}
	
}
