function MascaraTelefone(tel) {
	if (mascaraInteiro(tel) == false) {
		event.returnValue = false;
	}

	if (document.getElementById("telefone").value.length < 14)
		return formataCampo(tel, '(00) 0000-0000', event);
	return formataCampo(tel, '(00) 00000-0000', event);
}

function mascaraMatricula(campo_matricula) {
	if (mascaraInteiro(campo_matricula) == false) {
		event.returnValue = false;
	}
}

function formataCampo(campo, Mascara, evento) {
	var boleanoMascara;

	var Digitato = evento.keyCode;
	exp = /\-|\.|\/|\(|\)| /g
	campoSoNumeros = campo.value.toString().replace(exp, "");

	var posicaoCampo = 0;
	var NovoValorCampo = "";
	var TamanhoMascara = campoSoNumeros.length;;

	if (Digitato != 8) { // backspace 
		for (i = 0; i <= TamanhoMascara; i++) {
			boleanoMascara = ((Mascara.charAt(i) == "-") || (Mascara.charAt(i) == ".")
				|| (Mascara.charAt(i) == "/"))
			boleanoMascara = boleanoMascara || ((Mascara.charAt(i) == "(")
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
	if (event.keyCode < 48 || event.keyCode > 57) {
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
	if (document.forms[0].email.value == ""
		|| document.forms[0].email.value.indexOf('@') == -1
		|| document.forms[0].email.value.indexOf('.') == -1) {
		alert("Por favor, informe um E-MAIL válido!");
		return false;
	}
}

var aviso = false;
function conferirSenha(senha, repeteSenha) {
    if (senha.value != repeteSenha.value && repeteSenha.value != "") {
        if (aviso == false) {
            document.getElementById("div-senhas").style.visibility = "visible";
            aviso = true;
            document.getElementById("btn_enviar").setAttribute("disabled", true);
            
        }
    }
    else if (senha.value == repeteSenha.value && repeteSenha.value != "") {
        repeteSenha.setCustomValidity('');
        if (aviso == true) {
            document.getElementById("div-senhas").style.visibility = "hidden";
            aviso = false;
        }
        confereCampos("div-check-user", "senha", "repetirSenha", "btn_enviar");
    }
    else {
    	document.getElementById("btn_enviar").setAttribute("disabled", true);
  	}
}

$(document).ready(function() {
	document.getElementById("btn_enviar").setAttribute("disabled", true);
});

var userValido = "Usuario Valido";
function consultaUsuario(usuario) {
	$.ajax({
    	method: "POST",
    	url: "CadastroSecretariaServlet?opcao=checarUsuario",
    	data: {
    		'nomeUsuario' : usuario.value
    	},
    	beforeSend: function() {
    		if (usuario.value != "") {
			  document.getElementById("btn_enviar").setAttribute("disabled", true);
              document.getElementById("div-check-user").style.color = "gray";
              document.getElementById("div-check-user").style.visibility = "visible";
              document.getElementById("div-check-user").firstChild.innerText = "Procurando Usuario. Por favor, aguarde!";		    			
    		}
    	}
    })
	  .done(function(msg){
		  switch (msg) {
	    	  case '0':
	    		  document.getElementById("div-check-user").style.visibility = "hidden";
	    		  break;
	    	  case '1':
	    		  document.getElementById("div-check-user").style.color = "green";
				  document.getElementById("div-check-user").style.visibility = "visible";
				  document.getElementById("div-check-user").firstChild.innerText = userValido;
				  confereCampos("div-check-user", "senha", "repetirSenha", "btn_enviar");
	    		  break;
	    	  case '2':
	    	  	  document.getElementById("div-check-user").style.color = "red";
	    	  	  document.getElementById("div-check-user").style.visibility = "visible";
				  document.getElementById("div-check-user").firstChild.innerText = "Usuario ja existe! Por favor, insira outro."
	    		  break;
			  default:
		  }
    });
}


function confereCampos(id_div_user, id_senha, id_repeteSenha, id_botao) {
	if (document.getElementById(id_div_user).firstChild.innerText == userValido) {
		if ($("#"+id_senha).val().length > 0 && $("#"+id_repeteSenha).val().length > 0)
			if ($("#"+id_senha).val() == $("#"+id_repeteSenha).val()) {
				$("#"+id_botao).prop("disabled", false);
			}
	}
	else {
		$("#"+id_botao).prop("disabled", true);
	}
}