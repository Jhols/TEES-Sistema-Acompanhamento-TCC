package model;

import java.sql.SQLException;
import java.util.ArrayList;

import dao.CalendarioDAO;
import dao.ProfessorDAO;

public class Main {

	public static void main(String[] args) throws SQLException {
		
		CalendarioEntrega calendario = new CalendarioEntrega();
		calendario = CalendarioDAO.getInstance().findByIdTurma(11);
		
		if(calendario == null)
			System.out.println("calendario nulo");
		else
			System.out.println(calendario.getDescricao());
	}
	
}
