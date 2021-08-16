package cn.goat.oms.controller;

import cn.goat.oms.entity.dto.UserDTO;
import cn.goat.oms.entity.response.ResponseResult;
import cn.goat.oms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public ResponseResult register(@RequestBody UserDTO userDTO){
        ResponseResult register = userService.register(userDTO);
        return register;
    }

    @GetMapping("/login")
    public ResponseResult login(UserDTO userDTO){
        ResponseResult responseResult = userService.login(userDTO);
        return responseResult;
    }

    @GetMapping("/getUserPsw")
    public ResponseResult getPsw(String phone){
        return userService.getUserPsw(phone);
    }

}
