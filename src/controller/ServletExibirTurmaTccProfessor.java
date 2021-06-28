package controller;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CalendarioDAO;
import dao.TurmaDAO;
import model.Aluno;
import model.CalendarioEntrega;
import model.Entrega;
import model.Professor;
import model.Turma;

//TELA DE PROFESSOR TCC QUE VISUALIZA AS SUAS TURMAS DO SEMESTRE ATUAL
@WebServlet( urlPatterns = {"/visualizarTurmasTccProfessor"})
public class ServletExibirTurmaTccProfessor extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Tentar pegar o professor que esta logado atualmente
		var professor = (Professor) request.getSession().getAttribute("pessoa");
		int idTurma= (Integer.parseInt(request.getParameter("turma")));
		if (professor == null) {
			response.sendRedirect("login.html");
			return;
		}
		
		Turma turma = new Turma();
		turma = TurmaDAO.getInstance().pesquisarTurmaPorId(idTurma);
		ArrayList<Aluno> alunosVinculados = TurmaDAO.getInstance().pesquisarAlunosPorTurma(idTurma);
		// uma lista de linha para preencher a tabela de visualizacao
		var linhas = new ArrayList<HashMap<String, String>>();
		
		for (Aluno aluno : alunosVinculados) {
			//preencher os dados que serao mostrados na tabela
			// ou que serao usados pelos botoes (aceitar e rejeitar)
			var linha = new HashMap<String, String>();
			linha.put("nome", aluno.getNome());
			linha.put("matricula", aluno.getMatricula());
			linhas.add(linha);
		}
		
		CalendarioEntrega calendario = new CalendarioEntrega();
		calendario = CalendarioDAO.getInstance().findByIdTurma(idTurma);
		ArrayList<Entrega> entregas = new ArrayList<Entrega>();
		entregas = CalendarioDAO.buscarEntregasPorTurma(turma.getId());
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String html = "<!DOCTYPE html>\r\n"
		+ "<html lang=\"en\">\r\n"
		+ "\r\n"
		+ "<head>\r\n"
		+ "\r\n"
		+ "    <meta charset=\"utf-8\">\r\n"
		+ "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n"
		+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">\r\n"
		+ "    <meta name=\"description\" content=\"\">\r\n"
		+ "    <meta name=\"author\" content=\"\">\r\n"
		+ "\r\n"
		+ "    <title>Alunos Candidatos</title>\r\n"
		+ "\r\n"
		+ "    <!-- Custom fonts for this template -->\r\n"
		+ "    <link href=\"resources/bootstrap/vendor/fontawesome-free/css/all.min.css\" rel=\"stylesheet\" type=\"text/css\">\r\n"
		+ "    <link\r\n"
		+ "        href=\"https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i\"\r\n"
		+ "        rel=\"stylesheet\">\r\n"
		+ "\r\n"
		+ "    <!-- Custom styles for this template -->\r\n"
		+ "    <link href=\"resources/bootstrap/css/sb-admin-2.min.css\" rel=\"stylesheet\">\r\n"
		+ "	   <script src=\"resources/bootstrap/vendor/jquery/jquery.js\"></script>\r\n"
		+ "\r\n"
		+ "    <!-- Custom styles for this page -->\r\n"
		+ "    <link href=\"resources/bootstrap/vendor/datatables/dataTables.bootstrap4.min.css\" rel=\"stylesheet\">\r\n"
		+ "\r\n"
		+ "</head>\r\n"
		+ "\r\n"
		+ "<body id=\"page-top\">\r\n"
		+ "\r\n"
		+ "    <!-- Page Wrapper -->\r\n"
		+ "    <div id=\"wrapper\" class=\"card o-hidden border-0 shadow-lg my-5\">\r\n"
		+ "    \r\n"
		+ "        <!-- Content Wrapper -->\r\n"
		+ "        <div id=\"content-wrapper\" class=\"card-body\">\r\n"
		+ "\r\n"
		+ "            <!-- Main Content -->\r\n"
		+ "            <div id=\"content\">\r\n"
		+ "\r\n"
		+ "                <!-- Begin Page Content -->\r\n"
		+ "                <div class=\"container-fluid\">\r\n"
		+ "\r\n"
		+ "                    <!-- Page Heading -->\r\n"
		+ "                     <h1 class=\"h3 mb-2 text-gray-800\">Informações da turma</h1>\r\n"
		+ " 					<h5 class=\"h5 mb-2 text-gray-800\"> Nome: " + turma.getNome() + " </h5>\r\n"
		+ " 					<h5 class=\"h5 mb-2 text-gray-800\"> Semestre: " + turma.getSemestre() + " </h5>\r\n"
		+ "						<br>\r\n"
		+ "					    <h4 class=\"h4 mb-2 text-gray-800\"> Calendário de Entregas </h4>\r\n"
		+ "                    <!-- DataTales Example -->\r\n"
		+ "                    <div class=\"card shadow mb-4\">\r\n"
		+ "                        \r\n"
		+ "                        <div class=\"card-body\">\r\n"
		+ "                            <div class=\"table-responsive\">\r\n";
								if (!calendario.getDescricao().equals("")) {
		html += "						  <p> "+ calendario.getDescricao() +" </p>";
								}
		html += "						  <a href=\"#\" data-toggle=\"modal\" data-target=\"#ExemploModalCentralizado\"> + Criar uma nova tarefa </a><br><br>"
	    
		+ "                                <table class=\"table table-bordered\" id=\"dataTable\" width=\"100%\" cellspacing=\"0\">\r\n"
		+ "                                    <thead>\r\n"
		+ "                                        <tr>\r\n"
		+ "                                            <th>Título</th>\r\n"
		+ "                                            <th>Instruções</th>\r\n"
		+ "                                            <th>Prazo</th>\r\n"
		+ "                                        </tr>\r\n"
		+ "                                    </thead>\r\n"
		+ "                                    <tbody id=\"tabEntregas\">\r\n";
												if (entregas.isEmpty()) {													
		html += " 									<tr><td colspan=3> Não há nenhuma entrega cadastrada no momento... </td></tr>";
												} else {
													int x = 1;
													for (Entrega entrega : entregas) {
		html += "  										<tr id=\"tarefa"+x+"\">\r\n"
		+ "													<td> "+ entrega.getTitulo() +" </td>\r\n"
		+ "													<td> "+ entrega.getInstrucao() +" </td>\r\n"
		+ "													<td> "+ new SimpleDateFormat("dd/MM/yyyy").format(entrega.getDataPrazo()).toString() +" </td>\r\n"
		+ "												</tr>\r\n";
														x++;
													}
												}
		html += "                              </tbody>\r\n"
		+ "                                </table>\r\n"
		+ "                            </div>\r\n"
		+ "                        </div>\r\n"
		+ "                    </div>\r\n"
		+ "                    <!-- Page Heading -->\r\n"
		+ "                    <h1 class=\"h3 mb-2 text-gray-800\">Alunos da turma</h1>\r\n"
		+ "\r\n"
		+ "                    <!-- DataTales Example -->\r\n"
		+ "                    <div class=\"card shadow mb-4\">\r\n"
		+ "                        \r\n"
		+ "                        <div class=\"card-body\">\r\n"
		+ "                            <div class=\"table-responsive\">\r\n"
		+ "                                <table class=\"table table-bordered\" id=\"dataTable\" width=\"100%\" cellspacing=\"0\">\r\n"
		+ "                                    <thead>\r\n"
		+ "                                        <tr>\r\n"
		+ "                                            <th>Nome</th>\r\n"
		+ "                                            <th>Matrícula</th>\r\n"
		
		+ "                                        </tr>\r\n"
		+ "                                    </thead>\r\n"
		+ "                                    <tbody>\r\n";
		
		// cada linha da tabela representa um aluno cujo status e' candidato a tcc 
		for (var linha : linhas) {
			html += "<tr><td>" + linha.get("nome") + "<td>" + linha.get("matricula");
			// os botoees de aceitar e rejeitar passam por parametro o id do projeto e do aluno ou o id da inscricao
			
			html += "</tr>";
			
			
			
		}
		
		
		html += 
		 
		 "                                    </tbody>\r\n"
		+ "                                </table>\r\n"
		+ "                            </div>\r\n"
		+ "                        </div>\r\n"
		
		+ "                    </div>\r\n"
		+ "\n<a class= \"btn btn-primary\" align=\"center\" href= \"visualizarTurmas\" role=\"button\">Voltar</a>\r\n"
		+ "\n<a class= \"btn btn-primary\" align=\"center\" href= \"login.html\" role=\"button\">Login</a>\r\n"
		+ "\r\n"
		+ "                </div>\r\n"
		+ "                <!-- /.container-fluid -->\r\n"
		+ "\r\n"
		+ "            </div>\r\n"
		+ "            <!-- End of Main Content -->\r\n"
		+ "\r\n"
		+ "\r\n"
		+ "        </div>\r\n"
		+ "        <!-- End of Content Wrapper -->\r\n"
		+ "\r\n"
		+ "    </div>\r\n"
		+ "    <!-- End of Page Wrapper -->\r\n"
		+ "\r\n"
		+ "    <!-- Scroll to Top Button-->\r\n"
		+ "    <a class=\"scroll-to-top rounded\" href=\"#page-top\">\r\n"
		+ "        <i class=\"fas fa-angle-up\"></i>\r\n"
		+ "    </a>\r\n"
		+ "\r\n"
		+ "    <!-- Logout Modal-->\r\n"
		+ "    <div class=\"modal fade\" id=\"logoutModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"exampleModalLabel\"\r\n"
		+ "        aria-hidden=\"true\">\r\n"
		+ "        <div class=\"modal-dialog\" role=\"document\">\r\n"
		+ "            <div class=\"modal-content\">\r\n"
		+ "                <div class=\"modal-header\">\r\n"
		+ "                    <h5 class=\"modal-title\" id=\"exampleModalLabel\">Ready to Leave?</h5>\r\n"
		+ "                    <button class=\"close\" type=\"button\" data-dismiss=\"modal\" aria-label=\"Close\">\r\n"
		+ "                        <span aria-hidden=\"true\">ï¿½</span>\r\n"
		+ "                    </button>\r\n"
		+ "                </div>\r\n"
		+ "                <div class=\"modal-body\">Select \"Logout\" below if you are ready to end your current session.</div>\r\n"
		+ "                <div class=\"modal-footer\">\r\n"
		+ "                    <button class=\"btn btn-secondary\" type=\"button\" data-dismiss=\"modal\">Cancel</button>\r\n"
		+ "                    <a class=\"btn btn-primary\" href=\"login.html\">Logout</a>\r\n"
		+ "                </div>\r\n"
		+ "            </div>\r\n"
		+ "        </div>\r\n"
		+ "    </div>\r\n"
		+ "\r\n"
		+ "    <!-- Bootstrap core JavaScript-->\r\n"
		+ "    <script src=\"resources/bootstrap/vendor/jquery/jquery.min.js\"></script>\r\n"
		+ "    <script src=\"resources/bootstrap/vendor/bootstrap/js/bootstrap.bundle.min.js\"></script>\r\n"
		+ "\r\n"
		+ "    <!-- Core plugin JavaScript-->\r\n"
		+ "    <script src=\"resources/bootstrap/vendor/jquery-easing/jquery.easing.min.js\"></script>\r\n"
		+ "\r\n"
		+ "    <!-- Custom scripts for all pages-->\r\n"
		+ "    <script src=\"resources/bootstrap/js/sb-admin-2.min.js\"></script>\r\n"
		+ "\r\n"
		+ "    <!-- Page level plugins -->\r\n"
		+ "    <script src=\"resources/bootstrap/vendor/datatables/jquery.dataTables.min.js\"></script>\r\n"
		+ "    <script src=\"resources/bootstrap/vendor/datatables/dataTables.bootstrap4.min.js\"></script>\r\n"
		+ "\r\n"
		+ "    <!-- Page level custom scripts -->\r\n"
		+ "    <script src=\"resources/bootstrap/js/demo/datatables-demo.js\"></script>\r\n"
		+ "\r\n"
		
		
		+ " 		<!-- Modal -->"
		+ " 		<div class=\"modal fade\" id=\"ExemploModalCentralizado\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"TituloModalCentralizado\" aria-hidden\"true\">"
		+ " 			<div class=\"modal-dialog modal-dialog-centered\" role=\"document\">"
		+ "			    	<div class=\"modal-content\">"
		+ " 					<div class=\"modal-header\">"
		+ " 						 <h5 class=\"modal-title\" id=\"TituloModalCentralizado\"> Criar nova Tarefa </h5>"
		+ "	 			    	     <button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-label=\"Fechar\">"
		+ "							 <span aria-hidden=\"true\">&times;</span>"
		+ "							</button>"
	    + " 					</div>"
	    + "						 <div class=\"modal-body\">"
	    + "							 <form class=\"card mb-4\">"
	    + "								<label for=\"tituloTarefa\"> Título </label>"
	    + "								<input type=\"textfield\" class=\"form-control form-control-user\" name=\"tituloTarefa\" id=\"tituloTarefa\" style=\"margin-bottom:15px\">"
	    + "								<label for=\"instrucaoTarefa\"> Instruções </label>"
	    + "								<textarea rows=\"3\" class=\"form-control form-control-user\" style=\"resize: none; margin-bottom:15px\" type=\"textfield\" name=\"instrucaoTarefa\" id=\"instrucaoTarefa\"></textarea>"
	    + "								<label for=\"prazoTarefa\"> Prazo de entrega </label>"
	    + "								<input type=\"date\" class=\"form-control form-control-user\" name=\"prazoTarefa\" id=\"prazoTarefa\">"
	    + "							 </form>"
	    + "	 					 </div>"
	    + " 					<div class=\"modal-footer\">"
	    + "		 					<button type=\"button\" class=\"btn btn-secondary\" data-dismiss=\"modal\">Fechar</button>"
	    + "		 					<button type=\"button\" class=\"btn btn-primary\" id=\"salvarTarefa\" onclick=\"inserirTarefa()\">Salvar mudanças</button>"
	    + " 					</div>"
	    + " 				</div>"
	    + " 			</div>"
		+ " 		</div>"
    
		
		+ "</body>\r\n"
		+ "\r\n"
		+ "</html>"
		+ "<script>\r\n"
		+ "	  var today = new Date().toISOString().split('T')[0];\r\n"
		+ "	  document.getElementsByName(\"prazoTarefa\")[0].setAttribute('min', today);\r\n"
			
		+ "	  function inserirTarefa() {\r\n"
		+ "		$.ajax({\r\n"
		+ "			method : \"POST\",\r\n"
		+ "			url : \"ServletCalendarioEntrega?opcao=incluir\",\r\n"
		+ "			data : {\r\n"
		+ "				'idCalendario' : '" + calendario.getIdCalendario() + "',\r\n"
		+ "				'titulo' : $(\"#tituloTarefa\").val(),\r\n"
		+ "				'instrucoes' : $(\"#instrucaoTarefa\").val(),\r\n"
		+ "				'prazo' : $(\"#prazoTarefa\").val()\r\n"
		+ "			},\r\n"
		+ "			success : function(msg) { //Em caso de sucesso na requisicao, executa a seguinte funcao\r\n"
		+ "				alert(msg);\r\n"
		+ "				$(\"#tabEntregas\")."
		+ "			}\r\n"
		+ "		})\r\n"
		+ "	  }\r\n"
		+ "</script>";
		
		response.getWriter().write(html);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
}