package com.kingwsi.bs.api;

import com.kingwsi.bs.entity.user.User;
import com.kingwsi.bs.service.UserApplicationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Description: []
 * Name: UserController
 * Author: wangshu
 * Date: 2019/6/29 23:33
 */
@Api(tags = "用户相关接口")
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserApplicationService userApplicationService;

    @ApiOperation("创建用户")
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userApplicationService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }
}
