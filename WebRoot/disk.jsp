<%@ page language="java" import="java.util.*,com.liaoyb.po.User" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html >
<head>
    <meta charset="UTF-8">
    <title>网盘</title>
    <link rel="stylesheet" href="css/bootstrap.css">
     <link rel="shortcut icon" href="img/logo.ico" type="image/x-icon" />

    
    <style>

      .sel{
            width: 4%;
        }
        .fileicon{
            width: 4%;
        }
        .filename{
            width: 50%;
        }
        .row {
            margin-top: 51px;

        }

        .fixed-table-header table{

            margin-bottom: 0;
        }
        .bs-checkbox{
            width: 4%;
        }
        .bs-blank{
            width: 4%;
        }
        .bs-filename{
            width: 50%;
        }
        .bs-size{
            width: 21%;
        }
        .bs-edittime{
            width: 21%;
        }






        /*table区域的滚动条*/
        .fixed-table-body{

            overflow-y: auto;
            overflow-x: hidden;
        }
        #data_id{
            width: 0%;
        }
        #dir{
            margin-bottom: 0;
        }
        #dir ol{
            margin-bottom: 0;;
        }

        #men{
            height: 570px;
        }

    </style>
</head>
<body>
<%

User user=(User)session.getAttribute("user");
//如果用户未登录，重定向到首页
if(user==null){
	response.sendRedirect(request.getContextPath()+"/index.jsp");
}
%>

<a id="contextPath" hidden="hidden">${pageContext.request.contextPath}</a>
<!--固定在顶部-->
<nav class="navbar navbar-inverse navbar-fixed-top " role="navigation">
    <div class="container-fluid">
        <!--导航头-->
        <div class="navbar-header">
            <strong class="navbar-brand  " href="#">SKY</strong>
        </div>

        <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav" id="mytab">
                <li><a href="#" role="link" onclick='openUrl("${pageContext.request.contextPath}/index.jsp")'>主页</a></li>
                <li class="active"><a href="#">网盘</a></li>
                <li><a href="#">分享</a></li>
                <li><a href="#">应用</a></li>

            </ul>


            <!--下拉选项-->

            <ul class="nav navbar-nav navbar-left">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle " data-toggle="dropdown" role="button">${user.username}<span
                            class="caret"></span></a>
                    <ul class="dropdown-menu" role="menu">
                        <li><a href="#">个人资料</a></li>
                        <li><a href="#">购买容量</a></li>
                        <li><a href="${pageContext.request.contextPath}/servlet/LogoutServlet">退出</a></li>
                    </ul>
                </li>
            </ul>


            <ul class="nav navbar-nav navbar-right" id="mytab2">

                <!--默认这里链接点击没有效果,要用js控制,上一层加个id-->
                <li><a href="#">客户端下载</a></li>
                <li><a href="#">通知</a></li>
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"><span
                            class="glyphicon glyphicon-align-justify"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="#">移动平台</a></li>
                        <li><a href="#">收藏工具</a></li>
                        <li><a href="#">帮助中心</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="#">问题反馈</a></li>
                    </ul>
                </li>
            </ul>

            <!--搜索框,要加navbar-left或者navbar-right-->
            <ul class="nav  navbar-form navbar-right " role="search">
                <div class="form-group form-group-sm">
                    <input type="text" class="form-control" placeholder="search">
                    <button type="submit" class="btn btn-default btn-sm">Search</button>
                </div>

            </ul>

        </div>

    </div>

</nav>


