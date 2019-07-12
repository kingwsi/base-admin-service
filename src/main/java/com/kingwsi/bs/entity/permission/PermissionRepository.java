package com.kingwsi.bs.entity.permission;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Description: <br>
 * Comments Name: PermissionRepository<br>
 * Date: 2019/7/11 16:39<br>
 * Author: wangshu<br>
 */
@Repository
public interface PermissionRepository extends CrudRepository<Permission, String> {

}
