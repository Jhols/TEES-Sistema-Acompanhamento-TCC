package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AlunoDAO;
import dao.ProfessorDAO;
import model.Aluno;
import model.Professor;

@WebServlet(name = "Usuarios", urlPatterns = {"/cadProjeto"})
public class ServletCadProjeto extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String nome_professor = ((String) request.getSession().getAttribute("nome"));
		if (nome_professor == null) {
			nome_professor = "Prof Teste";
		}
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		var writer = response.getWriter();
	  String pagina="<!DOCTYPE html>\r\n"
	  		+ "<html lang=\"pt-br\">\r\n"
	  		+ "   <head>\r\n"
	  		+ "      <meta charset=\"utf-8\">\r\n"
	  		+ "      <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n"
	  		+ "      <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">\r\n"
	  		+ "      <meta name=\"description\" content=\"\">\r\n"
	  		+ "      <meta name=\"author\" content=\"\">\r\n"
	  		+ "      <title>SB Admin 2 - Register</title>\r\n"
	  		+ "      <!-- Custom fonts for this template-->\r\n"
	  		+ "      <link href=\"resources/bootstrap/vendor/fontawesome-free/css/all.min.css\" rel=\"stylesheet\" type=\"text/css\">\r\n"
	  		+ "      <link\r\n"
	  		+ "         href=\"https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i\"\r\n"
	  		+ "         rel=\"stylesheet\">\r\n"
	  		+ "      <!-- Custom styles for this template-->\r\n"
	  		+ "      <link href=\"resources/bootstrap/css/sb-admin-2.css\" rel=\"stylesheet\">\r\n"
	  		+ "   </head>\r\n"
	  		+ "   <body class=\"bg-gradient-primary\">\r\n"
	  		+ "     <form method=\"POST\" action= 'cadProjeto' name=\"form\">"
	  		+ "      <div class=\"container\">\r\n"
	  		+ "         <div class=\"card o-hidden border-0 shadow-lg my-5\">\r\n"
	  		+ "            <div class=\"card-body p-0\">\r\n"
	  		+ "               <!-- Nested Row within Card Body -->\r\n"
	  		+ "               <div class=\"page-header\">\r\n"
	  		+ "                  <h1 class=\"text-center font-weight-bold\">CADASTRO DE PROJETOS</h1>\r\n"
	  		+ "               </div>\r\n"
	  		+ "               <form>\r\n"
	  		+ "                  <div class=\"form-group\">\r\n"
	  		+ "                     <label for=\"exampleFormControlInput1\">Título do projeto</label>\r\n"
	  		+ "                     <input  class=\"form-control\" id=\"exampleFormControlInput1\" >\r\n"
	  		+ "                  </div>\r\n"
	  		
								+"<div class=\"form-group\">\r\n"
									+"<input type=\"text\" value=\"" + nome_professor +  "\" disabled>"
								+"</div>\r\n"
								+"<div class=\"form-group\">\r\n"
									+ "<label for=\"exampleFormControlSelect1\">Situação</label>\r\n"
									+ "<select class=\"btn btn-secondary dropdown-toggle\" id=\"exampleFormControlSelect1\">\r\n"
							            + "<option value=\"1\">EXCLUIDO</option>"
										+ "<option value=\"1\">DISPONIVEL</option>"
										+ "<option value=\"1\">ATIVO</option>"
										+ "<option value=\"1\">DESATIVADO</option>"
									
									+ "</select>\r\n"
								+ "</div>\r\n"	  		
	  		+ "                  <div class=\"form-group\">\r\n"
	  		+ "                     <label for=\"exampleFormControlTextarea1\">Descrição</label>\r\n"
	  		+ "                     <textarea class=\"form-control\" id=\"exampleFormControlTextarea1\" rows=\"3\"></textarea>\r\n"
	  		+ "                  </div>\r\n"
	  		+ "               </form>\r\n"
	  		+ "            </div>\r\n"
	  		+ "         </div>\r\n"
	  		+ "      </div>\r\n"
	  		+ "      <!-- Bootstrap core JavaScript-->\r\n"
	  		+ "      <script src=\"resources/bootstrap/vendor/jquery/jquery.min.js\"></script>\r\n"
	  		+ "      <script src=\"resources/bootstrap/vendor/bootstrap/js/bootstrap.bundle.min.js\"></script>\r\n"
	  		+ "      <!-- Core plugin JavaScript-->\r\n"
	  		+ "      <script src=\"resources/bootstrap/vendor/jquery-easing/jquery.easing.min.js\"></script>\r\n"
	  		+ "      <!-- Custom scripts for all pages-->\r\n"
	  		+ "      <script src=\"resources/bootstrap/js/sb-admin-2.min.js\"></script>\r\n"
	  		+ "      \r\n"
	  		+" </form>"
	  		+ "   </body>\r\n"
	  		+ "</html>";
	  		
	  	writer.write(pagina);
	  
	}
}
