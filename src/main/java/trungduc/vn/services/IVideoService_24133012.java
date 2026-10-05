package trungduc.vn.services;

import java.util.List;
import trungduc.vn.entity.Video_24133012;

public interface IVideoService_24133012 {
    void insert(Video_24133012 video);
    void update(Video_24133012 video);
    void delete(String videoId) throws Exception;
    Video_24133012 findById(String videoId);
    List<Video_24133012> findAll();
    List<Video_24133012> findAll(int page, int pageSize);
    long countAll();
    List<Video_24133012> findByCategory(int categoryId, int page, int pageSize);
    long countByCategory(int categoryId);
    void incrementViews(String videoId);
    Video_24133012 findDetailById(String videoId);
    void populateLikesAndShares(List<Video_24133012> videos);
}
