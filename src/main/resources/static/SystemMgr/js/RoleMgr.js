$(function(){
    field.invisible("id")
    field.invisible("mode")


    form.disabledForm("role_form");

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
    $("#role_grid").datagrid({
        onClickRow:function(index,data){
            onRowClick(index,data);
        }
    })

})



function insert(){
    form.resetForm("role_form")
    form.enabledForm("role_form");
    button.onInsertClick();

}
function update(){
    var row = grid.getCurrentSelectRowData("role_grid")
    if (row){
        button.onUpdateClick();
        form.enabledForm("role_form");
    }else{
        message.warning('请选择需要修改的数据！')
    }
}
function save(){
    $('#role_form').form('submit',{
        url: 'role/save',
        onSubmit: function(){
            return $(this).form('validate');
        },
        success: function(result){
            result = eval('('+result+')');
            if(result.code=="0"){
                message.info(result.message)
                grid.reloadGrid("role_grid")
                form.resetForm("role_form")
                form.disabledForm("role_form")
                button.onSaveClick();
            }else{
                message.error(result.message)
            }
        }
    });
}
function del(){
    var row = grid.getCurrentSelectRowData("role_grid")
    if (row){
        $.messager.confirm('Confirm','确定删除？',function(r){
            if (r){
                $.get('role/delete/'+row.id,function(result){
                    var result = eval('('+result+')');
                    if(result.code=="0"){
                        message.info(result.message)
                        grid.reloadGrid("role_grid")
                        form.resetForm("role_form")
                    }else{
                        msgShow('系统提示', result.message, 'error');
                        message.error(result.message)
                    }
                });
            }
        });
    }
}

function cancel(){
    form.disabledForm("role_form");
    button.onCancelClick();
    data = grid.getCurrentSelectRowData("role_grid");
    form.setFormValues("role_form",data);
}

function reset(){
    form.resetForm("role_form")
}

function onRowClick(index,data){
    form.setFormValues("role_form",data);
}

function refresh(){
    dialog.refresh()
}