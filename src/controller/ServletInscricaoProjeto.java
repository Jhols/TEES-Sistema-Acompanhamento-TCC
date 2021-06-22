package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AlunoDAO;
import dao.InscricaoProjetoDAO;
import dao.ProjetoDAO;
import dao.RelatorioDAO;
import enums.Perfil;
import model.Aluno;
import model.InscricaoProjeto;
import model.Pessoa;
import model.PessoaFactory;
import model.Projeto;
import model.Relatorio;

/**
 * Servlet implementation class InscricaoProjetoServlet
 */
@WebServlet("/InscricaoProjetoServlet")
public class ServletInscricaoProjeto extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
		
    public ServletInscricaoProjeto() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String opcao = request.getParameter("opcao");
    	
    	switch(opcao) {
	    	case "buscar":
	    		buscarInscricaoAssociada(request);
	    		buscarRelatorios(request);
	    		request.getRequestDispatcher("aluno_projeto.jsp").forward(request, response);
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
	    		
	    	case "apresentarRelatorio":
	    		apresentarRelatorio(request, response);
	    		break;
	    		
	    	case "enviarRelatorio":
	    		enviarRelatorio(request, response);
	    		break;
    	}
    }
    
    protected void teste(HttpServletRequest request, HttpServletResponse response) {
    	System.out.println("id da pessoa: " + request.getSession().getAttribute("idPessoa"));
    	System.out.println("id do aluno: " + request.getSession().getAttribute("idAluno"));
    }
    
    //Pesquisa pela inscricao em que um aluno possua um projeto associado
    protected void buscarInscricaoAssociada(HttpServletRequest request) throws IOException, ServletException {
    	InscricaoProjeto inscricao = new InscricaoProjeto();
    	int id = (int) request.getSession().getAttribute("idAluno");
    	inscricao = InscricaoProjetoDAO.getInstance().pesquisarProjetoAssociado(id);
    	request.setAttribute("inscricao", inscricao);
    }
    
    protected void buscarRelatorios(HttpServletRequest request) {
    	ArrayList<Relatorio> relatoriosRecebidos = new ArrayList<Relatorio>();
    	ArrayList<Relatorio> relatoriosEnviados = new ArrayList<Relatorio>();
    	
    	int idPessoa =  (int) request.getSession().getAttribute("idPessoa");
    	
    	relatoriosRecebidos = RelatorioDAO.getInstance().findAllRelatoriosByDestinatario(idPessoa);
    	relatoriosEnviados = RelatorioDAO.getInstance().findAllRelatoriosByAutor(idPessoa);
    	
    	request.setAttribute("relatoriosRecebidos", relatoriosRecebidos);
    	request.setAttribute("relatoriosEnviados", relatoriosEnviados);
    	System.out.println("Relatorios Procurados");
    }
    
    protected void apresentarRelatorio(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	int idRelatorio = Integer.parseInt(request.getParameter("idRelatorio"));
    	Relatorio relatorio = new Relatorio();
    	System.out.println("req " + request.getParameter("idRelatorio"));
    	System.out.println("id " + idRelatorio);
    	relatorio = RelatorioDAO.getInstance().findRelatorioById(idRelatorio);
    	
    	response.setContentType("text/Plain");
		PrintWriter out = response.getWriter();
		
		out.print(relatorio.getTexto());
    }
    
    //Armazena o relatorio enviado no banco.
    protected void enviarRelatorio(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	String titulo = request.getParameter("titulo");
    	int autor = Integer.parseInt(request.getParameter("autor"));
    	int destinatario = Integer.parseInt(request.getParameter("destinatario"));
    	String texto = request.getParameter("texto");
    	Date data = new Date();
    	
    	PrintWriter out;
    	response.setContentType("text/Plain");
    	out = response.getWriter();
    	
    	Relatorio relatorio = new Relatorio(titulo, autor, destinatario, texto, data);
    	if (RelatorioDAO.getInstance().addRelatorio(relatorio)) { //Se conseguir inserir o relatorio no banco
    		out.print("Relatório Enviado!");		
    	}
    	else {
    		out.print("Erro ao enviar o relatório!");
    	}
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
