package com.kingwsi.bs.entity.permission;

import com.kingwsi.bs.entity.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Description: 权限实体<br>
 * Comments Name: Permission<br>
 * Date: 2019/7/11 15:45<br>
 * Author: wangshu<br>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "permissions")
public class Permission extends BaseEntity {

    private String name;
    private String uri;
    private String method;

    public Permission(String method, String uri) {
        this.method = method;
        this.uri = uri;
    }
}