<div class="row">
    <div class="col-xs-3">

        <div id="men" class="nav-justified">
            <ul class="list-group list-unstyled ">
                <li class="list-group-item" role="button"><span></span><a>全部文件</a></li>
                <li class="list-group-item" role="button"><span class="glyphicon glyphicon-picture "></span><a >&nbsp;&nbsp;&nbsp;图片</a></li>
                <li class="list-group-item" role="button"><span class="glyphicon glyphicon-list-alt"></span><a>&nbsp;&nbsp;&nbsp;文档</a></li>
                <li class="list-group-item" role="button"><span class="glyphicon glyphicon-film"></span><a>&nbsp;&nbsp;&nbsp;视频</a></li>
                <li class="list-group-item" role="button"><span class="glyphicon glyphicon-music"></span><a>&nbsp;&nbsp;&nbsp;音乐</a></li>
                <li class="list-group-item" role="button"><span class="glyphicon glyphicon-stats"></span><a>&nbsp;&nbsp;&nbsp;其它</a></li>
                <li class="list-group-item" role="button"><span class="glyphicon glyphicon-trash"></span><a>&nbsp;&nbsp;&nbsp;回收站</a></li>
                <li class="list-group-item" role="button"><span class="glyphicon glyphicon-retweet"></span><a>&nbsp;&nbsp;&nbsp;我的分享</a></li>

            </ul>

        </div>

    </div>
    <div class="col-xs-9">
        <div id="toolbar">
            <div class="btn-group  ">
                <div class="btn-group" style="margin-right: 20px;margin-left: 20px;">
                <button type="button" class="btn btn-primary ">上传</button>
                   <button type="button" class="btn btn-primary dropdown" data-toggle="dropdown">
                       <span class="caret"></span>
                   </button>
                    <ul class="dropdown-menu">
                        <li><a   href="#" onclick="chooseFolder()">上传文件夹</a></li>
                        <li role="presentation" class="divider"></li>
                        <li><a  href="#" role="button" data-toggle="modal" data-target="#uploadModal">上传文件</a></li>
                    </ul>

                </div>
               <div class="btn-group" style="margin-right: 20px;">
                    <button type="button" class="btn btn-primary" onclick="downloadSelections()" data-toggle="modal" data-target="#downloadModal">下载</button>
                </div>
               <div class="btn-group">
                    <button type="button" class="btn btn-primary" id="btn_addfolder"  data-toggle="modal" data-target="#creDirModal">新建文件夹</button>
                </div>
            </div>

        </div>
            <nav class="navbar" id="dir">

                <!--路径导航-->
                <ol class="breadcrumb" id="mypath">
                    <li><a href="#" onclick="clickPath(this)">全部文件</a></li>

                </ol>
            </nav>
        <div class="fixed-table-container">
            <div class="fixed-table-header">
                <table class="table table-hover " >
                    <thead>
                    <tr>
                        <th class="bs-checkbox">
                            <input id="btSelectAll" type="checkbox">
                        </th>
                        <th class="bs-blank"></th>
                        <th class="bs-filename">文件名</th>
                        <th class="bs-size">大小</th>
                        <th class="bs-edittime">修改日期</th>
                        <th class="bs-data"></th>
                    </tr>
                    </thead>
                </table>
            </div>
            <div id="tablearea" class="fiexed-table-body">
                <table id="tab_body" data-toggle="table" data-url="${pageContext.request.contextPath}/servlet/DiskDataServlet?path=root" class="table table-striped  table-hover" id="mytable" data-click-to-select="true" data-show-header="false">
                    <!--表格标题-->
                    <thead >

                    <tr>
                        <th class="sel" data-field="state" data-checkbox="true" style="width: 4%"></th>
                        <th class="fileicon" data-field="fileicon" data-formatter="iconFormat" style="width: 4%"></th>
                        <th class="filename" data-field="filename" data-formatter="fileNameFormat" style="width: 50%">文件名</th>
                        <th data-field="filesize" data-sortable="true" style="width: 21%">大小</th>
                        <th  data-field="editdate" data-sortable="true" style="width: 21%">修改日期</th>
                        <th style="width: 4%" data-formatter="deleteNameFormat"></th>
                        <th data-field="data_id" id="data_id" ></th>
                    </tr>
                    </thead>


                </table>
            </div>
        </div>
        </div>


</div>


<!--新建文件夹提示框-->

