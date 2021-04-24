package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Statement;

import enums.BancoTabela;
import enums.SituacaoProjeto;
import model.Aluno;
import model.Projeto;
import util.ConnectionFactory;
import model.Projeto;

public class ProjetoDAOImpl implements InterfaceDAO {
	
	private static ProjetoDAOImpl uniqueInstance; //Singleton
	
	private ProjetoDAOImpl() { }
	
	public static synchronized ProjetoDAOImpl getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new ProjetoDAOImpl();
		return uniqueInstance;
	}
	
	@SuppressWarnings("finally")
	public Projeto findById(int id) {
		Projeto projeto = new Projeto();
		ResultSet resultado = null;
		String sql;
		
		Connection conexao = null;
		try {
			conexao = ConnectionFactory.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		sql = "SELECT * FROM " + BancoTabela.PROJETO + " INNER JOIN " + BancoTabela.SITUACAO_PROJETO + 
				" WHERE " + BancoTabela.PROJETO+".id_"+BancoTabela.PROJETO+ " = " + id + 
				" AND " + BancoTabela.PROJETO +".id_situacao = " +  BancoTabela.SITUACAO_PROJETO + ".id_situacao_projeto;";
		
		try {
            Statement stm = conexao.createStatement();
            resultado = stm.executeQuery(sql);
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		try {
			resultado.next();
			projeto.setId(id);
			projeto.setTitulo(resultado.getString(BancoTabela.PROJETO+".titulo"));
			projeto.setDescricao(resultado.getString(BancoTabela.PROJETO+".descricao"));
			projeto.setSituacao(SituacaoProjeto.valueOf(resultado.getString(BancoTabela.SITUACAO_PROJETO+".descricao").toUpperCase()));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			return projeto;
		}
	}

	// Consulta todos os projetos que estão no banco e retorna os que estão disponíveis para o Projeto escolher se candidatar
	@SuppressWarnings("finally")
	public ArrayList<Projeto> pesquisarProjetosDisponiveis() {
			
		ArrayList<Projeto> projetos = new ArrayList<>();
		ResultSet resultado = null;
		String sql;
		
		
		Connection conexao = null;
		try {
			conexao = ConnectionFactory.getConnection();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		sql = "SELECT * FROM " + BancoTabela.PROJETO + " INNER JOIN " + BancoTabela.SITUACAO_PROJETO + 
				" WHERE " + BancoTabela.SITUACAO_PROJETO + ".descricao = 'disponivel'";
		
		
		try {
			Statement stm = conexao.createStatement();
			resultado = stm.executeQuery(sql);
			
			while(resultado.next()) {
				Projeto projeto = new Projeto();
				projeto.setId(resultado.getInt(BancoTabela.PROJETO + ".id_projeto"));
				projeto.setTitulo(resultado.getString(BancoTabela.PROJETO + ".titulo"));
				projeto.setDescricao(resultado.getString(BancoTabela.PROJETO + ".descricao"));
				
				String situacao = resultado.getString(BancoTabela.SITUACAO_PROJETO + ".descricao");
				projeto.setSituacao(SituacaoProjeto.valueOf(situacao.toUpperCase()));
				
				projetos.add(projeto);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			return projetos;			
		}
	}

	@Override
	public boolean incluir() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void atualizar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean deletar() {
		// TODO Auto-generated method stub
		return false;
	}
		
		
}
