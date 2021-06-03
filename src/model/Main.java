package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.postgresql.util.PSQLException;

import dao.AlunoDAO;
import dao.InscricaoProjetoDAO;
import dao.ProfessorDAO;
import dao.ProjetoDAO;
import enums.BancoTabela;
import enums.Perfil;
import enums.SituacaoInscricao;
import enums.SituacaoProjeto;
import util.ConnectionFactory;

public class Main {

	public static void main(String[] args) throws SQLException {
		
		var professores = new ArrayList<Professor>();
		int[] ids = {3, 4, 13, 14, 15, 20};
		for (int id : ids) {
			professores.add(ProfessorDAO.getInstance().pesquisarProfessorPorIdPessoa(id));
		}
		var p1 = ProfessorDAO.getInstance().pesquisarProfessorPorIdPessoa(15);
		
		System.out.println(professores);
		System.out.println(p1);
		System.out.println(professores.contains(p1));
	}
	
}
