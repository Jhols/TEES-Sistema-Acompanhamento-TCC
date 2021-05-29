
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

</head>

<body class="bg-gradient-primary">

    <div class="container">

        <div class="card o-hidden border-0 shadow-lg my-5">
            <div class="card-body p-0">
                <!-- Nested Row within Card Body -->
                <div class="row">
                    <!-- <div class="col-lg-5 d-none d-lg-block bg-register-aluno-image"></div>-->
                    <div class="col-lg-5 d-none d-lg-block"><img src="https://images.unsplash.com/photo-1517486808906-6ca8b3f04846?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTl8fHN0dWRlbnR8ZW58MHx8MHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"/></div>
                    <div class="col-lg-7">
                        <div class="p-5">
                        	<%
                        	boolean confirmado = (Boolean) request.getSession().getAttribute("confirmado");
                        	if (confirmado) {
                        	%>
                            <div class="text-center">
                                <h1 class="h4 text-gray-900 mb-4">Obrigado, <%=request.getParameter("primeiroNome")%>!</h1>
                                <h3 class="h6 text-gray-900 mb-4">Sua solicita��o de cadastro foi enviada!</h3>
                                <h3 class="h6 text-gray-900 mb-4">
                                Agora, � s� aguardar para ter a sua conta liberada para o uso, atrav�s de um email com a senha e instru��es, enviados pelo seu professor.
                                </h3>
                                <h3 class="h6 text-gray-900 mb-4">
                                Em caso de d�vidas, contate seu professor de turma ou a secretaria do seu colegiado.
                                </h3>
                            </div>
                            <%} else {%>
                            <div class="text-center">
                                <h1 class="h5 text-gray-900 mb-4"> Houve um erro com a sua solicita��o de cadastro!</h1>
                                <h3 class="h6 text-gray-900 mb-4">Voc� pode tentar cadastra-se novamente ou contatar seu professor de turma ou a secretaria do seu colegiado.</h3>
                            </div>                            
                            <%}%>
                            <div class="text-center">
                                <a class="small" href="login.html">Voltar para a tela de Login</a>
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
    
    <script>
        function MascaraTelefone(tel){  
		    if(mascaraInteiro(tel)==false){
		            event.returnValue = false;
		    }       
		    return formataCampo(tel, '(00) 00000-0000', event);
		}
        
        function formataCampo(campo, Mascara, evento) { 
		    var boleanoMascara; 
		
		    var Digitato = evento.keyCode;
		    exp = /\-|\.|\/|\(|\)| /g
		    campoSoNumeros = campo.value.toString().replace( exp, "" ); 
		
		    var posicaoCampo = 0;    
		    var NovoValorCampo="";
		    var TamanhoMascara = campoSoNumeros.length;; 
		
		    if (Digitato != 8) { // backspace 
	            for(i=0; i<= TamanhoMascara; i++) { 
	                boleanoMascara  = ((Mascara.charAt(i) == "-") || (Mascara.charAt(i) == ".")
                                        || (Mascara.charAt(i) == "/")) 
	                boleanoMascara  = boleanoMascara || ((Mascara.charAt(i) == "(") 
	                                    || (Mascara.charAt(i) == ")") || (Mascara.charAt(i) == " ")) 
	                if (boleanoMascara) { 
	                    NovoValorCampo += Mascara.charAt(i); 
	                    TamanhoMascara++;
	                } else { 
                        NovoValorCampo += campoSoNumeros.charAt(posicaoCampo); 
                        posicaoCampo++; 
                  	}            
	             }      
	             campo.value = NovoValorCampo;
	             return true; 
		    } else { 
            	return true; 
		    }
		}
        
        function mascaraInteiro() {
		    if (event.keyCode < 48 || event.keyCode > 57){
		            event.returnValue = false;
		            return false;
		    }
		    return true;
		}
        
        function verifica() {
        	  if (document.forms[0].email.value.length == 0) {
        	    alert('Por favor, informe o seu EMAIL.');
        		document.frmEnvia.email.focus();
        	    return false;
        	  }
        	  return true;
       	}
        
        function checarEmail() {
	       	if( document.forms[0].email.value=="" 
	       	   || document.forms[0].email.value.indexOf('@')==-1 
	       	     || document.forms[0].email.value.indexOf('.')==-1 )
	       		{
	       		  alert( "Por favor, informe um E-MAIL v�lido!" );
	       		  return false;
	       		}
       	}
        
       	var aviso = false;
        function conferirSenha() {
        	var senha = document.getElementById("senha");
        	var repeteSenha = document.getElementById("repetirSenha");
        	
        	if (senha.value != repeteSenha.value && repeteSenha.value != "") {
        		if (aviso == false) {
        			var parag = document.createElement("p");
	        		parag.innerText = "As senhas est�o diferentes!";
	        		parag.setAttribute("id", "avisoSenha");
	        		parag.style.color = "red";
	        		document.getElementById("div-senhas").appendChild(parag);
	        		aviso = true;
        		}
        	}
        	else {
        		repeteSenha.setCustomValidity('');
        		if (aviso == true) {
	        		var parag = document.getElementById("avisoSenha");
	        		parag.remove()
	        		aviso = false;
        		}
        	}
        }
        </script>

</body>

</html>