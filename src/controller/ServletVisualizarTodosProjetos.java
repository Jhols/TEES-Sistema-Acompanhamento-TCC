package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.InscricaoProjetoDAO;
import dao.ProjetoDAO;
import enums.SituacaoProjeto;
import model.InscricaoProjeto;
import model.Projeto;

/**
 * Servlet implementation class ServletVisualizarTodosProjetos
 */
@WebServlet("/VisualizarTodosProjetos")
public class ServletVisualizarTodosProjetos extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletVisualizarTodosProjetos() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String opcao = request.getParameter("opcao");
		String perfil = request.getParameter("prf");
		
		switch (opcao) {
			case "pesquisarProjetos":
				pesquisarProjetos(request, response);
				
				switch (perfil) {
				case "1":
					request.getRequestDispatcher("view_secretaria/visualizar_projetos.jsp").forward(request, response);
					break;
				case "2":
					request.getRequestDispatcher("view_coordenador/visualizar_projetos.jsp").forward(request, response);
					break;
				case "3":
					request.getRequestDispatcher("view_professor/visualizar_projetos.jsp").forward(request, response);
					break;
				default:
					response.getWriter().append("Served at: ").append(request.getContextPath());
				}
				break;
			case "consultarProjeto":
				int idProjeto = Integer.parseInt(request.getParameter("id"));
				consultarProjeto(idProjeto, request, response);
				
				switch (perfil) {
					case "1":
						request.getRequestDispatcher("view_secretaria/visualizar_projeto_individual.jsp").forward(request, response);
						break;
					case "2":
						request.getRequestDispatcher("view_coordenador/visualizar_projeto_individual.jsp").forward(request, response);
						break;
					case "3":
						request.getRequestDispatcher("view_professor/visualizar_projeto_individual.jsp").forward(request, response);
						break;
					default:
						response.getWriter().append("Served at: ").append(request.getContextPath());
				}
				break;
			default:
				response.getWriter().append("Served at: ").append(request.getContextPath());
		}
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	//Procura todos os projetos que nao sejam excluidos ou desativados.
	public static void pesquisarProjetos(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//Obtem os projetos disponiveis atraves da servlet
		ArrayList<Projeto> projetos = new ArrayList<Projeto>();
		//Procura pelos projetos que nao sejam excluidos ou desativados.
		projetos = ProjetoDAO.getInstance().pesquisarProjetosDisponiveisEAtivos();
		
		request.getSession().setAttribute("projetos", projetos);
		
	}
	
	//Consulta os dados de um projeto
	public static void consultarProjeto(int idProjeto, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		Projeto projeto = new Projeto();
		projeto = ProjetoDAO.pesquisarProjetoPorIdProjeto(idProjeto);
		
		//Obtem os alunos que estao vinculados aos projetos atraves de suas inscricoes
		ArrayList<InscricaoProjeto> inscricoes = new ArrayList<InscricaoProjeto>();
		
		//Confere a situacao do projeto e armazena o aluno associado ou os candidatos ao projeto
		if (projeto.getSituacao() == SituacaoProjeto.ATIVO) {
			//Armazena o aluno associado
			inscricoes.add(InscricaoProjetoDAO.getInstance().pesquisarAlunoAssociadoAoProjeto(projeto.getId()));
		}
		else if (projeto.getSituacao() == SituacaoProjeto.DISPONIVEL) {
			//Armazena todos os candidatos ao projeto
			inscricoes = InscricaoProjetoDAO.getInstance().pesquisarInscricoesDeCandidatoParaProjeto(projeto);
		}
		
		request.getSession().setAttribute("projeto", projeto);
		request.getSession().setAttribute("inscricoes", inscricoes);
		
	}

}
