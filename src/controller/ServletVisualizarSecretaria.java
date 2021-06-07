package controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.SecretariaDAO;
import model.Administrador;
import model.Secretaria;


//TELA DE PROFESSOR TCC QUE VISUALIZA AS SUAS TURMAS DO SEMESTRE ATUAL
@WebServlet( urlPatterns = {"/visualizarSecretaria"})
public class ServletVisualizarSecretaria extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		// Tentar pegar o professor que está logado atualmente
		var adm = (Administrador) request.getSession().getAttribute("pessoa");
		
		if (adm == null) {
			response.sendRedirect("login.html");
			return;
		}
		
		
		ArrayList<Secretaria> secretarias = SecretariaDAO.getInstance().pesquisarTodasSecretarias();
				
		// uma lista de linha para preencher a tabela de visualiza��o
		var linhas = new ArrayList<HashMap<String, String>>();
		
		for (Secretaria secretaria : secretarias) {
			
			//preencher os dados que serão mostrados na tabela
			// ou que serão usados pelos botões (aceitar e rejeitar)
			var linha = new HashMap<String, String>();
			linha.put("nome", secretaria.getNome());
			linha.put("email", secretaria.getEmail());
			linha.put("telefone", secretaria.getTelefone());
			linha.put("idSecretaria",String.valueOf(secretaria.getId()));
			linhas.add(linha);
		}	
		
		
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
		+ "                    <h1 class=\"h3 mb-2 text-gray-800\">Secret�rias cadastradas</h1>\r\n"
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
		+ "                                            <th>Telefone</th>\r\n"
		+ "                                            <th>Email</th>\r\n"
		+ "                                            <th>Editar</th>\r\n"
		
		+"                                        </tr>\r\n"
		+ "                                    </thead>\r\n"
		+ "                                    <tbody>\r\n";
		
		// cada linha da tabela representa um aluno cujo status é candidato a tcc 
		for (var linha : linhas) {
			html += "<tr><td>" + linha.get("nome") + "<td>" + linha.get("telefone") +  "<td>" +linha.get("email");
			// os botões de aceitar e rejeitar passam por parametro o id do projeto e do aluno ou o id da inscricao
			html+="<td ><a class=\"btn btn-primary\" href=\"editarSecretaria?idSecretaria="+ linha.get("idSecretaria")+"\" role=\"button\">Editar</a>";
			html += "</tr>";
			
			
						
		}
		
		
		html += 
		 
		 "                                    </tbody>\r\n"
		+ "                                </table>\r\n"
		+ "                            </div>\r\n"
		+ "                        </div>\r\n"
		
		+ "                    </div>\r\n"
		+ "\n<a class= \"btn btn-primary\" align=\"center\" href= \"administradorDashboard\" role=\"button\">Voltar</a>\r\n"
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
		+ "                        <span aria-hidden=\"true\">�</span>\r\n"
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
		+ "</body>\r\n"
		+ "\r\n"
		+ "</html>";
		
		response.getWriter().write(html);
		
	}


	
}