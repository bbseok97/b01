package org.zerock.b01.domain;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "roleSet")
public class Member extends BaseEntity{

    @Id
    private String mid;

    private String mpw;

    private String email;
    
    private boolean del;

    //  열거형 처리... roleSet
    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<MemberRole> roleSet = new HashSet<>();

    //  소셜 로그인 처리
    private boolean social;     //true -> 소셜 정보 불러옴, false -> X

    //  이메일 변경
    public void changeEmail(String email){
        this.email = email;
    }

    //  패스워드 변경
    public void changePassword(String mpw){
        this.mpw = mpw;
    }

    //  삭제 여부 변경 가능
    public void changeDelete(boolean del){      //특정 기간동안 계정 삭제를 하지않고 보관
        this.del = del;
    }

    //  addRole 역할 추가
    public void addRole(MemberRole memberRole){
        this.roleSet.add(memberRole);
    }

    //  clearRole 역할 삭제
    public void clearRole(){
        this.roleSet.clear();
    }

    //  소셜 여부 변경을 위해
    public void changeSocial(boolean social){
        this.social = social;
    }


}
