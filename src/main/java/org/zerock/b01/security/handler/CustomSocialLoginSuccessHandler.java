package org.zerock.b01.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.zerock.b01.dto.MemberSecurityDTO;

import java.io.IOException;

@Log4j2
@RequiredArgsConstructor
public class CustomSocialLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final PasswordEncoder passwordEncoder;
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, 
                                        HttpServletResponse response, 
                                        Authentication authentication) throws IOException, ServletException {
        
        log.info("--------------------------------------");
        log.info("CustomSocialLoginSuccessHandler onAuthenticationSuccess");
        log.info(authentication.getPrincipal());

        MemberSecurityDTO memberSecurityDTO = (MemberSecurityDTO) authentication.getPrincipal();
        String encodePw = memberSecurityDTO.getMpw();
        
        //  소셜 로그인이고 회원 패스워드 1111
        if(memberSecurityDTO.isSocial() && (memberSecurityDTO.getMpw().equals("1111") 
                || passwordEncoder.matches("1111", memberSecurityDTO.getMpw()) //   matchers -> boolean 타입

        )){
            log.info("Should Change Password");
            log.info("Redirect to Member Modify");
            response.sendRedirect("/member/modify");

            return;
        }else {
            response.sendRedirect("/board/list");
        }


        
    }
}
