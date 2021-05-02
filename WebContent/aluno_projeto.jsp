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
	Pessoa aluno = PessoaFactory.getPessoa(Perfil.ALUNO, null, "0715123"); //Deve capturar da sessao do login
	aluno = AlunoDAO.getInstance().findByMatricula(((Aluno)aluno).getMatricula());
	
	InscricaoProjeto inscricao = new InscricaoProjeto();
	
	//Pesquisa no banco a inscricao com o projeto pelo qual o aluno e' associado
	inscricao = InscricaoProjetoDAO.getInstance().pesquisarProjetoAssociado((Aluno) aluno);
	%>
	
	<% //Condicao da pagina. Caso haja ou n�o um projeto associado ao aluno logado.
	if (inscricao != null) { //Se houver projeto associado, sao executadas as seguintes instrucoes.
		System.out.println("Inscricao do aluno: " + inscricao.getAluno().toString() + " " + inscricao.getProjeto().toString());
	%>	
		<div>
			<h4 style="display: inline">T�tulo: </h4> <span> <%= inscricao.getProjeto().getTitulo() %> </span> <br>
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
				<legend>Relat�rio</legend>
				<div><label class="col-form-label">T�tulo Relat�rio: <br><input type="text" id="" class="form-control" style="width: 425px"/> </label> </div>
				<div><label for="conteudo" class="col-form-label">Conte�do: <br><textarea id="" class="form-control" rows="10" cols="50"></textarea></label></div>
				<div><input type=submit id="" class="btn btn-primary btn-icon-split btn-lg" name="" onClick="" style="float:right; padding: 5px; margin-left: 10px" value="Enviar Relat�rio" /></div>
				<div><input type=button id="" class="btn btn-secondary btn-icon-split btn-sm" name="" onClick="" style="float:right; padding: 5px" value="Anexar Arquivo" /></div>
			</fieldset>
		</form>
	<%
	}
	else { //Se n�o houver projeto associado...
		System.out.println("Projeto associado nao encontrado!");
		%>
		<div>
			<h3>Ops... Voc� ainda n�o possui um projeto ativo!</h3>
			<h4>Clique no bot�o abaixo para se inscrever em um novo projeto.</h4>
	
			<a class="btn btn-primary btn-icon-split btn-lg"
				href="ProjetoServlet?opcao=listar"> <span class="text">Procurar
					Projetos Dispon�veis</span>
			</a>
		</div>
	<%}%>
</div>
<!-- /.container-fluid -->
<%@include file="template_rodape.html"%>