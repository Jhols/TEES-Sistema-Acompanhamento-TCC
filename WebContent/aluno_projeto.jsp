<%@page import="controller.InscricaoProjetoServlet"%>
<%@page import="dao.InscricaoProjetoDAO"%>
<%@page import="model.Projeto"%>
<%@page import="model.InscricaoProjeto"%>
<%@page import="model.PessoaFactory"%>
<%@page import="dao.AlunoDAO"%>
<%@page import="model.Aluno"%>
<%@page import="model.Pessoa"%>
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
	%>
	
	<% //Condicao da pagina. Caso haja ou nao um projeto associado ao aluno logado.
	if (inscricao != null) { //Se houver projeto associado, sao executadas as seguintes instrucoes.
		System.out.println("Inscricao do aluno: " + inscricao.getAluno().toString() + " " + inscricao.getProjeto().toString());
	%>	
		<div>
			<h4 style="display: inline">Título: </h4> <span> <%= inscricao.getProjeto().getTitulo() %> </span> <br>
			<h4 style="display: inline">Descricao: </h4> <span> <%= inscricao.getProjeto().getDescricao() %> </span> <br>
			<h4 style="display: inline">Orientador: </h4> <span> <%= inscricao.getProjeto().getProfessor().getNome() %> </span> <br>
			<br>
			<br>
			<br>
			<br>
			<br>
			<br>
		</div>
		<form action="" method="POST" id="" class="form-group" style="width:425px">
			<fieldset style="border:1">
				<legend>Relatório</legend>
				<div><label class="col-form-label">Título Relatório: <br><input type="text" id="" class="form-control" style="width: 425px"/> </label> </div>
				<div><label for="conteudo" class="col-form-label">Conteúdo: <br><textarea id="" class="form-control" rows="10" cols="50"></textarea></label></div>
				<div><input type=submit id="" class="btn btn-primary btn-icon-split btn-lg" name="" onClick="" style="float:right; padding: 5px; margin-left: 10px" value="Enviar RelatÃ³rio" /></div>
				<div><input type=button id="" class="btn btn-secondary btn-icon-split btn-sm" name="" onClick="" style="float:right; padding: 5px" value="Anexar Arquivo" /></div>
				<a class= "btn btn-primary" align="center" href= "alunoDashboard" role="button">Voltar</a>
				<a class= "btn btn-primary" align="center" href= "login.html" role="button">Login</a>
			</fieldset>
		</form>
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