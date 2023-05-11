package com.CreatorConnect.server.auth.oauth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
public class KakaoController {
    private static final String CLIENT_ID = "e6722e9848c6db656a44edaf1bbe8f09";
    private static final String CLIENT_SECRET = "KEHIiQRBd5UCjSfqaz7jFJXofxdAdd0Q";
    private static final String REDIRECT_URI = "http://localhost:8080/login/oauth2/code/kakao";
    private static final String AUTHORIZATION_ENDPOINT = "https://kauth.kakao.com/oauth/authorize";

    @GetMapping("/login/oauth2/kakao")
    @ResponseBody
    public String kakaoLogin(HttpSession session) {

        String authorizationRequestUrl = String.format("%s?response_type=code&client_id=%s&redirect_uri=%s",
                AUTHORIZATION_ENDPOINT, CLIENT_ID, REDIRECT_URI);
        return String.format("<a href='%s'><img height='50' src='http://static.nid.naver.com/oauth/small_g_in.PNG'/></a>",
                authorizationRequestUrl);
    }

//    @GetMapping("/login/oauth2/code/kakao")
//    public ResponseEntity kakaoLogin(@RequestParam("code") String code, RedirectAttributes ra,
//                                     HttpSession session, HttpServletResponse response, Model model) throws IOException {
//
//        System.out.println("kakao code:" + code);
//        return new ResponseEntity(HttpStatus.OK);
//    }

    @GetMapping("/login")
    public ResponseEntity login() throws IOException {

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/login/oauth2/code/kakao")
    public ResponseEntity kakaoCallback() {

        return new ResponseEntity(HttpStatus.OK);
    }

}

