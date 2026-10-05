package trungduc.vn.dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import trungduc.vn.configs.JpaConfig_24133012;
import trungduc.vn.entity.Category_24133012;

public class CategoryDaoImpl_24133012 implements ICategoryDao_24133012 {

    @Override
    public void insert(Category_24133012 category) {
        EntityManager em = JpaConfig_24133012.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.persist(category);
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
    public void update(Category_24133012 category) {
        EntityManager em = JpaConfig_24133012.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.merge(category);
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
    public void delete(int categoryId) throws Exception {
        EntityManager em = JpaConfig_24133012.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            Category_24133012 category = em.find(Category_24133012.class, categoryId);
            if (category != null) {
                em.remove(category);
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
    public Category_24133012 findById(int categoryId) {
        EntityManager em = JpaConfig_24133012.getEntityManager();
        try {
            return em.find(Category_24133012.class, categoryId);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Category_24133012> findAll() {
        EntityManager em = JpaConfig_24133012.getEntityManager();
        try {
            TypedQuery<Category_24133012> query = em.createQuery(
                    "SELECT c FROM Category_24133012 c WHERE c.status = true ORDER BY c.categoryId ASC",
                    Category_24133012.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public long countVideosByCategoryId(int categoryId) {
        EntityManager em = JpaConfig_24133012.getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                    "SELECT COUNT(v) FROM Video_24133012 v WHERE v.category.categoryId = :categoryId AND v.active = true",
                    Long.class);
            query.setParameter("categoryId", categoryId);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }
}
