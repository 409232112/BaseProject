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

    $("#btn_save").click(function(){
        save();
    });

    $("#role_grid").datagrid({
        onBeforeLoad: function () {
            $($(this).datagrid("getPager")).pagination({
                layout: ['prev', 'manual', 'next']
            })
        },
        onClickRow:function(index,data){
            onClickRow(data);
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
    var data =  form.getFormValues("role_form");
    grid.reloadWithData("role_grid",data)
}

function reset(){
    form.resetForm("role_form")
}


function refresh(){
    dialog.refresh()
}

function onClickRow(row){
    $("#model_tree").tree("options").url="model/findByRoleId/"+row.id;
    $('#model_tree').tree('reload');
}

function save(){
    var nodes = $('#model_tree').tree('getChecked', ['checked','indeterminate']);
    var datas=[]
    var roleId = grid.getCurrentSelectRowData("role_grid").id;
    for(var i = 0;i<nodes.length;i++){
        var data ={};
        data['modelId'] =nodes[i].id;
        data['roleId'] = roleId;
        datas.push(data);
    }
    if(datas.length == 0){
        var data ={};
        data['modelId'] =""
        data['roleId'] = roleId;
        datas.push(data);
    }
    $.ajax({
        type: "POST",
        contentType: "application/json;charset=UTF-8",
        url: "model/saveRoleModel",
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
