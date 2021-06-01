package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ProfessorDAO;
import dao.TurmaDAO;
import model.Professor;
import model.Secretaria;
import model.Turma;


@WebServlet( urlPatterns = {"/cadTurmasTcc"})
public class ServletCadTurma extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		var secretaria = (Secretaria) request.getSession().getAttribute("pessoa");
		
		if (secretaria == null) {
			response.sendRedirect("login.html");
		}
		System.out.println(request.getParameter("idTurma"));
		if (request.getParameter("idTurma") == null) {
			// Criando Turma nova
			Turma turma = new Turma();
			turma.setNome(request.getParameter("nome"));
			turma.setSemestre(request.getParameter("semestre"));
			
			int idTurma = TurmaDAO.getInstance().addTurma(turma);
			
			vincularProfessores(request, idTurma);
			
			response.sendRedirect("secretariaDashboard?turma=ok");
		}
		else {
			// Editando turma existente
			int idTurma = Integer.parseInt(request.getParameter("idTurma"));
			Turma turma = TurmaDAO.getInstance().pesquisarTurmaPorId(idTurma);
			turma.setNome(request.getParameter("nome"));
			turma.setSemestre(request.getParameter("semestre"));
			
			TurmaDAO.getInstance().atualizaTurma(turma);
			
			vincularProfessores(request, idTurma);
			
			response.sendRedirect("exibirTurma?turma=edited");
		}
	}
	
	private void vincularProfessores(HttpServletRequest request, int idTurma) {
		
		int idProf1 = Integer.parseInt(request.getParameter("professor1"));
		int idProf2 = 0;
		if (request.getParameter("professor2").length() > 0) {
			idProf2 = Integer.parseInt(request.getParameter("professor2"));
		}
		Professor prof1 = ProfessorDAO.pesquisarPorIdProfessor(idProf1);
		Professor prof2 = ProfessorDAO.pesquisarPorIdProfessor(idProf2);
		System.out.println(prof1);
		System.out.println(prof2);
		ArrayList<Professor> novosProfessores = new ArrayList<Professor>();
		
		ArrayList<Professor> professoresVinculados = TurmaDAO.getInstance().pesquisarProfessoresVinculados(idTurma);
		if (!professoresVinculados.contains(prof1)) 
		{
			TurmaDAO.getInstance().vincularProfessor(idTurma, idProf1);
			System.out.println("Vinculando Prof "+prof1.getNome());
		}
		if (prof2 != null && !professoresVinculados.contains(prof2)) 
		{
			TurmaDAO.getInstance().vincularProfessor(idTurma, idProf2);
			System.out.println("Vinculando Prof "+prof2.getNome());
		}
		for (Professor professor : professoresVinculados) {
			if (!novosProfessores.contains(professor)) {
				TurmaDAO.getInstance().desvincularProfessor(idTurma, professor.getIdProfessor());
				System.out.println("Desvinculando Prof " + professor.getNome());
			}
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		var secretaria = (Secretaria) request.getSession().getAttribute("pessoa");
		
		if (secretaria == null) {
			response.sendRedirect("login.html");
		}
		
		Turma turmaEditada = null;
		ArrayList<Professor> professoresVinculados = new ArrayList<Professor>();
		if (request.getParameter("turma") != null) {
			int idTurma = Integer.parseInt(request.getParameter("turma"));
			turmaEditada = TurmaDAO.getInstance().pesquisarTurmaPorId(idTurma);
			System.out.println("Turma Editada: " + turmaEditada);
			professoresVinculados = TurmaDAO.getInstance().pesquisarProfessoresVinculados(idTurma);
			for (Professor professor: professoresVinculados) {
				System.out.println(professor);
			}
		}
		
		ArrayList<Professor> professoresTCC = ProfessorDAO.getInstance().pesquisarProfessoresTCC();
		
		String options1 = "<option value=\"\">Selecione Professor</option>\r\n";
		String options2 = "<option value=\"\">Selecione Professor</option>\r\n";
		for (Professor professor : professoresTCC) {
			String sel1 = "";
			if (professoresVinculados.size() > 0 && professor.getId() == professoresVinculados.get(0).getId()) {
				sel1 = "selected";
			}
			String sel2 = "";
			if (professoresVinculados.size() > 1 && professor.getId() == professoresVinculados.get(1).getId()) {
				sel2 = "selected";
			}
			options1 += "<option value=\""+professor.getIdProfessor()+"\" " + sel1 + " >"+professor.getNome()+"</option>\r\n";
			options2 += "<option value=\""+professor.getIdProfessor()+"\" " + sel2 + " >"+professor.getNome()+"</option>\r\n";
		}
		
		String html = "<!DOCTYPE html>\r\n"
			+ "<html lang=\"pt-br\">\r\n"
			+ "    <head>\r\n"
			+ "        <meta charset=\"utf-8\" />\r\n"
			+ "        <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\r\n"
			+ "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\" />\r\n"
			+ "        <meta name=\"description\" content=\"\" />\r\n"
			+ "        <meta name=\"author\" content=\"\" />\r\n"
			+ "        <title>SB Admin 2 - Register</title>\r\n"
			+ "        <!-- Custom fonts for this template-->\r\n"
			+ "        <link href=\"resources/bootstrap/vendor/fontawesome-free/css/all.min.css\" rel=\"stylesheet\" type=\"text/css\" />\r\n"
			+ "        <link href=\"https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i\" rel=\"stylesheet\" />\r\n"
			+ "        <!-- Custom styles for this template-->\r\n"
			+ "        <link href=\"resources/bootstrap/css/sb-admin-2.css\" rel=\"stylesheet\" />\r\n"
			+ "    </head>\r\n"
			+ "    <body class=\"bg-gradient-primary\">\r\n"
			+ "        <form method=\"POST\" action=\"cadTurmasTcc\" name=\"form\" onSubmit=\"return ( verifica() )\" name=\"frmEnvia\">\r\n"
			+ "            <div class=\"container\">\r\n"
			+ "                <div class=\"card o-hidden border-0 shadow-lg my-5\">\r\n"
			+ "                    <div class=\"card-body\">\r\n"
			+ "                        <!-- Nested Row within Card Body -->\r\n"
			+ "                        <div class=\"page-header\">\r\n"
			+ "                            <h1 class=\"text-center font-weight-bold\">CADASTRO DE TURMA</h1>\r\n"
			+ "                            \r\n"
			+ "                        </div>\r\n"
			+ "                        <form>\r\n"
			+ "                            <div class=\"form-group\">\r\n"
			+ "                                <label for=\"exampleFormControlInput1\">Nome</label>\r\n"
			+ "                                <input class=\"form-control\" id=\"nome\" name=\"nome\" required ";
			
			if (turmaEditada != null) {
				html += "value=\""+turmaEditada.getNome()+"\"";
			}
		
			html += "/>\r\n"
			+ "                            </div>\r\n"
			+ "                            <div class=\"form-group\">\r\n"
			+ "                                <label for=\"exampleFormControlInput1\">Semestre</label>\r\n"
			+ "                                <input class=\"form-control\" id=\"semestre\" onBlur=\"checkSemestre()\" placeholder=\"ano.semestre\" name=\"semestre\" required ";
			
			if (turmaEditada != null) {
				html += "value=\""+turmaEditada.getSemestre()+"\"";
			}
			
			html += ">\r\n"
			+ "                            </div>\r\n"
			+ "                            <div class=\"form-group\">\r\n"
			+ "                            	<label for=\"exampleFormControlInput1\">Professor 1</label>\r\n"
			+ "	                            <select onChange=\"checkProf1()\" name=\"professor1\" class=\"form-control\" id=\"professor1\" required>\r\n"
			+ options1
			
			+ "								</select>\r\n"
			+ "							</div>\r\n"
			+ "							\r\n"
			+ "							<div class=\"form-group\">\r\n"
			+ "							<label for=\"exampleFormControlInput1\">Professor 2 (opcional)</label>\r\n"
			+ "								<select onChange=\"checkProf1()\" name=\"professor2\" class=\"form-control\" id=\"professor2\">\r\n"
			+ options2
			+ "								</select>\r\n"
			+ "							</div>\r\n"
			+ "							\r\n";
			
			if (turmaEditada != null) {
				html += "<input type=\"hidden\" name=\"idTurma\" value=\"" + turmaEditada.getId()+ "\">";
			}
			
			
			html += "                        </form>\r\n"
			+ "                        <input class=\"btn btn-primary\" type=\"submit\" value=\"Submit\" />\r\n"
			+ "                        <a class=\"btn btn-primary\" align=\"center\" href=\"exibirTurma\" role=\"button\">Voltar</a>\r\n"
			+ "\r\n"
			+ "                        \r\n"
			+ "                    </div>\r\n"
			+ "                </div>\r\n"
			+ "            </div>\r\n"
			+ "            <!-- Bootstrap core JavaScript-->\r\n"
			+ "            <script src=\"resources/bootstrap/vendor/jquery/jquery.min.js\"></script>\r\n"
			+ "            <script src=\"resources/bootstrap/vendor/bootstrap/js/bootstrap.bundle.min.js\"></script>\r\n"
			+ "            <!-- Core plugin JavaScript-->\r\n"
			+ "            <script src=\"resources/bootstrap/vendor/jquery-easing/jquery.easing.min.js\"></script>\r\n"
			+ "            <!-- Custom scripts for all pages-->\r\n"
			+ "            <script src=\"resources/bootstrap/js/sb-admin-2.min.js\"></script>\r\n"
			+ "            <script>\r\n"
			+ "        function checkSemestre() {\r\n"
			+ "        	var value = document.forms[0].semestre.value;\r\n"
			+ "        	var parts = value.split(\".\")\r\n"
			+ "        	if (parts.length != 2 || parts[0].length != 4 || parts[1].length != 1) {\r\n"
			+ "        		// erro\r\n"
			+ "        		alert(\"campo Semestre precisar estar no formato ano.semestre (ex: 2021.1)\");\r\n"
			+ "        		return false;\r\n"
			+ "        	}\r\n"
			+ "       		var currentYear = new Date().getFullYear();\r\n"
			+ "       		if (parts[0] < currentYear) {\r\n"
			+ "       			// erro\r\n"
			+ "       			alert(\"campo Semestre deve conter um ano igual ou maior ao atual\");\r\n"
			+ "       			return false;\r\n"
			+ "       		}\r\n"
			+ "       		\r\n"
			+ "	       	return true;\r\n"
			+ "        }\r\n"
			+ "        \r\n"
			+ "        </script>\r\n"
			+ "        </form>\r\n"
			+ "        \r\n"
			+ "   <script> function checkProf1() {\r\n"
			+ "        	var value1 = document.forms[0].professor1.value;\r\n"
			+ "        	var value2 = document.forms[0].professor2.value;\r\n"
			+ "        	if (value1 == value2 && value1 != 0) {\r\n"
			+ "        		alert(\"Professor 1 e Professor 2 não podem ser o mesmo\");\r\n"
			+ "        		document.forms[0].professor2.value = \"\";\r\n"
			+ "        		return false;\r\n"
			+ "        	}\r\n"
			+ "        	return true\r\n"
			+ "        }</script>"
			+ "    </body>\r\n"
			+ "    \r\n"
			+ "</html>\r\n"
			+ "";
			
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(html);
		}
	
}