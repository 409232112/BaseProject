//处理session过期ajax跳转
$.ajaxSetup({
    complete:function(XMLHttpRequest,textStatus){
        if(textStatus=="parsererror"){
            $.messager.alert('提示信息', "登陆超时！请重新登陆！", 'info',function(){
                top.location.href='login';
            });
        } else if(textStatus=="error"){
            $.messager.alert('提示信息', "服务器内部错误！", 'info');
        }
    }
});


//弹出信息窗口 title:标题 msgString:提示信息 msgType:信息类型 [error,info,question,warning]
function msgShow(title, msgString, msgType) {
    $.messager.alert(title, msgString, msgType);
}
var Msessage = function(){
    this.info = function(msg){
        $.messager.alert("系统提示", msg, "info");
    }
    this.warning = function(msg){
        $.messager.alert("系统提示", msg, "warning");
    }
    this.error = function(msg){
        $.messager.alert("系统提示", msg, "error");
    }
    this.info = function(msg){
        $.messager.alert("系统提示", msg, "info");
    }
}

var Field = function(){
    this.getFieldValue = function (field_id) {
        return $("#"+field_id).val();
    }
    this.setFieldValue = function (field_id,value) {
        $('#'+field_id).textbox('setValue',value);
    }
    //隐藏Field
    this.invisible =function(field_id) {
        $("#"+field_id).next().hide();
    }
    //显示Field
    this.visible =function(field_id) {
        $("#"+field_id).next().show();
    }
    //Field不可用
    this.disabled =function(field_id) {
        $('#'+field_id).textbox('textbox').attr('readonly',true);
    }
    this.enabled =function(field_id) {
        $('#'+field_id).textbox('textbox').attr('readonly',false);
    }

}


var Form =function() {
    //禁用表单
    this.disabledForm =function(form_id) {
        $("#"+form_id+" input").each(function(){

            //textarea
            if(typeof($(this).attr("data-options"))!="undefined" && $(this).attr("data-options").indexOf("multiline:true")!=-1){
                $(this).next().children().attr("disabled","disabled");
                //text input
            }else{
                $(this).attr("disabled","disabled");
            }

        });
        //combobox
        $("#"+form_id+" .easyui-combobox").each(function(){
            $(this).combobox({disabled: true});
        });
    }

    //启用表单
    this.enabledForm =function(form_id) {
        $("#"+form_id+" input").each(function(){
            if(typeof($(this).attr("data-options"))!="undefined" && $(this).attr("data-options").indexOf("multiline:true")!=-1){
                $(this).next().children().removeAttr("disabled");

            }else{
                $(this).removeAttr("disabled");
            }
        });
        $("#"+form_id+" .easyui-combobox").each(function(){
            var text = $(this).combobox('getText');
            var value = $(this).combobox('getValue');
            $(this).combobox({disabled: false});
            $(this).combobox('setValue',value);
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

    this.bindTextButtonClick = function (button_id,f) {
        $("#"+button_id).next('span').children("a").children("span").children("span").bind("click",f)
    }
}


var Grid = function() {
    this.reloadGrid = function (grid_id) {
        console.info($('#'+grid_id))
        $('#'+grid_id).datagrid('reload');
    }
    this.reloadWithData = function (grid_id,data) {
        $('#'+grid_id).datagrid('load',data);
    }
    this.getCurrentSelectRowData = function (grid_id) {
        return $('#'+grid_id).datagrid('getSelected');
    }
    this.unselectAllRow = function (grid_id){
        $('#'+grid_id).treegrid('unselectAll');
    }
}

var Tree = function(){

}

var TreeGrid = function() {
    this.reloadWithData = function (grid_id,data) {
        $('#'+grid_id).treegrid('load',data);
    }
    this.reloadGrid = function (grid_id) {
        $('#'+grid_id).treegrid('reload');
    }
    this.unselectAllRow = function (grid_id){
        $('#'+grid_id).treegrid('unselectAll');
    }
}

var EditGrid =function() {

}

var Dialog = function () {
    this.refresh = function () {
        window.location.reload()
    }
}

var Win = function () {
    this.open =function (winId) {
        $('#'+winId).window('open');
    }
    this.close =function (winId) {
        $('#'+winId).window('close');
    }
}

var User = function (){
    this.getId = function(){
        return localStorage.id;
    }
    this.getName = function(){
        return localStorage.name;
    }
    this.getRole = function(){
        return localStorage.role;
    }
    this.getDept = function(){
        return localStorage.dept;
    }
}

var DateFormatter =  function (){
    this.formatDatetime = function(value) {

        if (value) {
            if(typeof value == "string"){
                value = value.replace("T", " ");
                value = value.slice(0, value.indexOf("."));
            }
            var date = new Date(value);
            var day = date.getDate() > 9 ? date.getDate() : "0" + date.getDate();
            var month = (date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : "0" + (date.getMonth() + 1);
            var hor = date.getHours() > 9 ? date.getHours() : "0" + date.getHours();
            var min = date.getMinutes()> 9 ? date.getMinutes() : "0" + date.getMinutes();;
            var sec = date.getSeconds()> 9 ? date.getSeconds() : "0" + date.getSeconds();;
            return date.getFullYear() + '-' + month + '-' + day+" "+hor+":"+min+":"+sec;
        } else {
            return "";
        }
    }
    this.formatCotent = function(value, row, index){
        if(value){
            return "<div title='"+value+"' class='textEllipsis'>"+value+"</div>";
        }else{
            return '';
        }
    }


}

var message = new Msessage();
var field = new Field();
var form = new Form();
var button = new Button();
var grid = new Grid();
var editGrid = new EditGrid();
var tree = new Tree();
var treeGrid = new TreeGrid();
var dialog = new Dialog();
var win = new Win()
var currentUser = new User();
var dateFormatter = new DateFormatter()










