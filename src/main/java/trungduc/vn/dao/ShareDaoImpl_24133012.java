package trungduc.vn.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import trungduc.vn.configs.JpaConfig_24133012;
import trungduc.vn.entity.Share_24133012;

public class ShareDaoImpl_24133012 implements IShareDao_24133012 {

    @Override
    public long countByVideoId(String videoId) {
        EntityManager em = JpaConfig_24133012.getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                    "SELECT COUNT(s) FROM Share_24133012 s WHERE s.video.videoId = :videoId", Long.class);
            query.setParameter("videoId", videoId);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }

    @Override
    public void insert(Share_24133012 share) {
        EntityManager em = JpaConfig_24133012.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.persist(share);
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
