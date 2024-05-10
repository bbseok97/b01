package org.zerock.b01.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.b01.domain.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {
    
    //  소셜 사용하지 않는 사용자의 권한 로딩 (소셜 정보를 배제한 나머지를 작업하기 위한 설정)
    @EntityGraph(attributePaths = "roleSet")    // join을 위해서 사용한 어노테이션...
    @Query("select m from Member m where m.mid = :mid and m.social = false")    //  JPQL Query 작성
    Optional<Member> getWithRoles(String mid);

    //  Email을 이용한 회원정보 확인
    @EntityGraph(attributePaths = "roleSet")
    Optional<Member> findByEmail(String email);

    @Modifying  //  이 어노테이션을 사용하면 @Query에서 DML(Insert/UPDATE/DELETE)처리를 가능하게 함
    @Transactional
    @Query("update Member m set m.mpw = :mpw where m.mid = :mid")
    void updatePassword(@Param("mpw") String password, @Param("mid") String mid);

}
