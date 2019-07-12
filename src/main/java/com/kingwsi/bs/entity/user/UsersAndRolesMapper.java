package com.kingwsi.bs.entity.user;

import com.kingwsi.bs.entity.role.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Description: mybatis持久层接口<br>
 * Comments Name: UsersAndRolesMapper<br>
 * Date: 2019/7/11 18:18<br>
 * Author: Administrator<br>
 */
@Component
@Mapper
public interface UsersAndRolesMapper {

    @Select("SELECT _r.* FROM roles _r LEFT JOIN users_and_roles _u_r ON _r.id = _u_r.role_id WHERE _u_r.user_id = #{id}")
    List<Role> findRolesByUserId(@Param("id") String id);

    @Select("SELECT _r.* FROM roles _r LEFT JOIN users_and_roles _u_r ON _r.id = _u_r.role_id LEFT JOIN users _u ON _u_r.user_id = _u.id WHERE _u.username = #{username}")
    List<Role> findRolesByUserName(@Param("username") String username);
}