<div class="modal fade " id="creDirModal" role="dialog" >
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span>&times;</span>
                </button>
                <h4 class="modal-title" >新建文件夹</h4>
            </div>
            <div class="modal-body" id="creDir_body">

                <div class="form-group " >
                    <label class="">文件夹名称</label>
                    <input type="text" class="form-control " name="path" id="dirname" >
                </div>
                <div class="alert alert-info" role="alert" id="creMess">
                 请在上方的输入框中输入符合规范的文件夹名，请勿带有特殊字符
                </div>

            </div>
            <div class="modal-footer " >
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="creDir_btn" onclick="createFolder()">确定</button>
            </div>
        </div>
    </div>
</div>





<!--删除文件提示框-->

<div class="modal fade " id="delModal" role="dialog" >
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span>&times;</span>
                </button>
                <h4 class="modal-title" >警告</h4>
            </div>
            <div class="modal-body" id="del_body">


                <div class="alert alert-danger" role="alert" >
                   是否要删除当前文件
                </div>

            </div>
            <div class="modal-footer " >
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="del_btn" onclick="deletefile()">确定</button>
            </div>
        </div>
    </div>
</div>


<!--下载文件提示框-->
<div class="modal fade " id="downloadModal" role="dialog" >
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span>&times;</span>
                </button>
                <h4 class="modal-title" >下载</h4>
            </div>
            <div class="modal-body" id="download_body">
                <label id="download_mess">是否下载所选文件</label>
                <ul class="list-group" id="download_ul">
                    <!--<li class="list-group-item">文件1</li>-->
                    <!--<li class="list-group-item">文件2</li>-->
                    <!--<li class="list-group-item">文件3</li>-->
                </ul>


            </div>
            <div class="modal-footer " >
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="download_btn" onclick="download()">下载</button>
            </div>
        </div>
    </div>
</div>

<!--上传模糊框-->

<div class="modal fade " id="uploadModal" role="dialog" >
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span>&times;</span>
                </button>
                <h4 class="modal-title" id="exampleModalLabel">上传</h4>
            </div>
            <div class="modal-body">
                <form class="upload_file" enctype="multipart/form-data" id="upload_form">
                    <div class="form-group " style="display: none;">
                        <label class="">Path</label>
                        <input type="text" class="form-control " name="path" id="path" >
                    </div>
                     <div class="form-group" style="display: none;">
                        <label>Size</label>
                        <input type="text" class="form-control" name="size" id="size" >
                    </div>
                    <div class="form-group">
                        <input  type="file" name="file" id="upload_viewFile">
                    </div>
                </form>


                <!--进度条-->
                <div class="progress" style="display: none;" id="upload_progress">
                    <div class="progress-bar progress-bar-striped active" role="progressbar" aria-valuenow="45" aria-valuemin="0" aria-valuemax="100" style="width: 0%">
                        <span class="sr-only">45% Complete</span>
                    </div>
                </div>
                <div><a id="upload_p"></a></div>
            </div>
            <div class="modal-footer " >
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="upload_submit">上传</button>
            </div>
        </div>
    </div>
</div>







<script src="js/jquery-2.1.4.js"></script>
<script src="js/bootstrap.js"></script>
<script src="js/bootstrap-table.js"></script>

<script>

function openUrl(newurl){
    window.location.href=newurl;
}

  var allData;

//创建文件夹
    function createFolder(){
        //获得输入的文件夹名
        var dirname=$("#dirname").val().trim();
        console.log("文件夹名:"+dirname);
		
		  //遍历allData
        var index=0;
        for(index=0;index<allData.length;index++){
            if(allData[index].filename==dirname){
                $("#creMess").text("和已有的文件同名了");
                return;
            }
        }
		
        //校验，不能带有/
        if(/\/+/.test(dirname)){
            console.log("文件夹中不能含有/");
            $("#creMess").text("文件夹中不能含有/");
            return;
        }
        var createDirurl=$("#contextPath").text()+"/servlet/NewDirServlet";
         var path=getCurrentPath();
        //发送请求
        $.ajax({
            url:createDirurl,
            type:'post',
            data:'dirname='+dirname+'&path='+path,
            async : true, //默认为true 异步
            error:function(){
                alert('error');
            },
            success:function(data){
                //返回json数据
                var retJson=eval(data);

                console.log(retJson.mess);
                //刷新表格

       			 refreshCurrentTableData();
            }
        });



        //重置输入框
        $("#creMess").text("请在上方的输入框中输入符合规范的文件夹名，请勿带有特殊字符");
        $("#dirname").val("");

        //刷新表格

         refreshCurrentTableData();



        //这里关闭下载窗口

        $("#creDirModal").modal("hide");
    }





  
