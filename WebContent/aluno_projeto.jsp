<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.ArrayList"%>
<%@page import="controller.ServletInscricaoProjeto"%>
<%@page import="dao.InscricaoProjetoDAO"%>
<%@page import="model.Projeto"%>
<%@page import="model.InscricaoProjeto"%>
<%@page import="model.PessoaFactory"%>
<%@page import="dao.AlunoDAO"%>
<%@page import="model.Aluno"%>
<%@page import="model.Pessoa"%>
<%@page import="model.Relatorio"%>
<%@page import="enums.Perfil"%>
<%@include file="template_topo.html"%>
<!-- Begin Page Content -->
<div class="container-fluid">
	<!-- Page Heading -->
	<h1 class="h3 mb-4 text-gray-800">Meu Projeto</h1>
	
	<% //Instanciacao da inscricao do projeto associado
	InscricaoProjeto inscricao = new InscricaoProjeto();
	//Pesquisa no banco a inscricao com o projeto pelo qual o aluno e' associado
	inscricao = (InscricaoProjeto) request.getAttribute("inscricao");
	
	ArrayList<Relatorio> relatoriosRecebidos = new ArrayList<Relatorio>();
	ArrayList<Relatorio> relatoriosEnviados = new ArrayList<Relatorio>();
	
	relatoriosRecebidos = (ArrayList<Relatorio>) request.getAttribute("relatoriosRecebidos");
	relatoriosEnviados = (ArrayList<Relatorio>) request.getAttribute("relatoriosEnviados");
	
	%>
	
	<% //Condicao da pagina. Caso haja ou nao um projeto associado ao aluno logado.
	if (inscricao != null) { //Se houver projeto associado, sao executadas as seguintes instrucoes.
		System.out.println("Inscricao do aluno: " + inscricao.getAluno().toString() + " " + inscricao.getProjeto().toString());
	%>	
	<div style="display:flex">
		<div style="float:left">
			<h4>Título: </h4> <span> <%= inscricao.getProjeto().getTitulo() %> </span> <br>
			<h4>Descricao: </h4> <span> <%= inscricao.getProjeto().getDescricao() %> </span> <br>
			<h4>Orientador: </h4> <span> <%= inscricao.getProjeto().getProfessor().getNome() %> </span> <br>
			<br>
			<br>
			<br>
			<br>
			<br>
			<br>
			<a class= "btn btn-primary"  href= "alunoDashboard" role="button">Voltar</a>
			<a class= "btn btn-primary"  href= "login.html" role="button">Login</a>
		</div>
		
		<!-- Relatórios -->
		<div class="menu_relatorios">
		<h1 class="h4 mb-4 text-gray-600">Relatórios</h1><nav class="nav_tabs">
			<ul>
				<li>
					<input type="radio" id="tab1" class="rd_tab" name="tabs" checked="">
					<label for="tab1" class="tab_label">Recebidos</label>
					<div class="tab-content">
						<article>
						<div class="tabela_relatorios">
							<table class="table table-bordered">
								<thead>
								<tr>
									<td> Título </td>
									<td> Remetente </td>
									<td> Data </td>
								</tr>
								</thead>
								<tbody id=regsTabela1>
								<%
								 SimpleDateFormat fd = new SimpleDateFormat("dd-MM-yyyy");
								 if (!relatoriosRecebidos.isEmpty()) {
									 int x = 1;
									  for (Relatorio relatorio : relatoriosRecebidos) { %>
										<tr class="regTabela" onclick="apresentarRelatorio(<%= relatorio.getIdRelatorio() %>)">
											<td id="titulo<%=x%>"> <%=relatorio.getTitulo()%> </td>
											<td id="autor<%=x%>"> <%=relatorio.getNomeAutor()%> </td>
											<td id="data<%=x%>"> <%=fd.format(relatorio.getData())%> </td>
										</tr>
										<%x++;%>
								<% 	  }
								   } else { %>
										<tr>
											<td colspan=3> No momento, não há relatórios recebidos </td>
										</tr>
								<% } %>
								</tbody>
							</table>
						</div>
						</article>
					</div>
				</li>
				<li>
					<input type="radio" name="tabs" class="rd_tab" id="tab2">
					<label for="tab2" class="tab_label">Enviados</label>
					<div class="tab-content">
						<article>
						<div class="tabela_relatorios">
							<table class="table table-bordered">
								<thead>
								<tr>
									<td> Título </td>
									<td> Destinatário </td>
									<td> Data </td>
								</tr>
								</thead>
								<tbody>
								<%
								 if (!relatoriosEnviados.isEmpty()) {
								   int x = 1;
								   for (Relatorio relatorio : relatoriosEnviados) { %>
									<tr class="regTabela" onclick="apresentarRelatorio(<%= relatorio.getIdRelatorio() %>)">
										<td id="titulo<%=x%>"> <%=relatorio.getTitulo()%> </td>
										<td id="autor<%=x%>"> <%=relatorio.getNomeDestinatario()%> </td>
										<td id="data<%=x%>"> <%=fd.format(relatorio.getData())%> </td>
									</tr>
									<%x++;%>
								<% }
								  } else { %>
									<tr>
										<td colspan=3> No momento, não há relatórios enviados</td>
									</tr>
								<% } %>
								</tbody>
							</table>
						</div>
						</article>
					</div>
				</li>
				<li id="tabCriarRelatorio">
				  <input type="button" name="envRelatorio" class="btn btn-primary" value="Novo relatório" onClick="criarRelatorio()">
				</li>
			</ul>
		</nav>
		</div>
		
		<div id="regiaoTexto">
			<form id="formulario">
				<textarea id="textoRelatorio" cols="50" rows="14" readonly> </textarea>
				<div id="camposRelatorio">
				    <label for="tituloRelatorio"> Título </label><br>
				    <input id="tituloRelatorio" type="text" class="form-control form-control-user" required><br>
				    <label for="destinatarioRelatorio"> Destinatário </label><br>
				    <select id="destinatarioRelatorio"  class="form-control form-control-user" required>
				    	<option value="<%= inscricao.getProjeto().getProfessor().getId() %>" selected> Orientador </option>
				    </select>
				    <br>
			    	<div style="margin-top:130px"></div>
			    	<input type="button" id="btn-enviar" name="enviar" class="btn btn-primary" value="Enviar Relatório" onclick="enviarRelatorio(<%= request.getSession().getAttribute("idPessoa") %>)">
			    </div>
		    </form>
		</div>
	</div>
		
	<%
	}
	else { //Se nao houver projeto associado...
		System.out.println("Projeto associado nao encontrado!");
		%>
		<div>
			<h3>Ops... Voce ainda nao possui um projeto ativo!</h3>
			<h4>Clique no botao abaixo para se inscrever em um novo projeto.</h4>
	
			<a class="btn btn-primary btn-icon-split btn-lg"
				href="ProjetoServlet?opcao=listar"> <span class="text">Procurar
					Projetos Disponiveis</span>
			</a>
		</div>
	<%}%>
