package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ProjetoDAO;
import enums.SituacaoProjeto;
import model.Projeto;

/**
 * Servlet implementation class ProjetosProfessorServlet
 */
@WebServlet(name = "ProjetosProfessor", urlPatterns = { "/projetosProfessor" })
public class ProjetosProfessorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProjetosProfessorServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String opcao = request.getParameter("opcao");
		
		if (opcao == null) {
			// request sem opção
			return;
		}
		switch (opcao) {

		case "buscar":
			break;

		case "listar":
			if (opcao != null) {
				request.getRequestDispatcher("projetos_professor.jsp").forward(request, response);
			} 
			break;

		case "deletar":

			break;
		case "alterar_situacao_projeto":
			Integer idProjeto = Integer.parseInt(request.getParameter(("idProjeto")));
			String situacaoString = request.getParameter("novaSituacao") ;
			SituacaoProjeto novaSituacao = SituacaoProjeto.fromString(situacaoString);
			Projeto projeto = ProjetoDAO.getInstance().findById(idProjeto);
			projeto.setSituacao(novaSituacao);
			ProjetoDAO.getInstance().atualizar(projeto);
			break;

		default:
			request.getRequestDispatcher("projetos_professor.jsp").forward(request, response);
			break;
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}