//

    $("#tab_body").bootstrapTable({onLoadSuccess:function(data){
        allData=data;
        console.log("加载成功");
        console.log(data);
        
         if($("#tab_body tbody tr").attr("class")=="no-records-found"){
                //没有数据返回
                console.log("没有数据返回");
                return;
                
            }
            //给每一行添加一个移入事件
        $("#tab_body tbody tr").hover(function(){
        
//            console.log($(this).html());

            //可以取得每一行数据,取得data_id
//            console.log(data[$(this).attr("data-index")].data_id);

            console.log("移入了这一行");
            //最后一个子元素
//            console.log($(this).children(":last"));
            $(this).children(":last").empty();
            $(this).children(":last").append('<span class="glyphicon glyphicon-remove" role="button" onclick="deleteCurrentRow(this)"></span>');

        });
        $("#tab_body tbody tr").mouseleave(function(){
            $(this).children(":last").text("--");
        });

    }});



    var del_data_id;

    //删除文件
    function deletefile(){

        //    //发出删除请求
    var deleteurl=$("#contextPath").text()+"/servlet/DeleteFileServlet";
    $.ajax({
        url:deleteurl,
        type:'post',
        data:'data_id='+del_data_id,
        async: true, //默认为true 异步
        error:function(){
            alert('error');
        },
        success:function(data){
            //返回json数据
            var retJson=eval(data);

            console.log(retJson.mess);
            //刷新表格

            refreshCurrentTableData();

        }
    });


        $("#delModal").modal("hide");
    }




//删除当前行
function deleteCurrentRow(my){
	 $("#delModal").modal("show");
    var data_index=$(my).parent().parent().attr("data-index");
    console.log("删除对应数据"+allData[data_index].data_id);

    var data_id=allData[data_index].data_id;
    //发出删除请求
    var deleteurl=$("#contextPath").text()+"/servlet/DeleteFileServlet";
   
    del_data_id=data_id;

	}
	
	
	

 //点击文件的链接
    function clickFile(my){
        var data_index=$(my).parent().parent().attr("data-index");
        console.log("删除对应数据"+allData[data_index].data_id);
        var row=allData[data_index];
        //如果是文件夹，更新路径导航信息
        if(row.type=="0"){
            $("#mypath").append("<li><a href='#' onclick='clickPath(this)'>"+row.filename+"</a></li>");
            //刷新表格
            refreshCurrentTableData();
        }else{
            //是文件的话，就下载或跳到预览页面


        }



    }


   //点击某一行
   $("#tab_body").on('click-row.bs.table',function(e,row,$element){
//       alert('Event: click-row.bs.table, data: ' + JSON.stringify(row));




   });
   
   
   
   
   
   //这是下载对话框中的下载按钮的事件
    //这里是真正的下载

    //选择的文件的data_id
var select_data_id;

    function download(){
        if ( typeof(dataTest) !== 'undefined'){
            console.log("下载错误");
            $("#download_mess").text("下载错误");
            return;
        }
    var url=$("#contextPath").text()+"/servlet/DownloadServlet";
      //暂停几秒
      //下载第一个
      var arg=0;
        mytime = setInterval(gotodow, 1000);
        function gotodow(){
            window.location.href=url+"?data_id="+select_data_id[arg];
            console.log("下载:"+select_data_id[arg]);
            arg+=1;
            if(arg==select_data_id.length)
                clearInterval(mytime);
        }
		 //这里关闭下载窗口

        $("#downloadModal").modal("hide");


    }

    //这主窗口的下载按钮
    //下载，下载按钮对应事件
