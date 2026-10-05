package trungduc.vn.services;

import java.util.List;
import trungduc.vn.entity.User_24133012;

public interface IUserService_24133012 {
    User_24133012 login(String username, String password);
    boolean register(User_24133012 user);
    boolean activateUser(String username);
    User_24133012 findById(String username);
    User_24133012 findByEmail(String email);
    void update(User_24133012 user);
    List<User_24133012> findAll();
}
