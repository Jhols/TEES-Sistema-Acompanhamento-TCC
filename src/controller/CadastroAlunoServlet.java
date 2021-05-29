package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import enums.Perfil;
import model.Aluno;
import model.Aluno.StatusAlunoTCC;
import model.Pessoa;
import model.PessoaFactory;

/**
 * Servlet implementation class CadastroAlunoServlet
 */
@WebServlet("/CadastroAlunoServlet")
public class CadastroAlunoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CadastroAlunoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String primeiroNome = request.getParameter("primeiroNome");
    	String sobrenome = request.getParameter("sobrenome");
    	String email = request.getParameter("email");
    	String telefone = request.getParameter("telefone");
    	String matricula = request.getParameter("matricula");
    	
    	//Cria um registro de uma nova pessoa como aluno
    	Pessoa aluno = PessoaFactory.getPessoa(Perfil.ALUNO, primeiroNome + " " + sobrenome, matricula);
    	aluno.setEmail(email);
    	aluno.setTelefone(telefone);
    	
    	((Aluno) aluno).setstatusAlunoTCC(1); //Nao e' aluno ainda. Precisara' da confirmacao do professor para ter acesso ao sistema. 
    	
    	//AlunoDAO incluir aluno no banco.
    	
    	boolean confirmado = true;
    	request.getSession().setAttribute("confirmado", confirmado);
    	request.getRequestDispatcher("confirmacao_cadastro_aluno.jsp").forward(request, response);
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
