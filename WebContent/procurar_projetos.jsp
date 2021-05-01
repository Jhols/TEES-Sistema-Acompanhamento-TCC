<%@page import="enums.SituacaoProjeto"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="java.util.ArrayList, model.Projeto,dao.ProjetoDAO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@include file="template_topo.html"%>
<!-- Begin Page Content -->
<div class="container-fluid">

	<!-- Page Heading -->
	<h1 class="h3 mb-2 text-gray-800">Projetos</h1>
	<p class="mb-4">
		Aqui, você pode conferir projetos criados por seus professores que
		estejam disponíveis para sua inscrição. Você pode se candidatar a mais
		de um projeto por vez e terá de aguardar a aprovação de um professor.
		<br /> Caso possua um projeto ativo, para se candidatar a um novo, é
		necessário desativar o seu projeto atual. Contate seu professor
		orientador para saber mais informações.
	</p>

	<!-- DataTales Example -->
	<div class="card shadow mb-4">
		<div class="card-header py-3">
			<h6 class="m-0 font-weight-bold text-primary">Projetos
				Disponíveis</h6>
		</div>
		<div class="card-body">
			<div class="table-responsive" style="padding: 15px;">
				<table class="table table-bordered" id="dataTable" width="100%"
					cellspacing="0">
					<thead>
						<tr>
							<th>Título</th>
							<th>Descrição</th>
							<th>Orientador</th>
							<th>Inscrição</th>
						</tr>
					</thead>
					<tbody>

						<%
						ArrayList<Projeto> projetos = new ArrayList<Projeto>();
						projetos = ProjetoDAO.getInstance().pesquisarProjetosDisponiveis();
						int x = 1;
						for (Projeto projeto : projetos) {
							out.println("<tr>");
								out.println("<td id='titulo" + x + "'>" + projeto.getTitulo() + "</td>");
								out.println("<td>" + projeto.getDescricao() + "</td>");
								out.println("<td id='professor" + x + "'>" + projeto.getProfessor().getNome() + "</td>");
								out.println(
								"<td><input type='button' class='btn btn-primary btn-icon-split, text' style='width:95%' id='btn-candidatar-"
										+ x + "' onClick=\"enviarSolicitacao('btn-candidatar-" + x + "','titulo" + x + "', 'professor" + x
										+ "')\" name='btn-candidatar' value='Candidatar-se'></td>");
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