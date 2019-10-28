package com.osu.dianping.service.impl;

import com.osu.dianping.dao.PersonInfoDao;
import com.osu.dianping.entity.PersonInfo;
import com.osu.dianping.service.PersonInfoService;
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