</div>
<!-- /.container-fluid -->
<%@include file="template_rodape.html"%>

<script>
//Funcao que apresenta um relatorio na TextArea, como se fosse um email
function apresentarRelatorio(idRelatorio) {
	$("#camposRelatorio").css("visibility", "hidden");
	$.ajax({
		method : "POST",
		url : "InscricaoProjetoServlet?opcao=apresentarRelatorio",
		data : {
			'idRelatorio' : idRelatorio
		},
		beforeSend: function() { //Mensagem de carregamento da mensagem
			$("#textoRelatorio").css("color", "#8f8f8f");
			$("#textoRelatorio").val("Carregando mensagem...");
		},
		success : function(msg) { //Em caso de sucesso na requisicao, executa a seguinte funcao
			$("#textoRelatorio").attr("readonly", "readonly");
			$("#textoRelatorio").css("color", "#383838");
			$("#textoRelatorio").val(msg); //Apresenta a mensagem
		}
	})
}

//Funcao executada apos clicar no botao "Novo relatorio".
//Permite o aluno criar um relatorio para ser enviado para um professor
function criarRelatorio() {
	$("#camposRelatorio").css("visibility", "visible");
	$("#textoRelatorio").val("");
	$("#tituloRelatorio").val("");
	$("#destinatarioRelatorio").val("");
	$("#textoRelatorio").focus();
	$("#textoRelatorio").prop("readonly", false);
	$("#btn-enviar").prop("disabled", "disabled");
}

