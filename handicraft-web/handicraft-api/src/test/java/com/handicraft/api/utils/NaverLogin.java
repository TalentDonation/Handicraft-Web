package com.handicraft.api.utils;

public class NaverLogin {
    private String token;
    private String id;
    private String pw;
    private static NaverLogin NAVER_LOGIN;

    private NaverLogin(String id, String pw) {
    }

    public String getToken() {
        return token;
    }

    public static NaverLogin login(String id, String pw) {
        if(NAVER_LOGIN == null) {
            return new NaverLogin(id, pw);
        }

        return NAVER_LOGIN;
    }


}
