$(document).ready(function() {

    popularFormularioEmpresa();

    $('#form-empresa').on('submit', function(e) {
        let cnpj = JSON.parse(sessionStorage.getItem('empresa')).cnpj;

        e.preventDefault();
        $.ajax({
			url: '/rest/' + cnpj + '/empresa',
			type: 'PATCH',
			contentType: 'application/json',
			data: JSON.stringify(
			    {
                    cnpj: cnpj,
                    razaoSocial: $('#empresa-razao').val(),
                    nomeFantasia: $('#empresa-fantasia').val(),
                    cep: $('#empresa-cep').val(),
                    endereco: $('#empresa-endereco').val(),
                    numero: $('#empresa-numero').val(),
                    bairro: $('#empresa-bairro').val(),
                    complemento: $('#empresa-complemento').val(),
                    cidade: $('#empresa-cidade').val(),
                    estado: $('#empresa-estado').val(),
                    telefone: $('#empresa-telefone').val(),
                    email: $('#empresa-email').val(),
                    registroANS: $('#empresa-ans').val()
                }
            ),
			beforeSend: function() {
			    $('#form-empresa .alertas').empty().append('<div class="spinner-border spinner-border-sm" role="status"></div> Aguarde...');
			},
			complete: function() {
			},
			success: function(data, textStatus) {
			    sessionStorage.setItem("empresa", JSON.stringify(data));

			    $('#form-empresa .alertas').empty().append('<span class="text-success">Registro salvo com sucesso!</span>');
			    $('.text-success').fadeOut(6500).delay(1500);
			},
			error: function(xhr,er) {
			    switch (xhr.status) {
			        case 401:
			            sessionStorage.clear();
			            window.location = '/login.html';
			            break;
			        default:
			            $('#form-empresa .alertas').empty().append('<span class="text-danger">Não foi possível realizar a operação</span>');
			    }

			    $('.text-danger').fadeOut(6500).delay(1500);
			}
		});
    });

    $('#form-empresa').on('reset', function(e) {
        e.preventDefault();
        popularFormularioEmpresa();
    });

    function popularFormularioEmpresa() {
        let empresa = JSON.parse(sessionStorage.getItem('empresa'));

        $('#empresa-cnpj').val(empresa.cnpj);
        $('#empresa-razao').val(empresa.razaoSocial);
        $('#empresa-fantasia').val(empresa.nomeFantasia);
        $('#empresa-cep').val(empresa.cep);
        $('#empresa-endereco').val(empresa.endereco);
        $('#empresa-numero').val(empresa.numero);
        $('#empresa-bairro').val(empresa.bairro);
        $('#empresa-complemento').val(empresa.complemento);
        $('#empresa-cidade').val(empresa.cidade);
        $('#empresa-estado').val(empresa.estado);
        $('#empresa-telefone').val(empresa.telefone);
        $('#empresa-email').val(empresa.email);
        $('#empresa-ans').val(empresa.registroANS);
    }

});