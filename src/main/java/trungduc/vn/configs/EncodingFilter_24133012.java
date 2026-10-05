package trungduc.vn.configs;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

@WebFilter(filterName = "encodingFilter_24133012", urlPatterns = "/*")
public class EncodingFilter_24133012 implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // Chỉ set Content-Type text/html cho các request trang web, không áp dụng cho ảnh/tài nguyên tĩnh
        String uri = ((HttpServletRequest) request).getRequestURI();
        if (!uri.contains("/image") && !uri.contains("/uploads/") && !uri.contains("/static/")) {
            response.setContentType("text/html; charset=UTF-8");
        }

        chain.doFilter(request, response);
    }
}
