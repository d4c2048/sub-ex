package com.lee.config.interceptor;

import cn.hutool.core.bean.BeanUtil;
import com.lee.utils.StudentHolder;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The type Login interceptor.
 */
@NoArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {
        if(BeanUtil.isEmpty(StudentHolder.getStudent())) {
            System.out.println("拦截了" + request.getRequestURI());
            response.sendRedirect("/login");
        }
        return true;
    }

}
