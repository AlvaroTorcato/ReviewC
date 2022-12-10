package com.example.model.service;

import com.example.model.JWT;
import com.example.model.UserDetailsDTO;
import com.example.repository.JWTRepository;
import com.example.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Date;

@Service
public class JWTService {
    @Value("${sidis.secret}")
    private String secret;

    @Value("${sidis.password}")
    private String passwordSecret;

    @Autowired
    private JWTRepository jwtRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    RabbitMQSender rabbitMQSender;


    public UserDetailsDTO searchForUser(String jwt) {
        JWT find = jwtRepository.search(jwt);
        if (find == null){
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND, "JWT Not Found ");
        }
        Claims claims = Jwts.parser().setSigningKey(secret)
                .parseClaimsJws(jwt).getBody();
        UserDetailsDTO user = new UserDetailsDTO(
                claims.get("id",Integer.class),
                claims.get("username", String.class),
                claims.get("roles",String.class));
        Date expTime = claims.get("exp", Date.class);
        Instant now = Instant.now();
        Date date = Date.from(now);
        if (expTime.after(date)){
           new ResponseStatusException(HttpStatus.FORBIDDEN, "Expired token ");
        }
        return user;

    }
}
