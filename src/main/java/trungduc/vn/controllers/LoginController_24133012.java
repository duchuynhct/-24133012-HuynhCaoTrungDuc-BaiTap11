package trungduc.vn.controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import trungduc.vn.entity.User_24133012;
import trungduc.vn.services.IUserService_24133012;
import trungduc.vn.services.UserServiceImpl_24133012;
import trungduc.vn.utils.Constant_24133012;

@WebServlet(urlPatterns = { "/login" })
public class LoginController_24133012 extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private IUserService_24133012 userService = new UserServiceImpl_24133012();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String message = req.getParameter("message");
        if ("activate_success".equals(message)) {
            req.setAttribute("success", "Kích hoạt tài khoản bằng OTP thành công! Vui lòng đăng nhập.");
        } else if ("access_denied".equals(message)) {
            req.setAttribute("error", "Bạn phải đăng nhập với quyền Quản trị viên (Admin) để truy cập!");
        } else if ("logout_success".equals(message)) {
            req.setAttribute("success", "Đã đăng xuất khỏi hệ thống thành công!");
        }
        req.getRequestDispatcher("/views/auth/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User_24133012 user = userService.login(username, password);

        if (user != null) {
            HttpSession session = req.getSession();
            session.setAttribute(Constant_24133012.SESSION_USER, user);
            if (user.isAdmin()) {
                // Admin -> vào trang quản trị
                resp.sendRedirect(req.getContextPath() + "/admin/videos");
            } else {
                // User thường -> vào trang chủ
                resp.sendRedirect(req.getContextPath() + "/home");
            }
            return;
        } else {
            req.setAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng, hoặc tài khoản chưa kích hoạt OTP!");
            req.getRequestDispatcher("/views/auth/login.jsp").forward(req, resp);
        }
    }
}
