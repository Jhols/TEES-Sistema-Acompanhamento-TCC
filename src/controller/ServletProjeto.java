package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AlunoDAO;
import dao.InscricaoProjetoDAO;
import dao.ProjetoDAO;
import enums.Perfil;
import model.Aluno;
import model.InscricaoProjeto;
import model.Pessoa;
import model.PessoaFactory;
import model.Projeto;

/**
 * Servlet implementation class ProjetoServlet
 */
@WebServlet(urlPatterns = {"/ProjetoServlet"})
public class ServletProjeto extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletProjeto() {
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
				incluirInscricao(request, response);
	    		break;
	    		
	    	case "listar":
	    		listarProjetosDisponiveis(request, response);
	    		break;
    	}
    	
    }

	protected void listarProjetosDisponiveis(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<Projeto> projetos = new ArrayList<Projeto>();
		projetos = ProjetoDAO.getInstance().pesquisarProjetosDisponiveis();
		
		//Procura pelo aluno logado a partir da sessao.
		Pessoa aluno = PessoaFactory.getPessoa(Perfil.ALUNO);
		int idAluno = (int) request.getSession().getAttribute("idAluno");
		aluno = AlunoDAO.getInstance().findById(idAluno);
		
		ArrayList<InscricaoProjeto> inscricoes = new ArrayList<InscricaoProjeto>();
		 //Procura por todos as inscricoes que o aluno acima possui
		inscricoes = InscricaoProjetoDAO.getInstance().findAllByAluno(idAluno);
		
		//Empacota as listas para serem enviadas pra camada de view
		request.setAttribute("projetos", projetos);
		request.setAttribute("inscricoes", inscricoes);
		
		//Direciona o usuario para a pagina de apresentacao dos projetos disponiveis
		request.getRequestDispatcher("procurar_projetos.jsp").forward(request, response);
	}

	protected void incluirInscricao(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Boolean resultadoInsert = false;
		Projeto projeto = new Projeto();
		projeto.setTitulo(request.getParameter("titulo"));
		String professor = request.getParameter("professor");
		Pessoa aluno = PessoaFactory.getPessoa(Perfil.ALUNO, null, request.getParameter("alunoMatricula"));
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
