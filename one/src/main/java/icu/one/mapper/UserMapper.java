package icu.one.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import icu.one.entity.User;

@Mapper
public interface UserMapper {

    /**
     * 查询所有用户
     * @return 用户列表
     */
    List<User> selectAllUsers();

    /**
     * 根据角色查询用户
     * @param role 角色
     * @return 用户列表
     */
    List<User> getUsersByRole(@Param("role") String role);

    /**
     * 用户登录验证
     * @param username 用户名
     * @param password 密码
     * @return 用户信息
     */
    User login(@Param("username") String username, 
               @Param("password") String password);
}
