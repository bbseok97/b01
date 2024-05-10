package org.zerock.b01.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.dto.ReplyDTO;

@SpringBootTest
@Log4j2
public class ReplyServiceTests {

    @Autowired
    private ReplyService replyService;

    @Test
    public void testRegister(){

        ReplyDTO replyDTO = ReplyDTO.builder()
                .replyText("ReplyDTO Text")
                .replyer("replyer")
                .bno(201L)
                .build();

        log.info(replyService.register(replyDTO));
    }

    @Test
    public void testRead(){

        ReplyDTO replyDTO = replyService.read(100L);
        log.info(replyDTO);

    }

    @Test
    public void testGetListOfBoard(){
        Long bno = 201L;
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().build();
        PageResponseDTO<ReplyDTO> result = replyService.getListOfBoard(bno, pageRequestDTO);
//        log.info("토탈 값 확인: "+result.getTotal());
        result.getDtoList().forEach(replyDTO -> log.info(replyDTO));

    }

    @Test
    public void testDelete(){
        Long rno = 1000L;
        replyService.remove(rno);
    }

}
