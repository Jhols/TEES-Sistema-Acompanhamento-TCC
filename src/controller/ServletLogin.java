package controller;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.LoginDAO;



@WebServlet( urlPatterns = {"/login"})
public class ServletLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		//LoginDAO.pesquisaLogin(request.getParameter("login"), request.getParameter("senha"));
		String nomePessoa=LoginDAO.pesquisaLogin(request.getParameter("login"), request.getParameter("senha"));
		request.getSession().setAttribute("nome", nomePessoa);
				
	}

}