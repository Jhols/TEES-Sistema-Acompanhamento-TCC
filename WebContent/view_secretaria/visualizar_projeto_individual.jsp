<%@page import="enums.SituacaoProjeto"%>
<%@page import="enums.SituacaoInscricao"%>
<%@page import="dao.InscricaoProjetoDAO"%>
<%@page import="model.InscricaoProjeto"%>
<%@page import="java.util.ArrayList, model.Projeto,dao.ProjetoDAO"%>

<%@include file="topo_secretaria.html" %>

	<% 
		Projeto projeto = new Projeto();
		projeto = (Projeto) request.getSession().getAttribute("projeto");
		
		//Obtem as inscricoes que o aluno logado ja possui atraves da servlet
		ArrayList<InscricaoProjeto> inscricoes = new ArrayList<InscricaoProjeto>();
		inscricoes = (ArrayList<InscricaoProjeto>) request.getSession().getAttribute("inscricoes");
	%>
	
	<!-- DataTales Example -->
	<div class="card shadow mb-4" style="margin:0 0 0 200px; width: 800px;">
		<div class="card-header py-3">
			<h6 class="m-0 font-weight-bold text-primary">Visualizar Informações do Projeto </h6>
		</div>
		<div class="card-body">
			<a href="javascript: history.go(-1)"> <- Voltar para tela de projetos</a>
			<div style="padding: 15px;">
				<br>
				<div> <h3 style="display:inline"> Título: </h3><span style="font-size: 20px"> <%= projeto.getTitulo() %> </span> </div>
				<br>
				<div> <h5 style="display:inline"> Descrição:</h5> <span> <%= projeto.getDescricao() %></span> </div>
				<br>
				<div> <h5 style="display:inline"> Professor:</h5> <span>  <%= projeto.getProfessor().getNome() %> </span> </div>
				<br>
				<div> <h5 style="display:inline"> Matrícula Professor:</h5> <span>  <%= projeto.getProfessor().getMatricula() %> </span> </div>
				<br>
				<div> <h5 style="display:inline"> Situação:</h5> <span> <%= projeto.getSituacao() %> </span> </div>
				<br>
				<% if (projeto.getSituacao() == SituacaoProjeto.ATIVO) { %>
					<div> <h5 style="display:inline"> Orientando:</h5> <span> <%= inscricoes.get(0).getAluno().getNome() %> </span> </div>
					<br>
					<div> <h5 style="display:inline"> Matrícula Orientando:</h5> <span> <%= inscricoes.get(0).getAluno().getMatricula() %> </span> </div>
					<br>
				<% } else if (projeto.getSituacao() == SituacaoProjeto.DISPONIVEL) { %>
					<div class="card shadow mb-4" style="margin:20px 0 0 0">
						<div class="card-header py-3">
							<h6 class="m-0 font-weight-bold text-primary">Candidatos ao Projeto
								</h6>
						</div>
						<div class="card-body">
							<div class="table-responsive" style="padding: 15px;">
								<table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
									<thead>
										<tr>
											<th>Aluno</th>
											<th>Matrícula</th>
										</tr>
									</thead>
									<tbody>
										<%
										for (InscricaoProjeto inscricao : inscricoes) {
											out.println("<tr>");
												out.println("<td>"+ inscricao.getAluno().getNome() +"</td>");
												out.println("<td>"+ inscricao.getAluno().getMatricula() +"</td>");
											out.println("</tr>");
										}
										%>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				<% } %>
			</div>
			
		</div>
	</div>
	
<%@include file="rodape_secretaria.html" %>