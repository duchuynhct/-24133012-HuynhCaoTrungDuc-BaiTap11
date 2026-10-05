package trungduc.vn.controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import trungduc.vn.services.IUserService_24133012;
import trungduc.vn.services.UserServiceImpl_24133012;
import trungduc.vn.utils.Constant_24133012;
import trungduc.vn.utils.EmailUtil_24133012;

@WebServlet(urlPatterns = { "/verify-otp", "/resend-otp" })
public class VerifyOtpController_24133012 extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private IUserService_24133012 userService = new UserServiceImpl_24133012();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        HttpSession session = req.getSession();

        if ("/resend-otp".equals(path)) {
            String pendingUsername = (String) session.getAttribute(Constant_24133012.SESSION_REGISTER_USER);
            String email = (String) session.getAttribute("userEmail");
            if (pendingUsername != null && email != null) {
                String newOtp = EmailUtil_24133012.generateOtp();
                session.setAttribute(Constant_24133012.SESSION_OTP, newOtp);
                session.setAttribute(Constant_24133012.SESSION_OTP_TIME, System.currentTimeMillis());
                EmailUtil_24133012.sendOtpEmail(email, newOtp);
                req.setAttribute("message", "Mã OTP mới đã được gửi đến email " + email);
            }
        }

        req.getRequestDispatcher("/views/auth/verify-otp.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession();
        String sessionOtp = (String) session.getAttribute(Constant_24133012.SESSION_OTP);
        Long sessionOtpTime = (Long) session.getAttribute(Constant_24133012.SESSION_OTP_TIME);
        String pendingUsername = (String) session.getAttribute(Constant_24133012.SESSION_REGISTER_USER);

        String enteredOtp = req.getParameter("otp");

        if (sessionOtp == null || pendingUsername == null) {
            resp.sendRedirect(req.getContextPath() + "/register");
            return;
        }

        // Kiểm tra thời hạn 5 phút
        if (sessionOtpTime != null && (System.currentTimeMillis() - sessionOtpTime > 5 * 60 * 1000)) {
            req.setAttribute("error", "Mã OTP đã hết hạn (quá 5 phút). Vui lòng nhấn gửi lại mã OTP!");
            req.getRequestDispatcher("/views/auth/verify-otp.jsp").forward(req, resp);
            return;
        }

        // Kiểm tra trùng khớp mã OTP
        if (enteredOtp != null && enteredOtp.trim().equals(sessionOtp.trim())) {
            // Kích hoạt tài khoản
            userService.activateUser(pendingUsername);

            // Dọn dẹp session OTP
            session.removeAttribute(Constant_24133012.SESSION_OTP);
            session.removeAttribute(Constant_24133012.SESSION_OTP_TIME);
            session.removeAttribute(Constant_24133012.SESSION_REGISTER_USER);
            session.removeAttribute("userEmail");

            resp.sendRedirect(req.getContextPath() + "/login?message=activate_success");
        } else {
            req.setAttribute("error", "Mã OTP không chính xác. Vui lòng kiểm tra lại!");
            req.getRequestDispatcher("/views/auth/verify-otp.jsp").forward(req, resp);
        }
    }
}
