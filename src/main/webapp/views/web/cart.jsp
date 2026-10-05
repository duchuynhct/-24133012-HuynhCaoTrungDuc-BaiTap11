<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<!DOCTYPE html>
<html>
<head>
    <title>Giỏ Hàng - VideoHub (24133012)</title>
    <style>
        .cart-poster {
            width: 90px;
            height: 60px;
            object-fit: cover;
            border-radius: 4px;
        }
        .qty-btn {
            width: 32px;
            height: 32px;
            padding: 0;
            display: inline-flex;
            align-items: center;
            justify-content: center;
            font-weight: bold;
        }
        .qty-input {
            width: 50px;
            text-align: center;
            font-weight: 600;
        }
    </style>
</head>
<body>
    <div class="container my-4">
        <!-- Breadcrumb -->
        <nav aria-label="breadcrumb">
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/home" class="text-decoration-none">Trang Chủ</a></li>
                <li class="breadcrumb-item active" aria-current="page">Giỏ Hàng</li>
            </ol>
        </nav>

        <div class="d-flex justify-content-between align-items-center mb-4">
            <h3 class="fw-bold text-dark mb-0">
                <i class="fa-solid fa-cart-shopping text-primary me-2"></i>Giỏ Hàng Của Bạn
            </h3>
            <c:if test="${not empty sessionScope.cart and sessionScope.cart.size() > 0}">
                <a href="${pageContext.request.contextPath}/cart/clear" class="btn btn-outline-danger btn-sm" 
                   onclick="return confirm('Bạn có chắc chắn muốn xóa toàn bộ giỏ hàng không?');">
                    <i class="fa-solid fa-trash-can me-1"></i>Xóa Giỏ Hàng
                </a>
            </c:if>
        </div>

        <c:choose>
            <c:when test="${not empty sessionScope.cart and sessionScope.cart.size() > 0}">
                <div class="row g-4">
                    <!-- Danh sách món hàng -->
                    <div class="col-lg-8">
                        <div class="card shadow-sm border-0">
                            <div class="table-responsive">
                                <table class="table align-middle mb-0">
                                    <thead class="table-light">
                                        <tr>
                                            <th>Sản Phẩm</th>
                                            <th class="text-center" style="width: 140px;">Đơn Giá</th>
                                            <th class="text-center" style="width: 160px;">Số Lượng (1 - 99)</th>
                                            <th class="text-end" style="width: 150px;">Thành Tiền</th>
                                            <th class="text-center" style="width: 60px;">Xóa</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${sessionScope.cart.values()}" var="item">
                                            <tr>
                                                <td>
                                                    <div class="d-flex align-items-center">
                                                        <c:choose>
                                                            <c:when test="${not empty item.video.poster and item.video.poster.startsWith('http')}">
                                                                <img src="${item.video.poster}" alt="${item.video.title}" class="cart-poster me-3 shadow-sm">
                                                            </c:when>
                                                            <c:when test="${not empty item.video.poster}">
                                                                <img src="${pageContext.request.contextPath}/image?fname=${item.video.poster}" alt="${item.video.title}" class="cart-poster me-3 shadow-sm">
                                                            </c:when>
                                                            <c:otherwise>
                                                                <img src="https://picsum.photos/seed/${item.video.videoId}/120/80" alt="${item.video.title}" class="cart-poster me-3 shadow-sm">
                                                            </c:otherwise>
                                                        </c:choose>
                                                        <div>
                                                            <a href="${pageContext.request.contextPath}/video/detail?id=${item.video.videoId}" class="text-decoration-none fw-bold text-dark d-block">
                                                                ${item.video.title}
                                                            </a>
                                                            <span class="badge bg-secondary font-monospace">${item.video.videoId}</span>
                                                            <span class="badge bg-info text-dark">${item.video.category.categoryname}</span>
                                                        </div>
                                                    </div>
                                                </td>
                                                <td class="text-center fw-semibold text-secondary">
                                                    <fmt:formatNumber value="${item.price}" type="currency" currencySymbol="₫" maxFractionDigits="0"/>
                                                </td>
                                                <td class="text-center">
                                                    <!-- Bộ điều khiển số lượng trong giới hạn 1 - 99 -->
                                                    <div class="d-inline-flex align-items-center border rounded">
                                                        <a href="${pageContext.request.contextPath}/cart/update?id=${item.video.videoId}&action=dec" 
                                                           class="btn btn-light qty-btn border-end" title="Giảm số lượng">
                                                            <i class="fa-solid fa-minus small"></i>
                                                        </a>
                                                        <input type="text" class="form-control form-control-sm border-0 qty-input" value="${item.quantity}" readonly>
                                                        <a href="${pageContext.request.contextPath}/cart/update?id=${item.video.videoId}&action=inc" 
                                                           class="btn btn-light qty-btn border-start" title="Tăng số lượng (tối đa 99)">
                                                            <i class="fa-solid fa-plus small"></i>
                                                        </a>
                                                    </div>
                                                </td>
                                                <td class="text-end fw-bold text-danger">
                                                    <fmt:formatNumber value="${item.subTotal}" type="currency" currencySymbol="₫" maxFractionDigits="0"/>
                                                </td>
                                                <td class="text-center">
                                                    <a href="${pageContext.request.contextPath}/cart/delete?id=${item.video.videoId}" 
                                                       class="text-danger" title="Xóa món này khỏi giỏ">
                                                        <i class="fa-solid fa-trash-can"></i>
                                                    </a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>

                        <div class="mt-3">
                            <a href="${pageContext.request.contextPath}/home" class="btn btn-outline-secondary">
                                <i class="fa-solid fa-arrow-left me-1"></i>Tiếp Tục Chọn Sản Phẩm
                            </a>
                        </div>
                    </div>

                    <!-- Tóm tắt thanh toán -->
                    <div class="col-lg-4">
                        <div class="card shadow-sm border-0">
                            <div class="card-header bg-primary text-white py-3">
                                <h5 class="fw-bold mb-0"><i class="fa-solid fa-receipt me-2"></i>Tóm Tắt Đơn Hàng</h5>
                            </div>
                            <div class="card-body p-4">
                                <div class="d-flex justify-content-between mb-2">
                                    <span class="text-muted">Tổng số lượng:</span>
                                    <span class="fw-bold">${sessionScope.cartTotalQuantity} sản phẩm</span>
                                </div>
                                <div class="d-flex justify-content-between mb-2">
                                    <span class="text-muted">Tạm tính:</span>
                                    <span class="fw-bold">
                                        <fmt:formatNumber value="${sessionScope.cartTotalAmount}" type="currency" currencySymbol="₫" maxFractionDigits="0"/>
                                    </span>
                                </div>
                                <div class="d-flex justify-content-between mb-3 pb-3 border-bottom">
                                    <span class="text-muted">Phí giao hàng:</span>
                                    <span class="text-success fw-bold">Miễn phí</span>
                                </div>
                                <div class="d-flex justify-content-between align-items-center mb-4">
                                    <span class="fs-5 fw-bold">Tổng thanh toán:</span>
                                    <span class="fs-4 fw-bold text-danger">
                                        <fmt:formatNumber value="${sessionScope.cartTotalAmount}" type="currency" currencySymbol="₫" maxFractionDigits="0"/>
                                    </span>
                                </div>

                                <div class="d-grid gap-2">
                                    <a href="${pageContext.request.contextPath}/checkout" class="btn btn-primary btn-lg fw-bold shadow-sm">
                                        <i class="fa-solid fa-truck-fast me-2"></i>Thanh Toán COD
                                    </a>
                                </div>

                                <div class="text-muted small mt-3 text-center">
                                    <i class="fa-solid fa-shield-halved me-1 text-success"></i>Thanh toán tiền mặt khi nhận hàng an toàn 100%
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </c:when>

            <c:otherwise>
                <div class="card shadow-sm border-0 py-5 text-center">
                    <div class="card-body">
                        <i class="fa-solid fa-cart-shopping fa-4x text-muted mb-3"></i>
                        <h4 class="fw-bold text-dark">Giỏ hàng của bạn đang trống!</h4>
                        <p class="text-muted mb-4">Hãy thêm các khóa học / sản phẩm yêu thích vào giỏ hàng ngay nhé.</p>
                        <a href="${pageContext.request.contextPath}/home" class="btn btn-primary px-4 py-2 fw-semibold">
                            <i class="fa-solid fa-play me-2"></i>Khám Phá Sản Phẩm Ngay
                        </a>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>
