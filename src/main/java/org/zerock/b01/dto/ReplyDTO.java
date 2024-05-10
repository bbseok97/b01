package org.zerock.b01.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReplyDTO {


    private Long rno;   //특정한 번호의 댓글

    @NotNull
    private Long bno;   //특정한 게시물 번호

    @NotEmpty
    private String replyText;   //게시글 (응답내용)

    @NotEmpty
    private String replyer;     //작성자 (응답자)

    // Json이 문자열 형태이므로 날짜와 시간을 문자열로 formatting 설정
    //  날짜 형식 설정
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime regDate;

    @JsonIgnore     //formatting 제외
    private LocalDateTime modDate;


}
