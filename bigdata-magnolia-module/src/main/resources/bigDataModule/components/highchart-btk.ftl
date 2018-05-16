[#include "../macro/btk-util.ftl"]
[#assign iteration = rand(0,5000)?string["0"]]
<div class="row">
    [#--if content.title?has_content]<h2>${content.title!}</h2>[/#if--]
    <div class="highchart-btk" id="hchart${iteration!}">
    </div>
    <script type="text/javascript">
        window.deferAfterjQueryLoaded.push(function () {
            [#-- Change uri address to your spring component or create a dialog for selecting it--]
            jQuery.backofficeApp.global.doAjax('/api/rest/v1/stats/example', 'GET', null, function (result) {
                var myChart = Highcharts.chart('hchart${iteration!}', {
                    chart: {
                        type: '${content.chartType!"bar"}'
                    },
                    title: {
                        text: "${content.title!}"//result.title  [#-- Blossom data or api data --]
                    },
                    subtitle: {
                        text: "${content.subtitle!}"//result.subtitle
                    },
                    series: result.series
                });
            }, function (e) {
                console.log("error al cargar timeStats");
            });
        });
    </script>
</div>

