package trungduc.vn.services;

import java.util.List;

import trungduc.vn.dao.IUserDao_24133012;
import trungduc.vn.dao.UserDaoImpl_24133012;
import trungduc.vn.entity.User_24133012;

public class UserServiceImpl_24133012 implements IUserService_24133012 {

    private IUserDao_24133012 userDao = new UserDaoImpl_24133012();

    @Override
    public User_24133012 login(String username, String password) {
        User_24133012 user = userDao.findById(username);
        if (user != null && user.getPassword().equals(password) && user.isActive()) {
            return user;
        }
        return null;
    }

    @Override
    public boolean register(User_24133012 user) {
        if (userDao.findById(user.getUsername()) != null) {
            return false; // Đã tồn tại username
        }
        if (user.getEmail() != null && userDao.findByEmail(user.getEmail()) != null) {
            return false; // Đã tồn tại email
        }
        userDao.insert(user);
        return true;
    }

    @Override
    public boolean activateUser(String username) {
        User_24133012 user = userDao.findById(username);
        if (user != null) {
            user.setActive(true);
            userDao.update(user);
            return true;
        }
        return false;
    }

    @Override
    public User_24133012 findById(String username) {
        return userDao.findById(username);
    }

    @Override
    public User_24133012 findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public void update(User_24133012 user) {
        userDao.update(user);
    }

    @Override
    public List<User_24133012> findAll() {
        return userDao.findAll();
    }
}
