package org.zerock.b01.service;

import org.zerock.b01.dto.MemberJoinDTO;

public interface MemberService {
    
    static class MidExistException extends Exception{

        public MidExistException(){}

        public MidExistException(String msg){
            super(msg);
        }
    }
    
    void join(MemberJoinDTO member) throws MidExistException;   //동일 id가 존재할 때 발생할 예외
    
    
}
