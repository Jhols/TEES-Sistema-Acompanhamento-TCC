package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import enums.BancoTabela;
import enums.PerfilPessoa;
import model.Aluno;
import model.Pessoa;
import model.PessoaFactory;

public class AlunoDAOImpl implements InterfaceDAO {
	
	private static AlunoDAOImpl uniqueInstance; //Singleton
	
	private AlunoDAOImpl() { }
	
	public static synchronized AlunoDAOImpl getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new AlunoDAOImpl();
		return uniqueInstance;
	}
	
	@SuppressWarnings("finally")
	public Aluno findById(int id) {
		Pessoa aluno = PessoaFactory.getPessoa(PerfilPessoa.ALUNO);
		
		ResultSet resultado = PessoaDAOImpl.getInstance().selecionarPorPerfil(BancoTabela.ALUNO, id);
		
		try {
			resultado.next();
			aluno.setId(id);
			aluno.setNome(resultado.getString(BancoTabela.PESSOA+".nome"));
			((Aluno) aluno).setMatricula(resultado.getString(BancoTabela.ALUNO+".matricula"));
			aluno.setEmail(resultado.getString(BancoTabela.PESSOA+".email"));
			aluno.setTelefone(resultado.getString(BancoTabela.PESSOA+".telefone"));
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			return (Aluno) aluno;
		}
		
	}
	
	//Consulta todos os alunos no banco de dados e os inclui numa lista a ser retornada.
	@SuppressWarnings("finally")
	public ArrayList<Aluno> pesquisarTodosAlunos() {
		ArrayList<Aluno> alunos = new ArrayList<>();
		
		ResultSet resultado = PessoaDAOImpl.getInstance().selecionarPorPerfil(BancoTabela.ALUNO); //Realiza a consulta no banco
		
		try {
			while(resultado.next()) { //Atribui os valores encontrados em uma lista de objetos de alunos
				Aluno aluno = ((Aluno) PessoaFactory.getPessoa(PerfilPessoa.ALUNO, resultado));
				aluno.setMatricula(resultado.getString(PerfilPessoa.ALUNO + ".matricula"));
				alunos.add(aluno);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try { resultado.close(); } catch (Exception e) { } //Fecha o resultado da conexao
			return alunos;			
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
