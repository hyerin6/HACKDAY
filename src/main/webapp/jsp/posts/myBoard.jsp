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
                                <li class="nav-item tab_menu_btn on active show"><a href="/posts" target="_self" class="tab_menu_btn1 tab_menu_btn1 on active show" >MY POSTS</a></li>
                                <li class="nav-item tab_menu_btn"><a href="/timeline/feeds" target="_self" class="tab_menu_btn2" >TIME LINE</a></li>
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
                                    <li>
                                        <div class="timeline-body" style="padding-bottom: 10px;">
                                            <div class="form-group">
                                                <div class="panel-body timeline-comment-box" style="padding-top: 30px;">
                                                    <form:form method="post" modelAttribute="insertPostDto" action="/posts" enctype="multipart/form-data">
                                                    <form:textarea path="content" class="form-control" rows="4" placeholder="What are you thinking?" />
                                                    <form:errors path="content" class="error" />
                                                    <div class="mar-top clearfix">
                                                        <div class="form-inline form-group" style="padding-top: 15px;">
                                                            <form:label path="image" cssStyle="margin-left: 10px;">Image : </form:label>
                                                            <div class="col-sm-10">
                                                                <form:input type="file" class="form-control" placeholder="IMAGE" path="image" cssStyle="margin-left: 5px; padding-right: 10px; margin-right: 25px;"/>
                                                                <form:button class="btn-gradient blue mini" type="submit" onClick="window.location.reload()" cssStyle="float: right; margin-top: 2px;">Share</form:button>
                                                            </div>
                                                        </div>
                                                        </form:form>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </li><br/>
                                    <c:forEach var="post" items="${ posts }" varStatus="vs">
                                        <li>
                                            <div class="timeline-icon"><a href="javascript:;">&nbsp</a></div>
                                            <div class="timeline-body block">
                                                <div class="timeline-header">
                                                    <span class="username">${user.userName}</span>
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
                                                <div>
                                                    <div class="modify-button">
                                                        <div class="input-group">
                            <span class="input-group-btn p-l-10">
                                <button class="btn-gradient blue mini" type="button" id="viewDetailButton${vs.index}" data-target="#layerpop${vs.index}" data-toggle="modal" style="margin-left: 15px;">update</button>
                                <form method="PATCH">
                                    <div class="modal fade" id="layerpop${vs.index}">
                                        <div class="modal-dialog">
                                            <div class="modal-content" id="layerpop${vs.index}">
                                                <div class="modal-header">
                                                    <div class="modal-title">
                                                        <h3 style="margin-bottom: 20px;">update</h3>
                                                        <p>
                                                            수정을 원한다면 입력 후 '완료'를 누르세요. <br/>
                                                            '취소'를 누르면 이전 페이지로 돌아갑니다.
                                                        </p>
                                                    </div>
                                                    <button type="button" class="close" data-dismiss="modal">×</button>
                                                </div>
                                                <div class="modal-body"><br/>
                                                    <textarea id="mainText${vs.index}" path="content" class="form-control" rows="4">${post.content}</textarea><br/>
                                                </div>
                                                <div class="modal-footer" id="layerpop${vs.index}">
                                                    <input id="layerpop${vs.index}" class="btn-gradient blue mini" type="button" data-dismiss="modal" onClick="update_btn(${post.id}, $('#mainText'.concat(${vs.index})).val());" value="완료" style="float: right; margin-top: 15px;"/>
                                                    <button type="button" class="btn-gradient blue mini" data-dismiss="modal" style="margin-top: 14px; margin-left: 10px;">취소</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </form>
                            </span>
                                                            <span class="input-group-btn p-l-10" onClick="window.location.reload()">
                                <a href="/posts/${post.id}" class="btn-gradient blue mini" style="margin-left: 15px;">delete</a>
                            </span>
                                                        </div>
                                                    </div>
                                                </div><br/><br/>
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
    var minIdOfPosts = <c:out value="${minIdOfPosts}" />;
    var isLoading = false;
    var count = 4;

    $(window).scroll(function() {
        var window_height = window.innerHeight;
        if($(window).scrollTop() > 0 && !isLoading && lastIdOfPosts > minIdOfPosts) {
            if ($(window).scrollTop() == ($(document).height() - window_height)) {
                isLoading = true;
                $.ajax({
                    type: 'POST',
                    url: '/api/posts',
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
                        count += 5;
                        if(data.posts != null && data.posts.length != 0){
                            for(let i = 0; i < data.posts.length; ++i){
                                var imagePath = data.posts[i].image == null ? 'https://litebook-images.s3.ap-northeast-2.amazonaws.com/timeline-images/b.jpeg' : data.posts[i].image.filePath;
                                $(".posts").append(
                                    "<li>\n" +
                                    "<div class=\"timeline-icon\"><a href=\"javascript:;\">&nbsp</a></div>\n" +
                                    "<div class=\"timeline-body block\">\n" +
                                    "<div class=\"timeline-header\">\n" +
                                    "<span class=\"username\">" + "${user.userName}" + "</span>\n" +
                                    "<span class=\"date pull-right text-muted\">" + data.posts[i].regDate + "</span>\n" +
                                    "</div>\n" +
                                    "<div class=\"center-img\">\n" +
                                    "<div class=\"timeline-content max-small\">\n" +
                                    "<img class=\"max-small\" src=\"" + imagePath + "\" alt=\"\" onerror=\"this.src='https://litebook-images.s3.ap-northeast-2.amazonaws.com/timeline-images/b.jpeg'\">\n" +
                                    "</div><br/>\n" +
                                    "</div>" +
                                    "<div class=\"timeline-content\">\n" +
                                    "<p class=\"post\">" + data.posts[i].content + "</p>\n" +
                                    "</div>\n" +
                                    "<div class=\"timeline-footer\"></div>" +
                                    "<div>\n" +
                                    "<div class=\"modify-button\">\n" +
                                    "<div class=\"input-group\">\n" +
                                    "<span class=\"input-group-btn p-l-10\">\n" +
                                    "<button class=\"btn-gradient blue mini\" type=\"button\" id=\"viewDetailButton" +  count+i + "\" data-target=\"#layerpop" +  count+i + "\" data-toggle=\"modal\" style=\"margin-left: 15px;\">update</button>\n" +
                                    "<form method=\"PATCH\">\n" +
                                    "<div class=\"modal fade\" id=\"layerpop" + count+i + "\">\n" +
                                    "<div class=\"modal-dialog\">\n" +
                                    "<div class=\"modal-content\" id=\"layerpop" +  count+i + "\">\n" +
                                    "<div class=\"modal-header\">\n" +
                                    "<div class=\"modal-title\">\n" +
                                    "<h3 style=\"margin-bottom: 20px;\">update</h3>\n" +
                                    "<p>\n" +
                                    "수정을 원한다면 입력 후 '완료'를 누르세요. <br/>\n" +
                                    "'취소'를 누르면 이전 페이지로 돌아갑니다.\n" +
                                    "</p>\n" +
                                    "</div>\n" +
                                    "<button type=\"button\" class=\"close\" data-dismiss=\"modal\">×</button>\n" +
                                    "</div>\n" +
                                    "<div class=\"modal-body\"><br/>\n" +
                                    "<textarea id=\"mainText" + count+i + "\" path=\"content\" class=\"form-control\" rows=\"4\">" + data.posts[i].content + "</textarea><br/>\n" +
                                    "</div>\n" +
                                    "<div class=\"modal-footer\" id=\"layerpop" + count+i + "\">\n" +
                                    "<input id=\"layerpop" + count+i + "\" class=\"btn-gradient blue mini\" type=\"button\" data-dismiss=\"modal\" onClick=\"update_btn(" + data.posts[i].id + ", " + "$('#mainText'.concat(" + count+i + ")).val());" + "\" value=\"완료\" onClick=\"window.location.reload()\" style=\"float: right; margin-top: 15px;\"/>" +
                                    "<button type=\"button\" class=\"btn-gradient blue mini\" data-dismiss=\"modal\" style=\"margin-top: 14px; margin-left: 10px;\">취소</button>\n" +
                                    "</div>\n" +
                                    "</div>\n" +
                                    "</div>\n" +
                                    "</div>\n" +
                                    "</form>\n" +
                                    "</span>\n" +
                                    "<span class=\"input-group-btn p-l-10\">\n" +
                                    "<a href=\"/posts/" + data.posts[i].id + "\" class=\"btn-gradient blue mini\" onClick=\"window.location.reload()\" style=\"margin-left: 15px;\">delete</a>\n" +
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

    function update_btn(id, content) {
        $.ajax({
            type: 'PATCH',
            url: '/api/posts',
            headers: {
                "Content-Type": "application/json",
                "X-HTTP-Method-Override": "POST"
            },
            dataType: 'json',
            data: JSON.stringify({
                lastIdOfPosts: lastIdOfPosts,
                id: id,
                content: content
            }),
            success: function () {
                location.href = location.href;
            },
            error: function () {
                location.href = location.href;
            }
        });
    }
</script>
</body>
</html>