function enviarRelatorio(idAutor) {
	$.ajax({
		method : "POST",
		url : "InscricaoProjetoServlet?opcao=enviarRelatorio",
		data : {
			'titulo' : $("#tituloRelatorio").val(),
			'autor' : idAutor,
			'destinatario' : $("#destinatarioRelatorio").val(),
			'texto' : $("#textoRelatorio").val()
		},
		beforeSend: function() { //Mensagem de carregamento da mensagem
			$("#btn-enviar").prop("disabled", "disabled");
			$("#btn-enviar").removeClass("btn btn-primary").addClass("btn btn-secondary");
			$("#btn-enviar").val("Enviando, aguarde...");
		},
		success : function(msg) { //Em caso de sucesso na requisicao, executa a seguinte funcao
			$("#textoRelatorio").prop("readonly", "readonly");
			$("#textoRelatorio").css("color", "#383838");
			$("#btn-enviar").removeClass("btn btn-secondary").addClass("btn btn-success");
			$("#btn-enviar").val(msg);
		}
	})
}

$(document).ready(function() {
	$("#textoRelatorio").val("Selecione um relatório na lista ao lado para ler");
});
$("#textoRelatorio").keyup(verificarCampos);
$("#tituloRelatorio").keyup(verificarCampos);
$("#destinatarioRelatorio").change(verificarCampos);

function verificarCampos() {
	var t1 = false, t2 = false, t3 = false;
	
	if ($("#textoRelatorio").val() != "")
		t1 = true;
	if ($("#tituloRelatorio").val() != "")
		t2 = true;
	if ($("#destinatarioRelatorio").val() != null)
		t3 = true;
	if (t1 == t2 && t2 == t3 && t1 == true) {
		$("#btn-enviar").prop("disabled", false);
	}
	else {
		$("#btn-enviar").prop("disabled", true);
	}
}
</script>

<style>
	@import url(https://fonts.googleapis.com/css?family=Roboto+Condensed);
	* {
		margin: 0;
		padding: 0;
	}

	body {
		background-color: #ddd;
		font-family: 'Roboto Condensed';
	}

	h4 {
		display: inline;
	}

	.nav_tabs {
		width: 600px;
		height: 400px;
		margin: auto;
		position: relative;
	}

	.nav_tabs ul {
		list-style: none;
	}

	.nav_tabs ul li {
		float: left;
	}

	.tab_label {
		display: inline-table;
		width: 100px;
		background-color: #363b48;
		padding: 10px;
		font-size: 20px;
		color:#fff;
		cursor: pointer;
		text-align: center;
		margin: 0;
	}


	.nav_tabs .rd_tab { 
		display:none;
		position: absolute;
	}
	
	.nav_tabs .rd_tab:checked ~ label { 
		background-color: #5784ff; /*cor azul*/
		color:#fff;
	}
	
	.tab-content {
		border-top: solid 5px #5784ff; /*cor azul*/
		background-color: #fff;
		display: none;
		position: absolute;
		height: 320px;
		width: 600px;
		left: 0;	
	}
	
	.rd_tab:checked ~ .tab-content {
		display: block;
	}
	
	.tab-content h2 {
		padding: 10px;
		color: #525252;
	}
	
	.tab-content article {
		padding: 10px;
		color: #555;
	}
	
	.menu_relatorios {
		margin-left:50px;
		margin-right:50px;
	}
	
	.tabela_relatorios {
		overflow: auto;
		width: auto;
		height: 300px;
	}
	
	article table td {
		padding: 10px;
	}
	
	thead {
		color: #5c5c5c;
		font-size:20px;
	}
	
	thead tr td:nth-child(1) {
		width: 340px;
	}
	
	thead tr td:nth-child(2) {
		width: 150px;
	}
	
	thead tr td:nth-child(3) {
		width: 83px;
	}
	
	.regTabela:hover {
		background-color: #ebebeb;
		cursor: pointer;
	}
	
	#textoRelatorio {
		 resize: none;
		 margin-top:80px;
	}
	
	#tabCriarRelatorio {
		margin-top: 8px;
		float: right;
	}
	
	#camposRelatorio {
		float:right;
		margin: 80px 0 0 20px;
		visibility: hidden;
	}
	
	#camposRelatorio .form-control {
		width: 320px;
	}
</style>