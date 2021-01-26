package io.saidlaarab.businesslogicserver.config;

import io.saidlaarab.businesslogicserver.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthenticationServerProxy {
    @Autowired
    RestTemplate template;

    @Value("${auth.server.base.url}")
    private String baseUrl;

    public void sendAuth(String username, String password){
        String url = baseUrl + "/user/auth";

        User body = new User();
        body.setUsername(username);
        body.setPassword(password);

        HttpEntity<User> request = new HttpEntity<>(body);

        template.postForEntity(url, request, Void.class);
    }

    public boolean sendOTP(String username, String code){
        String url = baseUrl + "/otp/check";

        User body = new User();
        body.setUsername(username);
        body.setCode(code);

        HttpEntity<User> request = new HttpEntity<>(body);

        ResponseEntity<Void> response = template.postForEntity(url, request, Void.class);

        return response.getStatusCode().equals(HttpStatus.OK);
    }
}
