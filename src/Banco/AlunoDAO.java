package Banco;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import enums.BancoTabela;
import enums.PerfilPessoa;
import model.Aluno;
import model.Pessoa;
import model.PessoaFactory;

public class AlunoDAO {
	
	
	@SuppressWarnings("finally")
	private static ResultSet selecionarTodosAlunos() {
		
		ResultSet resultado = null;
		String sql;
		
		//conectarBanco();
		Connection conexao = null;
		try {
			conexao = ConnectionFactory.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		sql = "SELECT * FROM " + BancoTabela.ALUNO + " inner join " + BancoTabela.PESSOA + 
				" where " + BancoTabela.ALUNO +".id_pessoa = " +  BancoTabela.PESSOA + ".id_pessoa;";
		
		try {
            Statement stm = conexao.createStatement();
            resultado = stm.executeQuery(sql);
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		finally {
			return resultado;
		}
		
	}
	
	@SuppressWarnings("finally")
	public static ArrayList<Pessoa> pesquisarTodosAlunos() {
		Pessoa aluno = PessoaFactory.getPessoa(PerfilPessoa.ALUNO);
		ArrayList<Pessoa> alunos = new ArrayList<>();
		ResultSet resultado = selecionarTodosAlunos();
		
		try {
			while(resultado.next()) {
				aluno.setId(Integer.parseInt(resultado.getString(BancoTabela.PESSOA + ".id_pessoa")));
				aluno.setNome(resultado.getString(BancoTabela.PESSOA + ".nome"));
				((Aluno) aluno).setMatricula(resultado.getString(BancoTabela.ALUNO + ".matricula"));
				
				alunos.add(PessoaFactory.getPessoa(PerfilPessoa.ALUNO, aluno.getNome(), ((Aluno) aluno).getMatricula()));
				alunos.get(alunos.size()-1).setId(aluno.getId());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			return alunos;			
		}
		
	}
	
	
}
