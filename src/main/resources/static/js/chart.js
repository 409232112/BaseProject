/**
 * Created by wangyc on 2019/11/15.
 */

var baseBarChartOption={
    grid: {
        left: '5%',   //距离左边的距离
        right: '5%', //距离右边的距离
        bottom: '10%',//距离下边的距离
        top: '15%' //距离上边的距离
    },
    title:{
        top: '3%',
        text:'',
        x:'center',
        y:'top',
        textAlign:'center',
        textStyle: { //设置主标题风格
            fontStyle: '',//主标题文字风格
        }
    },
    legend: {                                   // 图例配置
        top:"8%",                               //与上方的距离 可百分比% 可像素px
        padding: 0,                             // 图例内边距，单位px，默认上下左右内边距为5
        itemGap: 10,                            // Legend各个item之间的间隔，横向布局时为水平间隔，纵向布局时为纵向间隔
    },
    tooltip: {                                  // 气泡提示配置
        trigger: 'item'                       // 触发类型，默认数据触发，可选为：'axis'
    },

    xAxis:{
        type: 'category'
    },
    yAxis:{
        name:"",
        type: 'value',                      // 坐标轴类型，纵轴默认为数值轴，类目轴则参考xAxis说明
        splitNumber: 4,                      // 数值轴用，分割段数，默认为5
        minInterval:1
    },
    series: []
}


var basePieChartOption = {
    title : {
        text: '',
        x:'center',
        top:"5%"
    },
    tooltip : {
        trigger: 'item',
        formatter: "{a} <br/>{b} : {c} ({d}%)"
    },
    legend: {
        orient: 'vertical',
        left: '90%',
        top: 'bottom',
        data: []
    },
    series : [
        {
            name: '',
            type: 'pie',
            center: ['50%', '60%'],
            data:[]
        }
    ]
};

var Chart  = function(){
    this.getChart = function (container) {
        var myChart = echarts.init(document.getElementById("container"));
        window.onresize = myChart.resize;
        return myChart;
    },
    this.getBaseBarChartOption = function (legendData,xAxisData,series) {
        var option = JSON.parse(JSON.stringify(baseBarChartOption))
        option.series = series;
        option.legend.data = legendData;
        option.xAxis.data = xAxisData;
        return option;
    }

    this.getBasePieChartOption = function (legendData,seriesData) {
        var option = JSON.parse(JSON.stringify(basePieChartOption))
        option.series[0].data = seriesData;
        option.legend.data = legendData;
        return option;
    }

}



var chart = new Chart();
