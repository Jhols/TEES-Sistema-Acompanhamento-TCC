<%@page import="enums.SituacaoProjeto"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="java.util.ArrayList, model.Projeto, dao.ProfessorDAO"%>
<%@page import="java.util.ArrayList, model.Projeto, dao.ProjetoDAO"%>
<%@page import="enums.SituacaoProjeto"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@include file="template_topo.html"%>
<!-- Begin Page Content -->
<div class="container-fluid">

	<!-- Page Heading -->
	<h1 class="h3 mb-2 text-gray-800">Projetos</h1>
	<p class="mb-4">Aqui voc� pode ver todos os seus projetos cadastrados 
		para cada projeto haver� a op��o de mudar o status do projeto, 
		exceto projetos que estejam com status ativo.</p>

	<!-- DataTales Example -->
	<div class="card shadow mb-4">
		<div class="card-header py-3">
			<h6 class="m-0 font-weight-bold text-primary">Projetos do
				Professor</h6>
		</div>
		<div class="card-body">
			<div class="table-responsive" style="padding: 15px;">
				<table class="table table-bordered" id="dataTable" width="100%"
					cellspacing="0">
					<thead>
						<tr>
							<th>T�tulo</th>
							<th>Descri��o</th>
							<th>Status do Projeto</th>
						</tr>
					</thead>
					<tbody>
						<%
						ArrayList<Projeto> projetos = new ArrayList<Projeto>();
						projetos = ProjetoDAO.getInstance().buscarProjetosPorIdProfessor(2);
						int x = 1;
						for (Projeto projeto : projetos) {
							SituacaoProjeto situacao = projeto.getSituacao();
							out.println("<tr>");
							out.println("<td id='titulo" + x + "'>" + projeto.getTitulo() + "</td>");
							out.println("<td>" + projeto.getDescricao() + "</td>"); //+ projeto.getSituacao()
							//out.println("<td>" + projeto.getSituacao() + "</td>");
							String combobox = "           <!-- Combobox -->\r\n"
								+ " <select name=\"sct_statusProjeto"+ x + "\"" + " id=\"sct_statusProjeto" + x + "\" onchange=\"enviarSolicitacao('sct_statusProjeto"+ x + "',"+ projeto.getId() + ")\"" + (situacao == SituacaoProjeto.ATIVO ? " disabled" : "") + ">\r\n"
								+ " 	<option value=\"" + SituacaoProjeto.ATIVO.getNomeSituacao() + "\" " + (situacao == SituacaoProjeto.ATIVO ? "selected >" : ">") + SituacaoProjeto.ATIVO.getNomeSituacao() + "</option>\r\n" 
								+ " 	<option value=\"" + SituacaoProjeto.DESATIVADO.getNomeSituacao() + "\" " + (situacao == SituacaoProjeto.DESATIVADO ? "selected >" : ">") + SituacaoProjeto.DESATIVADO.getNomeSituacao() +  "</option>\r\n" 
								+ "     <option value=\"" + SituacaoProjeto.DISPONIVEL.getNomeSituacao() + "\" " + (situacao == SituacaoProjeto.DISPONIVEL ? "selected >" : ">") + SituacaoProjeto.DISPONIVEL.getNomeSituacao() + "</option>\r\n"
								+ "     <option value=\"" + SituacaoProjeto.EXCLUIDO.getNomeSituacao() + "\" " + (situacao == SituacaoProjeto.EXCLUIDO ? "selected >" : ">") + SituacaoProjeto.EXCLUIDO.getNomeSituacao() + "</option>\r\n"
								+ " </select>\r\n";
							out.println("<td>" + combobox + "</td>");	
							out.println("</tr>");
							x++;
						}
						%>
					</tbody>
				</table>
			</div>
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
	
	/**
	* @author Jehcky
	* Esta fun��o envia uma solicita��o ajax para alterar a Situacao do Projeto
	*/
	function enviarSolicitacao(idCombobox, idProjeto) {
		var novaSituacao = $("#" + idCombobox + " option:selected").text();
			$.ajax({
				method : "POST",
				url : "projetosProfessor?opcao=alterar_situacao_projeto",
				data : {
					'novaSituacao' : novaSituacao,
					'idProjeto' : idProjeto
				},
				success : function(msg) { //Em caso de sucesso na requisi��o, executa a seguinte fun��o
					alert("Situacao do projeto atualizada");
				}
			})
		

	}
</script>