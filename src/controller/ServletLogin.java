package controller;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AlunoDAO;
import dao.LoginDAO;
import dao.ProfessorDAO;
import enums.Perfil;
import model.Aluno;
import model.Pessoa;
import model.Professor;



@WebServlet( urlPatterns = {"/login"})
public class ServletLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//LoginDAO.pesquisaLogin(request.getParameter("login"), request.getParameter("senha"));
		Pessoa pessoa = LoginDAO.pesquisaPessoa(request.getParameter("login"), request.getParameter("senha"));
		if (pessoa == null) {
			request.getRequestDispatcher("login.jsp").include(request, response);
		}
		else {
			request.getSession().setAttribute("perfil", pessoa.getPerfil());
			request.getSession().setAttribute("pessoa", pessoa);
			//int id = pessoa.getId();
			request.getSession().setAttribute("idPessoa", pessoa.getId());
			if (pessoa.getPerfil() == Perfil.ALUNO) {
				//id = ((Aluno) pessoa).getIdAluno();
				request.getSession().setAttribute("idAluno", ((Aluno) pessoa).getIdAluno());
				request.getSession().setAttribute("matricula", ((Aluno) pessoa).getMatricula());
			}
			else if (pessoa.getPerfil() == Perfil.PROFESSOR) {
				//id = ((Professor) pessoa).getIdProfessor();
				request.getSession().setAttribute("idProfessor", ((Professor) pessoa).getIdProfessor());
				request.getSession().setAttribute("matricula", ((Professor) pessoa).getMatricula());
			}
			//request.getSession().setAttribute("id", id);
			
			
			switch(pessoa.getPerfil()) {
				case ADMINISTRADOR:
					response.sendRedirect("administradorDashboard");
					break;
				case COORDENADOR:
					break;
				case NENHHUM:
					
					break;
				case PROFESSOR:
					response.sendRedirect("professorDashboard");
					break;
				case SECRETARIO:
					break;
				case ALUNO:
					response.sendRedirect("alunoDashboard");
					break;
				default:
					
					break;
				
			}
		}
	}
}