function downloadSelections(){
    //开始下载按钮是可用的
    $("#download_btn").removeAttr("disabled");

    var jsonArr=getSelect();
    //定义data_id数组
    var data_arr=new Array();
    var data_arr_name=new Array();
    var data_arr_id=new Array();
    var arg=0;
    for(var p in jsonArr){
       var jsobj=jsonArr[p];
        //是文件
        if(jsobj.type=="1"){
            data_arr[arg]=jsobj;
            data_arr_name[arg]=jsobj.filename;
            data_arr_id[arg]=jsobj.data_id;
            arg++;
        }

    }
    var selFileNames="";
    //遍历所选文件名数组
    $.each(data_arr_name,function(n,value){
        selFileNames+=value;
    });
    console.log("是否下载所选文件:"+selFileNames);


    //把文件名传递给下载模态框,并生成对应的文件名li
    $.each(data_arr_name,function(n,value){
        selFileNames+=value;
        $("#download_ul").append("<li class='list-group-item'>"+value+"</li>");
    });
    //如果没有选择任何文件，给出提示，并禁用掉下载按钮

    if(data_arr_name.length==0){
        $("#download_mess").text("未选择任何文件");
        $("#download_btn").attr("disabled","disabled");
    }else{
        $("#download_btn").removeAttr("disabled");
        $("#download_mess").text("是否下载选择文件");
    }

    //如果选择文件过多，给出提示，并且禁用下载
if(data_arr_name.length>6){
    $("#download_mess").text("选择文件过多，请勿一次下载");
    $("#download_btn").attr("disabled","disabled");
}

    select_data_id=data_arr_id;


    //提示，是否下载所选文件,文件夹不包括


	}
   
   
   
    //关闭下载模态框
    $("#downloadModal").on("hide.bs.modal",function(event){
        //清空文件名ul
        $("#download_ul").empty();


    });
   
   

  //拿到选中的
function getSelect(){
    var table_body=$("#tab_body");
//    alert('Selected values: ' + JSON.stringify(table_body.bootstrapTable('getSelections')));
    var jsonArr=table_body.bootstrapTable('getSelections');
    //解析,拿到data_id
    console.log("所选个数:"+jsonArr.length);
    for(var p in jsonArr){
        console.log(jsonArr[p]);
    }

    return jsonArr;

}





  //路径导航的切换
   function clickPath(my){


       //所有后面的兄弟节点  li删除
       $(my).parent().nextAll().each(function(){
           $(this).remove();
       });
       //刷新表格
     refreshCurrentTableData();




   }

 //获取当前路径
   function getCurrentPath(){
       var path="";
       //遍历子节点
       $("#mypath").children().each(function(){
           var t=$(this).children().text();
           if(t=="全部文件")
            path="root";
           else
            path=path+"_"+$(this).children().text();
       });
       return path;
   }

 



   //ajax发送请求，接收json数据,刷新表格

