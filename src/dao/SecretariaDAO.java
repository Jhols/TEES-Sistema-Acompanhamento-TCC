package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import enums.BancoTabela;
import model.Pessoa;
import model.Secretaria;
import util.ConnectionFactory;

public class SecretariaDAO {
	private static SecretariaDAO uniqueInstance; //Singleton
	
	private SecretariaDAO() { }
	
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
	
	// Consulta todos os alunos no banco de dados e os inclui numa lista a ser retornada.
		
		public ArrayList<Secretaria> pesquisarTodasSecretarias() {
			ArrayList<Secretaria> secretarias = new ArrayList<>();
			ResultSet resultado = null;
			try {
				Connection con = ConnectionFactory.getConnection();
				
				String sql = "Select * from " + BancoTabela.PESSOA 
						+ " inner join "+BancoTabela.PERFIL_PESSOA + " on "+ BancoTabela.PESSOA +".id_pessoa= "
						+BancoTabela.PERFIL_PESSOA+".id_pessoa where "+BancoTabela.PERFIL_PESSOA+".id_perfil=3";
				Statement stm = con.createStatement();
				resultado = stm.executeQuery(sql);
				
				while (resultado.next()) {
					Secretaria secretaria = new Secretaria();
					popularSecretaria(secretaria, resultado);
					secretarias.add(secretaria);
				}
						
			}
			
			catch (SQLException e) {
				e.printStackTrace();
			}
			
			return secretarias;
		}
		
		public Secretaria pesquisarSecretariaPorID(int idSecretaria) {
			Secretaria secretaria = null;
			ResultSet resultado = null;
			try {
				Connection con = ConnectionFactory.getConnection();
				
				String sql = "Select * from " + BancoTabela.PESSOA 
						+ " inner join "+BancoTabela.PERFIL_PESSOA + " on "+ BancoTabela.PESSOA +".id_pessoa= "
						+BancoTabela.PERFIL_PESSOA+".id_pessoa where "+BancoTabela.PERFIL_PESSOA+".id_perfil=3 	and "+
						BancoTabela.PESSOA+".id_pessoa=?";
						
						
				PreparedStatement stm = con.prepareStatement(sql);
				stm.setInt(1, idSecretaria);
				resultado = stm.executeQuery();
				if (resultado.next()) {
					secretaria = new Secretaria();
					popularSecretaria(secretaria, resultado);
				}
				
			}
			
			catch (SQLException e) {
				e.printStackTrace();
			}
			
			return secretaria;
		}
		
		public void updateSecretaria(Secretaria secretaria) {
			PessoaDAO.getInstance().updatePessoa(secretaria);
		}
		
		// Popula os campos de secretaria a partir do resultado de uma consulta sql
		private static void popularSecretaria(Secretaria secretaria, ResultSet resultado) throws SQLException {
			secretaria.setEmail(resultado.getString("email"));
			secretaria.setId(resultado.getInt("id_pessoa"));
			secretaria.setNome(resultado.getString("nome"));
			secretaria.setTelefone(resultado.getString("telefone"));
			
		}
		
		
		
	
}
