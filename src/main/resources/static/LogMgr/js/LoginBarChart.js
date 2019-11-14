$(function(){
    initChart();

    //刷新表格
    $("#btn_refresh").click(function(){
        refresh()
    });
})


function initChart(){
    var chart = Highcharts.chart('container',{
        chart: {
            type: 'column'
        },
        title: {
            text: '用户登陆统计'
        },
        xAxis: {
            categories: [
                'admin','二娃','三娃'
            ],
            crosshair: true
        },
        yAxis: {
            min: 0,
            title: {
                text: '登陆次数'
            }
        },
        tooltip: {
            // head + 每个 point + footer 拼接成完整的 table
            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
            pointFormat: '<tr><td style="color:{series.color};padding:0">登陆</td>' +
            '<td style="padding:0"><b>{point.y}（次）</b></td></tr>',
            footerFormat: '</table>',
            shared: true,
            useHTML: true
        },
        plotOptions: {
            column: {
                borderWidth: 0
            }
        },
        series: [{
            name: '姓名',
            data: [10, 2, 1]
        }]
    });
}


function refresh(){
    dialog.refresh()
}

