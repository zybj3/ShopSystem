package com.osu.dianping.dao;

import com.osu.dianping.BaseTest;
import com.osu.dianping.entity.PersonInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;
import java.util.Date;
import java.util.PriorityQueue;

public class PersonInfoDaoTest extends BaseTest {

    @Autowired
    PersonInfoDao personInfoDao;

    @Test
    public void testAddPersonInfo(){
        PersonInfo personInfo = new PersonInfo();
        personInfo.setName("test add person");
        personInfo.setCreateTime(new Date());
        personInfo.setLastEditTime(new Date());
        personInfo.setPassword("PASS");
        personInfo.setEmail("test email");
        personInfo.setGender("male");
        personInfo.setUserType(1);
        int effectiveNum = personInfoDao.addPersonInfo(personInfo);
        System.out.println(effectiveNum);
    }

    @Test
    public void testQueryPersonInfoByUserName(){
        String userName = "G01 person";
        int count = personInfoDao.queryPersonInfoByUserName(userName);
        System.out.println(count);

    }

    @Test
    public void testQueryIfUserExist(){
        String userName = "G01 person";
        String password = "123088";
        PersonInfo personInfo= personInfoDao.queryIfUserExist(userName, password);
        System.out.println(personInfo);
    }


}
