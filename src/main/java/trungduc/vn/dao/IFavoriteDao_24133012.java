package trungduc.vn.dao;

import trungduc.vn.entity.Favorite_24133012;

public interface IFavoriteDao_24133012 {
    long countByVideoId(String videoId);
    Favorite_24133012 findByUserAndVideo(String username, String videoId);
    void insert(Favorite_24133012 favorite);
    void delete(int favoriteId);
}
