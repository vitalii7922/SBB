package com.tsystems.javaschool.vm.service;

import com.tsystems.javaschool.vm.dao.UserDAO;
import com.tsystems.javaschool.vm.domain.User;
import com.tsystems.javaschool.vm.sub.Hasher;

import java.util.AbstractMap;
import java.util.Map;

public class UserService {
    UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public Map.Entry<Long, String> checkLoginAndCreateSession(String login, String password) {
        User user = userDAO.findByLogin(login);
        if (user != null) {
            String hash = Hasher.md5(password).substring(0, 16).toUpperCase();
            if (hash.equals(user.getPassword())) {
                Map.Entry<Long, String> entry = new AbstractMap.SimpleEntry<Long, String>(getRandomLong(), login);
                return entry;
            }
        } else {
            return null;
        }
        return null;
    }

    public Long getRandomLong() {
        return ((long) (Math.random() * Long.MAX_VALUE));
    }
}
