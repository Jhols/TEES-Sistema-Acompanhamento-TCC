package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import enums.BancoTabela;
import enums.SituacaoInscricao;
import model.InscricaoProjeto;
import util.ConnectionFactory;

public class InscricaoProjetoDAOImpl implements InterfaceDAO {
	
	private static InscricaoProjetoDAOImpl uniqueInstance; //Singleton
	
	private InscricaoProjetoDAOImpl() { }
	
	public static InscricaoProjetoDAOImpl getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new InscricaoProjetoDAOImpl();
		return uniqueInstance;
	}
	
	public ArrayList<InscricaoProjeto> pesquisarTodasInscricoes() {
		
		ArrayList<InscricaoProjeto> inscricoes = new ArrayList<>();		
		ResultSet resultado = null;
		String sql;
		
		Connection conexao = null;
		try {
			conexao = ConnectionFactory.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		sql = "SELECT * FROM " + BancoTabela.INSCRICAO_ALUNO_PROJETO + " INNER JOIN " + BancoTabela.SITUACAO_PROJETO +
				" WHERE " + BancoTabela.INSCRICAO_ALUNO_PROJETO + ".id_situacao_aluno_projeto = " + BancoTabela.SITUACAO_ALUNO_PROJETO + ".id_situacao_aluno_projeto;";
		
		try {
			Statement stm = conexao.createStatement();
			resultado = stm.executeQuery(sql);
			
			while (resultado.next()) {
				InscricaoProjeto inscricao = new InscricaoProjeto();
				
				inscricao.getAluno().setId(resultado.getInt(BancoTabela.INSCRICAO_ALUNO_PROJETO + ".id_aluno"));
				inscricao.getProjeto().setId(resultado.getInt(BancoTabela.INSCRICAO_ALUNO_PROJETO + ".id_projeto"));
				inscricao.setSituacaoInscricao(SituacaoInscricao.valueOf(resultado.getString(BancoTabela.SITUACAO_ALUNO_PROJETO + ".descricao").toUpperCase()));
				
				inscricao.setAluno(AlunoDAOImpl.getInstance().findById(inscricao.getAluno().getId()));
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return inscricoes;
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
