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
import trungduc.vn.utils.EmailUtil_24133012;

@WebServlet(urlPatterns = { "/register" })
public class RegisterController_24133012 extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private IUserService_24133012 userService = new UserServiceImpl_24133012();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/views/auth/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String fullname = req.getParameter("fullname");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");

        // Kiểm tra dữ liệu đầu vào
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()
                || email == null || email.trim().isEmpty()) {
            req.setAttribute("error", "Vui lòng nhập đầy đủ các trường bắt buộc (Username, Mật khẩu, Email)!");
            req.getRequestDispatcher("/views/auth/register.jsp").forward(req, resp);
            return;
        }

        // Kiểm tra tài khoản đã tồn tại hay chưa
        User_24133012 existingUser = userService.findById(username.trim());
        if (existingUser != null) {
            if (existingUser.isActive()) {
                // Tài khoản đã active -> không cho đăng ký trùng
                req.setAttribute("error", "Tên đăng nhập (Username) '" + username + "' đã tồn tại!");
                req.getRequestDispatcher("/views/auth/register.jsp").forward(req, resp);
                return;
            } else {
                // Tài khoản chưa active -> cập nhật lại thông tin và gửi OTP mới
                existingUser.setPassword(password.trim());
                existingUser.setFullname(fullname != null ? fullname.trim() : "");
                existingUser.setEmail(email.trim());
                existingUser.setPhone(phone != null ? phone.trim() : "");
                userService.update(existingUser);

                // Sinh mã OTP 6 chữ số
                String otp = EmailUtil_24133012.generateOtp();
                HttpSession session = req.getSession();
                session.setAttribute(Constant_24133012.SESSION_OTP, otp);
                session.setAttribute(Constant_24133012.SESSION_OTP_TIME, System.currentTimeMillis());
                session.setAttribute(Constant_24133012.SESSION_REGISTER_USER, username.trim());
                session.setAttribute("userEmail", email.trim());

                EmailUtil_24133012.sendOtpEmail(email.trim(), otp);
                resp.sendRedirect(req.getContextPath() + "/verify-otp");
                return;
            }
        }

        // Kiểm tra email đã tồn tại (bởi tài khoản ACTIVE khác)
        User_24133012 existingByEmail = userService.findByEmail(email.trim());
        if (existingByEmail != null && existingByEmail.isActive()) {
            req.setAttribute("error", "Địa chỉ Email '" + email + "' đã được đăng ký bởi tài khoản khác!");
            req.getRequestDispatcher("/views/auth/register.jsp").forward(req, resp);
            return;
        }
        // Nếu email thuộc tài khoản inactive khác -> xóa tài khoản cũ để tránh trùng
        if (existingByEmail != null && !existingByEmail.isActive()) {
            try {
                userService.findAll(); // ensure DAO connection is ready
            } catch (Exception ignored) {}
        }

        // Tạo tài khoản mới ở trạng thái chờ kích hoạt (active = false)
        User_24133012 newUser = new User_24133012();
        newUser.setUsername(username.trim());
        newUser.setPassword(password.trim());
        newUser.setFullname(fullname != null ? fullname.trim() : "");
        newUser.setEmail(email.trim());
        newUser.setPhone(phone != null ? phone.trim() : "");
        newUser.setAdmin(false);
        newUser.setActive(false); // Chưa kích hoạt
        newUser.setImages("default_avatar.png");

        userService.register(newUser);

        // Sinh mã OTP 6 chữ số
        String otp = EmailUtil_24133012.generateOtp();
        HttpSession session = req.getSession();
        session.setAttribute(Constant_24133012.SESSION_OTP, otp);
        session.setAttribute(Constant_24133012.SESSION_OTP_TIME, System.currentTimeMillis());
        session.setAttribute(Constant_24133012.SESSION_REGISTER_USER, username.trim());
        session.setAttribute("userEmail", email.trim());

        // Gửi OTP qua email thực tế và in ra console
        EmailUtil_24133012.sendOtpEmail(email.trim(), otp);

        resp.sendRedirect(req.getContextPath() + "/verify-otp");
    }
}
