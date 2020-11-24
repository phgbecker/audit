$(document).ready(function() {

    obterRegistrosExibirTabela();

    $('#modal-cadastrar').on('submit', function(e) {
        let cnpj = JSON.parse(sessionStorage.getItem('empresa')).cnpj;

        e.preventDefault();
        $.ajax({
			url: '/rest/' + cnpj + '/auditores',
			type: 'POST',
			contentType: 'application/json',
			data: JSON.stringify(
			    {
                    cpf: $('#auditor-cpf').val(),
                    nome: $('#auditor-nome').val(),
                    email: $('#auditor-email').val(),
                    telefone: $('#auditor-telefone').val(),
                    senha: $('#auditor-senha').val(),
                    administrador: $('#auditor-tipo-admin').prop('checked')
                }
            ),
			beforeSend: function() {
			    $('#modal-cadastrar .alertas').empty().append('<div class="spinner-border spinner-border-sm" role="status"></div> Aguarde...');
			},
			complete: function() {
			},
			success: function(data, textStatus) {
			    $('#modal-cadastrar .alertas').empty().append('<span class="text-success">Registro salvo com sucesso!</span>');
			    $('.text-success').fadeOut(6500).delay(1500);

			    setTimeout(function() {
                  $('#modal-cadastrar').modal('hide');
                }, 900);

                obterRegistrosExibirTabela();
			},
			error: function(xhr,er) {
			    switch (xhr.status) {
			        case 401:
			            sessionStorage.clear();
			            window.location = '/login.html';
			            break;
                    case 409:
                        $('#modal-cadastrar .alertas').empty().append('<span class="text-danger">' + xhr.responseJSON.erro + '</span>');
                        break;
			        default:
			            $('#modal-cadastrar .alertas').empty().append('<span class="text-danger">Não foi possível realizar a operação</span>');
			    }

			    $('.text-danger').fadeOut(6500).delay(1500);
			}
		});
    });

    $('#modal-editar').on('submit', function(e) {
        let cnpj = JSON.parse(sessionStorage.getItem('empresa')).cnpj;

        e.preventDefault();
        $.ajax({
            url: '/rest/' + cnpj + '/auditores/' + $('#editar-auditor-cpf').val(),
            type: 'PATCH',
            contentType: 'application/json',
            data: JSON.stringify(
                {
                    cpf: $('#editar-auditor-cpf').val(),
                    nome: $('#editar-auditor-nome').val(),
                    email: $('#editar-auditor-email').val(),
                    telefone: $('#editar-auditor-telefone').val(),
                    senha: $('#editar-auditor-senha').val() == '' ? null : $('#editar-auditor-senha').val(),
                    administrador: $('#editar-auditor-tipo-admin').prop('checked')
                }
            ),
            beforeSend: function() {
                $('#modal-editar .alertas').empty().append('<div class="spinner-border spinner-border-sm" role="status"></div> Aguarde...');
            },
            complete: function() {
            },
            success: function(data, textStatus) {
                $('#modal-editar .alertas').empty().append('<span class="text-success">Registro salvo com sucesso!</span>');
                $('.text-success').fadeOut(6500).delay(1500);

                setTimeout(function() {
                  $('#modal-editar').modal('hide');
                }, 900);

                obterRegistrosExibirTabela();
            },
            error: function(xhr,er) {
                switch (xhr.status) {
                    case 401:
                        sessionStorage.clear();
                        window.location = '/login.html';
                        break;
                    default:
                        $('#modal-editar .alertas').empty().append('<span class="text-danger">Não foi possível realizar a operação</span>');
                }

                $('.text-danger').fadeOut(6500).delay(1500);
            }
        });
    });

    $('#modal-remover .btn-remover').on('click', function(e) {
        let cnpj = JSON.parse(sessionStorage.getItem('empresa')).cnpj;

        e.preventDefault();
        $.ajax({
            url: '/rest/' + cnpj + '/auditores/' + $(this).val(),
            type: 'DELETE',
            beforeSend: function() {
            },
            complete: function() {
            },
            success: function(data, textStatus) {
                $('#modal-remover').modal('hide');

                obterRegistrosExibirTabela();
            },
            error: function(xhr,er) {
                switch (xhr.status) {
                    case 401:
                        sessionStorage.clear();
                        window.location = '/login.html';
                        break;
                }
            }
        });
    });

    $('#modal-cadastrar').on('hide.bs.modal', function(e) {
        $('#form-cadastrar').trigger('reset');
    });

    $('#modal-editar').on('show.bs.modal', function(e) {
        obterRegistroExibirFormulario($(e.relatedTarget).data('id'));
    });

    $('#modal-editar').on('hide.bs.modal', function(e) {
        $('#form-editar').trigger('reset');
    });

    $('#modal-remover').on('show.bs.modal', function(e) {
        $('#modal-remover .btn-remover').val($(e.relatedTarget).data('id'));
    });

    $('#modal-remover').on('hide.bs.modal', function(e) {
        $('#modal-remover .btn-remover').val(null);
    });

    function obterRegistroExibirFormulario(cpf) {
        let cnpj = JSON.parse(sessionStorage.getItem('empresa')).cnpj;

        $.ajax({
            url: '/rest/' + cnpj + '/auditores/' + cpf,
            type: 'GET',
            beforeSend: function() {
            },
            complete: function() {
            },
            success: function(data, textStatus) {
                exibirFormulario(data)
            },
            error: function(xhr,er) {
                switch (xhr.status) {
                    case 401:
                        sessionStorage.clear();
                        window.location = '/login.html';
                        break;
                }
            }
        });
    }

    function obterRegistrosExibirTabela() {
        let cnpj = JSON.parse(sessionStorage.getItem('empresa')).cnpj;

        $.ajax({
            url: '/rest/' + cnpj + '/auditores',
            type: 'GET',
            beforeSend: function() {
            },
            complete: function() {
            },
            success: function(data, textStatus) {
                exibirTabela(data)
            },
            error: function(xhr,er) {
                switch (xhr.status) {
                    case 401:
                        sessionStorage.clear();
                        window.location = '/login.html';
                        break;
                }
            }
        });
    }

    function exibirFormulario(registro) {
        $('#editar-auditor-cpf').val(registro.cpf);
        $('#editar-auditor-nome').val(registro.nome);
        $('#editar-auditor-email').val(registro.email);
        $('#editar-auditor-telefone').val(registro.telefone);
        $('#editar-auditor-tipo-admin').prop('checked', registro.administrador);
        $('#editar-auditor-tipo-auditor').prop('checked', !registro.administrador);
    }

    function exibirTabela(registros) {
        let cpfSessao = JSON.parse(sessionStorage.getItem('auditor')).cpf;

        let tabela = $('#tabela-registros').DataTable();
        tabela.clear();

        registros.forEach(registro => {
            let linha =
                '<tr>' +
                '<td>' + registro.nome     + '</td>' +
                '<td>' + registro.cpf      + '</td>' +
                '<td>' + registro.email    + '</td>' +
                '<td>' + registro.telefone + '</td>' +
                '<td>' + (registro.administrador ? 'Administrador' : 'Auditor') + '</td>' +
                '<td>' +
                    '<a class="btn btn-outline-primary btn-sm ' + (registro.cpf == cpfSessao ? 'disabled' : '') + '" href="#" data-toggle="modal" data-target="#modal-editar" data-id="' + registro.cpf + '"><i class="fas fa-user-edit"></i></a>' +
                    '<a class="btn btn-outline-danger btn-sm ml-1 ' + (registro.cpf == cpfSessao ? 'disabled' : '') + '" href="#" data-toggle="modal" data-target="#modal-remover" data-id="' + registro.cpf + '"><i class="fas fa-trash"></i></a>' +
                '</td>' +
                '</tr>';

            tabela.row.add($(linha).get(0))
        });

        tabela.draw();
    }

});