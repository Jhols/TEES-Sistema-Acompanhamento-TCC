package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CalendarioDAO;
import model.Entrega;

/**
 * Servlet implementation class ServletVisualizarCalendarioAdministrador
 */
@WebServlet(name = "VisualizarCalendarioAdministrador", urlPatterns = {"/visualizarCalendarioAdministrador"} )
public class ServletVisualizarCalendarioAdministrador extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletVisualizarCalendarioAdministrador() {
        super();
        // TODO Auto-generated constructor stub
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String opcao = request.getParameter("opcao");
		
		if (opcao == null) {
			// request sem op��o
			return;
		}
		switch (opcao) {

		case "exibir_calendario":
			ArrayList<Entrega> entregas = CalendarioDAO.buscarEntregasPorTurma(Integer.parseInt(request.getParameter("turma")));
			request.setAttribute("entregas", entregas);
			request.getRequestDispatcher("view_administrador/visualizar_calendario_turma.jsp").forward(request, response);
			break;
		default:
			request.getRequestDispatcher("view_administrador/visualizar_calendario_turma.jsp.jsp").forward(request, response);
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
