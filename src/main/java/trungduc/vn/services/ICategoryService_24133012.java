package trungduc.vn.services;

import java.util.List;
import trungduc.vn.entity.Category_24133012;

public interface ICategoryService_24133012 {
    void insert(Category_24133012 category);
    void update(Category_24133012 category);
    void delete(int categoryId) throws Exception;
    Category_24133012 findById(int categoryId);
    List<Category_24133012> findAll();
    List<Category_24133012> findAllWithVideoCount();
    long countVideosByCategoryId(int categoryId);
}
