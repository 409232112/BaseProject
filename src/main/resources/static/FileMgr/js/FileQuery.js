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


    //下拉框选择自动查询
    $('#file_type_code').combobox({
        onChange: function(param){
            query();
        }
    });
    //回车查询
    $('#file_name').textbox('textbox').keydown(function (e) {
        if (e.keyCode == 13) {
            query();
        }
    });
    $('#created_user_name').textbox('textbox').keydown(function (e) {
        if (e.keyCode == 13) {
            query();
        }
    });

    $("#file_type_code").combobox({
        url:"file/getFileType",//获取数据
        // 向服务器请求的模式
        method : "get",
        valueField: 'code',
        textField: 'text',
    })

    if(getQueryString("type")=="1"){
        field.invisible("created_user_name");
        $("#created_user_name").parent().prev().hide();
    }

    $("#file_grid").datagrid({
        queryParams: {
            file_type: getQueryString("type")
        },
        onLoadSuccess:function(data){
            $('.download').linkbutton({plain:true,iconCls:"icon-save"});
            $('.show').linkbutton({plain:true,iconCls:"icon-standard-eye"});
        }
    })

})


function query(){
    var data =  form.getFormValues("file_form");
    data['file_type']=getQueryString("type")
    grid.reloadWithData("file_grid",data)
}

function reset(){
    form.resetForm("file_grid")
}

function refresh(){
    dialog.refresh()
}



function handle(val,row,index){
    var btn = '<a class="download" onclick="downloadFile(\''+row.id+'\')"  href="javascript:void(0)">下载</a><a class="show" href="javascript:void(0)">查看</a>'
    return btn;
}

function downloadFile(file_id){
    window.location.href="file/downloadFile/"+file_id;
}


