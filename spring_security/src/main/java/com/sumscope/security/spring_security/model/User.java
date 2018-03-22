package com.sumscope.security.spring_security.model;

import lombok.Data;

import java.util.List;

/**
 * @author xuejian.sun
 * @date 2018/3/22
 */
@Data
public class User {

    private String id;

    private String userName;

    private String password;

    private List<Role> role;

    @Data
    class Role {

        private String id;

        private String name;
    }
}
