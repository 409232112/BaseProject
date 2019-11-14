$(function(){
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

    //下拉框选择自动查询
    $('#sex').combobox({
        onChange: function(param){
            query();
        }
    });
    //回车查询
    $('#name').textbox('textbox').keydown(function (e) {
        if (e.keyCode == 13) {
            query();
        }
    });
    $('#role').textbox('textbox').keydown(function (e) {
        if (e.keyCode == 13) {
            query();
        }
    });
    $('#department').textbox('textbox').keydown(function (e) {
        if (e.keyCode == 13) {
            query();
        }
    });

    $("#loginlog_grid").datagrid({
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
    var data =  form.getFormValues("loginlog_form");
    grid.reloadWithData("loginlog_grid",data)
}

function reset(){
    form.resetForm("loginlog_form")
}

function refresh(){
    dialog.refresh()
}


