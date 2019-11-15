$(function(){
    clearLocalStorage()
    $("#username").focus(function(){
        $("#username").attr("placeholder","");
    });
    $("#username").blur(function(){
        if($("#username").val()==''){
            $("#username").attr("placeholder","用户名");
        }
    });

    $("#password").focus(function(){
        $("#password").attr("placeholder","");
    });
    $("#password").blur(function(){
        if($("#password").val()==''){
            $("#password").attr("placeholder","密码");
        }
    });

    $("#code").focus(function(){
        $("#code").attr("placeholder","");
    });
    $("#code").blur(function(){
        if($("#code").val()==''){
            $("#code").attr("placeholder","验证码");
        }
    });

    $("#identifyCode").click(function(){
        var timestamp = new Date().getTime();
        $(this).attr("src", 'identifyCode?'+ timestamp);
    });

    $("#submit").click(function(){
        login()
    });
    $("body").keydown(function(event) {
        if (event.keyCode == "13") {//keyCode=13是回车键
            login()
        }
    })
})

function login() {
    if (formValidate()) {
        var username = $("#username").val();
        var password = $.md5($("#password").val());
        var code = $("#code").val();
        var data = {};
        data['username'] = username;
        data['password'] = password;
        data['code'] = code;
        $.ajax({
            type: "POST",
            contentType: "application/json;charset=UTF-8",
            url: "login",
            data: JSON.stringify(data),
            success: function (result) {
                result = eval("("+result+")")
                alert(result.message);
                if(result.code == "0"){
                    localStorage.setItem("id", result.data.id);
                    localStorage.setItem("name", result.data.name);
                    localStorage.setItem("role", result.data.role);
                    localStorage.setItem("dept", result.data.dept);
                    window.location.href="main";
                }else{
                    $("#identifyCode").click();
                }
            },

            error:function (result) {
                result = eval("("+result+")")
                alert(result.message);
            }
        });
    }
}

function formValidate(){
    var username =  $("#username").val();
    if(!username){
        alert("请输入用户名！");
        return false;
    }
    var password =  $("#password").val();
    if(!password){
        alert("请输入密码！");
        return false;
    }
    var code =  $("#code").val();
    if(!code){
        alert("请输入验证码！");
        return false;
    }
    return true;
}
function clearLocalStorage(){
    localStorage.removeItem("id");
    localStorage.removeItem("name");
    localStorage.removeItem("role");
    localStorage.removeItem("dept");
}