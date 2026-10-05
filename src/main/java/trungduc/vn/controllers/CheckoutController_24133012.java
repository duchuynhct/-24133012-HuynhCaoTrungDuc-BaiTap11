package trungduc.vn.controllers;

import java.io.IOException;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import trungduc.vn.entity.Order_24133012;
import trungduc.vn.entity.User_24133012;
import trungduc.vn.models.CartItem_24133012;
import trungduc.vn.services.IOrderService_24133012;
import trungduc.vn.services.OrderServiceImpl_24133012;
import trungduc.vn.utils.Constant_24133012;

@WebServlet(urlPatterns = { "/checkout" })
public class CheckoutController_24133012 extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private IOrderService_24133012 orderService = new OrderServiceImpl_24133012();

    @SuppressWarnings("unchecked")
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User_24133012 currentUser = (User_24133012) session.getAttribute(Constant_24133012.SESSION_USER);

        // Yêu cầu đăng nhập trước khi thanh toán
        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login?message=require_login");
            return;
        }

        Map<String, CartItem_24133012> cart = (Map<String, CartItem_24133012>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/cart?error=empty");
            return;
        }

        req.getRequestDispatcher("/views/web/checkout.jsp").forward(req, resp);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession();
        User_24133012 currentUser = (User_24133012) session.getAttribute(Constant_24133012.SESSION_USER);

        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login?message=require_login");
            return;
        }

        Map<String, CartItem_24133012> cart = (Map<String, CartItem_24133012>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/cart?error=empty");
            return;
        }

        String fullname = req.getParameter("fullname");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");
        String note = req.getParameter("note");

        if (fullname == null || fullname.trim().isEmpty() || phone == null || phone.trim().isEmpty()
                || address == null || address.trim().isEmpty()) {
            req.setAttribute("error", "Vui lòng nhập đầy đủ thông tin giao hàng bắt buộc!");
            req.getRequestDispatcher("/views/web/checkout.jsp").forward(req, resp);
            return;
        }

        // Tạo đơn hàng bằng phương thức thanh toán COD
        Order_24133012 order = orderService.createOrder(currentUser, fullname.trim(), phone.trim(), address.trim(),
                note != null ? note.trim() : "", cart.values());

        if (order != null) {
            // Xóa sạch giỏ hàng sau khi đặt hàng thành công
            cart.clear();
            session.setAttribute("cartTotalAmount", 0.0);
            session.setAttribute("cartTotalQuantity", 0);

            // Chuyển hướng tới trang Lịch sử đặt hàng
            resp.sendRedirect(req.getContextPath() + "/order/history?placed=true&orderId=" + order.getOrderId());
        } else {
            req.setAttribute("error", "Có lỗi xảy ra trong quá trình đặt hàng. Vui lòng thử lại!");
            req.getRequestDispatcher("/views/web/checkout.jsp").forward(req, resp);
        }
    }
}
