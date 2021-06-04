package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CoordenadorDAO;
import dao.LoginDAO;
import dao.SecretariaDAO;
import enums.Perfil;
import model.Pessoa;
import model.PessoaFactory;

/**
 * CADASTRO DE COORDENADOR
 */
@WebServlet("/CadastroCoordenadorServlet")
public class ServletCadastroCoordenador extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletCadastroCoordenador() {
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
			case "cadastrarCoordenador":
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
    	String telefone = request.getParameter("telefone");
    	String usuario = request.getParameter("usuario");
    	String senha = request.getParameter("senha");
    	
    	//Cria um registro de uma nova pessoa como coordenador
    	Pessoa coordenador = PessoaFactory.getPessoa(Perfil.COORDENADOR, primeiroNome + " " + sobrenome);
    	coordenador.setEmail(email);
    	coordenador.setTelefone(telefone);
    	
    	//Incluir coordenador no banco. Caso haja sucesso na inclusao e' exibida uma pagina de confirmacao do cadastro. Senao apresenta uma tela de erro.
    	int idPessoa = CoordenadorDAO.getInstance().addCoordenador(coordenador);
    	boolean loginCriado = LoginDAO.getInstance().addLogin(idPessoa, usuario, senha);
    	
    	if (!loginCriado) {
    		System.out.println("Erro ao incluir a nova conta de coordenador no banco");
    		idPessoa = 0;
    	}
    	else {
    		System.out.println("Login de coordenador " + primeiroNome + " criado com sucesso!");
    		request.getSession().setAttribute("primeiroNome", primeiroNome);
    	}
    	
    	request.getSession().setAttribute("confirmado", loginCriado);
    	request.getRequestDispatcher("view_administrador/confirmacao_cadastro_coordenador.jsp").forward(request, response);
   	
	}

}
