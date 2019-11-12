$(function(){

    field.invisible("id")
    field.invisible("mode")
    field.invisible("role_id")
    field.invisible("department_id")


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
    field.disabled("role")
    field.disabled("department")
    //选择角色
    button.bindTextButtonClick("role",showRoleWindow)
    //选择部门
    button.bindTextButtonClick("department",showDeptWindow)

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
        message.warning('请选择需要修改的数据！')
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
                message.info(result.message)
                grid.reloadGrid("user_grid")
                form.resetForm("user_form")
                form.disabledForm("user_form")
                button.onSaveClick();
            }else{
                message.error(result.message)
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
                        message.info(result.message)
                        grid.reloadGrid("user_grid")
                        form.resetForm("user_form")
                    }else{
                        message.error(result.message)
                    }
                });
            }
        });
    }
}

function cancel(){
    form.disabledForm("user_form");
    button.onCancelClick();
    data = grid.getCurrentSelectRowData("user_grid");
    form.setFormValues("user_form",data);
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

function showRoleWindow(){
    if ($("#role").attr("disabled")!="disabled") {
        $('#window').window({
            title: "选择角色",
            iconCls: 'icon-standard-application'
        });
        $("#department_gridtree_div").hide();
        $("#role_grid_div").show();
        $('#window').window('open');

        $("#user_grid").datagrid({
            onBeforeLoad: function () {
                $($("#role_grid").datagrid("getPager")).pagination({
                    layout: ['prev', 'manual', 'next']
                })
            },
            onDblClickRow: function (rowIndex, rowData) {
                chooseRole();
            }
        });
    }
}
function chooseRole(){
    var data = grid.getCurrentSelectRowData("role_grid");
    field.setFieldValue("role_id",data.id)
    field.setFieldValue("role",data.name)
    $('#window').window('close');
}

function showDeptWindow(){
    if ($("#department").attr("disabled")!="disabled"){
        $('#window').window({
            title:"选择部门",
            iconCls:'icon-standard-application'
        });
        $("#role_grid_div").hide();
        $("#department_gridtree_div").show();

        $('#window').window('open');

        $("#department_gridtree").treegrid({
            onDblClickRow: function (rowIndex, rowData) {
                chooseDept();
            },
        });
    }
}

function chooseDept(){
    var data = grid.getCurrentSelectRowData("department_gridtree");
    field.setFieldValue("department_id",data.id)
    field.setFieldValue("department",data.name)
    $('#window').window('close');
}