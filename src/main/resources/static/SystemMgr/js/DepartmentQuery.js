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


    //回车查询
    $('#name').textbox('textbox').keydown(function (e) {
        if (e.keyCode == 13) {
            query();
        }
    });
    $('#code').textbox('textbox').keydown(function (e) {
        if (e.keyCode == 13) {
            query();
        }
    });
})


function query(){
    var data =  form.getFormValues("department_form");
    treeGrid.reloadWithData("department_gridtree",data)
}

function reset(){
    form.resetForm("department_form")
}

function refresh(){
    dialog.refresh()
}


