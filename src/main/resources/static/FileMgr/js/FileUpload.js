﻿$(function(){

    field.invisible("id")
    field.invisible("mode")
    field.invisible("file_type")
    field.invisible("file_name")

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

    //获取文件类型下拉数据
    $("#file_type_code").combobox({
        url:"file/getFileType",//获取数据
        // 向服务器请求的模式
        method : "get",
        valueField: 'code',
        textField: 'text',
    })

    //表格行单击事件
    $("#file_grid").datagrid({
        queryParams: {
            file_type: getQueryString("type")
        },
        onClickRow:function(index,data){
            onRowClick(index,data);
        }
    })

    //文件选择变化
    $("#file").filebox({
        onChange:function(){
            var file_name = $(this).filebox('getValue');
            file_name = file_name.substring(file_name.lastIndexOf("\\")+1);
            field.setFieldValue("file_name",file_name);
        }
    })

    form.disabledForm("file_form");
})



function insert(){
    form.resetForm("file_form")
    form.enabledForm("file_form");
    button.onInsertClick();
    field.setFieldValue("file_type",getQueryString("type"))
}
function update(){
    var row = grid.getCurrentSelectRowData("file_grid")
    if (row){
        button.onUpdateClick();
        form.enabledForm("file_form");
    }else{
        message.warning('请选择需要修改的数据！')
    }
}
function save(){
    $('#file_form').form('submit',{
        url: 'file/save',
        onSubmit: function(){
            //return $(this).form('validate');
        },
        success: function(result){
            result = eval('('+result+')');
            if(result.code=="0"){
                message.info(result.message)
                grid.reloadGrid("file_grid")
                reset();
                form.disabledForm("file_form")
                button.onSaveClick();
            }else{
                message.error(result.message)
            }
        }
    });
}
function del(){
    var row = grid.getCurrentSelectRowData("file_grid")
    if (row){
        $.messager.confirm('Confirm','确定删除？',function(r){
            if (r){
                $.get('file/delete/'+row.id,function(result){
                    var result = eval('('+result+')');
                    if(result.code=="0"){
                        message.info(result.message)
                        grid.reloadGrid("file_grid")
                        reset();
                    }else{
                        message.error(result.message)
                    }
                });
            }
        });
    }
}

function cancel(){
    button.onCancelClick();
    data = grid.getCurrentSelectRowData("file_grid");
    form.setFormValues("file_form",data);
    form.disabledForm("file_form");
}

function reset(){
    form.resetForm("file_form");
    $('#file').filebox({prompt:''});
}

function onRowClick(index,data){

    form.setFormValues("file_form",data);
    form.disabledForm("file_form")
    button.onSaveClick();
}

function refresh(){
    dialog.refresh()
}
