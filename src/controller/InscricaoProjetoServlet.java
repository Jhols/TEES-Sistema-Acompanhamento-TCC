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
import enums.Perfil;
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
    	
    	switch(opcao) {
	    	case "buscar":
	    		buscarInscricaoAssociada(request, response);
	    		break;
	    		
	    	case "incluir": //Insere uma inscricao no banco de dados e atualiza a situacao do aluno em relacao ao projeto
	    		incluirInscricao(request, response);
	    		break;
	    		
	    	case "listar":   		
	    		break;
	    		
	    	case "deletar": //Desvincula o aluno do projeto atraves de uma atualizacao da situacao da inscricao.
	    		deletarInscricao(request, response);
	    		break;
	    		
	    	case "teste":
	    		teste(request, response);
	    		break;
    	}
    }
    
    protected void teste(HttpServletRequest request, HttpServletResponse response) {
    	System.out.println("id da pessoa: " + request.getSession().getAttribute("idPessoa"));
    	System.out.println("id do aluno: " + request.getSession().getAttribute("idAluno"));
    }
    
    //Pesquisa pela inscricao em que um aluno possua um projeto associado
    protected void buscarInscricaoAssociada(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	InscricaoProjeto inscricao = new InscricaoProjeto();
    	int id = (int) request.getSession().getAttribute("idAluno");
    	inscricao = InscricaoProjetoDAO.getInstance().pesquisarProjetoAssociado(id);
    	request.setAttribute("inscricao", inscricao);
    	request.getRequestDispatcher("aluno_projeto.jsp").forward(request, response);
    }
    
	protected void incluirInscricao(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out;
		Boolean resultadoInsert = false;
		Projeto projeto = new Projeto();
		projeto.setTitulo(request.getParameter("titulo"));
		String professor = request.getParameter("professor");
		Pessoa aluno = PessoaFactory.getPessoa(Perfil.ALUNO, null, request.getParameter("alunoMatricula"));
		aluno = AlunoDAO.getInstance().findByMatricula(((Aluno) aluno).getMatricula());
		InscricaoProjeto inscricao = null;
		
		//Preenche os dados do projeto a partir do titulo coletado na pagina
		projeto = ProjetoDAO.getInstance().findByTitulo(projeto.getTitulo());
		
		if (projeto.getProfessor().getNome().equals(professor)) { //Se este projeto e' do mesmo professor encontrado no banco...
			//Insere a inscricao no banco de dados
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
	}

	protected void deletarInscricao(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out;
		Boolean resultadoDelete = false;
		Projeto projeto = new Projeto();
		projeto.setTitulo(request.getParameter("titulo"));
		String professor = request.getParameter("professor");
		Pessoa aluno2 = PessoaFactory.getPessoa(Perfil.ALUNO, null, request.getParameter("alunoMatricula"));
		aluno2 = AlunoDAO.getInstance().findByMatricula(((Aluno) aluno2).getMatricula());
		InscricaoProjeto inscricao2 = null;
		
		projeto = ProjetoDAO.getInstance().findByTitulo(projeto.getTitulo());
		
		if (projeto.getProfessor().getNome().equals(professor)) {
			inscricao2 = new InscricaoProjeto((Aluno) aluno2, projeto);
			//Envia a requisicao para desvinculacao do aluno ao projeto
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