function refreshTableData(myurl){
    $("#tab_body").bootstrapTable('refresh', {
        url: myurl
    });
}

    function refreshCurrentTableData(){

        var dataUrl=$("#contextPath").text()+"/servlet/DiskDataServlet?path="+getCurrentPath();
//        alert(dataUrl);
        refreshTableData(dataUrl);

    }


  function refreshTableDataOrig(){
       var origUrl=$("#tab_body").attr("data-url");
       $("#tab_body").bootstrapTable('refresh', {
           url: origUrl
       });
   }



    //对表格中图标的格式化
    function iconFormat(value){
        var icon="";
        switch (value){
           case "0":icon='glyphicon-file';break;
            case "1":icon='glyphicon-film';break;
            case "2":icon='glyphicon-music';break;
            case "3":icon='glyphicon-picture';break;
            case "4":icon='glyphicon-briefcase';break;
            case "5":icon='glyphicon-file';break;
            case "6":icon='glyphicon-folder-open';break;//文件夹
        }
        return '<span class="glyphicon '+icon+'"></span>';

    }
    //对表格中文件名格式化，文件夹的话，就是到下一级目录    。文件的话就是打开此文件
    function fileNameFormat(value){
        return '<a role="link" href="javascript:void(0);"  onclick="clickFile(this)">'+value+'</a>';
    }

 	function deleteNameFormat(value){
        return '--';
    }




    //重置复选框,其它地方调用
    function resetCheck(){
        var incheckAll= $("#btSelectAll");
        incheckAll.attr("checked",false);
    }


    //选中所有

    $("#btSelectAll").click(function(){
        var incheckAll= $("#btSelectAll");
        //判断是否被选中
        var checked=incheckAll.is(":checked");

        //
        var table_body=$("#tab_body");
        //选中状态,就全选
        if(checked){
            table_body.bootstrapTable("checkAll");

        }else{
            table_body.bootstrapTable("uncheckAll");
        }



    });



    $("#mytab a,#mytab2 a").click(function (e) {
        e.preventDefault();
        if (this.parentNode.className == "disabled")
            return;
        $(this).tab("show");
    });

    //显示上传的模糊框
    $("#uploadModal").on("show.bs.modal",function(event){

        //如果当前还在上传，就应该把进度条显示出来
       var str= $("#upload_viewFile").attr('disabled');
        if(str=='disabled'){
            $("#upload_progress").css('display','block');
        }

//        获得事件的目标对象
        var button=$(event.relatedTarget);

//        取得data-whatever里面的值
//        var recipient=button.data("whatever");
        var modal=$(this);
//        modal.find(".modal-title").text("New Message to "+recipient);
//        modal.find(".modal-body input").val(recipient);
    });

    $("#uploadModal").on("hide.bs.modal",function(event){

    //  关闭上传对话框
    //刷新table
      refreshCurrentTableData();




    });


    function onprogress(evt) {
//设置进度信息
        console.log("current:"+evt.loaded+"total:"+evt.total);
        var percent=evt.loaded*100 / evt.total;
        console.log("进度:"+percent);
        setProgress(percent);
    };
    var xhr_provider = function() {
        var xhr = jQuery.ajaxSettings.xhr();
        if(onprogress && xhr.upload) {
            xhr.upload.addEventListener('progress', onprogress, false);
        }
        return xhr;
    };


//当选取文件后可以获取到文件信息
   $(':file').change(function(){
       var file = this.files[0];
       name = file.name;
       size = file.size;
       type = file.type;
//       alert(size);
       //把文件大小的输入栏更新
       $("#size").val(size);

       //把path栏的数据更新
        $("#path").val(getCurrentPath());


       //your validation
   });


    //点击上传按钮
    $("#upload_submit").click(function(){
    var myurl=$("#contextPath").text()+"/servlet/UploadServlet";
        //  获取path信息，     提交表单
        var formData=new FormData($("#upload_form")[0]);

        $.ajax({

            url: myurl,
            type: 'POST',
            xhr: xhr_provider,
            data: formData,
            async: true,
            cache: false,
            contentType: false,
            processData: false,
            success: function (returndata) {
                $("#upload_viewFile").removeAttr('disabled');
                $("#upload_submit").removeAttr('disabled');
                $("#upload_p").text("上传成功");
                //把进度条隐藏
                var prog=$("#upload_progress");

                //清0
                setProgress(0);
                prog.css('display','none');

           //     alert("success");
            //返回json数据
                var retJson=eval(returndata);
                //解析


               // alert(retJson.mess);
                $("#upload_p").text(retJson.mess);
            },
            error: function (returndata) {
                alert("error");
            }
        });


        //开始上传，显示进度条，更新上传进度信息


        //把上传按钮和文件浏览按钮禁用掉
        $("#upload_viewFile").attr('disabled','disabled');
        $("#upload_submit").attr('disabled','disabled');
        $("#upload_p").text("正在拼命上传......");
        var prog=$("#upload_progress");
        prog.css('display','block');
    });


//处理进度0.01
    function setProgress(percent){
        var p=""+percent+"%";
        var progBar=$("#upload_progress div");
        progBar.css('width',p);

    }

    //窗体加载事件

    $(window).load(function() {
        $("#tab_body").bootstrapTable('hideColumn','data_id');
    });



</script>


</body>
</html>