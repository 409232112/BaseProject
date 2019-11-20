$(function(){
    initChart();


    //刷新表格
    $("#btn_refresh").click(function(){
        refresh()
    });
    $("#btn_reset").click(function(){
        reset()
    });
    $("#btn_query").click(function(){
        query()
    });

    $('#year').combobox({
        onChange: function(param){
            query();
        }
    });
})


function initChart(){
    var data = {"chartType":"pie"}
    myChart = chart.getChart("container")
    $.ajax({
        type: "POST",
        contentType: "application/json;charset=UTF-8",
        url: "loginlog/chart",
        data: JSON.stringify(data),
        success: function (result) {
            var result = eval("("+result+")")
            if(result.code == "0"){
                var retData = result.data;
                var option = chart.getBasePieChartOption(retData.legendData,retData.seriesData)
                option.series[0].name = "登陆次数"
                option.title.text = "用户登陆统计图"
                myChart.setOption(option, true);
            }else{
                message.info(result.message);
            }
        }
    });
}

function reloadChart(data){
    $.ajax({
        type: "POST",
        contentType: "application/json;charset=UTF-8",
        url: "loginlog/chart",
        data: JSON.stringify(data),
        success: function (result) {
            loginJudge(result);
            var result = eval("("+result+")")
            if(result.code == "0"){
                var retData = result.data;
                var option = chart.getBasePieChartOption(retData.legendData,retData.seriesData)
                option.series[0].name = "登陆次数"
                option.title.text = data.year+"用户登陆统计图"
                myChart.setOption(option, true);
            }else{
                message.info(result.message);
            }
        }
    });
}


function refresh(){
    dialog.refresh()
}
function reset(){
    form.resetForm("loginlog_form")
}
function query(){
    var data =  form.getFormValues("loginlog_form");
    data["chartType"] = "pie"
    reloadChart(data)
}
