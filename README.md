# Dự án Bài Tập 11 - Lập Trình Web (MSSV: 24133012)

- **Họ và tên**: Huỳnh Cao Trung Đức
- **Mã số sinh viên (MSSV)**: 24133012
- **Mã đề thi**: Đề số 03
- **Tên dự án**: `24133012_made`
- **Công nghệ sử dụng**:
  - Java 17+, Jakarta EE (Servlet 6.0, JSP, JSTL)
  - JPA (Hibernate 6.4)
  - SiteMesh 3.2.1
  - SQL Server
  - Bootstrap 5 & FontAwesome 6

---

## 📌 Các tính năng chính đã hoàn thành

1. **Kiến trúc 3 lớp & SiteMesh Decorator**:
   - Phân tầng Presentation (Servlet/JSP) - Business Logic (Service) - Data Access (DAO).
   - Layout trang người dùng (`web.jsp`) và quản trị (`admin.jsp`).
   - Cố định thông tin sinh viên ở chân trang Footer.

2. **Xác thực & Bảo mật**:
   - Đăng ký tài khoản với tính năng gửi mã OTP thực tế qua Google Mail SMTP (TLS 587).
   - Kích hoạt tài khoản bằng mã OTP 6 số.
   - Đăng nhập, đăng xuất quản lý qua `HttpSession`.
   - Bộ lọc phân quyền `AuthFilter` bảo vệ vùng quản trị Admin.

3. **Quản trị CRUD Video (Admin)**:
   - Thêm, sửa, xóa video, tải lên ảnh poster hoặc nhập URL.
   - Phân trang 6 video/trang.

4. **Trang xem chi tiết Video (User)**:
   - Hiển thị poster, mã video, danh mục, mô tả chi tiết, giá tiền.
   - Tự động tăng lượt xem (`views++`) an toàn theo atomic update.
   - Tương tác Thích (`Like(x)`) và Chia sẻ (`Share(x)`).

5. **Trang chủ phân loại theo danh mục**:
   - Lọc video theo từng danh mục.
   - Phân trang 3 video/trang.
   - Đếm số lượng video theo từng danh mục: `Category Name (Count)`.

6. **Chức năng Giỏ hàng (`/cart`)**:
   - Thêm sản phẩm vào giỏ hàng từ trang chủ và trang chi tiết.
   - Sửa, tăng giảm số lượng (+ / -) giới hạn nghiêm ngặt từ 1 đến 99 sản phẩm.
   - Xóa từng món hoặc xóa toàn bộ giỏ hàng.
   - Huy hiệu (badge) đếm số lượng giỏ hàng trên thanh Menu.

7. **Thanh toán đơn hàng bằng COD (`/checkout`)**:
   - Nhập thông tin giao hàng: Họ tên, Số điện thoại, Địa chỉ nhận hàng, Ghi chú.
   - Phương thức thanh toán khi nhận hàng (COD).
   - Tự động lưu đơn hàng và chi tiết sản phẩm, làm trống giỏ hàng sau khi đặt.

8. **Lịch sử đặt hàng theo 8 trạng thái (`/order/history`)**:
   - Tab "Tất cả" và 8 Tab lọc trạng thái chuẩn:
     1. `Đơn hàng mới`
     2. `Đã xác nhận`
     3. `Chuẩn bị hàng`
     4. `Vận chuyển`
     5. `Giao hàng`
     6. `Đã giao`
     7. `Đơn hàng hủy`
     8. `Đơn hàng hoàn`
   - Hiển thị huy hiệu màu sắc trực quan, chi tiết sản phẩm và tổng tiền.
   - Hỗ trợ đổi trạng thái trực tiếp trong Database để kiểm tra.

---

## 💾 Hướng dẫn cài đặt CSDL

1. Chạy file script SQL tạo cơ sở dữ liệu: [`database_24133012_made.sql`](database_24133012_made.sql)
2. Chạy file script bổ sung bảng đơn hàng và cập nhật giá: [`add_cart_order_tables.sql`](add_cart_order_tables.sql)
3. Cấu hình thông tin kết nối CSDL trong `src/main/resources/META-INF/persistence.xml`.
