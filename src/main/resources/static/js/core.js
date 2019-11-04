﻿//弹出信息窗口 title:标题 msgString:提示信息 msgType:信息类型 [error,info,question,warning]
function msgShow(title, msgString, msgType) {
    $.messager.alert(title, msgString, msgType);
}



var Form =function() {
    //禁用表单
    this.disabledForm =function(form_id) {
        $("#"+form_id+" input").each(function(){
            $(this).attr("disabled","disabled");

        });
        $("#"+form_id+" .easyui-combobox").each(function(){
            $(this).combobox({disabled: true});
        });
    }

    //启用表单
    this.enabledForm =function(form_id) {
        $("#"+form_id+" input").each(function(){
            $(this).removeAttr("disabled");
        });
        $("#"+form_id+" .easyui-combobox").each(function(){
            var text = $(this).val()
            $(this).combobox({disabled: false});
            $(this).combobox('setValue',"1");
            $(this).combobox('setText',text);
        });
    }

    this.resetForm = function(form_id){
        $('#'+form_id).form('clear');
    }

    this.getFormValues = function(form_id){
        var data={};
        $("#"+form_id+" input[class^='easyui-']").each(function(){
            data[ $(this).attr('id')] = $(this).val()
        })
        return data;
    }

    this.setFormValues = function(form_id,data){

        for(var key in data){
            var input = $("#"+key);
            if(input.attr("class") != undefined ){
                if (input.attr("class").indexOf("easyui-combobox")!=-1){
                    input.combobox('setValue',data[key]);
                }else{
                    input.textbox('setValue',data[key]);
                }
            }
        }
    }
}

var Button = function(){
    this.disabledButton =function(id) {
        $("#"+id).linkbutton('disable');
    }
    this.enabledButton =function(id) {
        $("#"+id).linkbutton('enable');
    }
    this.onInsertClick = function(){
        $('#mode').textbox('setValue',"insert");
        this.disabledButton("btn_insert");
        this.disabledButton("btn_update");
        this.disabledButton("btn_del");

        this.enabledButton("btn_cancel");
        this.enabledButton("btn_save");
        this.enabledButton("btn_reset");
    }

    this.onUpdateClick = function(){
        $('#mode').textbox('setValue',"update");
        button.disabledButton("btn_insert");
        button.disabledButton("btn_update");
        button.disabledButton("btn_del");

        button.enabledButton("btn_cancel");
        button.enabledButton("btn_save");
        button.enabledButton("btn_reset");
    }
    this.onSaveClick = function(){
        button.enabledButton("btn_insert");
        button.enabledButton("btn_update");
        button.enabledButton("btn_del");

        button.disabledButton("btn_save");
        button.disabledButton("btn_reset");
        button.disabledButton("btn_cancel");
    }

    this.onCancelClick = function(){

        button.enabledButton("btn_insert");
        button.enabledButton("btn_update");
        button.enabledButton("btn_del");

        button.disabledButton("btn_save");
        button.disabledButton("btn_reset");
        button.disabledButton("btn_cancel");
    }
}


var Grid = function() {
    this.reloadGrid = function (grid_id) {
        $('#'+grid_id).datagrid('reload');
    }
    this.reloadWithData = function (grid_id,data) {
        $('#'+grid_id).datagrid('load',data);
    }
    this.getCurrentSelectRowData = function (grid_id) {
        return $('#'+grid_id).datagrid('getSelected');
    }
}

var EditGrid =function() {

}

var Dialog = function () {
    this.refresh = function () {
        window.location.reload()
    }
}


var form = new Form()
var button = new Button()
var grid = new Grid()
var editGrid = new EditGrid()
var dialog = new Dialog()






