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
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@include file="template_topo.html"%>
<!-- Begin Page Content -->
<div class="container-fluid">

	<!-- Page Heading -->
	<h1 class="h3 mb-2 text-gray-800">Projetos</h1>
	<p class="mb-4">
		Aqui, voc� pode conferir projetos criados por seus professores que
		estejam dispon�veis para sua inscri��o. Voc� pode se candidatar a mais
		de um projeto por vez e ter� de aguardar a aprova��o de um professor. Caso deseje realizar o descadastro em algum projeto no qual 
		tenha se candidato (status aguardando) clique novamente no bot�o.
		<br /> Caso possua um projeto ativo, para se candidatar a um novo, �
		necess�rio desativar o seu projeto atual. Contate seu professor
		orientador para saber mais informa��es.
	</p>

	<!-- DataTales Example -->
	<div class="card shadow mb-4">
		<div class="card-header py-3">
			<h6 class="m-0 font-weight-bold text-primary">Projetos
				Dispon�veis</h6>
		</div>
		<div class="card-body">
			<div class="table-responsive" style="padding: 15px;">
			
			<input type="hidden" id="teste" value="<%=request.getSession().getAttribute("matricula")%>" />
			
				<table class="table table-bordered" id="dataTable" width="100%"
					cellspacing="0">
					<thead>
						<tr>
							<th>T�tulo</th>
							<th>Descri��o</th>
							<th>Orientador</th>
							<th>Inscri��o</th>
						</tr>
					</thead>
					<tbody>

						<%
						//Obtem os projetos disponiveis atraves da servlet
						ArrayList<Projeto> projetos = new ArrayList<Projeto>();
						projetos = (ArrayList<Projeto>) request.getAttribute("projetos");
						
						//Obtem as inscricoes que o aluno logado ja possui atraves da servlet
						ArrayList<InscricaoProjeto> inscricoes = new ArrayList<InscricaoProjeto>();
						inscricoes = (ArrayList<InscricaoProjeto>) request.getAttribute("inscricoes");
						
						String matriculaAluno = (String) request.getSession().getAttribute("matricula");
						//Este bloco insere os dados dos projetos disponiveis em uma tabela
						int x = 1;
						boolean eCandidato; //Flag que identifica se o aluno ja e' candidato ou nao a um projeto
						for (Projeto projeto : projetos) {
							eCandidato = false;
							out.println("<tr>");
								out.println("<td id='titulo" + x + "'>" + projeto.getTitulo() + "</td>");
								out.println("<td>" + projeto.getDescricao() + "</td>");
								out.println("<td id='professor" + x + "'>" + projeto.getProfessor().getNome() + "</td>");
								for (InscricaoProjeto inscricao : inscricoes) {
									
									if (inscricao.getProjeto().getId() == projeto.getId() && inscricao.getSituacaoInscricao() == SituacaoInscricao.CANDIDATO) {
										//Caso o aluno ja seja candidato a este projeto, e' inserido um botao de aguardar a aprovacao do professor
										out.println(
												"<td><input type='button' class='btn btn-info btn-icon-split, text' style='width:95%' id='btn-candidatar-"
														+ x + "' onClick=\"enviarSolicitacao('btn-candidatar-" + x + "','titulo" + x + "', 'professor" + x
														+ "', '"+ matriculaAluno +"')\" name='btn-candidatar' value='Aguardando'></td>");
										eCandidato = true;
										break;
									}
								}
								
								if (!eCandidato) { //Se o aluno nao e' candidato ao projeto, insere um botao para candidatura
									out.println(
									"<td><input type='button' class='btn btn-primary btn-icon-split, text' style='width:95%' id='btn-candidatar-"
											+ x + "' onClick=\"enviarSolicitacao('btn-candidatar-" + x + "','titulo" + x + "', 'professor" + x
											+ "', '"+ matriculaAluno +"')\" name='btn-candidatar' value='Candidatar-se'></td>");
								}
							out.println("</tr>");
							x++;
						}
						%>
					</tbody>
				</table>
			</div>
		</div>
	</div>
<a class= "btn btn-primary"  href= "alunoDashboard" role="button">Voltar</a>
<a class= "btn btn-primary"  href= "login.html" role="button">Login</a>
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
	function enviarSolicitacao(idBotao, idTitulo, idProfessor, alunoMatricula) {
		var matricula = document.getElementById("teste").getAttribute("value");
		
		var titulo = $("#" + idTitulo).text();
		var professor = $("#" + idProfessor).text();
		if (document.getElementById(idBotao).getAttribute("value") == "Candidatar-se") {
			$.ajax({
				method : "POST",
				url : "InscricaoProjetoServlet?opcao=incluir",
				data : {
					'titulo' : titulo,
					'professor' : professor,
					'alunoMatricula' : matricula
				},
				success : function(msg) { //Em caso de sucesso na requisicao, executa a seguinte funcao
					console.log("Requisi��o Enviada: " + msg);
					document.getElementById(idBotao).setAttribute("value",
							"Aguardando");
					document.getElementById(idBotao).setAttribute("class",
							"btn btn-info btn-icon-split, text");
					alert("Inscri��o Conclu�da: Aguarde a aprova��o do professor");
				}
			})
		} else if (document.getElementById(idBotao).getAttribute("value") == "Aguardando") {
			$.ajax({
				method : "POST",
				url : "InscricaoProjetoServlet?opcao=deletar",
				data : {
					'titulo' : titulo,
					'professor' : professor,
					'alunoMatricula' : matricula
				},
				success : function(msg) { //Em caso de sucesso na requisicao, executa a seguinte funcao
					console.log("Requisi��o Enviada: " + msg);
					document.getElementById(idBotao).setAttribute("value",
							"Candidatar-se");
					document.getElementById(idBotao).setAttribute("class",
							"btn btn-primary btn-icon-split, text");
					alert("Inscri��o Desfeita: Voc� pode se reinscrever no projeto se quiser");
				}
			})
		}
		
	}
</script>