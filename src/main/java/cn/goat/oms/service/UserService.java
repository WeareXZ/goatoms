package cn.goat.oms.service;

import cn.goat.oms.entity.User;
import cn.goat.oms.entity.dto.UserDTO;
import cn.goat.oms.entity.response.ResponseResult;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 */
public interface UserService extends IService<User> {

    /**
     * 注册
     * @param userDTO
     * @return
     */
    ResponseResult register(UserDTO userDTO);

    /**
     * 登录
     * @param userDTO
     * @return
     */
    ResponseResult login(UserDTO userDTO);

    /**
     * 密码查询
     * @param phone
     * @return
     */
    ResponseResult getUserPsw(String phone);
}
