package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.LoginDAO;
import dao.ProfessorDAO;
import dao.SecretariaDAO;
import enums.Perfil;
import model.Pessoa;
import model.PessoaFactory;
import model.Professor;

/**
 * Servlet implementation class CadastroSecretariaServlet
 */
@WebServlet("/CadastroProfessorTCCServlet")
public class ServletCadastroProfessorTCC extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletCadastroProfessorTCC() {
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
			case "cadastrarProfessor":
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
		
		String primeiroNome = request.getParameter("primeiroNome");
    	String sobrenome = request.getParameter("sobrenome");
    	String email = request.getParameter("email");
    	String matricula = request.getParameter("matricula");
    	String telefone = request.getParameter("telefone");
    	String usuario = request.getParameter("usuario");
    	String senha = request.getParameter("senha");
    	
    	//Cria um registro de uma nova pessoa como professor
    	Pessoa professor = PessoaFactory.getPessoa(Perfil.PROFESSOR, primeiroNome + " " + sobrenome, matricula);
    	professor.setEmail(email);
    	professor.setTelefone(telefone);
    	
    	//Incluir professor no banco. Caso haja sucesso na inclusao e' exibida uma pagina de confirmacao do cadastro. Senao apresenta uma tela de erro.
    	int idPessoa = ProfessorDAO.getInstance().incluirProfessor((Professor)professor);
    	boolean loginCriado = LoginDAO.getInstance().addLogin(idPessoa, usuario, senha);
    	
    	if (!loginCriado) {
    		System.out.println("Erro ao incluir a nova conta de professor no banco");
    		idPessoa = 0;
    	}
    	else {
    		System.out.println("Login de professor " + primeiroNome + " criado com sucesso!");
    		request.getSession().setAttribute("primeiroNome", primeiroNome);
    	}
    	
    	request.getSession().setAttribute("confirmado", loginCriado);
    	request.getRequestDispatcher("view_administrador/confirmacao_cadastro_professor.jsp").forward(request, response);
   	
	}

}



