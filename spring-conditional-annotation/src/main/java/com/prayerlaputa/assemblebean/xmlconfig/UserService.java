package com.prayerlaputa.assemblebean.xmlconfig;

/**
 * chenglong.yu
 */
public class UserService {

    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void printService() {
        System.out.println("print Service....");

        userDao.printUserDao();
    }
}

