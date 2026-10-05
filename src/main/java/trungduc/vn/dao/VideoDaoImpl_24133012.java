package trungduc.vn.dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import trungduc.vn.configs.JpaConfig_24133012;
import trungduc.vn.entity.Video_24133012;

public class VideoDaoImpl_24133012 implements IVideoDao_24133012 {

    @Override
    public void insert(Video_24133012 video) {
        EntityManager em = JpaConfig_24133012.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.persist(video);
            trans.commit();
        } catch (Exception e) {
            if (trans.isActive()) trans.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void update(Video_24133012 video) {
        EntityManager em = JpaConfig_24133012.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.merge(video);
            trans.commit();
        } catch (Exception e) {
            if (trans.isActive()) trans.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(String videoId) throws Exception {
        EntityManager em = JpaConfig_24133012.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            Video_24133012 video = em.find(Video_24133012.class, videoId);
            if (video != null) {
                // Xoá các bản ghi con trong Favorites và Shares nếu có để tránh lỗi Foreign Key
                em.createQuery("DELETE FROM Favorite_24133012 f WHERE f.video.videoId = :id")
                  .setParameter("id", videoId).executeUpdate();
                em.createQuery("DELETE FROM Share_24133012 s WHERE s.video.videoId = :id")
                  .setParameter("id", videoId).executeUpdate();
                em.remove(video);
            }
            trans.commit();
        } catch (Exception e) {
            if (trans.isActive()) trans.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public Video_24133012 findById(String videoId) {
        EntityManager em = JpaConfig_24133012.getEntityManager();
        try {
            return em.find(Video_24133012.class, videoId);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Video_24133012> findAll() {
        EntityManager em = JpaConfig_24133012.getEntityManager();
        try {
            TypedQuery<Video_24133012> query = em.createQuery(
                    "SELECT v FROM Video_24133012 v ORDER BY v.videoId DESC", Video_24133012.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Video_24133012> findAll(int page, int pageSize) {
        EntityManager em = JpaConfig_24133012.getEntityManager();
        try {
            TypedQuery<Video_24133012> query = em.createQuery(
                    "SELECT v FROM Video_24133012 v ORDER BY v.videoId ASC", Video_24133012.class);
            query.setFirstResult((page - 1) * pageSize);
            query.setMaxResults(pageSize);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public long countAll() {
        EntityManager em = JpaConfig_24133012.getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery("SELECT COUNT(v) FROM Video_24133012 v", Long.class);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Video_24133012> findByCategory(int categoryId, int page, int pageSize) {
        EntityManager em = JpaConfig_24133012.getEntityManager();
        try {
            TypedQuery<Video_24133012> query = em.createQuery(
                    "SELECT v FROM Video_24133012 v WHERE v.category.categoryId = :catId AND v.active = true ORDER BY v.videoId ASC",
                    Video_24133012.class);
            query.setParameter("catId", categoryId);
            query.setFirstResult((page - 1) * pageSize);
            query.setMaxResults(pageSize);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public long countByCategory(int categoryId) {
        EntityManager em = JpaConfig_24133012.getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                    "SELECT COUNT(v) FROM Video_24133012 v WHERE v.category.categoryId = :catId AND v.active = true",
                    Long.class);
            query.setParameter("catId", categoryId);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }

    @Override
    public void incrementViews(String videoId) {
        EntityManager em = JpaConfig_24133012.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            // Dùng JPQL atomic update để tránh Lost Updates khi nhiều người truy cập đồng thời
            em.createQuery("UPDATE Video_24133012 v SET v.views = v.views + 1 WHERE v.videoId = :id")
              .setParameter("id", videoId)
              .executeUpdate();
            trans.commit();
        } catch (Exception e) {
            if (trans.isActive()) trans.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
