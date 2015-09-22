<%@ page language="java" import="java.util.*,com.liaoyb.po.User" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>下载</title>
    <link rel="stylesheet" href="css/bootstrap.css">
    <script src="js/jquery-2.1.4.js"></script>
    <script src="js/bootstrap.js"></script>
    <style>
       .list-group-item{
           height: 60px;
           padding-bottom: 10px;
           padding-top: 10px;
       }
    </style>
</head>
<body>

<div class="list-group">
    <div  class="list-group-item" data-id="0182b323-c03a-4976-983f-2005f2882e57" url="/OnlineDisk/servlet/DownloadServlet"><label class="col-xs-8">Cras justo odio</label><a href="javascript:void(0);" class="col-xs-4">下载</a></div>
    <div  class="list-group-item" data-id="797928357295" url="/OnlineDisk/servlet/DownloadServlet"><label class="col-xs-8">Cras justo odio</label><a href="javascript:void(0);" class="col-xs-4" >下载</a></div>
    <div  class="list-group-item" data-id="797928357295" url="/OnlineDisk/servlet/DownloadServlet"><label class="col-xs-8">Cras justo odio</label><a href="javascript:void(0);" class="col-xs-4">下载</a></div>
    <div  class="list-group-item" data-id="797928357295" url="/OnlineDisk/servlet/DownloadServlet"><label class="col-xs-8">Cras justo odio</label><a href="javascript:void(0);" class="col-xs-4">下载</a></div>

</div>
<script>




    $(".list-group-item a").each(function(){
        $(this).click(function(){
//            alert($(this).text());
            //获得父标签的属性
            var parent=$(this).parent();
            var data_id=parent.attr("data-id");
            var url=parent.attr("url");
 			window.location=url+"?data_id="+data_id;
         //   window.open();



//            alert(data_id);
//            alert(url);

//            这儿的链接不应该写死
    //        $.get(url,{Action:"get",data_id:data_id});

        });
    });



</script>
</body>
</html>