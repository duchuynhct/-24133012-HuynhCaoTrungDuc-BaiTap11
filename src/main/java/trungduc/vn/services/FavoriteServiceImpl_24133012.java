package trungduc.vn.services;

import java.util.Date;

import trungduc.vn.dao.FavoriteDaoImpl_24133012;
import trungduc.vn.dao.IFavoriteDao_24133012;
import trungduc.vn.dao.IUserDao_24133012;
import trungduc.vn.dao.IVideoDao_24133012;
import trungduc.vn.dao.UserDaoImpl_24133012;
import trungduc.vn.dao.VideoDaoImpl_24133012;
import trungduc.vn.entity.Favorite_24133012;
import trungduc.vn.entity.User_24133012;
import trungduc.vn.entity.Video_24133012;

public class FavoriteServiceImpl_24133012 implements IFavoriteService_24133012 {

    private IFavoriteDao_24133012 favoriteDao = new FavoriteDaoImpl_24133012();
    private IUserDao_24133012 userDao = new UserDaoImpl_24133012();
    private IVideoDao_24133012 videoDao = new VideoDaoImpl_24133012();

    @Override
    public long countByVideoId(String videoId) {
        return favoriteDao.countByVideoId(videoId);
    }

    @Override
    public boolean toggleLike(String username, String videoId) {
        Favorite_24133012 existing = favoriteDao.findByUserAndVideo(username, videoId);
        if (existing != null) {
            favoriteDao.delete(existing.getFavoriteId());
            return false; // Đã bỏ like
        } else {
            User_24133012 user = userDao.findById(username);
            Video_24133012 video = videoDao.findById(videoId);
            if (user != null && video != null) {
                Favorite_24133012 fav = new Favorite_24133012(new Date(), video, user);
                favoriteDao.insert(fav);
                return true; // Đã like thành công
            }
        }
        return false;
    }

    @Override
    public boolean isLiked(String username, String videoId) {
        return favoriteDao.findByUserAndVideo(username, videoId) != null;
    }
}
