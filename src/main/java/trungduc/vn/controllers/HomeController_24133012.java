package trungduc.vn.controllers;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import trungduc.vn.entity.Category_24133012;
import trungduc.vn.entity.Video_24133012;
import trungduc.vn.services.CategoryServiceImpl_24133012;
import trungduc.vn.services.ICategoryService_24133012;
import trungduc.vn.services.IVideoService_24133012;
import trungduc.vn.services.VideoServiceImpl_24133012;

@WebServlet(urlPatterns = { "/home", "" })
public class HomeController_24133012 extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ICategoryService_24133012 categoryService = new CategoryServiceImpl_24133012();
    private IVideoService_24133012 videoService = new VideoServiceImpl_24133012();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Lấy danh sách tất cả các Category kèm đếm số lượng Video theo từng Category (Câu 5: 0.5 điểm)
        List<Category_24133012> categories = categoryService.findAllWithVideoCount();

        Category_24133012 selectedCategory = null;
        int selectedCategoryId = 0;

        String catIdParam = req.getParameter("categoryId");
        if (catIdParam != null && !catIdParam.trim().isEmpty()) {
            try {
                selectedCategoryId = Integer.parseInt(catIdParam);
                for (Category_24133012 cat : categories) {
                    if (cat.getCategoryId() == selectedCategoryId) {
                        selectedCategory = cat;
                        break;
                    }
                }
            } catch (NumberFormatException ignored) {
            }
        }

        // Nếu chưa chọn hoặc không tìm thấy, mặc định chọn Category đầu tiên
        if (selectedCategory == null && !categories.isEmpty()) {
            selectedCategory = categories.get(0);
            selectedCategoryId = selectedCategory.getCategoryId();
        }

        // Xử lý phân trang 3 video / trang theo yêu cầu Câu 4 (2.5 điểm)
        int page = 1;
        int pageSize = 3;
        String pageParam = req.getParameter("page");
        if (pageParam != null && !pageParam.trim().isEmpty()) {
            try {
                page = Integer.parseInt(pageParam);
                if (page < 1) page = 1;
            } catch (NumberFormatException ignored) {
            }
        }

        long totalVideosInCat = 0;
        int totalPages = 1;
        List<Video_24133012> videos = null;

        if (selectedCategory != null) {
            totalVideosInCat = videoService.countByCategory(selectedCategoryId);
            totalPages = (int) Math.ceil((double) totalVideosInCat / pageSize);
            if (totalPages == 0) totalPages = 1;
            if (page > totalPages) page = totalPages;

            videos = videoService.findByCategory(selectedCategoryId, page, pageSize);
            // Nạp số lượng Like và Share cho từng video
            videoService.populateLikesAndShares(videos);
        }

        req.setAttribute("categories", categories);
        req.setAttribute("selectedCategory", selectedCategory);
        req.setAttribute("videos", videos);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("totalVideosInCat", totalVideosInCat);

        req.getRequestDispatcher("/views/web/home.jsp").forward(req, resp);
    }
}
