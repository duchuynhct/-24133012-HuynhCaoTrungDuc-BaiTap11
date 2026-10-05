package trungduc.vn.dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import trungduc.vn.configs.JpaConfig_24133012;
import trungduc.vn.entity.User_24133012;

public class UserDaoImpl_24133012 implements IUserDao_24133012 {

    @Override
    public void insert(User_24133012 user) {
        EntityManager em = JpaConfig_24133012.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.persist(user);
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
    public void update(User_24133012 user) {
        EntityManager em = JpaConfig_24133012.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.merge(user);
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
    public void delete(String username) throws Exception {
        EntityManager em = JpaConfig_24133012.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            User_24133012 user = em.find(User_24133012.class, username);
            if (user != null) {
                em.remove(user);
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
    public User_24133012 findById(String username) {
        EntityManager em = JpaConfig_24133012.getEntityManager();
        try {
            return em.find(User_24133012.class, username);
        } finally {
            em.close();
        }
    }

    @Override
    public User_24133012 findByEmail(String email) {
        EntityManager em = JpaConfig_24133012.getEntityManager();
        try {
            TypedQuery<User_24133012> query = em.createQuery(
                    "SELECT u FROM User_24133012 u WHERE u.email = :email", User_24133012.class);
            query.setParameter("email", email);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public List<User_24133012> findAll() {
        EntityManager em = JpaConfig_24133012.getEntityManager();
        try {
            TypedQuery<User_24133012> query = em.createQuery("SELECT u FROM User_24133012 u", User_24133012.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}
