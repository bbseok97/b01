package org.zerock.b01.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.b01.dto.BoardDTO;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;

import java.util.NoSuchElementException;

@Log4j2
@SpringBootTest
public class BoardServiceTests {

    @Autowired
    private BoardService boardService;

    @Test
    public void testRegister(){
        log.info(boardService.getClass().getName());    //BoardServiceImpl register()가 잘 동작하는지 테스트.

        BoardDTO boardDTO = BoardDTO.builder()
                .title("SampleTest....")
                .content("SampleContent...")
                .writer("user1")
                .build();

            long bno = boardService.register(boardDTO);
            log.info("bno= "+bno);

    }

    @Test
    public void testReadOne(){
        long bno = 101L;

        BoardDTO boardDTO = boardService.readOne(bno);
        log.info(boardDTO);
    }

    @Test
    public void testModify(){   //변경에 필요한 데이터만 처리.

        BoardDTO boardDTO = BoardDTO.builder()
                .bno(101L)
                .title("update..... 101")
                .content("update Content.... 101")
                .build();

        boardService.modify(boardDTO);
        //변경된 데이터 확인.

        log.info(boardService.readOne(boardDTO.getBno()));
    }

    @Test
    public void testRemove(){

        long bno = 101L;

        boardService.remove(bno);
//        BoardDTO boardDTO = boardService.readOne(bno);
//        log.info(boardDTO);

        Assertions.assertThrows(NoSuchElementException.class,       //예외 발생시 반환.
                                () -> boardService.readOne(bno));
    }

    @Test
    public void testList(){
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .type("tcw")
                .keyword("1")
                .page(1)
                .size(10)
                .build();

        PageResponseDTO<BoardDTO> responseDTO = boardService.list(pageRequestDTO);
        log.info(responseDTO);
    }
}
