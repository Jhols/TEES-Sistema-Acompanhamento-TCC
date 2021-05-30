<!DOCTYPE html>
<html lang="pt-br">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    

    <!-- Custom fonts for this template-->
    <link href="resources/bootstrap/vendor/fontawesome-free/css/all.css" rel="stylesheet" type="text/css">
    <link
        href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
        rel="stylesheet">

    <!-- Custom styles for this template-->
    <link href="resources/bootstrap/css/sb-admin-2.css" rel="stylesheet">
    
    <script src="resources/sistema/js/checagem.js"></script>

</head>

<body id="page-top">

    <!-- Page Wrapper -->
    <div id="wrapper" style="float:left">

        <!-- Sidebar -->
        <ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">

            <!-- Sidebar - Brand -->
            <a class="sidebar-brand d-flex align-items-center justify-content-center" href="index.html">
                <div class="sidebar-brand-icon rotate-n-15">
                    <i class="fas fa-laugh-wink"></i>
                </div>
                <div class="sidebar-brand-text mx-3">PERFIL ADMINISTRADOR</div>
            </a>

            <!-- Divider -->
            <hr class="sidebar-divider my-0">

            
            <!-- Divider -->
            <hr class="sidebar-divider">

                   

           <!-- Nav Item - Pages Collapse Menu -->
           <!-- Nav Item - Charts -->
            <li class="nav-item">
                <a class="nav-link" href="administradorDashboard?opcao=cadSecretaria">
                    <i class="fas fa-fw fa-wrench"></i>
                    <span style="text-align:center">Cadastro Secretaria</span></a>
            </li>

            <!-- Nav Item - Charts -->
            <li class="nav-item">
                <a class="nav-link" href="administradorDashboard?opcao=cadCoordenador">
                    <i class="fas fa-fw fa-wrench"></i>
                    <span>Cadastro Coordenador</span></a>
            </li>
            
            <!-- Nav Item - Charts -->
            <li class="nav-item">
                <a class="nav-link" href="administradorDashboard?opcao=cadProfessor">
                    <i class="fas fa-fw fa-wrench"></i>
                    <span>Cadastro Professor</span></a>
            </li>
            
            <!-- Nav Item - Charts -->
            <li class="nav-item">
                <a class="nav-link" href="administradorDashboard?opcao=sair" style="background-color:#0453ba">
                    <i class="fas fa-fw fa-wrench"></i>
                    <span>Sair</span></a>
            </li>
        </ul>
       
	</div>
   
   <!-- Conteudo -->
   <div class="container" style="display: flex;">

        <div class="card o-hidden border-0 shadow-lg my-5">
            <div class="card-body p-0">
                <!-- Nested Row within Card Body -->
                <div class="row">
                    <!-- <div class="col-lg-5 d-none d-lg-block bg-register-aluno-image"></div>-->
                    <div class="col-lg-5 d-none d-lg-block" style="float:left"><img src="https://images.unsplash.com/photo-1517486808906-6ca8b3f04846?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTl8fHN0dWRlbnR8ZW58MHx8MHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"/></div>
                    <div class="col-lg-7">
                        <div class="p-5">
                            <div class="text-center">
                                <h1 class="h5 text-gray-900 mb-4">Insira os dados abaixo para cadastrar um(a) secretário(a):</h1>
                            </div>
                            <form class="user" method="POST" action="CadastroAlunoServlet">
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
                                <div class="form-group">
                                    <input type="email" class="form-control form-control-user" id="email"
                                        name="email" placeholder="Endereço de E-mail" required>
                                </div>
                                <div class="form-group row" style="display: inline;">
	                                <div class="col-sm-6" style="float:left; padding: 0 5px 15px 0">
	                                	<input type="text" class="form-control form-control-user" id="matricula"
	                                    	name="matricula" placeholder="Nº de Matrícula" required maxlength="10" onKeyPress="mascaraMatricula(form.matricula)">
	                                </div>
	                                <div class="col-sm-6" style="float:right; padding: 0 0 15px 5px">
	                                	<input type="tel" class="form-control form-control-user" id="telefone"
	                                    	name="telefone" placeholder="Telefone (xx)xxxx-xxxx" required maxlength="15" onKeyPress="MascaraTelefone(form.telefone);">
	                                </div>
                                </div>
                                <div style="height: 20px; background-color:white"></div>
                                <input type="submit" id="btn_enviar" class="btn btn-primary btn-user btn-block" value="Cadastrar">
                            </form>
                            <hr>
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