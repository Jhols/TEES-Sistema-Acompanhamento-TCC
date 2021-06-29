package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ServletDashboardAdministrador
 */
@WebServlet(name = "dashboardAdministrador", urlPatterns = {"/administradorDashboard"})
public class ServletDashboardAdministrador extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletDashboardAdministrador() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String opcao = request.getParameter("opcao");
		HttpSession session = request.getSession();
		
		System.out.println("Login> id: " + session.getId());
		if (ServletLogin.getIdSessao() != session.getId())
			response.sendRedirect("login.html");
		else {
			if (opcao == null) {
				request.getRequestDispatcher("view_administrador/dashboardAdministrador.jsp").forward(request, response);
				String idSessao = request.getSession().getId();
			}
			else {
				switch (opcao) {
				case "cadSecretaria":
					request.getRequestDispatcher("view_administrador/cadastro_secretaria.jsp").forward(request, response);
					break;
				case "cadCoordenador":
					request.getRequestDispatcher("view_administrador/cadastro_coordenador.jsp").forward(request, response);
					break;
				case "cadProfessor":
					request.getRequestDispatcher("view_administrador/cadastro_professor_tcc.jsp").forward(request, response);
				case "visualizar_turmas":
					request.getRequestDispatcher("view_administrador/visualizar_turmas_tcc.jsp").forward(request, response);
					break;
				case "sair": //Limpa o cache, destroi a sessao e redireciona pra tela de login.
					//request.getRequestDispatcher("ServletLogout").forward(request, response);
					response.sendRedirect("ServletLogout");
					return;
					//break;
				default:
					//request.getRequestDispatcher("dashboardAdministrador.jsp").forward(request, response);
					break;
				}
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
