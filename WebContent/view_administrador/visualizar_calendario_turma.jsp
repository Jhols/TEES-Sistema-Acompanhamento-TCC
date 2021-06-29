<%@page import="model.Entrega"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="java.util.ArrayList "%>
<%@page import="java.util.Date" %>
<%@page import="java.text.SimpleDateFormat" %>

<%@include file="topo_admin.html" %>
	
	<%  %>
	<!-- DataTales Example -->
	<div class="card shadow mb-4" style="margin:0 0 0 200px">
		<div class="card-header py-3">
			<h6 class="m-0 font-weight-bold text-primary">Calendário de Entregas
				</h6>
		</div>
		<div class="card-body">
			<div class="table-responsive" style="padding: 15px;">
				<table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
					<thead>
						<tr>
							<th>Atividade</th>
							<th>Data de Entrega</th>
						</tr>
					</thead>
					<tbody>
						<% 
						ArrayList<Entrega> entregas = (ArrayList<Entrega>) request.getAttribute("entregas");
						
						SimpleDateFormat formatacaoData = new SimpleDateFormat("dd/MM/yyyy");
						if (!entregas.isEmpty()) {
							for (Entrega entrega : entregas) { %>
							<tr>
								<td><%=entrega.getTitulo() %></td>
								<td><%=formatacaoData.format(entrega.getDataPrazo()) %></td>
							</tr>
							<% } 
						} else { %>
							<tr><td colspan="2">Não há tarefas cadastradas para esta turma.</td></tr>
						<% } %>
						
					</tbody>
				</table>
			</div>
			<a class="btn btn-primary" href="administradorDashboard" role="button">Voltar</a>
		</div>
	</div>
	
<%@include file="rodape_admin.html" %>