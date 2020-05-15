<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<c:url var="R" value="/" />
<link rel="stylesheet" href="${R}css/myBoard.css">
<html>
<head>
    <meta charset="utf-8">
    <title>Timeline</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
    <link href="http://netdna.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet">
<div class="container">
<div class="row">
<div class="col-md-12">
<div id="content" class="content content-full-width">
<div class="profile">
<div class="profile-header">
<div class="profile-header-cover"></div>
<div class="profile-header-content">
<div class="profile-header-img">
<img src="https://litebook-images.s3.ap-northeast-2.amazonaws.com/timeline-images/b.jpeg">
</div>
<div class="profile-header-info">
<h4 class="m-t-10 m-b-5">${user.userName}</h4>
</div>
</div>
<div class="main_nav tab_wrap">
    <ul class="profile-header-tab nav nav-tabs center tab_menu_container">
        <li class="nav-item tab_menu_btn on"><a href="#" target="_self" class="tab_menu_btn1 tab_menu_btn1 on active show">${user.userName} POSTS</a></li>
        <li class="nav-item tab_menu_btn on"><a href="/posts" target="_self" class="tab_menu_btn2" >MY POSTS</a></li>
        <li class="nav-item tab_menu_btn"><a href="/" target="_self" class="tab_menu_btn3" >HOME</a></li>
    </ul>
</div>
</div>
</div>
<div class="tab_box_container tab_box_container">
    <div class="profile-content">
        <div class="tab-content p-0">
            <div class="tab-pane fade active show tab_box1 tab_box on big-box" id="profile-posts">
                <img class="empty-img" alt="" src="https://litebook-images.s3.ap-northeast-2.amazonaws.com/timeline-images/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA+2020-05-15+%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE+3.53.14.png">
            </div>
        </div>
    </div>
</div>
</div>
</div>
</div>
</div>
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script src="http://netdna.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script type="text/javascript">
</script>
</body>
</html>