function MascaraTelefone(tel) {
	if (mascaraInteiro(tel) == false) {
		event.returnValue = false;
	}
	console.log(document.getElementById("telefone").value.length);
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
function conferirSenha() {
	var senha = document.getElementById("senha");
	var repeteSenha = document.getElementById("repetirSenha");

	if (senha.value != repeteSenha.value && repeteSenha.value != "") {
		if (aviso == false) {
			var parag = document.createElement("p");
			parag.innerText = "As senhas estão diferentes!";
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