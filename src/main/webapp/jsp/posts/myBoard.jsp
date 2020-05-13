<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<c:url var="R" value="/" />
<link rel="stylesheet" href="${R}css/myBoard.css">
<!DOCTYPE html>
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
<div class="tab_box_container tab_box_container">
<!-- begin profile-content -->
<div class="profile-content">
    <!-- begin tab-content -->
    <div class="tab-content p-0">
        <!-- begin #profile-post tab -->
        <div class="tab-pane fade active show tab_box1 tab_box on big-box" id="profile-posts">
            <!-- begin timeline -->
            <ul class="timeline posts">
                <li>
                    <div class="timeline-body" style="padding-bottom: 10px;">
                    <div class="form-group">
                        <div class="panel-body timeline-comment-box" style="padding-top: 30px;">
                            <form:form method="post" modelAttribute="insertPostDto" action="/posts">
                                <form:textarea path="content" class="form-control" rows="4" placeholder="What are you thinking?" />
                                <div class="mar-top clearfix">
                                    <form:button class="btn-gradient blue mini" type="submit" style="float: right; margin-top: 15px;">Share</form:button>
                                </div>
                            </form:form>
                        </div>
                    </div>
                    </div>
                </li><br/>

                <c:forEach var="post" items="${ posts }">
                    <li>
                        <div class="timeline-icon"><a href="javascript:;">&nbsp</a></div>
                        <div class="timeline-body block">
                            <div class="timeline-header">
                                <span class="username"><a href="javascript:;">
                                    <!--<sec:authentication property="user.name" />--> hyerin </a>
                                </span>
                                <span class="date pull-right text-muted">${post.regDate}</span>
                            </div>
                            <div class="timeline-content">
                                <img class="max-small" src="${path}" alt="" onerror="this.src='https://litebook-images.s3.ap-northeast-2.amazonaws.com/litebook/profile.jpeg'">
                            </div><br/>
                            <div class="timeline-content">
                                <p class="post">${post.content}</p>
                            </div>
                            <div class="timeline-footer"></div>
                            <div>
                                <div class="modify-button">
                                    <div class="input-group">
                                        <span class="input-group-btn p-l-10">
                                            <button class="btn-gradient blue mini" type="button" style="margin-left: 15px;">delete</button>
                                        </span>
                                        <span class="input-group-btn p-l-10">
                                            <button class="btn-gradient blue mini" type="button" style="margin-left: 15px;">update</button>
                                        </span>
                                    </div>
                                </div>
                            </div><br/><br/>
                        </div>
                    </li>
                </c:forEach>
            </ul>
            <!-- end timeline-body -->
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
    var minIdOfPosts = 1;
    var isLoading = false;

    $(window).scroll(function() {
        var window_height = window.innerHeight; // 실제 화면 높이
        console.log("실제화면 높이 - " + window_height);
        console.log("$(document).height() - " + $(document).height());
        console.log("$(window).scrollTop() - " + $(window).scrollTop());

        if($(window).scrollTop() > 0) { // 스크롤을 내리는 중일 때 $(window).scrollTop() > 0 && !isLoading && lastIdOfPosts > minIdOfPosts
            if ($(window).scrollTop() == $(document).height() - window_height) {
                isLoading = true; // 로딩 시작
                $.ajax({
                    type: 'POST',
                    url: '/getPosts',
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
                                $(".posts").append(
                                    "<li>\n" +
                                    "<div class=\"timeline-icon\"><a href=\"javascript:;\">&nbsp</a></div>\n" +
                                    "<div class=\"timeline-body block\">\n" +
                                    "<div class=\"timeline-header\">\n" +
                                    "<span class=\"username\"><a href=\"javascript:;\">\n" +
                                    "<!--<sec:authentication property=\"user.name\" />--> hyerin </a>\n" +
                                    "</span>\n" +
                                    "<span class=\"date pull-right text-muted\">" + "${post.regDate}" + "</span>\n" +
                                    "</div>\n" +
                                    "<div class=\"timeline-content\">\n" +
                                    "<img class=\"max-small\" src=\"" + "${path}" + "\" alt=\"\" onerror=\"this.src='https://litebook-images.s3.ap-northeast-2.amazonaws.com/litebook/profile.jpeg'\">\n" +
                                    "</div><br/>\n" +
                                    "<div class=\"timeline-content\">\n" +
                                    "<p class=\"post\">" + "${post.content}" + "</p>\n" +
                                    "</div>\n" +
                                    "<div class=\"timeline-footer\"></div>\n" +
                                    "<div>\n" +
                                    "<div class=\"modify-button\">\n" +
                                    "<div class=\"input-group\">\n" +
                                    "<span class=\"input-group-btn p-l-10\">\n" +
                                    "<button class=\"btn-gradient blue mini\" type=\"button\" style=\"margin-left: 15px;\">delete</button>\n" +
                                    "</span>\n" +
                                    "<span class=\"input-group-btn p-l-10\">\n" +
                                    "<button class=\"btn-gradient blue mini\" type=\"button\" style=\"margin-left: 15px;\">update</button>\n" +
                                    "</span>\n" +
                                    "</div>\n" +
                                    "</div>\n" +
                                    "</div><br/><br/>\n" +
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

