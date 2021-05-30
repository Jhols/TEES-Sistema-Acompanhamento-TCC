package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.LoginDAO;

/**
 * Servlet implementation class CadastroSecretariaServlet
 */
@WebServlet("/CadastroSecretariaServlet")
public class CadastroSecretariaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CadastroSecretariaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String opcao = request.getParameter("opcao");
		
		switch (opcao) {
			case "checarUsuario":
				checarUsuario(request, response);
				break;
			case "cadastrarSecretaria":
				cadastrar(request, response);
				break;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	protected void checarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nomeUsuario = request.getParameter("nomeUsuario");
		//Confere no banco se o usuario existe
		int userExiste = LoginDAO.getInstance().existeLogin(nomeUsuario);
				
		response.setContentType("text/Plain");
		PrintWriter out = response.getWriter();
		out.print(userExiste);
		
	}
	
	protected void cadastrar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().print("cadastrou " + request.getParameter("usuario"));
	}

}
