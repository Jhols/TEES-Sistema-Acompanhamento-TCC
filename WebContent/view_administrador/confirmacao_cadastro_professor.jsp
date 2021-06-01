<%@include file="topo_admin.html" %>
   
   <!-- Conteudo -->
   <div class="container" style="display: flex;">

        <div class="card o-hidden border-0 shadow-lg my-5">
            <div class="card-body p-0">
                <!-- Nested Row within Card Body -->
                <div class="row">
                    <!-- <div class="col-lg-5 d-none d-lg-block bg-register-aluno-image"></div>-->
                    <div class="col-lg-5 d-none d-lg-block" style="float:left"><img height=500 width=500 src="https://image.freepik.com/vetores-gratis/personagem-do-professor_15624-207.jpg"/></div>
                    <div class="col-lg-7">
                        <div class="p-5">
                            <div class="text-center">
                                <h1 class="h5 text-gray-900 mb-4" style="text-align:center">Professor(a) <%=request.getSession().getAttribute("primeiroNome")%> cadastrado(a) com sucesso!</h1>
                                <a href="administradorDashboard?opcao=cadProfessor">Cadastrar novo(a) professor(a)</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

<%@include file="rodape_admin.html" %>