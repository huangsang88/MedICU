package icu.one.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import icu.one.entity.User;
import icu.one.mapper.UserMapper;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    /**
     * 查询所有用户
     * @return 用户列表
     */
    @GetMapping
    public ResponseEntity<List<User>> selectAllUsers() {
        List<User> users = userMapper.selectAllUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * 根据角色查询用户
     * @param role 角色
     * @return 用户列表
     */
    @GetMapping("/role/{role}")
    public ResponseEntity<List<User>> getUsersByRole(@PathVariable String role) {
        List<User> users = userMapper.getUsersByRole(role);
        return ResponseEntity.ok(users);
    }

    /**
     * 用户登录验证
     * @param username 用户名
     * @param password 密码
     * @return 用户信息
     */
    @PostMapping("/login")
    public ResponseEntity<User> login(
            @RequestParam String username,
            @RequestParam String password) {
        try {
            User user = userMapper.login(username, password);
            return user != null ? 
                ResponseEntity.ok(user) : 
                ResponseEntity.status(401).body(null);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
