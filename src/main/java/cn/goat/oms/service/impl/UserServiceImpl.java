package cn.goat.oms.service.impl;

import cn.goat.oms.entity.dto.UserDTO;
import cn.goat.oms.entity.response.CustomException;
import cn.goat.oms.entity.response.ResponseResult;
import cn.goat.oms.uitls.AESUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.goat.oms.entity.User;
import cn.goat.oms.service.UserService;
import cn.goat.oms.mapper.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{

    @Autowired
    UserMapper userMapper;

    @Override
    public ResponseResult register(UserDTO userDTO) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("phone",userDTO.getPhone());
        User user = userMapper.selectOne(queryWrapper);
        if(null != user){
            throw new CustomException("该用户已经存在");
        }
        User u = new User();
        BeanUtils.copyProperties(u,userDTO);
        String password = u.getPassword();
        String encodingPsw = AESUtil.encodingPsw(password);
        u.setPassword(encodingPsw);
        userMapper.insert(u);
        return ResponseResult.SUCCESS();
    }
}




