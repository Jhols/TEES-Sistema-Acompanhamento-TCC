package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AlunoDAO;
import enums.Perfil;
import model.Aluno;
import model.Pessoa;
import model.PessoaFactory;

//CADASTRO DE ALUNOS 
@WebServlet("/CadastroAlunoServlet")
public class ServletCadastroAluno extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletCadastroAluno() {
        super();
        // TODO Auto-generated constructor stub
    }

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String primeiroNome = request.getParameter("primeiroNome");
    	String sobrenome = request.getParameter("sobrenome");
    	String email = request.getParameter("email");
    	String telefone = request.getParameter("telefone");
    	String matricula = request.getParameter("matricula");
    	System.out.println(matricula);
    	
    	//Cria um registro de uma nova pessoa como aluno
    	Aluno aluno = (Aluno) PessoaFactory.getPessoa(Perfil.ALUNO, primeiroNome + " " + sobrenome, matricula);
    	aluno.setEmail(email);
    	aluno.setTelefone(telefone);
    	
    	((Aluno) aluno).setstatusAlunoTCC(2); //TIPO CANDIDATO
    	
    	System.out.println(aluno.getMatricula());
    	
    	//Incluir aluno no banco. Caso haja sucesso na inclusao e' exibida uma pagina de confirmacao do cadastro. Senao apresenta uma tela de erro.
    	boolean confirmado = AlunoDAO.getInstance().addAluno((Aluno) aluno);
   	
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
