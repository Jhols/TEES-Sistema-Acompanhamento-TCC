package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AlunoDAO;
import dao.InscricaoProjetoDAO;
import dao.ProjetoDAO;
import enums.PerfilPessoa;
import model.Aluno;
import model.InscricaoProjeto;
import model.Pessoa;
import model.PessoaFactory;
import model.Projeto;

/**
 * Servlet implementation class InscricaoProjetoServlet
 */
@WebServlet("/InscricaoProjetoServlet")
public class InscricaoProjetoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InscricaoProjetoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String opcao = request.getParameter("opcao");
    	PrintWriter out = null;
    	
    	switch(opcao) {
	    	case "buscar":
	    		break;
	    		
	    	case "incluir":
	    		Boolean resultadoInsert = false;
	    		Projeto projeto = new Projeto();
	    		projeto.setTitulo(request.getParameter("titulo"));
	    		String professor = request.getParameter("professor");
	    		Pessoa aluno = PessoaFactory.getPessoa(PerfilPessoa.ALUNO, null, request.getParameter("alunoMatricula"));
	    		aluno = AlunoDAO.getInstance().findByMatricula(((Aluno) aluno).getMatricula());
	    		InscricaoProjeto inscricao = null;
	    		
	    		projeto = ProjetoDAO.getInstance().findByTitulo(projeto.getTitulo());
	    		
	    		if (projeto.getProfessor().getNome().equals(professor)) {
	    			inscricao = new InscricaoProjeto((Aluno) aluno, projeto);
	    			resultadoInsert = InscricaoProjetoDAO.getInstance().incluir(inscricao);
	    		}
	    		
	    		response.setContentType("text/Plain");
	    		out = response.getWriter();
	    		if (resultadoInsert) {
	    			out.print(aluno.getNome() +" "+ inscricao.getSituacaoInscricao());	    			
	    		}
	    		else {
	    			out.print("Erro: Falha no cadastro da inscricao aluno-projeto");
	    		}
	    		break;
	    		
	    	case "listar":   		
	    		break;
	    		
	    	case "deletar":
	    		Boolean resultadoDelete = false;
	    		Projeto projeto2 = new Projeto();
	    		projeto2.setTitulo(request.getParameter("titulo"));
	    		professor = request.getParameter("professor");
	    		Pessoa aluno2 = PessoaFactory.getPessoa(PerfilPessoa.ALUNO, null, request.getParameter("alunoMatricula"));
	    		aluno2 = AlunoDAO.getInstance().findByMatricula(((Aluno) aluno2).getMatricula());
	    		InscricaoProjeto inscricao2 = null;
	    		
	    		projeto2 = ProjetoDAO.getInstance().findByTitulo(projeto2.getTitulo());
	    		
	    		if (projeto2.getProfessor().getNome().equals(professor)) {
	    			inscricao2 = new InscricaoProjeto((Aluno) aluno2, projeto2);
	    			resultadoDelete = InscricaoProjetoDAO.getInstance().deletar(inscricao2);
	    		}
	    		
	    		response.setContentType("text/Plain");
	    		out = response.getWriter();
	    		if (resultadoDelete) {
	    			out.print(aluno2.getNome() +" "+ inscricao2.getSituacaoInscricao());	    			
	    		}
	    		else {
	    			out.print("Erro: Falha na exclusao da inscricao aluno-projeto");
	    		}
	    		break;
    	}
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
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
