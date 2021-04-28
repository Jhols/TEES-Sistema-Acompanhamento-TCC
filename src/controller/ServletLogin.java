package controller;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
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
		System.out.println(pessoa.getPerfil());
		request.getSession().setAttribute("perfil", pessoa.getPerfil());
		request.getSession().setAttribute("pessoa", pessoa);
		
		RequestDispatcher requestDispatcher;
		switch(pessoa.getPerfil()) {
			case ADMINISTRADOR:
				requestDispatcher = request.getRequestDispatcher("/WEB-INF/paginas/UsuarioLogado.jsp");
				break;
			case COORDENADOR:
				requestDispatcher = request.getRequestDispatcher("/WEB-INF/paginas/UsuarioLogado.jsp");
				break;
			case NENHHUM:
				requestDispatcher = request.getRequestDispatcher("/WEB-INF/paginas/UsuarioLogado.jsp");
				break;
			case PROFESSOR:
				requestDispatcher = request.getRequestDispatcher("/professorDashboard");
				break;
			case SECRETARIO:
				requestDispatcher = request.getRequestDispatcher("/WEB-INF/paginas/UsuarioLogado.jsp");
				break;
			default:
				requestDispatcher = request.getRequestDispatcher("/WEB-INF/paginas/UsuarioLogado.jsp");
				break;
			
		}
		requestDispatcher.forward(request, response);
	}

}