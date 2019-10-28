package com.osu.dianping.service;

import com.osu.dianping.entity.PersonInfo;

public interface PersonInfoService {
    int addPersonInfo(PersonInfo personInfoCondition);
    int queryPersonInfoByUserName(String username);
    PersonInfo queryIfUserExist(String userName, String password);
}
