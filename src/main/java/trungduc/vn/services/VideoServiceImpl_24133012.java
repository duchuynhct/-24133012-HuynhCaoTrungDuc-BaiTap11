package trungduc.vn.services;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import jakarta.persistence.EntityManager;
import trungduc.vn.configs.JpaConfig_24133012;
import trungduc.vn.dao.FavoriteDaoImpl_24133012;
import trungduc.vn.dao.IFavoriteDao_24133012;
import trungduc.vn.dao.IShareDao_24133012;
import trungduc.vn.dao.IVideoDao_24133012;
import trungduc.vn.dao.ShareDaoImpl_24133012;
import trungduc.vn.dao.VideoDaoImpl_24133012;
import trungduc.vn.entity.Video_24133012;

public class VideoServiceImpl_24133012 implements IVideoService_24133012 {

    private IVideoDao_24133012 videoDao = new VideoDaoImpl_24133012();
    private IFavoriteDao_24133012 favoriteDao = new FavoriteDaoImpl_24133012();
    private IShareDao_24133012 shareDao = new ShareDaoImpl_24133012();

    @Override
    public void insert(Video_24133012 video) {
        videoDao.insert(video);
    }

    @Override
    public void update(Video_24133012 video) {
        videoDao.update(video);
    }

    @Override
    public void delete(String videoId) throws Exception {
        videoDao.delete(videoId);
    }

    @Override
    public Video_24133012 findById(String videoId) {
        return videoDao.findById(videoId);
    }

    @Override
    public List<Video_24133012> findAll() {
        return videoDao.findAll();
    }

    @Override
    public List<Video_24133012> findAll(int page, int pageSize) {
        return videoDao.findAll(page, pageSize);
    }

    @Override
    public long countAll() {
        return videoDao.countAll();
    }

    @Override
    public List<Video_24133012> findByCategory(int categoryId, int page, int pageSize) {
        return videoDao.findByCategory(categoryId, page, pageSize);
    }

    @Override
    public long countByCategory(int categoryId) {
        return videoDao.countByCategory(categoryId);
    }

    @Override
    public void incrementViews(String videoId) {
        videoDao.incrementViews(videoId);
    }

    @Override
    public Video_24133012 findDetailById(String videoId) {
        // Tăng lượt xem lên 1
        videoDao.incrementViews(videoId);
        Video_24133012 video = videoDao.findById(videoId);
        if (video != null) {
            long likeCount = favoriteDao.countByVideoId(videoId);
            long shareCount = shareDao.countByVideoId(videoId);
            video.setLikeCount(likeCount);
            video.setShareCount(shareCount);
        }
        return video;
    }

    @Override
    public void populateLikesAndShares(List<Video_24133012> videos) {
        if (videos == null || videos.isEmpty()) return;

        // Tối ưu N+1 Query: Dùng 2 câu GROUP BY thay vì 2*N queries
        EntityManager em = JpaConfig_24133012.getEntityManager();
        try {
            // Lấy tất cả like count trong 1 query
            @SuppressWarnings("unchecked")
            List<Object[]> likeCounts = em.createQuery(
                "SELECT f.video.videoId, COUNT(f) FROM Favorite_24133012 f GROUP BY f.video.videoId")
                .getResultList();
            Map<String, Long> likeMap = new HashMap<>();
            for (Object[] row : likeCounts) {
                likeMap.put((String) row[0], (Long) row[1]);
            }

            // Lấy tất cả share count trong 1 query
            @SuppressWarnings("unchecked")
            List<Object[]> shareCounts = em.createQuery(
                "SELECT s.video.videoId, COUNT(s) FROM Share_24133012 s GROUP BY s.video.videoId")
                .getResultList();
            Map<String, Long> shareMap = new HashMap<>();
            for (Object[] row : shareCounts) {
                shareMap.put((String) row[0], (Long) row[1]);
            }

            // Gán vào từng video
            for (Video_24133012 v : videos) {
                v.setLikeCount(likeMap.getOrDefault(v.getVideoId(), 0L));
                v.setShareCount(shareMap.getOrDefault(v.getVideoId(), 0L));
            }
        } finally {
            em.close();
        }
    }
}

