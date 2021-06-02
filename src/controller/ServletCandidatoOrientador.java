package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.ProfessorDAO;
import model.Professor;

/**
 * Servlet implementation class ServletCandidatoOrientador
 */
@WebServlet(name="CandidatoOrientador", urlPatterns ="/candidatoOrientador")
public class ServletCandidatoOrientador extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletCandidatoOrientador() {
        super();
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String opcao = request.getParameter("opcao");
		if (opcao == null) {
			// request sem opção
			return;
		}
		
		Professor professor = ProfessorDAO.pesquisarPorIdProfessor(Integer.parseInt(request.getParameter("idProfessor")));
		Professor.Tipo tipoAntigo = professor.getTipo();
		switch (opcao) {

		case "aceitar_candidatura":
			professor.setStatusOrientador(Professor.fromInt(2));
			
			if (professor.getTipo() == Professor.Tipo.PROFESSOR) {
				professor.setTipo(Professor.Tipo.PROFESSOR_ORIENTADOR);
				professor.setStatusOrientador(Professor.StatusOrientador.ACEITO);
			} else if (professor.getTipo() == Professor.Tipo.PROFESSOR_TCC) {
				professor.setTipo(Professor.Tipo.PROFESSOR_TCC_E_ORIENTADOR);
				professor.setStatusOrientador(Professor.StatusOrientador.ACEITO);
			}
			
			ProfessorDAO.getInstance().alterarStatusCandidatoOrientador(professor, tipoAntigo);
			System.out.println("Aceitou");
			break;

		case "recusar_candidatura":
			professor.setStatusOrientador(Professor.StatusOrientador.REJEITADO);
			ProfessorDAO.getInstance().alterarStatusCandidatoOrientador(professor, tipoAntigo);
			System.out.println("Recusou");
			break;

		default:
			request.getRequestDispatcher("projetos_professor.jsp").forward(request, response);
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
		doGet(request, response);
	}

}