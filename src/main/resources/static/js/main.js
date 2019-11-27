$(function(){
    $.ajaxSetup({
        complete:function(XMLHttpRequest,textStatus){
            if(textStatus=="parsererror"){
                $.messager.alert('提示信息', "登陆超时！请重新登陆！", 'info',function(){
                    top.location.href='login';
                });
            } else if(textStatus=="error"){
                $.messager.alert('提示信息', "请求超时！请稍后再试！", 'info');
            }
        }
    });
    initLeftMenu();
    tabClose();
    tabCloseEven();
    initTopButton();
    addIndexPage();
})


function addIndexPage(){
    $('#tabs').tabs('add',{
        title:'首页',
        content:createFrame('index.html')
    }).tabs({
        onSelect: function (title) {
            var currTab = $('#tabs').tabs('getTab', title);
            var iframe = $(currTab.panel('options').content);

            var src = iframe.attr('src');

            //if(src)切换tab刷新
            //$('#tabs').tabs('update', { tab: currTab, options: { content: createFrame(src)} });

        }
    });
}


//初始化头部按钮
function initTopButton() {

    win.close('pwdWindow')
    $('#changePwd').click(function() {
        win.open('pwdWindow')

    });
    $('#btnCancel').click(function(){
        win.close('pwdWindow')
    });

    $('#btnEp').click(function() {
        changePwd();
    })

    $('#logout').click(function() {
        $.messager.confirm('系统提示', '您确定要退出本次登录吗?', function(r) {
            if (r) {
                $.ajax({
                    type: "GET",
                    url: "logout",
                    success: function (result) {
                        result = eval("("+result+")")

                        if(result.code == "0"){
                            $.messager.alert('提示信息', result.message, 'info',function(){
                                window.location.href="login";
                            });
                        }
                    },
                    error:function (result) {
                        result = eval("("+result+")")
                        alert(result.message);
                    }
                });
            }
        });
    })
}


//初始化左侧
function initLeftMenu() {
    $("#nva").accordion({animate:false});

    $.get('SystemMgr/model/findForMenu/',function(result){
        $.each(result, function(i, n) {
            var menulist ='';
            menulist +='<ul id='+n.id+'_tree>';

            menulist += '</ul>';

            $('#nav').accordion('add', {
                title: n.text,
                content: menulist,
                iconCls: 'icon ' + n.iconCls,
                selected:false
            });

            $("#"+n.id+"_tree").tree({
                data : n.children,
                lines : false,
                animate:true,
                onClick : function (node) {
                    if (node.url) {
                        addTab(node.text,node.url,node.iconCls);
                    }
                },
                onDblClick: function(node) {
                    $("#"+n.id+"_tree").tree(node.state == 'closed' ? 'expand' : 'collapse',node.target);
                }
            });

            $("#"+n.id+"_tree").tree("collapseAll");
        });
    });







}


function addTab(subtitle,url,icon){
    if(!$('#tabs').tabs('exists',subtitle)){
        $('#tabs').tabs('add',{
            title:subtitle,
            content:createFrame(url),
            closable:true,
            iconCls:icon,

        });
    }else{
        $('#tabs').tabs('select',subtitle);
        //左边点击刷新右边
        //$('#tabsMenu-tabupdate').click();
    }
    tabClose();
}

function createFrame(url)
{
    var s = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
    return s;
}

function tabClose()
{
    /*双击关闭TAB选项卡*/
    $(".tabs-inner").dblclick(function(){
        var subtitle = $(this).children(".tabs-closable").text();
        $('#tabs').tabs('close',subtitle);
    })
    /*为选项卡绑定右键*/
    $(".tabs-inner").bind('contextmenu',function(e){
        $('#tabsMenu').menu('show', {
            left: e.pageX,
            top: e.pageY
        });

        var subtitle =$(this).children(".tabs-closable").text();

        $('#tabsMenu').data("currtab",subtitle);
        $('#tabs').tabs('select',subtitle);
        return false;
    });
}
//绑定右键菜单事件
function tabCloseEven()
{
    //刷新
    $('#tabsMenu-tabupdate').click(function(){
        var currTab = $('#tabs').tabs('getSelected');
        var url = $(currTab.panel('options').content).attr('src');
        $('#tabs').tabs('update',{
            tab:currTab,
            options:{
                content:createFrame(url)
            }
        })
    })
    //关闭当前
    $('#tabsMenu-tabclose').click(function(){
        var currtab_title = $('#tabsMenu').data("currtab");
        $('#tabs').tabs('close',currtab_title);
    })
    //全部关闭
    $('#tabsMenu-tabcloseall').click(function(){
        $('.tabs-inner span').each(function(i,n){
            var t = $(n).text();
            if(t!="首页"){
                $('#tabs').tabs('close',t);
            }
        });
    });
    //关闭除当前之外的TAB
    $('#tabsMenu-tabcloseother').click(function(){
        $('#tabsMenu-tabcloseright').click();
        $('#tabsMenu-tabcloseleft').click();
    });
    //关闭当前右侧的TAB
    $('#tabsMenu-tabcloseright').click(function(){
        var nextall = $('.tabs-selected').nextAll();
        if(nextall.length==0){
            //msgShow('系统提示','后边没有啦~~','error');
            return false;
        }
        console.info(nextall)
        nextall.each(function(i,n){
            var t=$('a:eq(0) span',$(n)).text();
            if(t!="首页"){
                $('#tabs').tabs('close',t);
            }
            $('#tabsMenu').menu('hide')
        });
        return false;
    });
    //关闭当前左侧的TAB
    $('#tabsMenu-tabcloseleft').click(function(){
        var prevall = $('.tabs-selected').prevAll();
        if(prevall.length==0){
            return false;
        }
        prevall.each(function(i,n){
            var t=$('a:eq(0) span',$(n)).text();
            if(t!="首页"){
                $('#tabs').tabs('close',t);
            }
            $('#tabsMenu').menu('hide')
        });
        return false;
    });

    //退出
    $("#tabsMenu-exit").click(function(){
        $('#tabsMenu').menu('hide');
    })
}






//修改密码
function changePwd() {
    var newpass = $('#txtNewPass');
    var rePass = $('#txtRePass');

    if (newpass.val() == '') {
        message.warning('请输入密码！');
        return false;
    }
    if (rePass.val() == '') {
        message.warning( '请在一次输入密码！');
        return false;
    }

    if (newpass.val() != rePass.val()) {
        message.warning( '两次密码不一至！请重新输入');
        return false;
    }
    var data={}
    data['password'] = $.md5(newpass.val());
    data['userId'] = currentUser.getId();

    $.ajax({
        type: "POST",
        contentType: "application/json;charset=UTF-8",
        url: "SystemMgr/user/changePassword",
        data: JSON.stringify(data),
        success: function (result,status,xhr) {
            loginJudge(result);
            if(result.code == "0"){
                message.info(result.message);
            }else{
                message.error(result.message);
            }
            $("#txtNewPass").val("");
            $("#txtRePass").val("");
            win.close("pwdWindow")


        },

        error:function (result) {
            result = eval("("+result+")")
            message.error(result.message);
        }
    });
}
