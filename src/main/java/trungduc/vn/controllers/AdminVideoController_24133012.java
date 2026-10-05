package trungduc.vn.controllers;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import trungduc.vn.entity.Category_24133012;
import trungduc.vn.entity.Video_24133012;
import trungduc.vn.services.CategoryServiceImpl_24133012;
import trungduc.vn.services.ICategoryService_24133012;
import trungduc.vn.services.IVideoService_24133012;
import trungduc.vn.services.VideoServiceImpl_24133012;
import trungduc.vn.utils.Constant_24133012;
import trungduc.vn.utils.UploadUtil_24133012;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 50)
@WebServlet(urlPatterns = { "/admin/videos", "/admin/video/add", "/admin/video/edit", "/admin/video/delete" })
public class AdminVideoController_24133012 extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private IVideoService_24133012 videoService = new VideoServiceImpl_24133012();
    private ICategoryService_24133012 categoryService = new CategoryServiceImpl_24133012();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();

        if ("/admin/videos".equals(path)) {
            // Xem danh sách phân trang 6 video/trang (Câu 2b)
            int page = 1;
            int pageSize = 6;
            String pageParam = req.getParameter("page");
            if (pageParam != null && !pageParam.trim().isEmpty()) {
                try {
                    page = Integer.parseInt(pageParam);
                    if (page < 1) page = 1;
                } catch (NumberFormatException ignored) {
                }
            }

            long totalVideos = videoService.countAll();
            int totalPages = (int) Math.ceil((double) totalVideos / pageSize);
            if (totalPages == 0) totalPages = 1;
            if (page > totalPages) page = totalPages;

            List<Video_24133012> videos = videoService.findAll(page, pageSize);

            req.setAttribute("videos", videos);
            req.setAttribute("currentPage", page);
            req.setAttribute("totalPages", totalPages);
            req.setAttribute("totalVideos", totalVideos);

            String message = req.getParameter("message");
            if ("add_success".equals(message)) {
                req.setAttribute("success", "Thêm video mới thành công!");
            } else if ("update_success".equals(message)) {
                req.setAttribute("success", "Cập nhật thông tin video thành công!");
            } else if ("delete_success".equals(message)) {
                req.setAttribute("success", "Xóa video thành công!");
            }

            String errorParam = req.getParameter("error");
            if ("delete_failed".equals(errorParam)) {
                req.setAttribute("error", "Xóa video thất bại! Có lỗi xảy ra trong quá trình xử lý.");
            }

            req.getRequestDispatcher("/views/admin/video-list.jsp").forward(req, resp);

        } else if ("/admin/video/add".equals(path)) {
            // Form thêm mới video
            List<Category_24133012> categories = categoryService.findAll();
            req.setAttribute("categories", categories);
            req.getRequestDispatcher("/views/admin/video-add.jsp").forward(req, resp);

        } else if ("/admin/video/edit".equals(path)) {
            // Form chỉnh sửa video
            String id = req.getParameter("id");
            Video_24133012 video = videoService.findById(id);
            if (video == null) {
                resp.sendRedirect(req.getContextPath() + "/admin/videos");
                return;
            }
            List<Category_24133012> categories = categoryService.findAll();
            req.setAttribute("video", video);
            req.setAttribute("categories", categories);
            req.getRequestDispatcher("/views/admin/video-edit.jsp").forward(req, resp);

        } else if ("/admin/video/delete".equals(path)) {
            // Không cho phép xóa qua GET - chuyển hướng về danh sách
            resp.sendRedirect(req.getContextPath() + "/admin/videos");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String path = req.getServletPath();
        String uploadPath = req.getServletContext().getRealPath("/") + Constant_24133012.UPLOAD_DIRECTORY;

        if ("/admin/video/add".equals(path)) {
            String videoId = req.getParameter("videoId");
            String title = req.getParameter("title");
            String description = req.getParameter("description");
            String categoryIdStr = req.getParameter("categoryId");
            boolean active = req.getParameter("active") != null;

            if (videoId == null || videoId.trim().isEmpty() || title == null || title.trim().isEmpty()) {
                req.setAttribute("error", "Mã video và Tiêu đề không được để trống!");
                req.setAttribute("categories", categoryService.findAll());
                req.getRequestDispatcher("/views/admin/video-add.jsp").forward(req, resp);
                return;
            }

            if (videoService.findById(videoId.trim()) != null) {
                req.setAttribute("error", "Mã video '" + videoId + "' đã tồn tại trong hệ thống!");
                req.setAttribute("categories", categoryService.findAll());
                req.getRequestDispatcher("/views/admin/video-add.jsp").forward(req, resp);
                return;
            }

            Part filePart = req.getPart("posterFile");
            String posterName = UploadUtil_24133012.processUpload(filePart, uploadPath);
            if (posterName == null) {
                String posterUrl = req.getParameter("posterUrl");
                posterName = (posterUrl != null && !posterUrl.trim().isEmpty()) ? posterUrl.trim() : "default_poster.png";
            }

            Category_24133012 category = categoryService.findById(Integer.parseInt(categoryIdStr));

            Video_24133012 video = new Video_24133012();
            video.setVideoId(videoId.trim());
            video.setTitle(title.trim());
            video.setDescription(description != null ? description.trim() : "");
            video.setPoster(posterName);
            video.setViews(0);
            video.setActive(active);
            video.setCategory(category);

            String priceStr = req.getParameter("price");
            if (priceStr != null && !priceStr.trim().isEmpty()) {
                try {
                    video.setPrice(Double.parseDouble(priceStr.trim()));
                } catch (NumberFormatException ignored) {
                }
            }

            videoService.insert(video);
            resp.sendRedirect(req.getContextPath() + "/admin/videos?message=add_success");

        } else if ("/admin/video/edit".equals(path)) {
            String videoId = req.getParameter("videoId");
            String title = req.getParameter("title");
            String description = req.getParameter("description");
            String categoryIdStr = req.getParameter("categoryId");
            boolean active = req.getParameter("active") != null;

            Video_24133012 video = videoService.findById(videoId);
            if (video != null) {
                video.setTitle(title.trim());
                video.setDescription(description != null ? description.trim() : "");
                video.setActive(active);

                String priceStr = req.getParameter("price");
                if (priceStr != null && !priceStr.trim().isEmpty()) {
                    try {
                        video.setPrice(Double.parseDouble(priceStr.trim()));
                    } catch (NumberFormatException ignored) {
                    }
                }

                Part filePart = req.getPart("posterFile");
                String newPoster = UploadUtil_24133012.processUpload(filePart, uploadPath);
                if (newPoster != null) {
                    video.setPoster(newPoster);
                } else {
                    String posterUrl = req.getParameter("posterUrl");
                    if (posterUrl != null && !posterUrl.trim().isEmpty()) {
                        video.setPoster(posterUrl.trim());
                    }
                }

                Category_24133012 category = categoryService.findById(Integer.parseInt(categoryIdStr));
                video.setCategory(category);

                videoService.update(video);
                resp.sendRedirect(req.getContextPath() + "/admin/videos?message=update_success");
            } else {
                resp.sendRedirect(req.getContextPath() + "/admin/videos");
            }
        } else if ("/admin/video/delete".equals(path)) {
            // Xóa video qua POST (an toàn hơn GET)
            String id = req.getParameter("id");
            if (id != null) {
                try {
                    videoService.delete(id);
                    resp.sendRedirect(req.getContextPath() + "/admin/videos?message=delete_success");
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    resp.sendRedirect(req.getContextPath() + "/admin/videos?error=delete_failed");
                    return;
                }
            }
            resp.sendRedirect(req.getContextPath() + "/admin/videos");
        }
    }
}
