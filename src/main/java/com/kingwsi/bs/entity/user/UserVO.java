package com.kingwsi.bs.entity.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description: []
 * Name: UserVO
 * Author: wangshu
 * Date: 2019/6/29 15:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {
    private String username;
    private String password;
    private Boolean remember;
}
