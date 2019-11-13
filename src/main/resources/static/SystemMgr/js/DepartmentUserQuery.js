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

    $("#department_gridtree").treegrid({
        onClickRow:function(data){
            onClickRow(data);
        }
    });

    $("#user_grid").datagrid({});
    $("#user_grid").datagrid("options").url="user/find"

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


function onClickRow(row){
    var data={}
    data["departmentId"] = row.id;
    grid.reloadWithData("user_grid",data);
}
function searchData(value){
    var row = grid.getCurrentSelectRowData("department_gridtree");
    var data={}
    data["departmentId"] = row.id;
    data["name"] = value
    grid.reloadWithData("user_grid",data);
}




