package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import dao.ProfessorDAO;

import model.Professor;


@WebServlet( urlPatterns = {"/cadProfessorOrientador"})
public class ServletCadProfessorOrientador extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	
		Professor professor = new Professor();
		professor.setNome(request.getParameter("nome"));
		professor.setEmail(request.getParameter("email"));
		professor.setMatricula(request.getParameter("matricula"));
		professor.setTelefone(request.getParameter("telefone"));
		
		ProfessorDAO.addProfessor(professor);
		
		
		response.sendRedirect("login.html");
	}
}
