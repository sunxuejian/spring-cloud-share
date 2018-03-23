package com.sumscope.security.spring_security.service;

import com.sumscope.security.spring_security.Utils.JsonUtil;
import com.sumscope.security.spring_security.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.CollectionUtils;

import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author xuejian.sun
 * @date 2018/3/22
 */

@Slf4j
public class UserService implements UserDetailsService {

    private Map<String,User> users = new HashMap<>();

    public UserService() {
        try {
                URI uri = getClass().getClassLoader().getResource("user_manage.txt").toURI();
                List<String> strs = Files.lines(Paths.get(uri),Charset.defaultCharset())
                        .flatMap(line -> Arrays.stream(line.split("\n")))
                        .collect(Collectors.toList());
                for(String str : strs) {
                    User user = JsonUtil.readValue(str, User.class);
                    users.put(user.getUsername(), user);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(CollectionUtils.isEmpty(users) || !users.containsKey(username)){
            log.error("user initialize error,user don't exist");
            return null;
        }
        User user = users.get(username);
        return user;
    }
}
