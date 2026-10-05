package trungduc.vn.dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import trungduc.vn.configs.JpaConfig_24133012;
import trungduc.vn.entity.Order_24133012;

public class OrderDaoImpl_24133012 implements IOrderDao_24133012 {

    @Override
    public void insert(Order_24133012 order) {
        EntityManager em = JpaConfig_24133012.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.persist(order);
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
    public Order_24133012 findById(int orderId) {
        EntityManager em = JpaConfig_24133012.getEntityManager();
        try {
            return em.find(Order_24133012.class, orderId);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Order_24133012> findByUser(String username) {
        EntityManager em = JpaConfig_24133012.getEntityManager();
        try {
            TypedQuery<Order_24133012> query = em.createQuery(
                    "SELECT o FROM Order_24133012 o WHERE o.user.username = :username ORDER BY o.orderDate DESC",
                    Order_24133012.class);
            query.setParameter("username", username);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Order_24133012> findByUserAndStatus(String username, String status) {
        EntityManager em = JpaConfig_24133012.getEntityManager();
        try {
            TypedQuery<Order_24133012> query = em.createQuery(
                    "SELECT o FROM Order_24133012 o WHERE o.user.username = :username AND o.status = :status ORDER BY o.orderDate DESC",
                    Order_24133012.class);
            query.setParameter("username", username);
            query.setParameter("status", status);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public void update(Order_24133012 order) {
        EntityManager em = JpaConfig_24133012.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.merge(order);
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
    public void updateStatus(int orderId, String newStatus) {
        EntityManager em = JpaConfig_24133012.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.createQuery("UPDATE Order_24133012 o SET o.status = :status WHERE o.orderId = :id")
              .setParameter("status", newStatus)
              .setParameter("id", orderId)
              .executeUpdate();
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
