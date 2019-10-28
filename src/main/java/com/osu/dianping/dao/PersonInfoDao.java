package com.osu.dianping.dao;


import com.osu.dianping.entity.PersonInfo;
import org.apache.ibatis.annotations.Param;

public interface PersonInfoDao {
    int addPersonInfo(PersonInfo personInfoCondition);
    int queryPersonInfoByUserName(String username);
    PersonInfo queryIfUserExist(@Param("userName") String userName, @Param("password") String password);


}
