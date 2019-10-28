package com.osu.shopsystem.dao;


import com.osu.shopsystem.entity.PersonInfo;
import org.apache.ibatis.annotations.Param;

public interface PersonInfoDao {
    int addPersonInfo(PersonInfo personInfoCondition);
    int queryPersonInfoByUserName(String username);
    PersonInfo queryIfUserExist(@Param("userName") String userName, @Param("password") String password);


}
