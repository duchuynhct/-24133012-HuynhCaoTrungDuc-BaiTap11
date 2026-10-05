package trungduc.vn.services;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import jakarta.persistence.EntityManager;
import trungduc.vn.configs.JpaConfig_24133012;
import trungduc.vn.dao.CategoryDaoImpl_24133012;
import trungduc.vn.dao.ICategoryDao_24133012;
import trungduc.vn.entity.Category_24133012;

public class CategoryServiceImpl_24133012 implements ICategoryService_24133012 {

    private ICategoryDao_24133012 categoryDao = new CategoryDaoImpl_24133012();

    @Override
    public void insert(Category_24133012 category) {
        categoryDao.insert(category);
    }

    @Override
    public void update(Category_24133012 category) {
        categoryDao.update(category);
    }

    @Override
    public void delete(int categoryId) throws Exception {
        categoryDao.delete(categoryId);
    }

    @Override
    public Category_24133012 findById(int categoryId) {
        return categoryDao.findById(categoryId);
    }

    @Override
    public List<Category_24133012> findAll() {
        return categoryDao.findAll();
    }

    @Override
    public List<Category_24133012> findAllWithVideoCount() {
        List<Category_24133012> list = categoryDao.findAll();
        // Tối ưu N+1 Query: Dùng 1 câu query duy nhất lấy tất cả count thay vì lặp từng category
        EntityManager em = JpaConfig_24133012.getEntityManager();
        try {
            @SuppressWarnings("unchecked")
            List<Object[]> results = em.createQuery(
                "SELECT v.category.categoryId, COUNT(v) FROM Video_24133012 v WHERE v.active = true GROUP BY v.category.categoryId")
                .getResultList();
            Map<Integer, Long> countMap = new HashMap<>();
            for (Object[] row : results) {
                countMap.put((Integer) row[0], (Long) row[1]);
            }
            for (Category_24133012 cat : list) {
                cat.setVideoCount(countMap.getOrDefault(cat.getCategoryId(), 0L));
            }
        } finally {
            em.close();
        }
        return list;
    }

    @Override
    public long countVideosByCategoryId(int categoryId) {
        return categoryDao.countVideosByCategoryId(categoryId);
    }
}

