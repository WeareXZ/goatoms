package cn.goat.oms.service.impl;

import cn.goat.oms.config.Constant;
import cn.goat.oms.entity.dto.UserDTO;
import cn.goat.oms.entity.response.CommonCode;
import cn.goat.oms.entity.response.CustomException;
import cn.goat.oms.entity.response.ResponseResult;
import cn.goat.oms.uitls.EncryptUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.goat.oms.entity.User;
import cn.goat.oms.service.UserService;
import cn.goat.oms.mapper.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Optional;


/**
 *
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Override
    public ResponseResult register(UserDTO userDTO) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("phone",userDTO.getPhone());
        User user = userMapper.selectOne(queryWrapper);
        if(null != user){
            throw new CustomException("该手机号用户已经存在");
        }
        User u = new User();
        BeanUtils.copyProperties(userDTO,u);
        String password = u.getPassword();
        String encodingPsw = EncryptUtil.AESencode(password, Constant.AES);
        u.setPassword(encodingPsw);
        userMapper.insert(u);
        return ResponseResult.SUCCESS();
    }

    @Override
    public ResponseResult login(UserDTO userDTO) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("phone",userDTO.getPhone());
        User user = userMapper.selectOne(queryWrapper);
        if(!Optional.ofNullable(user).isPresent()){
            throw new CustomException("用户不存在!");
        }
        String password = EncryptUtil.AESdecode(user.getPassword(),Constant.AES);
        if(!password.equals(userDTO.getPassword())){
            throw new CustomException("用户账户或密码错误!");
        }
        if(user.getAbleFlag() == 0){
            throw new CustomException("该用户不可用");
        }
        return new ResponseResult(CommonCode.USER_LOGIN_SUCCESS,user);
    }

    @Override
    public ResponseResult getUserPsw(String phone) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("phone",phone);
        User user = userMapper.selectOne(queryWrapper);
        if(null == user){
            throw new CustomException("该用户不存在!");
        }
        String password = user.getPassword();
        password = EncryptUtil.AESdecode(password,Constant.AES);
        return ResponseResult.SUCCESS(password);
    }
}




