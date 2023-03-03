//package com.example.rod.oauth2.service;
//
//import com.example.rod.auth.service.AuthService;
//import com.example.rod.security.details.UserDetailsImpl;
//import lombok.RequiredArgsConstructor;
//import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Component
//@RequiredArgsConstructor
//public class OAuth2AuthenticationSuccessHandlerImpl extends SimpleUrlAuthenticationSuccessHandler {
//
//    private final AuthService authService;
//
//    @Value("${endpoint.front}") private String ENDPOINT_FRONT;
//
//    @Override
//    public void oAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
//            throws IOException, ServletException {
//        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
//
//        int hash = user.hashCode();
//        authService.saveAuth(Auth.builder().email(user.getUsername()).hash(hash).build());
//
//        // 클라이언트에서 Query String을 이용하기위해서 다음처럼 URL을 구성해 준다.
//        String frontend = ENDPOINT_FRONT + "/oauthlogin?name=" + user.getUsername()
//                + "&role=" + user.getUser().getRole() + "&hash=" + hash + "&state=" + user.getUser().isState();
//
//        response.sendRedirect(frontend);
//    }
//}