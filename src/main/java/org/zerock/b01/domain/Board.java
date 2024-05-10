package org.zerock.b01.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity     //엔터티 선언
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Board extends BaseEntity{

    @Id     //PK 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY)     //키 생성 전략
    /*
        IDENTITY : 데이터베이스에 위임 (AUTO_INCREMENT)
        SEQUENCE : 데이터베이스 시킨스 오브젝트를 사용 - @SequenceGenerator 필요함...
        TABLE : 키 생성을 테이블 사용. 모든 DB에서 사용 - @TableGenerator 필요함...
        AUTO : 방언에 따라서 자동 지정됨. 기본값
     */

    private Long bno;

    @Column(length = 500, nullable = false)     //컬럼의 길이와 null 허용여부 결정.
    private String title;

    @Column(length = 2000, nullable = false)
    private String content;

    @Column(length = 50, nullable = false)
    private String writer;

    //  Entity 내에서 변경 가능한 title, content 값을 수정하는 메서드...
    public void change(String title, String content){
        this.title = title;
        this.content = content;
    }
    
}
