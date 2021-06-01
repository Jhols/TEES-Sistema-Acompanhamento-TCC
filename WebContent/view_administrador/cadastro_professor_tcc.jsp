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
                                <h1 class="h5 text-gray-900 mb-4">Insira os dados abaixo para cadastrar um(a) professor(a):</h1>
                            </div>
                            <form class="user" method="POST" action="CadastroSecretariaServlet?opcao=cadastrarProfessor">
                                <div class="form-group row" style="display: inline;">
                                    <div class="col-sm-6 mb-3 mb-sm-0" style="float:left; padding: 0 5px 15px 0">
                                        <input type="text" class="form-control form-control-user" id="primeiroNome"
                                            name="primeiroNome" placeholder="Primeiro Nome" required>
                                    </div>
                                    <div class="col-sm-6" style="float:right; padding: 0 0 15px 5px">
                                        <input type="text" class="form-control form-control-user" id="sobrenomne"
                                            name="sobrenome" placeholder="Sobrenome" required>
                                    </div>
                                </div>
                                <div class="form-group" style="display: inline;">
                                	<div class="col-sm-6" style="float:left; padding: 0 5px 15px 0">
                                    	<input type="email" class="form-control form-control-user" id="email"
                                        	name="email" placeholder="Endereço de E-mail" required>
                                  	</div>
                                    <div class="col-sm-6" style="float:right; padding: 0 0 15px 5px">
                                	<input type="text" class="form-control form-control-user" id="matricula"
                                    	name="matricula" placeholder="Nº de Matrícula" required maxlength="10" onKeyPress="mascaraMatricula(form.matricula)">
	                                </div>
                                </div>
                                <div class="form-group row" style="display: inline;">
	                                <div class="col-sm-6" style="float:left; padding: 0 5px 15px 0">
	                                	<input type="tel" class="form-control form-control-user" id="telefone"
	                                    	name="telefone" placeholder="Telefone (xx)xxxx-xxxx" required maxlength="15" onKeyPress="MascaraTelefone(form.telefone);">
	                                </div>
	                                <div class="col-sm-6" style="float:right; padding: 0 0 0 5px">
	                                	<input type="text" class="form-control form-control-user" id="usuario"
	                                    	name="usuario" placeholder="Nome de Usuário" required maxlength="10" onblur="consultaUsuario(form.usuario);">
	                                    	<div id="div-check-user" style="visibility:hidden; color:green; font-size:14px"><p id=p-user>Usuario Valido</p></div>
	                                </div>
                                </div>
                                <div class="form-group row" id="check_senhas" style="display: inline;">
                                    <div class="col-sm-6 mb-3 mb-sm-0" style="float:left; padding: 0 5px 15px 0; margin-top: 18px;">
                                        <input type="password" class="form-control form-control-user"
                                            id="senha" name="senha" placeholder="Senha" required onblur="conferirSenha(form.senha, form.repetirSenha)">
                                    </div>
                                    <div class="col-sm-6" style="float:right; padding: 0 0 15px 5px; margin-top: -5px;">
                                        <input type="password" class="form-control form-control-user"
                                            id="repetirSenha" placeholder="Repetir Senha" required onblur="conferirSenha(form.senha, form.repetirSenha)">
                                    	<div id="div-senhas" style="visibility:hidden; color:red; font-size:14px;"><p>As senhas estão diferentes!</p></div>
                                    </div>
                                </div>
                                <div style="height: 20px; background-color:white"></div>
                                <input type="submit" id="btn_enviar" class="btn btn-primary btn-user btn-block" value="Cadastrar" disabled>
                            </form>
                            <hr>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

<%@include file="rodape_admin.html" %>