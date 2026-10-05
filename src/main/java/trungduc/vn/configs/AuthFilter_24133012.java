package trungduc.vn.configs;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import trungduc.vn.entity.User_24133012;
import trungduc.vn.utils.Constant_24133012;

@WebFilter(filterName = "authFilter_24133012", urlPatterns = "/admin/*")
public class AuthFilter_24133012 implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);
        User_24133012 currentUser = (session != null) ? (User_24133012) session.getAttribute(Constant_24133012.SESSION_USER) : null;

        // Nếu chưa đăng nhập hoặc không phải quyền Admin
        if (currentUser == null || !currentUser.isAdmin()) {
            resp.sendRedirect(req.getContextPath() + "/login?message=access_denied");
            return;
        }

        chain.doFilter(request, response);
    }
}
