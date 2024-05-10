package org.zerock.b01.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.b01.domain.Reply;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.dto.ReplyDTO;
import org.zerock.b01.repository.ReplyRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;
    private final ModelMapper modelMapper;

    @Override
    public Long register(ReplyDTO replyDTO){    //등록

        Reply reply = modelMapper.map(replyDTO, Reply.class);
        reply.setBoard(replyDTO.getBno());

        Long rno = replyRepository.save(reply).getRno();
        log.info("생성된 reply : " + reply);
        log.info(reply.getBoard().getBno());

        return rno;
    }
    @Override
    public ReplyDTO read(Long rno){     //조회

        // Optional<T> - null이 올 수 있는 값을 감싸는 Wrapper 클래스
        // -> Optional 클래스 자체가 private final T value에 값을 저장하기 때문에 값이 null이더라도
        Optional<Reply> replyOptional = replyRepository.findById(rno);
        Reply reply = replyOptional.orElseThrow();
        ReplyDTO replyDTO = modelMapper.map(reply, ReplyDTO.class);
        log.info("read ReplyDTO : " + replyDTO);
        replyDTO.setBno(reply.getBoard().getBno());

        return replyDTO;

    }

    @Override
    public void modify(ReplyDTO replyDTO) {     //수정

        Optional<Reply> replyOptional = replyRepository.findById(replyDTO.getRno());
        Reply reply = replyOptional.orElseThrow();

        reply.changeText(replyDTO.getReplyText());  //댓글의 내용만 수정


        replyRepository.save(reply);

    }

    @Override
    public void remove(Long rno) {  //삭제

        replyRepository.deleteById(rno);

    }

    @Override
    public PageResponseDTO<ReplyDTO> getListOfBoard(Long bno, PageRequestDTO pageRequestDTO) {

        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() <= 0 ? 0 : pageRequestDTO.getPage() -1,
                pageRequestDTO.getSize(),
                Sort.by("rno").ascending());    //ascending - 오름차순(첫 댓글이 제일 상단), descending - 내림차순(최신글이 제일 상단)

        Page<Reply> result = replyRepository.listOfBoard(bno, pageable);

        List<ReplyDTO> dtoList = result.getContent().stream().map(reply -> {
            ReplyDTO replyDTO = modelMapper.map(reply, ReplyDTO.class);
            replyDTO.setBno(reply.getBoard().getBno());

            return replyDTO;
        }).collect(Collectors.toList());

//            log.info("Reply Total(ReplyServiceImpl)" + result.getTotalElements());

        return PageResponseDTO.<ReplyDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
    }
}
