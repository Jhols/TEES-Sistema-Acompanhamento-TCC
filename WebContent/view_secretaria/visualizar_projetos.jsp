<%@page import="enums.SituacaoInscricao"%>
<%@page import="dao.AlunoDAO"%>
<%@page import="model.Aluno"%>
<%@page import="enums.Perfil"%>
<%@page import="model.PessoaFactory"%>
<%@page import="model.Pessoa"%>
<%@page import="dao.InscricaoProjetoDAO"%>
<%@page import="model.InscricaoProjeto"%>
<%@page import="enums.SituacaoProjeto"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="java.util.ArrayList, model.Projeto,dao.ProjetoDAO"%>

<%@include file="topo_secretaria.html" %>
	
	<!-- DataTales Example -->
	<div class="card shadow mb-4" style="margin:0 0 0 200px">
		<div class="card-header py-3">
			<h6 class="m-0 font-weight-bold text-primary">Visualizar todos os Projetos
				</h6>
		</div>
		<div class="card-body">
			<div class="table-responsive" style="padding: 15px;">
				<table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
					<thead>
						<tr>
							<th>Título</th>
							<th>Descrição</th>
							<th>Orientador</th>
							<th>Situação</th>
						</tr>
					</thead>
					<tbody>
				
						<%
						//Obtem os projetos disponiveis atraves da servlet
						ArrayList<Projeto> projetos = new ArrayList<Projeto>();
						projetos = (ArrayList<Projeto>) request.getSession().getAttribute("projetos");
						
						//Obtem as inscricoes que o aluno logado ja possui atraves da servlet
						ArrayList<InscricaoProjeto> inscricoes = new ArrayList<InscricaoProjeto>();
						inscricoes = (ArrayList<InscricaoProjeto>) request.getSession().getAttribute("inscricoesAtivas");
						
						//Este bloco insere os dados dos projetos em uma tabela
						int x = 1;
						for (Projeto projeto : projetos) {
							out.println("<tr>");
								out.println("<td id='titulo" + x + "'>" + projeto.getTitulo() + "</td>");
								out.println("<td>" + projeto.getDescricao() + "</td>");
								out.println("<td id='professor" + x + "'>" + projeto.getProfessor().getNome() + "</td>");
								out.println("<td id='situacao" + x + "'>" + projeto.getSituacao().getNomeSituacao() + "</td>");
								out.println("<td id='visualizarProjeto" + x + "'> <a href='VisualizarTodosProjetos?opcao=consultarProjeto&prf=1&id="+projeto.getId()+"'> Visualizar + </a> </td>");
								/*out.print("<td id='aluno" + x + "'>");
								if (projeto.getSituacao() == SituacaoProjeto.ATIVO) {
									for (InscricaoProjeto inscricao : inscricoes) {
										if (inscricao.getIdProjeto() == projeto.getId()) {
											out.println(inscricao.getAluno().getNome());
											break;
										}
									}
								}
								out.println("</td>");
								*/
								/*
								for (InscricaoProjeto inscricao : inscricoes) {
									
									if (inscricao.getProjeto().getId() == projeto.getId() && inscricao.getSituacaoInscricao() == SituacaoInscricao.CANDIDATO) {
										//Caso o aluno ja seja candidato a este projeto, e' inserido um botao de aguardar a aprovacao do professor
										out.println(
												"<td><input type='button' class='btn btn-info btn-icon-split, text' style='width:95%' id='btn-candidatar-"
														+ x + "' onClick=\"enviarSolicitacao('btn-candidatar-" + x + "','titulo" + x + "', 'professor" + x
														+ "', '"+ matriculaAluno +"')\" name='btn-candidatar' value='Aguardando'></td>");
										break;
									}
								}
								
									out.println(
									"<td><input type='button' class='btn btn-primary btn-icon-split, text' style='width:95%' id='btn-candidatar-"
											+ x + "' onClick=\"enviarSolicitacao('btn-candidatar-" + x + "','titulo" + x + "', 'professor" + x
											+ "', '"+ matriculaAluno +"')\" name='btn-candidatar' value='Candidatar-se'></td>");
								}
								*/
							out.println("</tr>");
							x++;
						}
						%>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	
<%@include file="rodape_secretaria.html" %>