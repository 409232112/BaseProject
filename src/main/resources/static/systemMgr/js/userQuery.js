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


})


function query(){
    var data =  form.getFormValues("user_form");
    grid.reloadWithData("user_grid",data)
}

function reset(){
    form.resetForm("user_form")
}


function refresh(){
    dialog.refresh()
}