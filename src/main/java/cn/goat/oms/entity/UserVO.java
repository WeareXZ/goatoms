package cn.goat.oms.entity;

import lombok.Data;

@Data
public class UserVO extends User {

    private String sessionId;
}
