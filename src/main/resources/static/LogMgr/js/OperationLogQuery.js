﻿$(function(){
    //查询
    $("#btn_query").click(function(){
        query()
    });

    //清空表单
    $("#btn_reset").click(function(){
        reset()
    });

    //刷新表格
    $("#btn_refresh").click(function(){
        refresh()
    });

    //导出
    $("#btn_toExcel").click(function(){
        exportExcel()
    });

    //回车查询
    $('#name').textbox('textbox').keydown(function (e) {
        if (e.keyCode == 13) {
            query();
        }
    });
    $('#department').textbox('textbox').keydown(function (e) {
        if (e.keyCode == 13) {
            query();
        }
    });
    $('#operation_type').combobox({
        onChange: function(param){
            query();
        }
    });
    $('#operation_object').textbox('textbox').keydown(function (e) {
        if (e.keyCode == 13) {
            query();
        }
    });
    $('#ip').textbox('textbox').keydown(function (e) {
        if (e.keyCode == 13) {
            query();
        }
    });

    $("#operationlog_grid").datagrid({
        onLoadSuccess:function(data){
            if(data.total==0){
                var dc = $(this).data('datagrid').dc;
                var header2Row = dc.header2.find('tr.datagrid-header-row');
                dc.body2.find('table').append(header2Row.clone().css({"visibility":"hidden"}));
            }
        }
    });
})


function query(){
    var data =  form.getFormValues("operationlog_form");
    grid.reloadWithData("operationlog_grid",data)
}

function reset(){
    form.resetForm("operationlog_form")
}

function refresh(){
    dialog.refresh()
}

function exportExcel(){
    grid.allToExcel("operationlog_grid","操作日志");
}



