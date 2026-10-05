package trungduc.vn.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import trungduc.vn.configs.JpaConfig_24133012;
import trungduc.vn.entity.Favorite_24133012;

public class FavoriteDaoImpl_24133012 implements IFavoriteDao_24133012 {

    @Override
    public long countByVideoId(String videoId) {
        EntityManager em = JpaConfig_24133012.getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                    "SELECT COUNT(f) FROM Favorite_24133012 f WHERE f.video.videoId = :videoId", Long.class);
            query.setParameter("videoId", videoId);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }

    @Override
    public Favorite_24133012 findByUserAndVideo(String username, String videoId) {
        EntityManager em = JpaConfig_24133012.getEntityManager();
        try {
            TypedQuery<Favorite_24133012> query = em.createQuery(
                    "SELECT f FROM Favorite_24133012 f WHERE f.user.username = :username AND f.video.videoId = :videoId",
                    Favorite_24133012.class);
            query.setParameter("username", username);
            query.setParameter("videoId", videoId);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public void insert(Favorite_24133012 favorite) {
        EntityManager em = JpaConfig_24133012.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.persist(favorite);
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
    public void delete(int favoriteId) {
        EntityManager em = JpaConfig_24133012.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            Favorite_24133012 fav = em.find(Favorite_24133012.class, favoriteId);
            if (fav != null) {
                em.remove(fav);
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
}
