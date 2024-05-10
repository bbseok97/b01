package org.zerock.b01.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

@Log4j2
public class Custom403Handler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {

        log.info("----------------------- ACCESS DENIED -------------------------");

        response.setStatus(HttpStatus.FORBIDDEN.value());

        //  JSON 요청이였는지 확인
        String contentType = request.getHeader("Content-Type");
        boolean jsonRequest = contentType.startsWith("application/json");       // content-Type에 시작값이 application/json 값인지 확인
        log.info("isJSON: " + jsonRequest);

        //  일반 Request인 경우
        if(!jsonRequest){
            response.sendRedirect("/member/login?error=ACCESS_DENIED");     //error 파라미터 값으로 ACCESS_DENIED 지정
        }

    }
}
