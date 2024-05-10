package org.zerock.b01.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/*
    baseEntity 설정할 내용 :  모든 테이블에서 공통적으로 사용하는 컬럼을 작성.
    @MappedSuperClass를 사용하여 공통 속성 적용 !!
    이를 통해서 공통으로 사용되는 컬럼을 지정하여 해당 클래스를 상속하여 사용할 수 있음.
 */
@MappedSuperclass
@EntityListeners(value = {AuditingEntityListener.class})
@Getter
abstract class BaseEntity {

    //  시간 설정 - 등록, 수정 시간 설정...
    //  등록.
    @CreatedDate
    @Column(name = "regdate", updatable = false)    //컬럼명을 정해주고, updatable이 false일 경우 변경 불가.
    private LocalDateTime regDate;

    //  수정.
    @LastModifiedDate
    @Column(name = "moddate")
    private LocalDateTime modDate;

}
