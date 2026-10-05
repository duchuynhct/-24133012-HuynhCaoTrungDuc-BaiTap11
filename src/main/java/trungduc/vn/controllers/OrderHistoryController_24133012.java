package trungduc.vn.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import trungduc.vn.entity.Order_24133012;
import trungduc.vn.entity.User_24133012;
import trungduc.vn.services.IOrderService_24133012;
import trungduc.vn.services.OrderServiceImpl_24133012;
import trungduc.vn.utils.Constant_24133012;

@WebServlet(urlPatterns = { "/order/history", "/orders" })
public class OrderHistoryController_24133012 extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private IOrderService_24133012 orderService = new OrderServiceImpl_24133012();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User_24133012 currentUser = (User_24133012) session.getAttribute(Constant_24133012.SESSION_USER);

        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login?message=require_login");
            return;
        }

        String statusParam = req.getParameter("status");
        if (statusParam == null || statusParam.trim().isEmpty()) {
            statusParam = "ALL";
        }

        // Lấy toàn bộ đơn hàng của user để đếm số lượng theo từng trạng thái
        List<Order_24133012> allOrders = orderService.findByUser(currentUser.getUsername());
        Map<String, Integer> statusCountMap = new HashMap<>();
        statusCountMap.put("ALL", allOrders.size());
        for (Order_24133012 o : allOrders) {
            String st = o.getStatus();
            statusCountMap.put(st, statusCountMap.getOrDefault(st, 0) + 1);
        }

        // Lấy danh sách đơn hàng tương ứng theo bộ lọc trạng thái được chọn
        List<Order_24133012> filteredOrders = orderService.findByUserAndStatus(currentUser.getUsername(), statusParam);

        req.setAttribute("orders", filteredOrders);
        req.setAttribute("selectedStatus", statusParam);
        req.setAttribute("statusCountMap", statusCountMap);

        req.getRequestDispatcher("/views/web/order-history.jsp").forward(req, resp);
    }
}
