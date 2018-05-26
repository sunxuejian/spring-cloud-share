package com.sumscope.security.spring_security.service;

import com.sumscope.security.spring_security.Utils.BeanUtil;
import com.sumscope.security.spring_security.Utils.JsonUtil;
import com.sumscope.security.spring_security.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.CollectionUtils;

import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author xuejian.sun
 * @date 2018/3/22
 */

@Slf4j
public class UserService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Map<String, User> users = new HashMap<>();

    /**
     * 类初始化时,将配置文件中的用户信息加载到内存当中
     * 这里我在配置文件中配置了两个用户其中一个账号被冻结
     */
    private void initialize() {
        try {
            URI uri = Thread.currentThread().getContextClassLoader().getResource("users.txt").toURI();
            users = Files.lines(Paths.get(uri), Charset.defaultCharset())
                    .flatMap(line -> Arrays.stream(line.split("\n")))
                    .map(str -> JsonUtil.readValue(str,User.class))
                    .collect(Collectors.toMap(User::getUsername,user -> user));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }


    /**
     * 根据登录用户名来匹配数据库用户信息
     * 因为在 WebSecurityConfiguration中配置了登录信息加密,我们此处存的用户密码并非是加密的，所以这里使用相同的加密技术做一次加密，
     * （实际业务开发中，我们在注册用户的时候吧用户密码加密存到数据库中，然后在登录加密配置中使用对称解密即可）
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(CollectionUtils.isEmpty(users) || !users.containsKey(username)) {
            log.error("user initialize error,user don't exist");
            return null;
        }
        User user = users.get(username);
        User copyUser = BeanUtil.cloneBean(user);
        copyUser.setPassword(passwordEncoder.encode(user.getPassword()));
        log.info("数据库用户信息：{}",copyUser);
        return copyUser;
    }
}
