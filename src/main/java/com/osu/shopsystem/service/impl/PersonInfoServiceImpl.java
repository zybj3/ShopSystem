package com.osu.shopsystem.service.impl;

import com.osu.shopsystem.dao.PersonInfoDao;
import com.osu.shopsystem.entity.PersonInfo;
import com.osu.shopsystem.service.PersonInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PersonInfoServiceImpl implements PersonInfoService {

    @Autowired
    private PersonInfoDao personInfoDao;

    @Override
    public int addPersonInfo(PersonInfo personInfoCondition) {
        int effectiveNum = 0;
        try {
            effectiveNum = personInfoDao.addPersonInfo(personInfoCondition);

        }catch (RuntimeException e){
            e.printStackTrace();
        }

        return effectiveNum;
    }

    @Override
    public int queryPersonInfoByUserName(String username) {
        return personInfoDao.queryPersonInfoByUserName(username);
    }

    @Override
    public PersonInfo queryIfUserExist(String userName, String password) {
        return personInfoDao.queryIfUserExist(userName, password);
    }
}
