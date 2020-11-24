$(document).ready(function() {

    obterRegistrosExibirTabelaGuia();

    $('#modal-cadastrar').on('submit', function(e) {
        let cnpj = JSON.parse(sessionStorage.getItem('empresa')).cnpj;

        e.preventDefault();
        $.ajax({
			url: '/rest/' + cnpj + '/guias',
			type: 'POST',
			contentType: 'application/json',
			data: JSON.stringify(
			    {
                    codigo: $('#guia-codigo').val(),
                    codigoBeneficiario: $('#guia-codigo-beneficiario').val(),
                    idadeBeneficiario: $('#guia-idade-beneficiario').val(),
                    cid: $('#guia-cid').val(),
                    dataEmissao: $('#guia-data-emissao').val(),
                    procedimentos: $('#tabela-procedimentos').serializeFormJSON()
                }
            ),
			beforeSend: function() {
			    $('#modal-cadastrar .alertas').empty().append('<div class="spinner-border spinner-border-sm" role="status"></div> Processando guia. Aguarde...');
			},
			complete: function() {
			},
			success: function(data, textStatus) {
			    $('#modal-cadastrar .alertas').empty().append('<span class="text-success">Registro salvo com sucesso!</span>');
			    $('.text-success').fadeOut(6500).delay(1500);

			    setTimeout(function() {
                  $('#modal-cadastrar').modal('hide');
                }, 900);

                obterRegistrosExibirTabelaGuia();
			},
			error: function(xhr,er) {
			    switch (xhr.status) {
			        case 401:
			            sessionStorage.clear();
			            window.location = '/login.html';
			            break;
                    case 409:
                    case 417:
                    case 424:
                        $('#modal-cadastrar .alertas').empty().append('<span class="text-danger">' + xhr.responseJSON.erro + '</span>');
                        break;
			        default:
			            $('#modal-cadastrar .alertas').empty().append('<span class="text-danger">Não foi possível realizar a operação</span>');
			    }

			    $('.text-danger').fadeOut(6500).delay(1500);
			}
		});
    });

    $('#modal-remover .btn-remover').on('click', function(e) {
        let cnpj = JSON.parse(sessionStorage.getItem('empresa')).cnpj;

        e.preventDefault();
        $.ajax({
            url: '/rest/' + cnpj + '/guias/' + $(this).val(),
            type: 'DELETE',
            beforeSend: function() {
            },
            complete: function() {
            },
            success: function(data, textStatus) {
                $('#modal-remover').modal('hide');

                obterRegistrosExibirTabelaGuia();
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

    $('#modal-cadastrar').on('show.bs.modal', function(e) {
        if ($(e.relatedTarget).data('tipo') == 'leitura') {
            obterRegistroExibirFormularioProcedimento($(e.relatedTarget).data('id'));
        } else {
            $('#form-cadastrar input').prop('readonly', false);
            $('#form-cadastrar button').prop('disabled', false);

            $('#tabela-procedimentos > tbody').empty().append(obterTemplateProcedimento());
        }
    });

    $('#modal-cadastrar').on('hide.bs.modal', function(e) {
        $('#form-cadastrar').trigger('reset');
    });

    $('#modal-remover').on('show.bs.modal', function(e) {
        $('#modal-remover .btn-remover').val($(e.relatedTarget).data('id'));
    });

    $('#modal-remover').on('hide.bs.modal', function(e) {
        $('#modal-remover .btn-remover').val(null);
    });

    $('#tabela-procedimentos').on('click', '.btn-adicionar-procedimento', function(e) {
        $(this).closest('tbody').append(obterTemplateProcedimento());
    });

    $('#tabela-procedimentos').on('click', '.btn-remover-procedimento', function(e) {
        if ($(this).closest('tbody').children().length > 1) {
            $(this).closest('tr').remove();
        }
    });

    function obterTemplateProcedimento() {
        return '<tr class="d-flex">' +
                   '<td class="col-2">'  +
                       '<input type="number" class="form-control" name="codigo" placeholder="Código" required>' +
                   '</td>' +
                   '<td class="col-3">'  +
                       '<input type="text" class="form-control" name="descricao" placeholder="Descrição" required>' +
                   '</td>' +
                   '<td class="col-2">'  +
                       '<input type="text" class="form-control" name="especialidade" placeholder="Especialidade" required>' +
                   '</td>' +
                   '<td class="col-2">'  +
                       '<input type="text" class="form-control" name="especialidadePrestador" placeholder="Especialidade prestador" required>' +
                   '</td>' +
                   '<td class="col-1">'  +
                       '<input type="number" class="form-control" name="quantidade" min="0" placeholder="0" required>' +
                   '</td>' +
                   '<td class="col-1">'  +
                       '<input type="number" class="form-control" name="valor" step="any" min="0" placeholder="0" required>' +
                   '</td>' +
                   '<td class="col-1">'  +
                       '<button type="button" class="btn btn-outline-primary btn-sm btn-adicionar-procedimento"><i class="fas fa-plus"></i></button>' +
                       '<button type="button" class="btn btn-outline-danger btn-sm btn-remover-procedimento"><i class="fas fa-trash"></i></button>' +
                   '</td>' +
               '</tr>'
    }

    function obterRegistroExibirFormularioProcedimento(codigo) {
        let cnpj = JSON.parse(sessionStorage.getItem('empresa')).cnpj;

        $.ajax({
            url: '/rest/' + cnpj + '/guias/' + codigo,
            type: 'GET',
            beforeSend: function() {
            },
            complete: function() {
            },
            success: function(data, textStatus) {
                exibirFormularioProcedimento(data)
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

    function obterRegistrosExibirTabelaGuia() {
        let cnpj = JSON.parse(sessionStorage.getItem('empresa')).cnpj;

        $.ajax({
            url: '/rest/' + cnpj + '/guias',
            type: 'GET',
            beforeSend: function() {
            },
            complete: function() {
            },
            success: function(data, textStatus) {
                exibirTabelaGuia(data)
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

    function exibirFormularioProcedimento(registro) {
        $('#form-cadastrar input').prop('readonly', true);
        $('#form-cadastrar button').prop('disabled', true);

        $('#guia-codigo').val(registro.codigo);
        $('#guia-codigo-beneficiario').val(registro.codigoBeneficiario);
        $('#guia-idade-beneficiario').val(registro.idadeBeneficiario);
        $('#guia-cid').val(registro.cid);
        $('#guia-data-emissao').val(registro.dataEmissao);

        $('#tabela-procedimentos > tbody').empty();

        registro.procedimentos.forEach(procedimento => {
            let linha =
                '<tr class="d-flex">' +
                   '<td class="col-2">'  +
                       '<input type="number" class="form-control" value="' + procedimento.codigo + '" readonly>' +
                   '</td>' +
                   '<td class="col-3">'  +
                       '<input type="text" class="form-control" value="' + procedimento.descricao + '" readonly>' +
                   '</td>' +
                   '<td class="col-2">'  +
                       '<input type="text" class="form-control" value="' + procedimento.especialidade + '" readonly>' +
                   '</td>' +
                   '<td class="col-2">'  +
                       '<input type="text" class="form-control" value="' + procedimento.especialidadePrestador + '" readonly>' +
                   '</td>' +
                   '<td class="col-1">'  +
                       '<input type="number" class="form-control" value="' + procedimento.quantidade + '" readonly>' +
                   '</td>' +
                   '<td class="col-1">'  +
                       '<input type="number" class="form-control" value="' + procedimento.valor + '" readonly>' +
                   '</td>' +
                   '<td class="col-1">'  +
                      '<button type="button" class="btn btn-outline-primary btn-sm" disabled><i class="fas fa-plus"></i></button>' +
                      '<button type="button" class="btn btn-outline-danger btn-sm" disabled><i class="fas fa-trash"></i></button>' +
                  '</td>' +
               '</tr>';

               $('#tabela-procedimentos > tbody').append(linha);
        });
    }

    function exibirTabelaGuia(registros) {
        let tabela = $('#tabela-registros').DataTable();
        tabela.clear();

        registros.forEach(registro => {
            let linha =
                '<tr>' +
                '<td>' + registro.codigo      + '</td>' +
                '<td>' + registro.dataCriacao + '</td>' +
                '<td>' + registro.auditor     + '</td>' +
                '<td>' + registro.statusProcessamento + '</td>' +
                '<td class="text-center">' +
                    '<span class="btn btn-sm ' +
                        (registro.indicativoFraude == 'NAO' ? 'btn-success' : registro.indicativoFraude == 'SIM' ? 'btn-danger' : 'btn-warning') +
                    '">' + registro.indicativoFraude + '</span>' +
                '</td>' +
                '<td>' + registro.scoreIndicativo + '</td>' +
                '<td>'  +
                    '<a class="btn btn-outline-primary btn-sm" href="#" data-toggle="modal" data-target="#modal-cadastrar" data-tipo="leitura" data-id="' + registro.codigo + '"><i class="fas fa-info"></i></a>' +
                    '<a class="btn btn-outline-danger btn-sm ml-1" href="#" data-toggle="modal" data-target="#modal-remover" data-id="' + registro.codigo + '"><i class="fas fa-trash"></i></a>' +
                '</td>' +
                '</tr>';

            tabela.row.add($(linha).get(0))
        });

        tabela.draw();
    }

    (function($) {
        $.extend({
            toDictionary: function(query) {
                var parms = {};
                var items = query.split("&");
                for (var i = 0; i < items.length; i++) {
                    var values = items[i].split("=");
                    var key = decodeURIComponent(values.shift());
                    var value = values.join("=")
                    parms[key] = decodeURIComponent(value);
                }
                return (parms);
            }
        })
    })(jQuery);

    (function($) {
        $.fn.serializeFormJSON = function() {
            var o = [];
            $(this).find('tr').each(function() {
                var elements = $(this).find('input');
                if (elements.length > 0) {
                    var serialized = elements.serialize();
                    var item = $.toDictionary(serialized );
                    o.push(item);
                }
            });
            return o;
        };
    })(jQuery);

});