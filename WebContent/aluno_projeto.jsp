<%@page import="controller.ServletInscricaoProjeto"%>
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
			<a class= "btn btn-primary"  href= "alunoDashboard" role="button">Voltar</a>
			<a class= "btn btn-primary"  href= "login.html" role="button">Login</a>
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