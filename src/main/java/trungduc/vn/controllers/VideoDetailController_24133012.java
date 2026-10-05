package trungduc.vn.controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import trungduc.vn.entity.User_24133012;
import trungduc.vn.entity.Video_24133012;
import trungduc.vn.services.FavoriteServiceImpl_24133012;
import trungduc.vn.services.IFavoriteService_24133012;
import trungduc.vn.services.IShareService_24133012;
import trungduc.vn.services.IVideoService_24133012;
import trungduc.vn.services.ShareServiceImpl_24133012;
import trungduc.vn.services.VideoServiceImpl_24133012;
import trungduc.vn.utils.Constant_24133012;

@WebServlet(urlPatterns = { "/video/detail", "/video/like", "/video/share" })
public class VideoDetailController_24133012 extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private IVideoService_24133012 videoService = new VideoServiceImpl_24133012();
    private IFavoriteService_24133012 favoriteService = new FavoriteServiceImpl_24133012();
    private IShareService_24133012 shareService = new ShareServiceImpl_24133012();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();

        if ("/video/detail".equals(path)) {
            String videoId = req.getParameter("id");
            if (videoId == null || videoId.trim().isEmpty()) {
                resp.sendRedirect(req.getContextPath() + "/home");
                return;
            }

            // Lấy chi tiết video (đồng thời tự động tăng view + 1 và nạp số Like, Share)
            Video_24133012 video = videoService.findDetailById(videoId.trim());
            if (video == null) {
                resp.sendRedirect(req.getContextPath() + "/home");
                return;
            }

            // Kiểm tra trạng thái đã Like của người dùng hiện tại (nếu đã đăng nhập)
            HttpSession session = req.getSession(false);
            User_24133012 currentUser = (session != null) ? (User_24133012) session.getAttribute(Constant_24133012.SESSION_USER) : null;
            boolean isLiked = false;
            if (currentUser != null) {
                isLiked = favoriteService.isLiked(currentUser.getUsername(), videoId);
            }

            req.setAttribute("video", video);
            req.setAttribute("isLiked", isLiked);
            req.getRequestDispatcher("/views/web/video-detail.jsp").forward(req, resp);

        } else if ("/video/like".equals(path)) {
            String videoId = req.getParameter("id");
            HttpSession session = req.getSession();
            User_24133012 currentUser = (User_24133012) session.getAttribute(Constant_24133012.SESSION_USER);

            String username = (currentUser != null) ? currentUser.getUsername() : "guest";
            favoriteService.toggleLike(username, videoId);

            String redirect = req.getParameter("redirect");
            if ("home".equals(redirect)) {
                String catId = req.getParameter("categoryId");
                String page = req.getParameter("page");
                resp.sendRedirect(req.getContextPath() + "/home?categoryId=" + (catId != null ? catId : "") + "&page=" + (page != null ? page : "1"));
                return;
            }
            resp.sendRedirect(req.getContextPath() + "/video/detail?id=" + videoId);

        } else if ("/video/share".equals(path)) {
            // Hỗ trợ chia sẻ nhanh qua GET
            String videoId = req.getParameter("id");
            if (videoId == null) videoId = req.getParameter("videoId");
            String recipientEmail = req.getParameter("email");
            if (recipientEmail == null || recipientEmail.trim().isEmpty()) {
                recipientEmail = "friend@example.com";
            }

            HttpSession session = req.getSession();
            User_24133012 currentUser = (User_24133012) session.getAttribute(Constant_24133012.SESSION_USER);
            String username = (currentUser != null) ? currentUser.getUsername() : "guest";

            shareService.shareVideo(username, videoId, recipientEmail);

            String redirect = req.getParameter("redirect");
            if ("home".equals(redirect)) {
                String catId = req.getParameter("categoryId");
                String page = req.getParameter("page");
                resp.sendRedirect(req.getContextPath() + "/home?categoryId=" + (catId != null ? catId : "") + "&page=" + (page != null ? page : "1"));
                return;
            }
            resp.sendRedirect(req.getContextPath() + "/video/detail?id=" + videoId + "&shared=true");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();

        if ("/video/share".equals(path)) {
            String videoId = req.getParameter("videoId");
            if (videoId == null) videoId = req.getParameter("id");
            String recipientEmail = req.getParameter("email");
            if (recipientEmail == null || recipientEmail.trim().isEmpty()) {
                recipientEmail = "friend@example.com";
            }

            HttpSession session = req.getSession();
            User_24133012 currentUser = (User_24133012) session.getAttribute(Constant_24133012.SESSION_USER);
            String username = (currentUser != null) ? currentUser.getUsername() : "guest";

            shareService.shareVideo(username, videoId, recipientEmail);

            String redirect = req.getParameter("redirect");
            if ("home".equals(redirect)) {
                String catId = req.getParameter("categoryId");
                String page = req.getParameter("page");
                resp.sendRedirect(req.getContextPath() + "/home?categoryId=" + (catId != null ? catId : "") + "&page=" + (page != null ? page : "1"));
                return;
            }
            resp.sendRedirect(req.getContextPath() + "/video/detail?id=" + videoId + "&shared=true");
        }
    }
}
