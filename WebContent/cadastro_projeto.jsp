<%@page import="enums.SituacaoProjeto"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="java.util.ArrayList, model.Projeto, dao.ProjetoDAO"%>
<%@page import="javax.servlet.http.HttpSession"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@include file="template_topo.html"%>

<!-- Begin Page Content -->
<div class="container">

	<!-- Page Heading -->
	<h1 class="h3 mb-2 text-gray-800"></h1>
	<p class="mb-4"></p>

	<!-- DataTales Example -->
	<div class="card shadow mb-4">
		<div class="card-header py-3">
			<h6 class="m-0 font-weight-bold text-primary">Cadastro de
				Projeto</h6>
		</div>
		<div class="card-body">
			<form method="POST" action="cadastroProjeto?opcao=alterar">
				<%
					Projeto projeto = ProjetoDAO.getInstance().findById(Integer.valueOf(request.getParameter("idProjeto")));
					String titulo = (projeto.getTitulo() == null ? "" : projeto.getTitulo());
					String descricao = (projeto.getDescricao() == null ? "" : projeto.getDescricao());
				%>
				<div class="form-group">
					<input type="hidden" name="idProjeto" value="<%=request.getParameter("idProjeto")%>" >
					<label for="tx_titulo_projeto">Título</label> <input type="text"
						class="form-control" name="tx_tituloProjeto" id="tx_tituloProjeto"
						placeholder="Informe o título do seu Projeto" value="<%= titulo %>">
				</div>
				<div class="form-group">
					<label for="tx_descricao_projeto">Descrição</label> <input
						type="text" class="form-control" name="tx_descricao_projeto" id="tx_descricao_projeto"
						placeholder="Informe uma descrição para seu projeto." value="<%= descricao %>">
				</div>
				<button type="submit" class="btn btn-primary">Gravar</button>
				<a class="btn btn-primary" href="professorDashboard"
		onclick="window.history.go(-1); return false;" role="button">Voltar</a>
			</form>
		</div>
	</div>
</div>
<!-- /.container-fluid -->
<%@include file="template_rodape.html"%>
<!-- Page level plugins -->

<script
	src="resources/bootstrap/vendor/datatables/jquery.dataTables.min.js"></script>
<script
	src="resources/bootstrap/vendor/datatables/dataTables.bootstrap4.min.js"></script>

<!-- Page level custom scripts -->
<script src="resources/bootstrap/js/demo/datatables-demo.js"></script>