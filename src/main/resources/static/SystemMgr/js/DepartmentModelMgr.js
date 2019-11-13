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

    $("#btn_save").click(function(){
        save();
    });

    $("#department_gridtree").treegrid({
        onClickRow:function(data){
            if(data.hasOwnProperty("children")){
                $("#model_tree").tree("loadData",{});
                $("#btn_save").css('visibility','hidden');
            }else{
                $("#btn_save").css('visibility','visible');
                onClickRow(data);
            }
        }
    });

    $("#model_tree").tree({
        onBeforeLoad:function(node,param){
            $("#model_tree").tree("options").cascadeCheck=false;
        },
        onLoadSuccess:function(node,data){
            $("#model_tree").tree("options").cascadeCheck=true;
        }
    })
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
    $("#model_tree").tree("options").url="model/findByDepartmentId/"+row.id;
    $('#model_tree').tree('reload');
}

function save(){
    var nodes = $('#model_tree').tree('getChecked', ['checked','indeterminate']);
    var datas=[]
    var departmentId = grid.getCurrentSelectRowData("department_gridtree").id;
    for(var i = 0;i<nodes.length;i++){
        var data ={};
        data['modelId'] =nodes[i].id;
        data['departmentId'] = departmentId;
        datas.push(data);
    }
    if(datas.length == 0){
        var data ={};
        data['modelId'] =""
        data['departmentId'] = departmentId;
        datas.push(data);
    }
    $.ajax({
        type: "POST",
        contentType: "application/json;charset=UTF-8",
        url: "model/saveDepartmentModel",
        data: JSON.stringify(datas),
        success: function (result) {
            var result = eval('('+result+')');
            if(result.code=="0"){
                message.info(result.message)
                $('#model_tree').tree('reload');
            }else{
                message.error(result.message)
            }
        },
        error:function (result) {
            var result = eval("("+result+")")
            message.error(result.message)
        }
    });
}


