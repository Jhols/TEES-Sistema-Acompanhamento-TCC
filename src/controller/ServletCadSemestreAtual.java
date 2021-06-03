package controller;

import java.io.IOException;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import dao.SemestreDAO;
import enums.Perfil;
import model.Pessoa;
import model.Semestre;


@WebServlet( urlPatterns = {"/cadSemestreAtual"})
public class ServletCadSemestreAtual extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Pessoa pessoa = (Pessoa) request.getSession().getAttribute("pessoa");
		if (pessoa == null) {
			response.sendRedirect("login.html");
			return;
		}
		if (pessoa.getPerfil() != Perfil.ADMINISTRADOR) {
			response.sendRedirect("login.html");
			return;
		}
		
		request.setCharacterEncoding("UTF-8");
		
		String inicio = request.getParameter("inicioSemestre");
		String fim = request.getParameter("finalSemestre");
		String semestre = request.getParameter("semestre");
		
		
		System.out.println(inicio);
		System.out.println(fim);
		System.out.println(semestre);
		
		try {
			Semestre semAtual = new Semestre();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			
			Date inicioDate = formatter.parse(inicio);
			semAtual.setInicioSemestre(inicioDate);
			
			Date fimDate = formatter.parse(fim);
			semAtual.setFinalSemestre(fimDate);
			
			semAtual.setSemestreAtual(semestre);
			
			SemestreDAO.getInstance().addSemestre(semAtual);
		}
		catch (ParseException p) {
			p.printStackTrace();
		}
		
		response.setCharacterEncoding("UTF-8");
		var writer = response.getWriter();
		
		String html = "<html><body>"; 
		html += "<script> alert(\"Semestre atual cadastrado com sucesso!\"); window.location.href = \"administradorDashboard\" </script>";
		html += "</body></html>";
		writer.write(html);
	}
	
	
}
