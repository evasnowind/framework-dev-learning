package com.prayerlaputa.assemblebean.halfauto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * chenglong.yu
 */
@Service
public class HalfAutoUserService {

    @Autowired
    private HalfAutoUserDao halfAutoUserDao;

    public void setHalfAutoUserDao(HalfAutoUserDao halfAutoUserDao) {
        this.halfAutoUserDao = halfAutoUserDao;
    }

    public void printService() {
        System.out.println("HalfAutoUserService....");

        halfAutoUserDao.printUserDao();
    }
}

