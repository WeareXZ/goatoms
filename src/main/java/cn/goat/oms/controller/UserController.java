package cn.goat.oms.controller;

import cn.goat.oms.entity.User;
import cn.goat.oms.entity.UserVO;
import cn.goat.oms.entity.dto.UserDTO;
import cn.goat.oms.entity.response.ResponseResult;
import cn.goat.oms.service.UserService;
import cn.goat.oms.uitls.CookieUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;

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
    public ResponseResult login(UserDTO userDTO, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        ResponseResult<User> responseResult = userService.login(userDTO);
        HttpSession session = httpServletRequest.getSession();
        session.setAttribute("userName",responseResult.getData().getUserName());
        session.setMaxInactiveInterval(600);
        String id = responseResult.getData().getUserId().toString();
        CookieUtils.setCookie(httpServletRequest,httpServletResponse,"uid",id);
        return responseResult;
    }

    @GetMapping("/loginOut")
    public ResponseResult loginOut(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        httpServletRequest.getSession().invalidate();
        CookieUtils.deleteCookie(httpServletRequest,httpServletResponse,"uid");
        return ResponseResult.SUCCESS();
    }

    @GetMapping("/getUserPsw")
    public ResponseResult getPsw(String phone){
        return userService.getUserPsw(phone);
    }

}
