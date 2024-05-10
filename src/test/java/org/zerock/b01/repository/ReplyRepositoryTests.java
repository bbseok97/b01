package org.zerock.b01.repository;


import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.b01.domain.Board;
import org.zerock.b01.domain.Reply;

import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class ReplyRepositoryTests {

    @Autowired
    private ReplyRepository replyRepository;


    // 테스트 : 있는 게시글 중에 댓글 추가... 201번에 댓글 추가... (insert 처리...)

    @Test
    public void testInsert(){

        Long bno = 201L;

        Board board = Board.builder().bno(bno).build();     //Pk가 설정된 객체를 사용하겠다.

        IntStream.rangeClosed(1, 100).forEach( i -> {


        Reply reply = Reply.builder()
                .board(board)
                .replyText("댓글 테스트1....")
                .replyer("replyer"+i)
                .build();

        replyRepository.save(reply);   //있으면 수정, 없으면 등록
        });
    }

//    @Transactional  //  쿼리가 모두 성공해야 성공 처리...
    @Test
    public void testBoardReplies(){
        //  실제 게시물 번호
        Long bno = 201L;

        Pageable pageable = PageRequest.of(0, 10, Sort.by("rno").descending());

        Page<Reply> result = replyRepository.listOfBoard(bno, pageable);
//        log.info("게시물의 댓글 수: " + result.getTotalElements());

        result.getContent().forEach( reply -> {
            log.info(reply);
        });
    }

    @Test
    public void testDelete(){

        replyRepository.deleteById(103L);
    }

//    @Test
//    public void testTotal(){
//        log.info("count: "+replyRepository.count());
//    }



}
