package org.zerock.b01.dto;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardListReplyCountDTO {   //Reply : RESTful 방식이기 때문에 값을 처리할 DTO 가 필요.

    //  게시글에 댓글 숫자(개수) 값을 처리하는 DTO
    private Long bno;
    private String title;
    private String writer;
    private LocalDateTime regDate;

    private Long replyCount;    // 게시글 목록 응답 갯수


}
