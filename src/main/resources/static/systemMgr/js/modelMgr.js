$(function(){
    field.invisible("id")
    field.invisible("mode")
    field.invisible("parent_id")
    field.invisible("level")

    form.disabledForm("model_form");

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
    $("#model_gridtree").treegrid({
        onClickRow:function(data){
            onRowClick(data);
        }
    })

})


function insert(){
    form.resetForm("model_form")
    form.enabledForm("model_form");
    button.onInsertClick();
    var rowdata = grid.getCurrentSelectRowData("model_gridtree")
    if(rowdata){
        var parent_id =rowdata.id;
        var level = rowdata.level+1;
    }else{
        var parent_id = 0;
        var level = 1;
    }

    field.setFieldValue("parent_id",parent_id)
    field.setFieldValue("level",level)


}
function update(){
    var row = grid.getCurrentSelectRowData("model_gridtree")
    if (row){
        button.onUpdateClick();
        form.enabledForm("model_form");
    }else{
        message.warning('请选择需要修改的数据！')
    }
}
function save(){
    $('#model_form').form('submit',{
        url: 'model/save',
        onSubmit: function(){
            return $(this).form('validate');
        },
        success: function(result){
            result = eval('('+result+')');
            if(result.code=="0"){
                message.info(result.message)

                treeGrid.reloadGrid("model_gridtree")
                treeGrid.unselectAllRow("model_gridtree");

                form.resetForm("model_form")
                form.disabledForm("model_form")

                button.onSaveClick();
            }else{
                message.error(result.message)
            }
        }
    });
}
function del(){
    var row = grid.getCurrentSelectRowData("model_gridtree")
    if (row){
        $.messager.confirm('Confirm','确定删除？',function(r){
            if (r){
                $.get('model/delete/'+row.id,function(result){
                    var result = eval('('+result+')');
                    if(result.code=="0"){
                        message.info(result.message)
                        treeGrid.reloadGrid("model_gridtree")
                        form.resetForm("model_form")
                    }else{
                        message.error(result.message)
                    }
                });
            }
        });
    }
}

function cancel(){
    form.disabledForm("model_form");
    button.onCancelClick();

    data = grid.getCurrentSelectRowData("model_gridtree");
    form.setFormValues("model_form",data);
}

function reset(){
    form.resetForm("model_form")
}

function onRowClick(data){
    form.disabledForm("model_form");
    button.onCancelClick();
    form.setFormValues("model_form",data);
}

function refresh(){
    dialog.refresh()
}