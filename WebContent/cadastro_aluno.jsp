
<!DOCTYPE html>
<html lang="pt-BR">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>SB Admin 2 - Cadastrar Aluno</title>

    <!-- Custom fonts for this template-->
    <link href="resources/bootstrap/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link
        href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
        rel="stylesheet">

    <!-- Custom styles for this template-->
    <link href="resources/bootstrap/css/sb-admin-2.min.css" rel="stylesheet">

	<script src="resources/sistema/js/checagem.js"></script>
</head>

<body class="bg-gradient-primary">

    <div class="container">

        <div class="card o-hidden border-0 shadow-lg my-5">
            <div class="card-body p-0">
                <!-- Nested Row within Card Body -->
                <div class="row">
                    <!-- <div class="col-lg-5 d-none d-lg-block bg-register-aluno-image"></div>-->
                    <div class="col-lg-5 d-none d-lg-block" style="float:left"><img src="https://images.unsplash.com/photo-1517486808906-6ca8b3f04846?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTl8fHN0dWRlbnR8ZW58MHx8MHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"/></div>
                    <div class="col-lg-7">
                        <div class="p-5">
                            <div class="text-center">
                                <h1 class="h5 text-gray-900 mb-4">Insira os dados abaixo para se cadastrar, estudante!</h1>
                                <h6 class="h6 text-gray-900 mb-4">Se o seu cadastro for aprovado seu login será o <b>email informado</b> e a senha a <b>matricula informada!</b></h6>
                            </div>
                            <form class="user" method="POST" action="CadastroAlunoServlet">
                                <div class="form-group row">
                                    <div class="col-sm-6 mb-3 mb-sm-0">
                                        <input type="text" class="form-control form-control-user" id="primeiroNome"
                                            name="primeiroNome" placeholder="Primeiro Nome" required>
                                    </div>
                                    <div class="col-sm-6">
                                        <input type="text" class="form-control form-control-user" id="sobrenomne"
                                            name="sobrenome" placeholder="Sobrenome" required>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <input type="email" class="form-control form-control-user" id="email"
                                        name="email" placeholder="Endereço de E-mail" required>
                                </div>
                                <div class="form-group row">
	                                <div class="col-sm-6">
	                                	<input type="text" class="form-control form-control-user" id="matricula"
	                                    	name="matricula" placeholder="Nº de Matrícula" required maxlength="10" onKeyPress="mascaraMatricula(form.matricula)">
	                                </div>
	                                <div class="col-sm-6">
	                                	<input type="tel" class="form-control form-control-user" id="telefone"
	                                    	name="telefone" placeholder="Telefone (xx)xxxx-xxxx" required maxlength="15" onKeyPress="MascaraTelefone(form.telefone);">
	                                </div>
                                </div>
                                <!-- <div class="form-group row">
                                    <div class="col-sm-6 mb-3 mb-sm-0">
                                        <input type="password" class="form-control form-control-user"
                                            id="senha" name="senha" placeholder="Senha" required type="text" onblur="conferirSenha()">
                                    </div>
                                    <div class="col-sm-6" id="div-senhas">
                                        <input type="password" class="form-control form-control-user"
                                            id="repetirSenha" placeholder="Repetir senha" required type="text" onblur="conferirSenha()">
                                    </div>
                                </div> -->
                                <div style="height: 20px; background-color:white"></div>
                                <input type="submit" id="btn_enviar" class="btn btn-primary btn-user btn-block" value="Enviar Solicitação">
                            </form>
                            <hr style="margin-top: 170px;">
                            <div>
	                            
	                            <div class="text-center">
	                                <a class="small" href="login.html">Já possui uma conta? Faça o Login!</a>
	                            </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>


    <!-- Bootstrap core JavaScript-->
    <script src="resources/bootstrap/vendor/jquery/jquery.min.js"></script>
    <script src="resources/bootstrap/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

    <!-- Core plugin JavaScript-->
    <script src="resources/bootstrap/vendor/jquery-easing/jquery.easing.min.js"></script>

    <!-- Custom scripts for all pages-->
    <script src="resources/bootstrap/js/sb-admin-2.min.js"></script>

</body>

</html>