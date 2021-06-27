package util;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import model.ArquivoAnexado;

public class AnexoDeArquivo {

	
	public static boolean extrairArquivo(ArquivoAnexado arquivo, HttpServletRequest request) throws IllegalStateException, IOException, ServletException {
		Part filePart = request.getPart("arquivo");
		if (filePart != null) {
			System.out.println(filePart.getName());
			System.out.println(filePart.getSize());
			System.out.println(filePart.getContentType());
			String fileName = extractFileName(filePart);
			System.out.println(fileName);
			
			InputStream inputStream = filePart.getInputStream();
			arquivo.setAnexo(inputStream);
			arquivo.setFileName(fileName);
			arquivo.setContentType(filePart.getContentType());
			return true;
		}
		else {
			System.out.println("Não foi possivel carregar arquivo");
			return false;
		}
	}

	private static String extractFileName(Part part) {
		String contentDisp = part.getHeader("content-disposition");
		String[] items = contentDisp.split(";");
		for (String s : items) {
			if (s.trim().startsWith("filename")) {
				return s.substring(s.indexOf("=") + 2, s.length()-1);
			}
		}
		return "";
	}
	
	public static void dowloadArquivo(ArquivoAnexado arquivo, HttpServletResponse response) throws IOException {
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
