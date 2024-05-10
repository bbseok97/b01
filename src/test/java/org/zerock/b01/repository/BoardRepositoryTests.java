package org.zerock.b01.repository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.b01.domain.Board;
import org.zerock.b01.dto.BoardListReplyCountDTO;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Log4j2
@SpringBootTest
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;

    //insert
    @Test
    public void testInsert(){
        IntStream.range(0,100).forEach(i -> {
            Board board = Board.builder()
                    .title("Title....."+i)
                    .content("content........"+i)
                    .writer("user"+(i%10))  //  10명의 사용자 (0 ~ 9)
                    .build();

            Board result = boardRepository.save(board);
            log.info(" BNO : " +result.getBno());
        });
    }

    @Test
    public void testSelect() {
        Long bno = 100L;

        Optional<Board> result = boardRepository.findById(bno);

        Board board = result.orElseThrow();

        log.info(board);
    }

    //  update
    //  Entity는 생성 시 불변이면 좋으나, 변경이 일어날 경우 최소한으로 설계...
    @Test
    public void testUpdate(){
        Long bno = 100L;

        Optional<Board> result = boardRepository.findById(bno);     //bno를 찾고

        Board board = result.orElseThrow();     //  orElseThrow() : Optional에서 원하는 객체를 불러오거나, 예외 처리.

        board.change("update...Title1000", "update content.......100");

        boardRepository.save(board);        //  save 전에 내용이 있는지 한번 더 확인.
    }

    // delete
    @Test
    public void testDelete(){
        Long bno = 1L;      // @Id 값 존재 여부 확인

        boardRepository.deleteById(bno);    //동일한 값이 있을경우 delete 실행
    }

    //  Pageable, Page<E> 타입을 이용한 페이징 처리
    //  페이징 처리는 Pageable 타입의 객체를 구성해서 파라미터로 전달...
    //  Pageable :  인터페이스로 설계되어있다. PageRequest.of()를 이용해서 개발함.
    //  PageRequest.of(페이지 번호, 사이즈) : 페이지 번호는 0번부터...
    //  PageRequest.of(페이지 번호, 사이즈, Sort) : Sort 객체를 통한 정렬 조건 추가.
    //  PageRequest.of(페이지 번호, 사이즈, Sort.Direction, 속성) : 정렬 방향과 여러 속성 추가 지정

    //  Pageable로 값을 넘기면 반환타입은 Page<T>를 이용하게 됨.

    @Test
    public void testPaging(){   //PageResponseDTO와 비슷하다.
        //  1. page order by bno desc
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());

        boardRepository.findAll(pageable);
        Page<Board> result = boardRepository.findAll(pageable);

        log.info(" total count : "+result.getTotalElements());
        log.info(" total page : " +result.getTotalPages());
        log.info(" page number : " + result.getNumber());
        log.info(" page size : " + result.getSize());
        log.info(" page prev : " + result.hasPrevious());
        log.info(" page next : " + result.hasNext());

       List<Board> boardList = result.getContent();
       boardList.forEach(board -> log.info(board));
    }

    //  쿼리 메서드 및 @Query 테스트
    @Test
    public void testQueryMethod(){

        Pageable pageable = PageRequest.of(0, 10);

        String keyword = "title";

        Page<Board> result = boardRepository.findByTitleContainingOrderByBnoDesc(
                keyword,
                pageable
        );
        result.getContent().forEach(board -> log.info(board));
    }

    @Test
    public void testQueryAnnotation(){

        Pageable pageable = PageRequest.of(
                0,
                10,
                Sort.by("bno").descending());

        Page<Board> result = boardRepository.findKeyword("title", pageable);

        result.getContent().forEach(board -> log.info(board));
    }

    @Test
    public void testGetTime(){

        log.info(boardRepository.getTime());
    }

    @Test
    public void testSearch1(){
        //  2. page order by bno desc
        Pageable pageable = PageRequest.of(1, 10, Sort.by("bno").descending());

        boardRepository.search1(pageable);
    }

    @Test
    public void testSearchAll(){

        String[] types = {"t", "c", "w"};

        String keyword = "1";

        Pageable pageable = PageRequest.of(1, 10, Sort.by("bno").descending());

         Page<Board> result = boardRepository.searchAll(types, keyword, pageable);

         result.getContent().forEach(board -> log.info(board));
         log.info(" 페이지 사이즈 : "+result.getSize());
         log.info(" 페이지 번호 : "+result.getNumber());
         log.info(" 이전 페이지 "+result.hasPrevious());
         log.info(" 다음 페이지"+result.hasNext());

    }

    @Test
    public void testSearchWIthReplyCount(){

        String[] types = {"t", "c", "w"};

        String keyword = "테스트";

        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());

        Page<BoardListReplyCountDTO> result = boardRepository.searchWithReplyCount(types, keyword, pageable);

        result.getContent().forEach(board -> log.info(board));

    }

}
