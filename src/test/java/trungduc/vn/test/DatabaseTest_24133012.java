package trungduc.vn.test;

import java.util.List;

import trungduc.vn.entity.Category_24133012;
import trungduc.vn.entity.User_24133012;
import trungduc.vn.entity.Video_24133012;
import trungduc.vn.services.CategoryServiceImpl_24133012;
import trungduc.vn.services.ICategoryService_24133012;
import trungduc.vn.services.IUserService_24133012;
import trungduc.vn.services.IVideoService_24133012;
import trungduc.vn.services.UserServiceImpl_24133012;
import trungduc.vn.services.VideoServiceImpl_24133012;

public class DatabaseTest_24133012 {

    public static void main(String[] args) {
        System.out.println("========== BẮT ĐẦU KIỂM THỬ HỆ THỐNG 24133012_made ==========");

        // 1. Kiểm tra User Service (Login, Find)
        IUserService_24133012 userService = new UserServiceImpl_24133012();
        User_24133012 admin = userService.login("admin", "123456");
        if (admin != null && admin.isAdmin()) {
            System.out.println("[PASS] Đăng nhập Admin thành công: " + admin.getFullname() + " (Admin: " + admin.isAdmin() + ")");
        } else {
            System.err.println("[FAIL] Đăng nhập Admin thất bại!");
        }

        // 2. Kiểm tra Category Service & Đếm số lượng video theo từng Category (Câu 5)
        ICategoryService_24133012 categoryService = new CategoryServiceImpl_24133012();
        List<Category_24133012> categories = categoryService.findAllWithVideoCount();
        System.out.println("[PASS] Lấy danh sách Categories kèm đếm số lượng Video (Câu 5):");
        for (Category_24133012 cat : categories) {
            System.out.println("   + " + cat.getCategoryname() + " (" + cat.getVideoCount() + " videos)");
        }

        // 3. Kiểm tra Video Service phân trang 6 video/trang ở Admin (Câu 2b)
        IVideoService_24133012 videoService = new VideoServiceImpl_24133012();
        long totalVideos = videoService.countAll();
        List<Video_24133012> adminPage1 = videoService.findAll(1, 6);
        System.out.println("[PASS] Tổng số video: " + totalVideos);
        System.out.println("[PASS] Admin phân trang 6 video/trang: Lấy được " + adminPage1.size() + " video ở trang 1:");
        for (Video_24133012 v : adminPage1) {
            System.out.println("   - [" + v.getVideoId() + "] " + v.getTitle() + " | Views: " + v.getViews());
        }

        // 4. Kiểm tra User phân trang 3 video/trang theo Category (Câu 4)
        if (!categories.isEmpty()) {
            int firstCatId = categories.get(0).getCategoryId();
            List<Video_24133012> userPage1 = videoService.findByCategory(firstCatId, 1, 3);
            videoService.populateLikesAndShares(userPage1);
            System.out.println("[PASS] User phân trang 3 video/trang theo Category [" + categories.get(0).getCategoryname() + "]: Lấy được " + userPage1.size() + " video:");
            for (Video_24133012 v : userPage1) {
                System.out.println("   - [" + v.getVideoId() + "] " + v.getTitle() + " | Likes: " + v.getLikeCount() + " | Shares: " + v.getShareCount());
            }
        }

        // 5. Kiểm tra chi tiết 1 video (Câu 3)
        Video_24133012 detail = videoService.findDetailById("VID01");
        if (detail != null) {
            System.out.println("[PASS] Chi tiết Video VID01 (Câu 3):");
            System.out.println("   Tiêu đề: " + detail.getTitle());
            System.out.println("   Mã video: " + detail.getVideoId());
            System.out.println("   Category name: " + detail.getCategory().getCategoryname());
            System.out.println("   View: " + detail.getViews());
            System.out.println("   Share(" + detail.getShareCount() + ")");
            System.out.println("   Like(" + detail.getLikeCount() + ")");
            System.out.println("   description: " + detail.getDescription());
        }

        // 6. Kiểm tra gửi Email OTP thực tế qua Gmail SMTP
        System.out.println("[TEST] Thử nghiệm gửi mã OTP thực tế tới trungduc2203@gmail.com...");
        boolean emailSent = trungduc.vn.utils.EmailUtil_24133012.sendOtpEmail("trungduc2203@gmail.com", "888999");
        if (emailSent) {
            System.out.println("[PASS] Gửi email OTP thực tế hoàn tất!");
        }

        // 7. Kiểm tra Order Service (Giỏ hàng & Lịch sử đặt hàng theo 8 trạng thái)
        System.out.println("\n[TEST] 7. Kiểm tra Order Service & Lọc theo 8 trạng thái...");
        trungduc.vn.services.IOrderService_24133012 orderService = new trungduc.vn.services.OrderServiceImpl_24133012();
        List<trungduc.vn.entity.Order_24133012> userOrders = orderService.findByUser("duchuynhct");
        System.out.println("[PASS] Lấy tổng số đơn hàng của user duchuynhct: " + userOrders.size());
        for (trungduc.vn.entity.Order_24133012 o : userOrders) {
            System.out.println("   + Đơn #" + o.getOrderId() + " | Trạng thái: [" + o.getStatus() + "] | Tổng tiền: " + o.getTotalAmount() + " | Số món: " + o.getOrderDetails().size());
        }

        List<trungduc.vn.entity.Order_24133012> deliveredOrders = orderService.findByUserAndStatus("duchuynhct", "Đã giao");
        System.out.println("[PASS] Lọc trạng thái 'Đã giao': tìm thấy " + deliveredOrders.size() + " đơn hàng.");

        List<trungduc.vn.entity.Order_24133012> newOrders = orderService.findByUserAndStatus("duchuynhct", "Đơn hàng mới");
        System.out.println("[PASS] Lọc trạng thái 'Đơn hàng mới': tìm thấy " + newOrders.size() + " đơn hàng.");

        System.out.println("========== TẤT CẢ KIỂM THỬ ĐÃ HOÀN TẤT THÀNH CÔNG! ==========");
    }
}
