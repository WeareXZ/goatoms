package cn.goat.oms.service;

import cn.goat.oms.entity.User;
import cn.goat.oms.entity.dto.UserDTO;
import cn.goat.oms.entity.response.ResponseResult;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 */
public interface UserService extends IService<User> {

    ResponseResult register(UserDTO userDTO);
}
