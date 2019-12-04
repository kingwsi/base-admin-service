package com.kingwsi.bs.service;

import com.kingwsi.bs.entity.user.*;
import com.kingwsi.bs.exception.CustomException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
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

    @Autowired
    private UsersAndRolesMapper usersAndRolesMapper;

    public User createUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        this.userRepository.save(user);
        return user;
    }

    public String createToken(UserVO vo) {
        User user = Optional.ofNullable(vo)
                .map(u -> userRepository.findByUsername(u.getUsername())).filter(resultUser -> bCryptPasswordEncoder.matches(vo.getPassword(), resultUser.getPassword())).orElse(null);
        if (user != null) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("role", usersAndRolesMapper.findRoleIdsByUserId(user.getId()));
            map.put("user", user.getUsername());
            return Jwts.builder()
                    .setClaims(map)
                    .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
                    .signWith(SignatureAlgorithm.HS512, "MyJwtSecret")
                    .compact();
        }
        throw new CustomException("密码错误或账号不存在！");
    }

    public User getCurrentUser(HttpServletRequest request) {
        User user = Optional.ofNullable(request.getHeader("Authorization"))
                .map(token -> Jwts.parser()
                        .setSigningKey("MyJwtSecret")
                        .parseClaimsJws(token)
                        .getBody()
                        .get("user").toString())
                .map(username -> userRepository.findByUsername(username)).orElse(null);
        if (user == null) {
            throw new CustomException("身份校验失败！");
        }
        return user;
    }
}
