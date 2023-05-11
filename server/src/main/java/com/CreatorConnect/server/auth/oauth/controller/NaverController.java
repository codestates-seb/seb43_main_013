package com.CreatorConnect.server.auth.oauth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.security.SecureRandom;

@RestController
public class NaverController {
    private static final String CLIENT_ID = "kWyzXRG7eYBVviajn1kX";
    private static final String CLIENT_SECRET = "33pIJWiW3P";
    private static final String REDIRECT_URI = "http://localhost:8080/login/oauth2/code/naver";
    private static final String AUTHORIZATION_ENDPOINT = "https://nid.naver.com/oauth2.0/authorize";
    private static final String TOKEN_ENDPOINT = "https://nid.naver.com/oauth2.0/token";

    public String generateState() {
        SecureRandom random = new SecureRandom();
        return new BigInteger(130, random).toString(32);
    }

    private final String STATE = generateState();

    @GetMapping("/login/oauth2/naver")
    public String naverLogin(HttpSession session) {

        session.setAttribute("state", STATE);

        String authorizationRequestUrl = String.format("%s?response_type=code&client_id=%s&redirect_uri=%s&state=%s",
                AUTHORIZATION_ENDPOINT, CLIENT_ID, REDIRECT_URI, STATE);
        return String.format("<a href='%s'><img height='50' src='http://static.nid.naver.com/oauth/small_g_in.PNG'/></a>", authorizationRequestUrl);
    }

//    @GetMapping("/login/oauth2/code/naver")
//    public String naverCallback(@RequestParam("code") String code, @RequestParam("state") String state) throws IOException, InterruptedException {
//
//        if (!STATE.equals(state)) {
//            return "Invalid State";
//        }
//
//        String tokenRequestUrl = String.format("%s?grant_type=authorization_code&client_id=%s&client_secret=%s&redirect_uri=%s&code=%s",
//                TOKEN_ENDPOINT, CLIENT_ID, CLIENT_SECRET, REDIRECT_URI, code);
//
//        HttpClient client = HttpClient.newHttpClient();
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(tokenRequestUrl))
//                .header("X-Naver-Client-Id", CLIENT_ID)
//                .header("X-Naver-Client-Secret", CLIENT_SECRET)
//                .build();
//        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//
//        if (response.statusCode() == 200) {
//            return response.body();
//        } else {
//            return String.format("Error: %d", response.statusCode());
//        }
//    }

    @GetMapping("/login/oauth2/code/naver")
    public ResponseEntity naverCallback() {

        return new ResponseEntity(HttpStatus.OK);
    }

}
