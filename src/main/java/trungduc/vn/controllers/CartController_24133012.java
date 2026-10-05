package trungduc.vn.controllers;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import trungduc.vn.entity.Video_24133012;
import trungduc.vn.models.CartItem_24133012;
import trungduc.vn.services.IVideoService_24133012;
import trungduc.vn.services.VideoServiceImpl_24133012;

@WebServlet(urlPatterns = { "/cart", "/cart/add", "/cart/update", "/cart/delete", "/cart/clear" })
public class CartController_24133012 extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private IVideoService_24133012 videoService = new VideoServiceImpl_24133012();

    @SuppressWarnings("unchecked")
    private Map<String, CartItem_24133012> getCart(HttpSession session) {
        Map<String, CartItem_24133012> cart = (Map<String, CartItem_24133012>) session.getAttribute("cart");
        if (cart == null) {
            cart = new LinkedHashMap<>();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    private void updateCartTotals(HttpSession session, Map<String, CartItem_24133012> cart) {
        double totalAmount = 0.0;
        int totalQuantity = 0;
        for (CartItem_24133012 item : cart.values()) {
            totalAmount += item.getSubTotal();
            totalQuantity += item.getQuantity();
        }
        session.setAttribute("cartTotalAmount", totalAmount);
        session.setAttribute("cartTotalQuantity", totalQuantity);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        HttpSession session = req.getSession();
        Map<String, CartItem_24133012> cart = getCart(session);

        if ("/cart/add".equals(path)) {
            String videoId = req.getParameter("id");
            if (videoId != null && !videoId.trim().isEmpty()) {
                Video_24133012 video = videoService.findById(videoId.trim());
                if (video != null) {
                    CartItem_24133012 item = cart.get(videoId.trim());
                    if (item != null) {
                        item.setQuantity(item.getQuantity() + 1);
                    } else {
                        cart.put(videoId.trim(), new CartItem_24133012(video, 1, video.getPrice()));
                    }
                    updateCartTotals(session, cart);
                }
            }
            String redirect = req.getParameter("redirect");
            if ("home".equals(redirect)) {
                resp.sendRedirect(req.getContextPath() + "/home?added=true");
                return;
            } else if ("detail".equals(redirect)) {
                resp.sendRedirect(req.getContextPath() + "/video/detail?id=" + videoId + "&added=true");
                return;
            }
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;

        } else if ("/cart/update".equals(path)) {
            String videoId = req.getParameter("id");
            String action = req.getParameter("action");
            if (videoId != null && cart.containsKey(videoId)) {
                CartItem_24133012 item = cart.get(videoId);
                if ("inc".equals(action)) {
                    item.setQuantity(item.getQuantity() + 1); // Giới hạn tối đa 99 đã cài trong model
                } else if ("dec".equals(action)) {
                    if (item.getQuantity() > 1) {
                        item.setQuantity(item.getQuantity() - 1);
                    } else {
                        cart.remove(videoId);
                    }
                } else {
                    String qtyStr = req.getParameter("qty");
                    if (qtyStr != null) {
                        try {
                            int qty = Integer.parseInt(qtyStr);
                            if (qty <= 0) {
                                cart.remove(videoId);
                            } else {
                                item.setQuantity(qty);
                            }
                        } catch (NumberFormatException ignored) {
                        }
                    }
                }
                updateCartTotals(session, cart);
            }
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;

        } else if ("/cart/delete".equals(path)) {
            String videoId = req.getParameter("id");
            if (videoId != null) {
                cart.remove(videoId);
                updateCartTotals(session, cart);
            }
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;

        } else if ("/cart/clear".equals(path)) {
            cart.clear();
            updateCartTotals(session, cart);
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }

        // Xem trang giỏ hàng
        updateCartTotals(session, cart);
        req.getRequestDispatcher("/views/web/cart.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
