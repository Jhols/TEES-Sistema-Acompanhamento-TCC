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
import model.Aluno;
import model.Professor;


@WebServlet( urlPatterns = {"/editAluno"})
public class ServletEditaAluno extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		Aluno aluno = (Aluno) request.getSession().getAttribute("pessoa");

		aluno.setNome(request.getParameter("nome"));
		aluno.setEmail(request.getParameter("email"));
		aluno.setMatricula(request.getParameter("matricula"));
		aluno.setTelefone(request.getParameter("telefone"));
		
		AlunoDAO.atualizaAluno(aluno);
		
		
		
		response.sendRedirect("alunoDashboard?editaAluno=OK");
	}
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Aluno aluno = (Aluno) request.getSession().getAttribute("pessoa");
		
		if (aluno == null) {
			response.sendRedirect("login.html");
		}
		
		String nome_aluno = aluno.getNome();
		String email_aluno= aluno.getEmail();
		String matricula_aluno= aluno.getMatricula();
		String aluno_telefone= aluno.getTelefone();
		
		
		request.setCharacterEncoding("UTF-8");
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
	  		+ "     <form method=\"POST\" action= 'editAluno' name=\"form\">"
	  		+ "      <div class=\"container\">\r\n"
	  		+ "         <div class=\"card o-hidden border-0 shadow-lg my-5\">\r\n"
	  		+ "            <div class=\"card-body\">\r\n"
	  		+ "               <!-- Nested Row within Card Body -->\r\n"
	  		+ "               <div class=\"page-header\">\r\n"
	  		+ "                  <h1 class=\"text-center font-weight-bold\">Editar dados de cadastro</h1>\r\n"
	  		+ "               </div>\r\n"
	  		+ "               <form>\r\n"
	  		+                  "<div class=\"form-group\">\r\n"
            +                    "<label for=\\\"exampleFormControlInput1\\\">Nome</label>\n"
									+"<input class=\"form-control\" name=\"nome\" value=\"" + nome_aluno +  "\" required=\".$this->fields[\"comment\"]>"
								+"</div>\r\n"
	  		
	  		+ "                  <div class=\"form-group\">\r\n"
	  		+ "                     <label for=\"exampleFormControlInput1\">Email</label>\r\n"
	  		+ "                     <input  class=\"form-control\" name=\"email\" onblur=\"checarEmail();\" value=\""+ email_aluno+ "\" required=\".$this->fields[\"comment\"]\r\n"
	  		+ "                  </div>\r\n"
	  		
			+ "                  <div class=\"form-group\">\r\n"
			+ "                     <label for=\"exampleFormControlInput1\">Telefone</label>\r\n"
			+ "                     <input  class=\"form-control\" name=\"telefone\" onKeyPress=\"MascaraTelefone(form.telefone);\" placeholder=\"XX XXXXX-XXXX\" maxlength=\"15\" value=\""+ aluno_telefone+ "\" required=\".$this->fields[\"comment\"]\r\n"
			+ "                  </div>\r\n"					
			
			+ "                  <div class=\"form-group\">\r\n"
			+ "                     <label for=\"exampleFormControlInput1\">Matricula</label>\r\n"
			+ "                     <input  class=\"form-control\" name=\"matricula\" value=\""+ matricula_aluno+ "\" required=\".$this->fields[\"comment\"]\r\n"
			+ "                  </div>\r\n"						  		
	  		
	  		+ "               </form>\r\n"
	  		+	"<input class=\"btn btn-primary\" type=\"submit\" value=\"Submit\">"
	  		+ "\n<a class= \"btn btn-primary\" align=\"center\" href= \"alunoDashboard\" role=\"button\">Voltar</a>\r\n"
	  		+ "\n<a class= \"btn btn-primary\" align=\"center\" href= \"login.html\" role=\"button\">Login</a>\r\n"
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
	  		+" <script>\r\n"
	  		+ "        function MascaraTelefone(tel){  \r\n"
	  		+ "		    if(mascaraInteiro(tel)==false){\r\n"
	  		+ "		            event.returnValue = false;\r\n"
	  		+ "		    }       \r\n"
	  		+ "		    return formataCampo(tel, '(00) 00000-0000', event);\r\n"
	  		+ "		}\r\n"
	  		+ "        \r\n"
	  		+ "        function formataCampo(campo, Mascara, evento) { \r\n"
	  		+ "		    var boleanoMascara; \r\n"
	  		+ "		\r\n"
	  		+ "		    var Digitato = evento.keyCode;\r\n"
	  		+ "		    exp = /\\-|\\.|\\/|\\(|\\)| /g\r\n"
	  		+ "		    campoSoNumeros = campo.value.toString().replace( exp, \"\" ); \r\n"
	  		+ "		\r\n"
	  		+ "		    var posicaoCampo = 0;    \r\n"
	  		+ "		    var NovoValorCampo=\"\";\r\n"
	  		+ "		    var TamanhoMascara = campoSoNumeros.length;; \r\n"
	  		+ "		\r\n"
	  		+ "		    if (Digitato != 8) { // backspace \r\n"
	  		+ "		            for(i=0; i<= TamanhoMascara; i++) { \r\n"
	  		+ "		                    boleanoMascara  = ((Mascara.charAt(i) == \"-\") || (Mascara.charAt(i) == \".\")\r\n"
	  		+ "		                                                            || (Mascara.charAt(i) == \"/\")) \r\n"
	  		+ "		                    boleanoMascara  = boleanoMascara || ((Mascara.charAt(i) == \"(\") \r\n"
	  		+ "		                                                            || (Mascara.charAt(i) == \")\") || (Mascara.charAt(i) == \" \")) \r\n"
	  		+ "		                    if (boleanoMascara) { \r\n"
	  		+ "		                            NovoValorCampo += Mascara.charAt(i); \r\n"
	  		+ "		                              TamanhoMascara++;\r\n"
	  		+ "		                    }else { \r\n"
	  		+ "		                            NovoValorCampo += campoSoNumeros.charAt(posicaoCampo); \r\n"
	  		+ "		                            posicaoCampo++; \r\n"
	  		+ "		                      }              \r\n"
	  		+ "		              }      \r\n"
	  		+ "		            campo.value = NovoValorCampo;\r\n"
	  		+ "		              return true; \r\n"
	  		+ "		    }else { \r\n"
	  		+ "		            return true; \r\n"
	  		+ "		    }\r\n"
	  		+ "		}\r\n"
	  		+ "        \r\n"
	  		+ "        function mascaraInteiro(){\r\n"
	  		+ "		    if (event.keyCode < 48 || event.keyCode > 57){\r\n"
	  		+ "		            event.returnValue = false;\r\n"
	  		+ "		            return false;\r\n"
	  		+ "		    }\r\n"
	  		+ "		    return true;\r\n"
	  		+ "		}\r\n"
	  		+ "        \r\n"
	  		+ "        function verifica() {\r\n"
	  		+ "        	  if (document.forms[0].email.value.length == 0) {\r\n"
	  		+ "        	    alert('Por favor, informe o seu EMAIL.');\r\n"
	  		+ "        		document.frmEnvia.email.focus();\r\n"
	  		+ "        	    return false;\r\n"
	  		+ "        	  }\r\n"
	  		+ "        	  return true;\r\n"
	  		+ "        	}\r\n"
	  		+ "        \r\n"
	  		+" function checarEmail(){\r\n"
	  		+ "        	if( document.forms[0].email.value==\"\" \r\n"
	  		+ "        	   || document.forms[0].email.value.indexOf('@')==-1 \r\n"
	  		+ "        	     || document.forms[0].email.value.indexOf('.com')==-1 \r\n"
	  		+ "        	     || document.forms[0].email.value.indexOf('@')==-1 \r\n"
	  		+ "        	     \r\n"
	  		+ "        	   ){\r\n"
	  		+ "        		alert( \"Por favor, informe um E-MAIL válido!\" );\r\n"
	  		+ "         		  return false;\r\n"
	  		+ "        		}\r\n"
	  		+ "        		\r\n"
	  		+ "        	\r\n"
	  		+ "        	 \r\n"
	  		+ "        	if(document.forms[0].email.value.indexOf('.') < document.forms[0].email.value.indexOf('@')){\r\n"
	  		+ "        		alert( \"Por favor, informe um E-MAIL válido!\" );\r\n"
	  		+ "           		  return false;\r\n"
	  		+ "        		}\r\n"
	  		+ "        		 \r\n"
	  		+ "        		\r\n"
	  		+ "        	}"
	  		+ "        </script>"
	  		+" </form>"
	  		+ "   </body>\r\n"
	  		+ "</html>";
	  	
	  	writer.write(pagina);
	  
	}
}
