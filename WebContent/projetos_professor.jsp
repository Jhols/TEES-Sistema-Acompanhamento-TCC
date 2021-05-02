<%@page import="enums.SituacaoProjeto"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="java.util.ArrayList, model.Projeto,dao.ProfessorDAO"%>
<%@page import="java.util.ArrayList, model.Projeto,dao.ProjetoDAO"%>
<%@page language="java"
	import="java.util.ArrayList, enums.SituacaoProjeto"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@include file="template_topo.html"%>
<!-- Begin Page Content -->
<div class="container-fluid">

	<!-- Page Heading -->
	<h1 class="h3 mb-2 text-gray-800">Projetos</h1>
	<p class="mb-4">Aqui você pode ver todos os seus projetos que NÃO
		SÃO ATIVOS cadastrados e para cada projeto haverá a opção de mudar o
		status do projeto, exceto projetos que estejam com status ativo. Os
		projetos que têm status ativo nem devem aparecer na lista.</p>

	<!-- DataTales Example -->
	<div class="card shadow mb-4">
		<div class="card-header py-3">
			<h6 class="m-0 font-weight-bold text-primary">Projetos do
				Professor</h6>
		</div>
		<div class="card-body">
			<div class="table-responsive" style="padding: 15px;">
				<table class="table table-bordered" id="dataTable" width="100%"
					cellspacing="0">
					<thead>
						<tr>
							<th>Título</th>
							<th>Descrição</th>
							<th>Status do Projeto</th>
						</tr>
					</thead>
					<tbody>

						<%
						ArrayList<Projeto> projetos = new ArrayList<Projeto>();
						projetos = ProjetoDAO.getInstance().buscarProjetosPorProfessorESituacoesNaoAtivo(2);
						int x = 1;
						for (Projeto projeto : projetos) {
							out.println("<tr>");
							out.println("<td id='titulo" + x + "'>" + projeto.getTitulo() + "</td>");
							out.println("<td>" + projeto.getDescricao() + "</td>"); //+ projeto.getSituacao()
							out.println("<td id='statusProjeto" + x + "'>" + "<select name='sct_statusProjeto' " + "</td>");
							out.println("</tr>");
							x++;
						}
						%>
					</tbody>
				</table>
			</div>
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

<script>
	function enviarSolicitacao(idBotao, idTitulo, idProfessor) {
		var titulo = $("#" + idTitulo).text();
		var professor = $("#" + idProfessor).text();
		var alunoMatricula = "0715123";

		if (document.getElementById(idBotao).getAttribute("value") == "Candidatar-se") {
			$.ajax({
				method : "POST",
				url : "InscricaoProjetoServlet?opcao=incluir",
				data : {
					'titulo' : titulo,
					'professor' : professor,
					'alunoMatricula' : alunoMatricula
				},
				success : function(msg) { //Em caso de sucesso na requisição, executa a seguinte função
					console.log("Requisição Enviada: " + msg);
					document.getElementById(idBotao).setAttribute("value",
							"Aguardando");
					document.getElementById(idBotao).setAttribute("class",
							"btn btn-info btn-icon-split, text");
				}
			})

		} else if (document.getElementById(idBotao).getAttribute("value") == "Aguardando") {
			$.ajax({
				method : "POST",
				url : "InscricaoProjetoServlet?opcao=deletar",
				data : {
					'titulo' : titulo,
					'professor' : professor,
					'alunoMatricula' : alunoMatricula
				},
				success : function(msg) { //Em caso de sucesso na requisição, executa a seguinte função
					console.log("Requisição Enviada: " + msg);
					document.getElementById(idBotao).setAttribute("value",
							"Candidatar-se");
					document.getElementById(idBotao).setAttribute("class",
							"btn btn-primary btn-icon-split, text");
				}
			})

		}

	}
</script>