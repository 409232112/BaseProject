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
    $('#sex').combobox({
        onChange: function(param){
            query();
        }
    });
    //回车查询
    $('#name').textbox('textbox').keydown(function (e) {
        if (e.keyCode == 13) {
            query();
        }
    });
    $('#email').textbox('textbox').keydown(function (e) {
        if (e.keyCode == 13) {
            query();
        }
    });
    $('#phone').numberbox({inputEvents:$.extend({},$.fn.numberbox.defaults.inputEvents,{
        keyup:function(e){
                if (e.keyCode == 13) {
                    query();
                }
            }
        })
    })
})


function query(){
    var data =  form.getFormValues("user_form");
    grid.reloadWithData("user_grid",data)
}

function reset(){
    form.resetForm("user_form")
}


