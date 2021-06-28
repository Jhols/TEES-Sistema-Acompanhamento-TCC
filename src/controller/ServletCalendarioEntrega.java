package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CalendarioDAO;
import model.Entrega;

/**
 * Servlet implementation class ServletCalendarioEntrega
 */
@WebServlet("/ServletCalendarioEntrega")
public class ServletCalendarioEntrega extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletCalendarioEntrega() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String opcao = request.getParameter("opcao");
		
		switch (opcao) {
			case "incluir":
				incluirEntrega(request, response);
				break;
		}
	}

	private void incluirEntrega(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Entrega entrega = new Entrega();
		PrintWriter out;
		response.setContentType("text/Plain");
		out = response.getWriter();
		
		entrega.setIdCalendarioEntrega(Integer.parseInt(request.getParameter("idCalendario")));
		entrega.setTitulo((String) request.getParameter("titulo"));
		entrega.setInstrucao((String) request.getParameter("instrucoes"));
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			entrega.setDataPrazo(df.parse((String) request.getParameter("prazo")));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		boolean inclusao = CalendarioDAO.getInstance().inserirEntrega(entrega);
		
		if (inclusao) {
			out.print("Tarefa incluida com sucesso!");
		}
		else {
			out.print("Erro ao incluir a tarefa");
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
