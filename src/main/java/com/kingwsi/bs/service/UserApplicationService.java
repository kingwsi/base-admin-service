package com.kingwsi.bs.service;

import com.kingwsi.bs.entity.user.User;
import com.kingwsi.bs.entity.user.UserRepository;
import com.kingwsi.bs.entity.user.UserVO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Description: []
 * Name: UserApplicationService
 * Author: wangshu
 * Date: 2019/6/29 23:18
 */
@Service
public class UserApplicationService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User createUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        this.userRepository.save(user);
        return user;
    }

    public String createToken(UserVO vo) {
        Boolean pass = Optional.ofNullable(vo)
                .map(u -> userRepository.findByUsername(u.getUsername()))
                .map(resultUser -> bCryptPasswordEncoder.matches(vo.getPassword(), resultUser.getPassword())).orElse(false);
        if (pass) {
            return Jwts.builder()
                    .setSubject(vo.getUsername())
                    .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
                    .signWith(SignatureAlgorithm.HS512, "MyJwtSecret")
                    .compact();
        }
        throw new RuntimeException("密码错误或账号不存在！");
    }

    public User getCurrent(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        User user = Optional.ofNullable(token)
                .map(t -> Jwts.parser()
                        .setSigningKey("MyJwtSecret")
                        .parseClaimsJws(t.replace("Bearer ", ""))
                        .getBody()
                        .getSubject())
                .map(username -> userRepository.findByUsername(username)).orElse(null);
        if (user == null) {
            throw new RuntimeException("身份校验失败！");
        }
        return user;
    }
}