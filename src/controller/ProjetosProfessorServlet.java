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

		switch (opcao) {

		case "buscar":
			break;

		case "listar":
			if (opcao != null) {
				request.getRequestDispatcher("projetos_professor.jsp").forward(request, response);
			} else {
				request.getRequestDispatcher("buttons.html").forward(request, response);
			}
			break;

		case "deletar":

			break;
		case "alterar_situacao_projeto":
			Integer idProjeto = Integer.parseInt(request.getParameter(("idProjeto")));
			Projeto projeto = ProjetoDAO.getInstance().findById(idProjeto);
			// Falta salvar a nova situacao no banco
			break;

		default:
			if (opcao == null) {

				request.getRequestDispatcher("buttons.html").forward(request, response);
			} else {
				request.getRequestDispatcher("projetos_professor.jsp").forward(request, response);
			}
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
