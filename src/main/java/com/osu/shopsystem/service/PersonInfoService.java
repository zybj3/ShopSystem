package com.osu.shopsystem.service;

import com.osu.shopsystem.entity.PersonInfo;

public interface PersonInfoService {
    int addPersonInfo(PersonInfo personInfoCondition);
    int queryPersonInfoByUserName(String username);
    PersonInfo queryIfUserExist(String userName, String password);
}
