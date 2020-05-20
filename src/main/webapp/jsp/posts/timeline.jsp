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
    <style> .error { color: red; }</style>
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
                                <li class="nav-item tab_menu_btn"><a href="/posts" target="_self" class="tab_menu_btn1 tab_menu_btn1" >MY POSTS</a></li>
                                <li class="nav-item tab_menu_btn on active show"><a href="/timeline/feeds" target="_self" class="tab_menu_btn2" >TIME LINE</a></li>
                                <li class="nav-item tab_menu_btn"><a href="/" target="_self" class="tab_menu_btn3" >HOME</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="tab_box_container tab_box_container">
                    <div class="profile-content">
                        <div class="tab-content p-0">
                            <div class="tab-pane fade active show tab_box1 tab_box on big-box" id="profile-posts">
                                <ul class="timeline posts">
                                    <c:forEach var="post" items="${ posts }" varStatus="vs">
                                        <li>
                                            <div class="timeline-icon"><a href="javascript:;">&nbsp</a></div>
                                            <div class="timeline-body block">
                                                <div class="timeline-header">
                                                    <span class="username">${post.user.userName}</span>
                                                    <span class="date pull-right text-muted">${post.regDate}</span>
                                                </div>
                                                <div class="center-img">
                                                    <div class="timeline-content max-small">
                                                        <img class="max-small" src="${post.image.filePath}" alt="" onerror="this.src='https://litebook-images.s3.ap-northeast-2.amazonaws.com/timeline-images/b.jpeg'">
                                                    </div><br/>
                                                </div>
                                                <div class="timeline-content">
                                                    <p class="post">${post.content}</p>
                                                </div>
                                                <div class="timeline-footer"></div>
                                            </div>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </div>
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

    var lastIdOfPosts = <c:out value="${lastIdOfPosts}" />;
    var minIdOfSubsPosts = <c:out value="${minIdOfSubsPosts}" />;
    var isLoading = false;

    $(window).scroll(function() {
        var window_height = window.innerHeight;
        if($(window).scrollTop() > 0 && !isLoading && lastIdOfPosts > minIdOfSubsPosts) {
            if ($(window).scrollTop() == ($(document).height() - window_height)) {
                isLoading = true;

                $.ajax({
                    type: 'POST',
                    url: '/api/timeline/feeds',
                    headers: {
                        "Content-Type": "application/json",
                        "X-HTTP-Method-Override": "POST"
                    },
                    dataType: 'json',
                    data: JSON.stringify({
                        lastIdOfPosts: lastIdOfPosts
                    }),
                    success: function (data) {
                        console.log(data);
                        lastIdOfPosts = data.lastIdOfPosts;
                        if(data.posts != null && data.posts.length != 0){
                            for(let i = 0; i < data.posts.length; ++i){
                                var imagePath = data.posts[i].image == null ? 'https://litebook-images.s3.ap-northeast-2.amazonaws.com/timeline-images/b.jpeg' : data.posts[i].image.filePath;

                                $(".posts").append(
                                    "<li>\n" +
                                    "<div class=\"timeline-icon\"><a href=\"javascript:;\">&nbsp</a></div>\n" +
                                    "<div class=\"timeline-body block\">\n" +
                                    "<div class=\"timeline-header\">\n" +
                                    "<span class=\"username\">" + data.posts[i].user.userName + "</span>\n" +
                                    "<span class=\"date pull-right text-muted\">" + data.posts[i].regDate + "</span>\n" +
                                    "</div>\n" +
                                    "<div class=\"center-img\">\n" +
                                    "<div class=\"timeline-content max-small\">\n" +
                                    "<img class=\"max-small\" src=\"" + imagePath + "\" alt=\"\" onerror=\"this.src='https://litebook-images.s3.ap-northeast-2.amazonaws.com/timeline-images/b.jpeg'\">\n" +
                                    "</div><br/>" +
                                    "</div>" +
                                    "<div class=\"timeline-content\">\n" +
                                    "<p class=\"post\">" + data.posts[i].content + "</p>\n" +
                                    "</div>\n" +
                                    "<div class=\"timeline-footer\"></div>\n" +
                                    "</div>\n" +
                                    "</li>"
                                )
                            }
                        }
                        isLoading = false;
                    },
                    error: function(request, status, error){
                        this.isLoading = false;
                        alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" +  "error:" + error);
                    }
                });
            }
        }
    });

</script>
</body>
</html>