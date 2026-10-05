package trungduc.vn.configs;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppContextListener_24133012 implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Ứng dụng khởi động - có thể dùng để log hoặc khởi tạo tài nguyên
        System.out.println("[24133012] Ứng dụng đã khởi động thành công.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Giải phóng EntityManagerFactory khi ứng dụng dừng/redeploy để tránh rò rỉ bộ nhớ
        JpaConfig_24133012.shutdown();
        System.out.println("[24133012] Đã giải phóng EntityManagerFactory - Ứng dụng đã dừng.");
    }
}
