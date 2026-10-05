package trungduc.vn.services;

public interface IFavoriteService_24133012 {
    long countByVideoId(String videoId);
    boolean toggleLike(String username, String videoId);
    boolean isLiked(String username, String videoId);
}
