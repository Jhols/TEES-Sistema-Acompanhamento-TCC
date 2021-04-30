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
 * Servlet implementation class ProjetoServlet
 */
@WebServlet(urlPatterns = {"/ProjetoServlet"})
public class ProjetoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProjetoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String opcao = request.getParameter("opcao");
    	
    	switch(opcao) {
	    	case "buscar":
	    		break;
	    	case "incluir":
	    		break;
	    	case "ajax":
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
	    		PrintWriter out = response.getWriter();
	    		if (resultadoInsert) {
	    			out.print(aluno.getNome() +" "+ inscricao.getSituacaoInscricao());	    			
	    		}
	    		else {
	    			out.print("Erro: Falha no cadastro da inscricao aluno-projeto");
	    		}
	    		break;
	    		
	    	case "listar":
	    		//ArrayList<model.Projeto> projetos = new ArrayList<>();
	    		if (opcao == null) {
	    			request.getRequestDispatcher("buttons.html").forward(request, response);
	    		}
	    		else {
	    			request.getRequestDispatcher("procurar_projetos.jsp").forward(request, response);
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
