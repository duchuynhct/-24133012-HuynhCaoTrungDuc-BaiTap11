package trungduc.vn.dao;

import java.util.List;
import trungduc.vn.entity.User_24133012;

public interface IUserDao_24133012 {
    void insert(User_24133012 user);
    void update(User_24133012 user);
    void delete(String username) throws Exception;
    User_24133012 findById(String username);
    User_24133012 findByEmail(String email);
    List<User_24133012> findAll();
}
