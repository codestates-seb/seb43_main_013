package com.CreatorConnect.server.auth.jwt.refreshToken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public class CustomHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private final String newAccessToken;

    public CustomHttpServletRequestWrapper(HttpServletRequest request, String newAccessToken) {
        super(request);
        this.newAccessToken = newAccessToken;
    }

    @Override
    public String getHeader(String name) {
        if (name.equalsIgnoreCase("Authorization")) {
            return "Bearer " + newAccessToken;
        }
        return super.getHeader(name);
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        if (name.equalsIgnoreCase("Authorization")) {
            List<String> headers = new ArrayList<>();
            headers.add("Bearer " + newAccessToken);
            return Collections.enumeration(headers);
        }
        return super.getHeaders(name);
    }
}

