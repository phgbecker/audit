$(document).ready(function() {

    popularFormularioPerfil();

    $('#form-perfil').on('submit', function(e) {
        let auditor = JSON.parse(sessionStorage.getItem('auditor'));
        let cnpj = JSON.parse(sessionStorage.getItem('empresa')).cnpj;

        e.preventDefault();
        $.ajax({
			url: '/rest/' + cnpj + '/auditores/' + auditor.cpf,
			type: 'PATCH',
			contentType: 'application/json',
			data: JSON.stringify(
			    {
                    cpf: auditor.cpf,
                    nome: $('#perfil-nome').val(),
                    email: $('#perfil-email').val(),
                    telefone: $('#perfil-telefone').val(),
                    senha: $('#perfil-senha').val() == '' ? null : $('#perfil-senha').val()
                }
            ),
			beforeSend: function() {
			    $('#form-perfil .alertas').empty().append('<div class="spinner-border spinner-border-sm" role="status"></div> Aguarde...');
			},
			complete: function() {
			},
			success: function(data, textStatus) {
			    sessionStorage.setItem("auditor", JSON.stringify(data));

			    $('#form-perfil .alertas').empty().append('<span class="text-success">Registro salvo com sucesso!</span>');
			    $('.text-success').fadeOut(6500).delay(1500);
			},
			error: function(xhr,er) {
			    switch (xhr.status) {
			        case 401:
			            sessionStorage.clear();
			            window.location = '/login.html';
			            break;
			        default:
			            $('#form-perfil .alertas').empty().append('<span class="text-danger">Não foi possível realizar a operação</span>');
			    }

			    $('.text-danger').fadeOut(6500).delay(1500);
			}
		});
    });

    $('#form-perfil').on('reset', function(e) {
        e.preventDefault();
        popularFormularioPerfil();
    });

    function popularFormularioPerfil() {
        let auditor = JSON.parse(sessionStorage.getItem('auditor'));

        $('#perfil-cpf').val(auditor.cpf);
        $('#perfil-nome').val(auditor.nome);
        $('#perfil-email').val(auditor.email);
        $('#perfil-telefone').val(auditor.telefone);
        $('#perfil-senha').val(null);
        $('#perfil-tipo-admin').prop('checked', auditor.administrador);
        $('#perfil-tipo-auditor').prop('checked', !auditor.administrador);
    }

});