$(document).ready(function() {

    let data = new Date()
    $('#filtro-data-inicial').val(data.getFullYear() + '-' + (data.getMonth() + 1) + '-01');
    $('#filtro-data-final').val(data.getFullYear() + '-' + (data.getMonth() + 1) + '-' + data.getDate());

    obterRegistrosExibirRelatorio();

    $('#form-relatorio-guias-tempo').on('submit', function(e) {
        e.preventDefault();
        obterRegistrosExibirRelatorio();
    });

    function obterRegistrosExibirRelatorio() {
        let cnpj = JSON.parse(sessionStorage.getItem('empresa')).cnpj;

        $.ajax({
            url: '/rest/' + cnpj + '/relatorios/tempo?dataInicial=' + $('#filtro-data-inicial').val() + '&dataFinal=' + $('#filtro-data-final').val(),
            type: 'GET',
            beforeSend: function() {
                $('#form-relatorio-guias-tempo .alertas').empty().append('<div class="spinner-border spinner-border-sm" role="status"></div> Aguarde...');
            },
            complete: function() {
            },
            success: function(data, textStatus) {
                $('#form-relatorio-guias-tempo .alertas').empty();

                exibirGrafico(
                    data.grafico.legendas,
                    data.grafico.dataset.nao,
                    data.grafico.dataset.sim,
                    data.grafico.dataset.inconclusivo,
                );

                exibirRegistros(data.detalhamento);
            },
            error: function(xhr,er) {
                switch (xhr.status) {
                    case 401:
                        sessionStorage.clear();
                        window.location = '/login.html';
                        break;
                    default:
                        $('#form-relatorio-guias-tempo .alertas').empty().append('<span class="text-danger">Não foi possível realizar a operação</span>');
                }

                $('.text-danger').fadeOut(6500).delay(1500);
            }
        });
    }

    function exibirGrafico(legendas, dadosSemIndicativo, dadosComIndicativo, dadosInconclusivos) {
        Chart.defaults.global.defaultFontFamily = 'Nunito', '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
        Chart.defaults.global.defaultFontColor = '#858796';

        $('.canvas-area').empty().append('<canvas id="canvas-grafico"></canvas>');
        let canvas = document.getElementById('canvas-grafico').getContext('2d');

        new Chart(canvas, {
          type: 'line',
          data: {
            labels: legendas,
            datasets: [
               {
                 label: "Com indicativo fraude",
                 lineTension: 0.3,
                 backgroundColor: "rgba(231, 74, 59, 0.05)",
                 borderColor: "rgba(231, 74, 59, 1)",
                 pointRadius: 3,
                 pointBackgroundColor: "rgba(231, 74, 59, 1)",
                 pointBorderColor: "rgba(231, 74, 59, 1)",
                 pointHoverRadius: 3,
                 pointHoverBackgroundColor: "rgba(231, 74, 59, 1)",
                 pointHoverBorderColor: "rgba(231, 74, 59, 1)",
                 pointHitRadius: 10,
                 pointBorderWidth: 2,
                 data: dadosComIndicativo
               },
               {
                 label: "Sem indicativo fraude",
                 lineTension: 0.3,
                 backgroundColor: "rgba(28, 200, 138, 0.05)",
                 borderColor: "rgba(28, 200, 138, 1)",
                 pointRadius: 3,
                 pointBackgroundColor: "rgba(28, 200, 138, 1)",
                 pointBorderColor: "rgba(28, 200, 138, 1)",
                 pointHoverRadius: 3,
                 pointHoverBackgroundColor: "rgba(28, 200, 138, 1)",
                 pointHoverBorderColor: "rgba(28, 200, 138, 1)",
                 pointHitRadius: 10,
                 pointBorderWidth: 2,
                 data: dadosSemIndicativo
               },
               {
                 label: "Inconclusivo",
                 lineTension: 0.3,
                 backgroundColor: "rgba(246, 182, 62, 0.05)",
                 borderColor: "rgba(246, 182, 62, 1)",
                 pointRadius: 3,
                 pointBackgroundColor: "rgba(246, 182, 62, 1)",
                 pointBorderColor: "rgba(246, 182, 62, 1)",
                 pointHoverRadius: 3,
                 pointHoverBackgroundColor: "rgba(246, 182, 62, 1)",
                 pointHoverBorderColor: "rgba(246, 182, 62, 1)",
                 pointHitRadius: 10,
                 pointBorderWidth: 2,
                 data: dadosInconclusivos
               }
             ],
          },
          options: {
            maintainAspectRatio: false,
            layout: {
              padding: {
                left: 10,
                right: 25,
                top: 25,
                bottom: 0
              }
            },
            scales: {
              xAxes: [{
                time: {
                  unit: 'date'
                },
                gridLines: {
                  display: false,
                  drawBorder: false
                },
                ticks: {
                  maxTicksLimit: 7
                }
              }],
              yAxes: [{
                ticks: {
                  maxTicksLimit: 5,
                  padding: 10,
                  callback: function(value, index, values) {
                    return value;
                  }
                },
                gridLines: {
                  color: "rgb(234, 236, 244)",
                  zeroLineColor: "rgb(234, 236, 244)",
                  drawBorder: false,
                  borderDash: [2],
                  zeroLineBorderDash: [2]
                }
              }],
            },
            legend: {
              display: false
            },
            tooltips: {
              backgroundColor: "rgb(255,255,255)",
              bodyFontColor: "#858796",
              titleMarginBottom: 10,
              titleFontColor: '#6e707e',
              titleFontSize: 14,
              borderColor: '#dddfeb',
              borderWidth: 1,
              xPadding: 15,
              yPadding: 15,
              displayColors: false,
              intersect: false,
              mode: 'index',
              caretPadding: 10,
              callbacks: {
                label: function(tooltipItem, chart) {
                  var datasetLabel = chart.datasets[tooltipItem.datasetIndex].label || '';
                  return datasetLabel + ': ' + tooltipItem.yLabel;
                }
              }
            }
          }
        });
    }

    function exibirRegistros(registros) {
        let tabela = $('#tabela-registros').DataTable();
        tabela.clear();

        registros.forEach(registro => {
            let linha =
                '<tr>' +
                '<td>' + registro.codigo             + '</td>' +
                '<td>' + registro.codigoBeneficiario + '</td>' +
                '<td>' + registro.idadeBeneficiario  + '</td>' +
                '<td>' + registro.cid                + '</td>' +
                '<td>' + registro.dataEmissao        + '</td>' +
                '<td class="text-center">' +
                    '<span class="btn btn-sm ' +
                        (registro.indicativoFraude == 'NAO' ? 'btn-success' : registro.indicativoFraude == 'SIM' ? 'btn-danger' : 'btn-warning') +
                    '">' + registro.indicativoFraude + '</span>' +
                '</td>' +
                '</tr>';

            tabela.row.add($(linha).get(0))
        });

        tabela.draw();
    }

});