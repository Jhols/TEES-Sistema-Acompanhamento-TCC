<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Turma" %>
<%@ page import="dao.TurmaDAO" %>
<%@ include file="topo_coordenador.html"%>

<!-- Begin Page Content -->
<div class="container">

	<!-- DataTales Example -->
	<div class="card shadow mb-4" style="display: flex;">
		<div class="card-header py-3">
			<h6 class="m-0 font-weight-bold text-primary">Turmas de TCC</h6>
		</div>
		<div class="card-body">
			<div class="table-responsive" style="padding: 15px;">
				<table class="table table-bordered" id="dataTable" width="100%"
					cellspacing="0">
					
					<% ArrayList<Turma> listaTurmas = TurmaDAO.getInstance().pesquisarTurmas(); %>
					<thead>
						<tr>
							<th>Turma</th>
							<th>Semestre</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
					
					<% for (Turma turma : listaTurmas) { %>
						<tr>
							<td><%= turma.getNome() %></td>
							<td><%= turma.getSemestre() %></td>
							<td><a class="btn btn-primary" href="visualizarCalendarioCoordenador?opcao=exibir_calendario&turma=<%=turma.getId() %>" role="button">Calendário</a></td>
						</tr>
					<% } %>
					</tbody>
				</table>
			</div>
			<a class="btn btn-primary" href="coordenadorDashboard" role="button">Voltar</a>
	<a class="btn btn-primary" href="login.html" role="button">Login</a>
		</div>
	</div>
	
</div>
<!-- /.container-fluid -->
<%@include file="rodape_coordenador.html"%>
<!-- Page level plugins -->

<script
	src="resources/bootstrap/vendor/datatables/jquery.dataTables.min.js"></script>
<script
	src="resources/bootstrap/vendor/datatables/dataTables.bootstrap4.min.js"></script>

<!-- Page level custom scripts -->
<script src="resources/bootstrap/js/demo/datatables-demo.js"></script>