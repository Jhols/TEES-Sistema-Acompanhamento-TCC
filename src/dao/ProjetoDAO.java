package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Statement;

import enums.BancoTabela;
import enums.SituacaoProjeto;
import model.Projeto;
import util.ConnectionFactory;
import model.Projeto;

public class ProjetoDAO {

	// Consulta todos os projetos que estão no banco e retorna os que estão disponíveis para o Projeto escolher se candidatar
	public static ArrayList<Projeto> pesquisarProjetosDisponiveis() {
			
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
		
		
}
