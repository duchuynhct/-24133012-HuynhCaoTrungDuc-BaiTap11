package trungduc.vn.configs;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;

import jakarta.servlet.annotation.WebFilter;

@WebFilter(filterName = "sitemeshFilter_24133012", urlPatterns = "/*")
public class SiteMeshFilterConfig_24133012 extends ConfigurableSiteMeshFilter {

    @Override
    protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
        // Tiền tố đường dẫn chứa decorator
        builder.setDecoratorPrefix("/WEB-INF/decorators");

        // Áp dụng decorator cho giao diện quản trị
        builder.addDecoratorPath("/admin/*", "/admin.jsp")
               // Áp dụng decorator cho giao diện người dùng
               .addDecoratorPath("/*", "/web.jsp")
               // Loại trừ các trang xác thực và tài nguyên tĩnh
               .addExcludedPath("/login*")
               .addExcludedPath("/register*")
               .addExcludedPath("/verify-otp*")
               .addExcludedPath("/resend-otp*")
               .addExcludedPath("/uploads/*")
               .addExcludedPath("/image*")
               .addExcludedPath("/static/*");
    }
}
