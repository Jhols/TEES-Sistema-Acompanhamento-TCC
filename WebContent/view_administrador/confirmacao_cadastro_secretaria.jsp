<%@include file="topo_admin.html" %>
   
   <!-- Conteudo -->
   <div class="container" style="display: flex;">

        <div class="card o-hidden border-0 shadow-lg my-5">
            <div class="card-body p-0">
                <!-- Nested Row within Card Body -->
                <div class="row">
                    <!-- <div class="col-lg-5 d-none d-lg-block bg-register-aluno-image"></div>-->
                    <div class="col-lg-5 d-none d-lg-block" style="float:left"><img src="https://i2.wp.com/educacaoinfantil.aix.com.br/wp-content/uploads/2019/11/501991-PI9MXK-511.jpg?fit=500%2C2000&ssl=1/"></div>
                    <div class="col-lg-7">
                        <div class="p-5">
	                        <%
	                       	boolean confirmado = (Boolean) request.getSession().getAttribute("confirmado");
	                       	if (confirmado) {
	                       	%>
                            <div class="text-center">
                                <h1 class="h5 text-gray-900 mb-4" style="text-align:center">Secretário(a) <%=request.getSession().getAttribute("primeiroNome")%> cadastrado(a) com sucesso!</h1>
                                <a href="administradorDashboard?opcao=cadSecretaria">Cadastrar novo(a) secretário(a)</a>
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