package cn.goat.oms.config;

import cn.goat.oms.entity.response.CustomException;
import cn.goat.oms.uitls.CookieUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //从header中获取token
        String token = CookieUtils.getCookieValue(request, "uid");
        //token为空
        if (StringUtils.isBlank(token)) {
            throw new CustomException("登录信息不存在，请重新登录");
        }
        return true;
    }
}
