$(document).ready(function() {

    obterRegistrosExibirTabela();

    $('#form-treinamento').on('submit', function(e) {
        let cnpj = JSON.parse(sessionStorage.getItem('empresa')).cnpj;

        e.preventDefault();
        $.ajax({
            url: '/rest/' + cnpj + '/motores/treinamento',
            type: 'POST',
            data: new FormData($('#form-treinamento')[0]),
            enctype: 'multipart/form-data',
            processData: false,
            contentType: false,
            beforeSend: function() {
                $('#form-treinamento .alertas').empty()
                                               .show()
                                               .append('<div class="spinner-border spinner-border-sm" role="status"></div> Enviando arquivo para treinamento. Aguarde...');
            },
            complete: function() {
            },
            success: function(data, textStatus) {
                $('#form-treinamento .alertas').empty()
                                               .append('<span class="text-success">Treinamento agendado com sucesso!</span>')
                                               .fadeOut(7000).delay(1000);
            },
            error: function(xhr,er) {
                switch (xhr.status) {
                    case 401:
                        sessionStorage.clear();
                        window.location = '/login.html';
                        break;
                    default:
                        $('#form-treinamento .alertas').empty().append('<span class="text-danger">Não foi possível realizar a operação</span>');
                }

                $('.text-danger').fadeOut(6500).delay(1500);
            }
        });
    });

    $('.custom-file-input').on('change', function() {
        var arquivo = $(this).val().split("\\").pop();
        $(this).siblings(".custom-file-label").addClass("selected").html(arquivo);
    });

    function obterRegistrosExibirTabela() {
        let cnpj = JSON.parse(sessionStorage.getItem('empresa')).cnpj;

        $.ajax({
            url: '/rest/' + cnpj + '/motores',
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

    function exibirTabela(registros) {
            let tabela = $('#tabela-registros').DataTable();
            tabela.clear();

            registros.forEach(registro => {
                let linha =
                    '<tr>' +
                    '<td>' + registro.registros           + '</td>' +
                    '<td>' + registro.registrosTreino     + '</td>' +
                    '<td>' + registro.registrosTeste      + '</td>' +
                    '<td>' + registro.acuracia            + '</td>' +
                    '<td>' + registro.sensibilidade       + '</td>' +
                    '<td>' + registro.especificidade      + '</td>' +
                    '<td>' + registro.baseHistorica       + '</td>' +
                    '</tr>';

                tabela.row.add($(linha).get(0))
            });

            tabela.draw();
        }

});