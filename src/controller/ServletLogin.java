package controller;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.LoginDAO;
import model.Pessoa;



@WebServlet( urlPatterns = {"/login"})
public class ServletLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//LoginDAO.pesquisaLogin(request.getParameter("login"), request.getParameter("senha"));
		Pessoa pessoa = LoginDAO.pesquisaPessoa(request.getParameter("login"), request.getParameter("senha"));
		request.getSession().setAttribute("perfil", pessoa.getPerfil());
		request.getSession().setAttribute("pessoa", pessoa);
		
		
		switch(pessoa.getPerfil()) {
			case ADMINISTRADOR:
				break;
			case COORDENADOR:
				break;
			case NENHHUM:
				
				break;
			case PROFESSOR:
				response.sendRedirect("professorDashboard");
				break;
			case SECRETARIO:
				break;
			case ALUNO:
				response.sendRedirect("alunoDashboard");
				break;
			default:
				
				break;
			
		}
	}

}