$(document).ready(function() {

    $('#form-login').on('submit', function(e) {
        e.preventDefault();
        $.ajax({
			url: '/login',
			type: 'POST',
			data: 'username=' + $('#email').val() + '&password=' + $('#senha').val(),
			beforeSend: function() {
                $('#form-login .alertas').empty().append('<div class="spinner-border spinner-border-sm" role="status"></div> Aguarde...');
			},
			complete: function() {
			},
			success: function(data, textStatus) {
			    sessionStorage.setItem("auditor", JSON.stringify(data.auditor));
			    sessionStorage.setItem("empresa", JSON.stringify(data.empresa));
			    window.location = '/';
			},
			error: function(xhr,er) {
			    switch (xhr.status) {
			        case 401:
			            $('#form-login .alertas').empty().append('<span class="text-danger">Usuário e/ou senha inválidos</span>');
			            break;
			        default:
			            $('#form-login .alertas').empty().append('<span class="text-danger">Não foi possível realizar a operação</span>');
			    }

			    $('.text-danger').fadeOut(6500).delay(1500);
			}
		});
    });

});