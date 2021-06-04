
package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ProjetoDAO;
import enums.SituacaoProjeto;
import model.Projeto;

/**
 * CADASTRO DE PROJETOS
 */
@WebServlet(name = "CadastroProjeto", urlPatterns = { "/cadastroProjeto" })
public class ServletCadastroProjeto extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletCadastroProjeto() {
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

		case "deletar":
			break;
		case "alterar":
			Integer idProjeto = Integer.parseInt(request.getParameter("idProjeto"));
			String tituloProjeto = request.getParameter("tx_tituloProjeto");
			String descricaoProjeto = request.getParameter("tx_descricao_projeto");
			Projeto projeto = ProjetoDAO.getInstance().pesquisarProjetoPorIdProjeto(idProjeto);
			projeto.setTitulo(tituloProjeto);
			projeto.setDescricao(descricaoProjeto);
			ProjetoDAO.getInstance().atualizar(projeto);
			request.getRequestDispatcher("projetos_professor.jsp").forward(request, response);
			break;
		default:
			request.getRequestDispatcher("cadastro_projeto.jsp").forward(request, response);
			break;
		}
	}
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
