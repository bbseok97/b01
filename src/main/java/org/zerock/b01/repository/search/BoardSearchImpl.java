package org.zerock.b01.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.b01.domain.Board;
import org.zerock.b01.domain.QBoard;
import org.zerock.b01.domain.QReply;
import org.zerock.b01.dto.BoardListReplyCountDTO;

import java.util.List;


/*
 *  BoardSearch를 상속받는 구현제
 */
public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch{

    public BoardSearchImpl(){
        super(Board.class); //쿼리 dsl 서퍼트에게 넘기는 것이다
    }

    @Override
    public Page<Board> search1(Pageable pageable) {

        // Q도메인을 이용한 쿼리 작성 및 테스트
        // Querydsl의 목적은 "타입" 기반으로 "코드"를 이용해서 JPQL 쿼리를 생성하고 실행한다...
        // Q도메인은 이 때에 코드를 만드는 대신 클래스가 Q도메인 클래스...

        // 1. Q도메인 객체 생성
        QBoard board = QBoard.board;

        // 2. Query 작성
        JPQLQuery<Board> query = from(board);   // select .. from board

        //  BooleanBuilder() 사용
        BooleanBuilder booleanBuilder = new BooleanBuilder();   // (

        booleanBuilder.or(board.title.contains("11"));          // title like ...
        booleanBuilder.or(board.content.contains("11"));        // content like ...

//      query.where(board.title.contains("1")); // where title like ...

        query.where(booleanBuilder);                            // )
        query.where(board.bno.gt(0L));      //bno.gt(0L) : bno > 0      ~가 ()보다 크다.
        // paging
        this.getQuerydsl().applyPagination(pageable, query);

        List<Board> title = query.fetch();      // JPQLQuery에 대한 실행

        long count = query.fetchCount();        // 쿼리 실행....

        return null;
    }

    @Override
    public Page<Board> searchAll(String[] types, String keyword, Pageable pageable) {

        //  1. QBoard 도메인 객체 생성
        QBoard board = QBoard.board;

        //  2. QL 작성...
        JPQLQuery<Board> query = from(board);   //  select ... from board

        if((types != null && types.length > 0) && keyword != null){
            //  검색 조건과 키워드가 있는 경우...

            BooleanBuilder booleanBuilder = new BooleanBuilder();   //(

            for(String type : types){
                switch (type){
                    case "t":
                        booleanBuilder.or(board.title.contains(keyword));   //title like concat('%', 'keyword', '%")
                        break;
                    case "c":
                        booleanBuilder.or(board.content.contains(keyword)); //content like concat('%', 'keyword', '%")
                        break;
                    case "w":
                        booleanBuilder.or(board.writer.contains(keyword));  //writer like concat('%', 'keyword', '%")
                        break;
                }
            }   //  for end

            query.where(booleanBuilder);    // )

        }   //if end

            //bno > 0
        query.where(board.bno.gt(0L));  //  조건 추가

        //  paging
        this.getQuerydsl().applyPagination(pageable, query);    //  limit(page, count)

        List<Board> list = query.fetch();

        long count = query.fetchCount();


        // 페이징 값에 대한 Page<T> 형식으로 반환 : Page<Board>
        //  PageImpl을 통해서 반환 : (list - 실제 목록 data, pageable, total - 전체 개수)
        return new PageImpl<>(list, pageable, count);
    }

    @Override
    public Page<BoardListReplyCountDTO> searchWithReplyCount(String[] types, String keyword, Pageable pageable) {

        QBoard board = QBoard.board;
        QReply reply = QReply.reply;    //QueryDsl 에 의해 만들어짐

        JPQLQuery<Board> query = from(board);       //select * from board
        query.leftJoin(reply).on(reply.board.eq(board));    //  select * from leftJoin reply on (Reply)board bno = (Board)bno. 인 경우를 확인함
        //reply.board 와 QBoard.board 가 같을 떄. (조건 지정)

        query.groupBy(board);   //게시물 당 처리....      //  group by

        if((types != null && types.length > 0) && keyword != null){     //  검색 조건
            BooleanBuilder booleanBuilder = new BooleanBuilder();   // (

            for(String type : types){
                switch (type){
                    case "t":
                        booleanBuilder.or(board.title.contains(keyword));   //title like concat('%', 'keyword', '%")
                        break;
                    case "c":
                        booleanBuilder.or(board.content.contains(keyword)); //content like concat('%', 'keyword', '%")
                        break;
                    case "w":
                        booleanBuilder.or(board.writer.contains(keyword));  //writer like concat('%', 'keyword', '%")
                        break;
                }
            }   //  for end

            query.where(booleanBuilder);    // )

        }   //if end

        //bno > 0
        query.where(board.bno.gt(0L));  //  조건 추가

        //  Projections.bean() -> JPQL 결과를 바로 DTO로 처리하는 기능 제공.
        //  QueryDsl도 마찬가지로 이런 기능을 제공합니다.
        JPQLQuery<BoardListReplyCountDTO> dtoQuery = query.select(Projections.bean
                (BoardListReplyCountDTO.class,
                        board.bno,
                        board.title,
                        board.writer,
                        board.regDate,
                        reply.count().as("replyCount")      //reply.count() - 기본값
                        // reply - 응답 게시물 개수
                ));

        this.getQuerydsl().applyPagination(pageable, dtoQuery);     //  페이징 작업
        List<BoardListReplyCountDTO> dtoList = dtoQuery.fetch();    //BoardListReplyCount -> dtoList 값을 넘겨주고 dtoList -> PageRequestDTO 전달

        long count = dtoQuery.fetchCount();

        return new PageImpl<>(dtoList, pageable, count);
    }
}