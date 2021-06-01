<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.ArrayList, dao.ProfessorDAO, model.Professor "%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@include file="template_topo.html"%>

<div class="container">

	<!-- Page Heading -->
	<h1 class="h3 mb-2 text-gray-800"></h1>
	<p class="mb-4"></p>

	<!-- DataTales Example -->
	<div class="card shadow mb-4">
		<div class="card-header py-3">
			<h6 class="m-0 font-weight-bold text-primary">Candidatos a
				Orientador</h6>
		</div>
		<div class="card-body">
			<div class="table-responsive" style="padding: 15px;">
				<%
				ArrayList<Professor> candidatos = ProfessorDAO.getInstance().pesquisarCandidatosOrientador();
				%>
				<table class="table table-bordered" name="tb_candidatosOrientador">
					<thead>
						<tr>
							<th>Candidatos</th>
							<th></th>
							<th></th>
						</tr>
					</thead>
					<tbody>
					<% for (Professor professor : candidatos) { %>
						<tr>
							<td><%=professor.getNome()%></td>
							<td align="center" style="width: 15%"><input type="button"
								class="btn btn-success"
								onclick="return aceitarOrientador(<%=professor.getIdProfessor()%>);" value="Aceitar"></td>
							<td align="center" style="width: 15%"><input type="button"
								class="btn btn-danger" 
								onclick="return recusarOrientador(<%=professor.getIdProfessor()%>);" value="Recusar"></td>
						</tr>
					<% } %>
					</tbody>
				</table>
			</div>
			<a class="btn btn-primary" href="secretariaDashboard" role="button">Voltar</a>
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
	function aceitarOrientador(idProfessor) {
		$.ajax({
			method : "POST",
			url : "candidatoOrientador?opcao=aceitar_candidatura",
			data : {
				'idProfessor' : idProfessor
			},
			success : function(msg) { //Em caso de sucesso na requisição, executa a seguinte função
				alert("Candidato aceito!");
				location.reload();
			},
			error : function(msg) {
				alert("Não foi possível aceitar o candidato.");
				location.reload();
			}
		})
	}

	function recusarOrientador(idProfessor) {
			$.ajax({
				method : "POST",
				url : "candidatoOrientador?opcao=recusar_candidatura",
				data : {
					'idProfessor' : idProfessor
				},
				success : function(msg) { //Em caso de sucesso na requisição, executa a seguinte função
					alert("Candidato aceito!");
					location.reload();
				},
				error : function(msg) {
					alert("Não foi possível recusar o candidato.");
					location.reload();
				}
			})
	}
</script>