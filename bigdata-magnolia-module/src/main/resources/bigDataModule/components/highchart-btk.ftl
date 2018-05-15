[#include "../macro/btk-util.ftl"]
[#assign iteration = rand(0,5000)?string["0"]]
<div class="row">
[#if content.title?has_content]
    <h2>${content.title!}</h2>
[/#if]
    <div class="highchart-btk" id="hchart${iteration!}">
    </div>
    <script type="text/javascript">
        window.deferAfterjQueryLoaded.push(function () {
            jQuery.backofficeApp.global.doAjax('/api/rest/v1/stats/example', 'GET', null, function (result) {
                var myChart = Highcharts.chart('hchart${iteration!}', {
                    chart: {
                        type: '${content.chartType!"bar"}'
                    },
                    title: {
                        text: result.title
                    },
                    subtitle: {
                        text: result.subtitle
                    },
                    /*xAxis: {
                        categories: ['Apples', 'Bananas', 'Oranges']
                    },
                    yAxis: {
                        title: {
                            text: 'Fruit eaten'
                        }
                    },*/
                    series: result.series
                });
            }, function (e) {
                console.log("error al cargar timeStats");
            });


        });
    </script>
</div>

