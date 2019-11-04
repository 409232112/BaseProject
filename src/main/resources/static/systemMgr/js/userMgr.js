$(function(){
    $("#id").next().hide();
    $("#mode").next().hide();

    form.disabledForm("user_form");

    button.disabledButton("btn_save");
    button.disabledButton("btn_reset");
    button.disabledButton("btn_cancel");

    //新增按钮
    $("#btn_insert").click(function(){
        insert();
    });

    //修改
    $("#btn_update").click(function(){
        update()

    });

    //保存
    $("#btn_save").click(function(){
        save()
    });

    //取消按钮
    $("#btn_cancel").click(function(){
        cancel()
    });

    //删除
    $("#btn_del").click(function(){
        del()
    });

    //清空表单
    $("#btn_reset").click(function(){
        reset()
    });

    //刷新表格
    $("#btn_refresh").click(function(){
        refresh()
    });

    //表格行单击事件
    $("#user_grid").datagrid({
        onClickRow:function(index,data){
            onRowClick(index,data);
        }
    })

})



function insert(){
    form.resetForm("user_form")
    form.enabledForm("user_form");
    button.onInsertClick();

}
function update(){
    var row = grid.getCurrentSelectRowData("user_grid")
    if (row){
        button.onUpdateClick();
        form.enabledForm("user_form");
    }else{
        msgShow('系统提示', '请选择需要修改的数据！', 'warning');
	}
}
function save(){
    $('#user_form').form('submit',{
        url: 'user/save',
        onSubmit: function(){
            return $(this).form('validate');
        },
        success: function(result){
            result = eval('('+result+')');
            if(result.code=="0"){
                msgShow('系统提示', result.message, 'info');
                grid.reloadGrid("user_grid")
                form.resetForm("user_form")
                form.disabledForm("user_form")
                button.onSaveClick();
            }else{
                msgShow('系统提示', result.message, 'error');
            }
        }
    });
}
function del(){
    var row = grid.getCurrentSelectRowData("user_grid")
    if (row){
        $.messager.confirm('Confirm','确定删除？',function(r){
            if (r){
                $.get('user/delete/'+row.id,function(result){
                    var result = eval('('+result+')');
                    if(result.code=="0"){
                        msgShow('系统提示', result.message, 'info');
                        grid.reloadGrid("user_grid")
                        form.resetForm("user_form")
                    }else{
                        msgShow('系统提示', result.message, 'error');
                    }
                });
            }
        });
    }
}

function cancel(){
    form.disabledForm("user_form");
    button.onCancelClick();
}

function reset(){
    form.resetForm("user_form")
}

function onRowClick(index,data){
    form.setFormValues("user_form",data);
}

function refresh(){
    dialog.refresh()
}