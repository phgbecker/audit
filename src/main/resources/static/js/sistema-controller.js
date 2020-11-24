$(document).ready(function() {

    if (sessionStorage.length == 0
        || sessionStorage.getItem('auditor') == null
        || sessionStorage.getItem('auditor') == "null"
        || sessionStorage.getItem('empresa') == null
        || sessionStorage.getItem('empresa') == "null") {
        console.error('Sessão inválida');
        window.location = '/login.html';
    }

    if (JSON.parse(sessionStorage.getItem('auditor')).administrador) {
        $('.perfil-acesso').css('display', 'block');
    } else {
        $('.perfil-acesso').css('display', 'none');
    }

    $('#nome-perfil').text(
        JSON.parse(sessionStorage.getItem('auditor')).nome.split(' ')[0]
    );

    $('#sair-sistema').on('click', function(e) {
        e.preventDefault();
        $.ajax({
			url: '/logout',
			type: 'POST',
			beforeSend: function() {
			},
			complete: function() {
			},
			success: function(data, textStatus) {
			    sessionStorage.clear();
			    window.location = '/login.html';
			},
			error: function(xhr,er) {
			    alert('Não foi possível realizar a operação');
			}
		});
    });

});