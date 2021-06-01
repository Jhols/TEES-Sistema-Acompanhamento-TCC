<%@include file="topo_admin.html" %>
   
   <!-- Conteudo -->
   <div class="container" style="display: flex;">

        <div class="card o-hidden border-0 shadow-lg my-5">
            <div class="card-body p-0">
                <!-- Nested Row within Card Body -->
                <div class="row">
                    <!-- <div class="col-lg-5 d-none d-lg-block bg-register-aluno-image"></div>-->
                    <div class="col-lg-5 d-none d-lg-block" style="float:left"><img height="500" width="500" src="https://image.freepik.com/vetores-gratis/charaters-de-trabalhadores-de-negocios-executivos_18591-59403.jpg"></div>
                    <div class="col-lg-7">
                        <div class="p-5">
                        	<%
	                       	boolean confirmado = (Boolean) request.getSession().getAttribute("confirmado");
	                       	if (confirmado) {
	                       	%>
                            <div class="text-center">
                                <h1 class="h5 text-gray-900 mb-4" style="text-align:center">Coordenador(a) <%=request.getSession().getAttribute("primeiroNome")%> cadastrado(a) com sucesso!</h1>
                                <a href="administradorDashboard?opcao=cadCoordenador">Cadastrar novo(a) coordenador(a)</a>
                            </div>
                            <%} else {%>
	                            <div class="text-center">
	                                <h1 class="h5 text-gray-900 mb-4"> Houve um erro com a sua solicitação de cadastro! Por favor, tente novamente.</h1>
	                                <h3 class="h6 text-gray-900 mb-4">Caso o erro persista, por gentileza, consulte o suporte técnico.</h3>
	                            </div>                            
                            <%}%>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

<%@include file="rodape_admin.html" %>