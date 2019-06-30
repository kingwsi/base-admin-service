package com.kingwsi.bs.entity.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Description: 用户<br>
 * Comments Name: User<br>
 * Date: 2019/6/28 18:20<br>
 * Author: Administrator<br>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User
{
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @GeneratedValue(generator = "uuid")
    private String id;

    @Column(name = "username",unique = true)
    private String username;

    private String password;

    private String fullName;
}
