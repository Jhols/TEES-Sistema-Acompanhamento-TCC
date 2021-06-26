package controller;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.ArquivoDeTccDAO;
import enums.Perfil;
import model.ArquivoDeTcc;
import model.Pessoa;


@WebServlet(urlPatterns = {"/downloadAnexoDeTCC"})
@MultipartConfig(maxFileSize = 16177215)    // upload file's size up to 16MB

public class ServletDownloadArquivoDeTcc extends HttpServlet{


	private static final long serialVersionUID = 1L;
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		// Tentar pegar o professor que está logado atualmente
		Pessoa pessoa = (Pessoa) request.getSession().getAttribute("pessoa");
		
		if (pessoa == null) {
			response.sendRedirect("login.html");
			return;
		}
		
		if (pessoa.getPerfil() != Perfil.PROFESSOR && pessoa.getPerfil() != Perfil.ALUNO) {
			System.out.println("Não é professor nem aluno");
			response.sendRedirect("login.html");
			return;
		}
		
		int idArquivo = Integer.parseInt(request.getParameter("anexo"));
		ArquivoDeTcc arquivo = ArquivoDeTccDAO.procurarArquivoPorId(idArquivo);
		
		
		response.setContentType(arquivo.getContentType());
		response.setHeader( "Content-Disposition", "filename=" + arquivo.getFileName());
		InputStream in = arquivo.getAnexo(); 
		ServletOutputStream out = response.getOutputStream();
		byte[] buffer = new byte[4096];
		while(in.read(buffer, 0, 4096) != -1) {
			out.write(buffer, 0, 4096);
		}
		in.close();
		out.flush();
		out.close();
		
		
	}

}