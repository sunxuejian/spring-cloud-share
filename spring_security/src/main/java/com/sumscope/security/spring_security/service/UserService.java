package com.sumscope.security.spring_security.service;

import com.sumscope.security.spring_security.Utils.JsonUtil;
import com.sumscope.security.spring_security.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xuejian.sun
 * @date 2018/3/22
 */

@Slf4j
public class UserService implements UserDetailsService {

    private List<User> users;

    public UserService() {
        try {
             List<String> strs = Files.lines(Paths.get("user_manage"), Charset.defaultCharset())
                    .flatMap(line -> Arrays.stream(line.split("\n")))
                    .collect(Collectors.toList());
            users = strs.stream().map(str -> JsonUtil.readValue(str,User.class)).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(CollectionUtils.isEmpty(users)){
            log.error("user initialize error,user don't exist");
            return null;
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        users.get
        return null;
    }
}
