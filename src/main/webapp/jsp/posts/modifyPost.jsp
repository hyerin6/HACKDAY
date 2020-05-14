<c:forEach var="post" items="${ posts }" varStatus="vs">
    <li>
        <div class="timeline-icon"><a href="javascript:;">&nbsp</a></div>
        <div class="timeline-body block">
            <div class="timeline-header">
                    <span class="username">
                        <a href="/users/${user.userName}" class="profile-link">${user.userName}</a>
                    </span>
                <span class="date pull-right text-muted">${post.regDate}</span>
            </div>
            <div class="timeline-content">
                <img class="max-small" src="${post.image.filePath}" alt="" onerror="this.src='https://litebook-images.s3.ap-northeast-2.amazonaws.com/litebook/profile.jpeg'">
            </div><br/>
            <div class="timeline-content">
                <p class="post">${post.content}</p>
            </div>
            <div class="timeline-footer"></div>
            <div>
                <div class="modify-button">
                    <div class="input-group">
                            <span class="input-group-btn p-l-10">
                                <button class="btn-gradient blue mini" id="viewDetailButton${vs.index}" data-target="#layerpop${vs.index}" type="button" data-toggle="modal" style="margin-left: 15px;">update</button>
                                <form method="PATCH">
                                    <div class="modal fade" id="layerpop${vs.index}">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
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
                                                    <textarea path="content" class="form-control" rows="4">${post.content}</textarea><br/>
                                                </div>
                                                <div class="modal-footer">
                                                    <button class="btn-gradient blue mini" id = "update_btn" style="float: right; margin-top: 15px;">완료</button>
                                                    <button type="button" class="btn-gradient blue mini" data-dismiss="modal" style="margin-top: 14px; margin-left: 10px;">취소</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </form>
                            </span>
                        <span class="input-group-btn p-l-10">
                                <a href="/posts/${post.id}" class="btn-gradient blue mini" style="margin-left: 15px;">delete</a>
                            </span>
                    </div>
                </div>
            </div><br/><br/>
        </div>
    </li>
</c:forEach>
</ul>