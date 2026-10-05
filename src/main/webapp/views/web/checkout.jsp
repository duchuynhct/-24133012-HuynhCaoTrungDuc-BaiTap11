<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<!DOCTYPE html>
<html>
<head>
    <title>Thanh Toán Đơn Hàng COD - VideoHub (24133012)</title>
</head>
<body>
    <div class="container my-4">
        <!-- Breadcrumb -->
        <nav aria-label="breadcrumb">
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/home" class="text-decoration-none">Trang Chủ</a></li>
                <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/cart" class="text-decoration-none">Giỏ Hàng</a></li>
                <li class="breadcrumb-item active" aria-current="page">Thanh Toán Đơn Hàng</li>
            </ol>
        </nav>

        <h3 class="fw-bold text-dark mb-4">
            <i class="fa-solid fa-credit-card text-primary me-2"></i>Thanh Toán Đơn Hàng (COD)
        </h3>

        <c:if test="${not empty error}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <i class="fa-solid fa-circle-exclamation me-2"></i>${error}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>

        <form action="${pageContext.request.contextPath}/checkout" method="post">
            <div class="row g-4">
                <!-- Thông tin giao hàng -->
                <div class="col-lg-7">
                    <div class="card shadow-sm border-0 mb-4">
                        <div class="card-header bg-white py-3 border-bottom">
                            <h5 class="fw-bold mb-0 text-primary">
                                <i class="fa-solid fa-location-dot me-2"></i>Thông Tin Giao Hàng
                            </h5>
                        </div>
                        <div class="card-body p-4">
                            <div class="mb-3">
                                <label class="form-label fw-semibold">Họ và tên người nhận: <span class="text-danger">*</span></label>
                                <input type="text" name="fullname" class="form-control" 
                                       value="${not empty param.fullname ? param.fullname : sessionScope.currentUser.fullname}" required>
                            </div>

                            <div class="mb-3">
                                <label class="form-label fw-semibold">Số điện thoại liên hệ: <span class="text-danger">*</span></label>
                                <input type="tel" name="phone" class="form-control" 
                                       value="${not empty param.phone ? param.phone : sessionScope.currentUser.phone}" required>
                            </div>

                            <div class="mb-3">
                                <label class="form-label fw-semibold">Địa chỉ nhận hàng chi tiết: <span class="text-danger">*</span></label>
                                <textarea name="address" rows="3" class="form-control" placeholder="Số nhà, tên đường, phường/xã, quận/huyện, tỉnh/thành phố..." required>${param.address}</textarea>
                            </div>

                            <div class="mb-3">
                                <label class="form-label fw-semibold">Ghi chú giao hàng (Tùy chọn):</label>
                                <textarea name="note" rows="2" class="form-control" placeholder="Ví dụ: Giao giờ hành chính, gọi trước khi giao...">${param.note}</textarea>
                            </div>
                        </div>
                    </div>

                    <!-- Phương thức thanh toán COD -->
                    <div class="card shadow-sm border-0">
                        <div class="card-header bg-white py-3 border-bottom">
                            <h5 class="fw-bold mb-0 text-primary">
                                <i class="fa-solid fa-money-bill-wave me-2"></i>Phương Thức Thanh Toán
                            </h5>
                        </div>
                        <div class="card-body p-4">
                            <div class="form-check p-3 border rounded border-primary bg-light">
                                <input class="form-check-input" type="radio" name="paymentMethod" id="codMethod" value="COD" checked>
                                <label class="form-check-label fw-bold text-dark d-block" for="codMethod">
                                    <i class="fa-solid fa-hand-holding-dollar text-success me-2 fs-5"></i>
                                    Thanh toán khi nhận hàng (COD)
                                </label>
                                <small class="text-muted d-block ms-4 mt-1">
                                    Bạn chỉ cần thanh toán tiền mặt trực tiếp cho nhân viên giao hàng khi đã nhận và kiểm tra kiện hàng.
                                </small>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Tóm tắt đơn hàng bên phải -->
                <div class="col-lg-5">
                    <div class="card shadow-sm border-0">
                        <div class="card-header bg-primary text-white py-3">
                            <h5 class="fw-bold mb-0"><i class="fa-solid fa-box-open me-2"></i>Sản Phẩm Đặt Mua (${sessionScope.cartTotalQuantity})</h5>
                        </div>
                        <div class="card-body p-4">
                            <ul class="list-group list-group-flush mb-3">
                                <c:forEach items="${sessionScope.cart.values()}" var="item">
                                    <li class="list-group-item d-flex justify-content-between align-items-center px-0 py-2">
                                        <div class="me-2">
                                            <div class="fw-semibold text-dark text-truncate" style="max-width: 250px;">
                                                ${item.video.title}
                                            </div>
                                            <small class="text-muted">SL: <b>x${item.quantity}</b> &bull; Đơn giá: <fmt:formatNumber value="${item.price}" type="currency" currencySymbol="₫" maxFractionDigits="0"/></small>
                                        </div>
                                        <span class="fw-bold text-dark">
                                            <fmt:formatNumber value="${item.subTotal}" type="currency" currencySymbol="₫" maxFractionDigits="0"/>
                                        </span>
                                    </li>
                                </c:forEach>
                            </ul>

                            <div class="d-flex justify-content-between mb-2">
                                <span class="text-muted">Tạm tính:</span>
                                <span class="fw-bold">
                                    <fmt:formatNumber value="${sessionScope.cartTotalAmount}" type="currency" currencySymbol="₫" maxFractionDigits="0"/>
                                </span>
                            </div>

                            <div class="d-flex justify-content-between mb-3 pb-3 border-bottom">
                                <span class="text-muted">Phí vận chuyển COD:</span>
                                <span class="text-success fw-bold">Miễn phí (0 ₫)</span>
                            </div>

                            <div class="d-flex justify-content-between align-items-center mb-4">
                                <span class="fs-5 fw-bold text-dark">Tổng cộng:</span>
                                <span class="fs-4 fw-bold text-danger">
                                    <fmt:formatNumber value="${sessionScope.cartTotalAmount}" type="currency" currencySymbol="₫" maxFractionDigits="0"/>
                                </span>
                            </div>

                            <div class="d-grid gap-2">
                                <button type="submit" class="btn btn-primary btn-lg fw-bold shadow-sm">
                                    <i class="fa-solid fa-check me-2"></i>Xác Nhận Đặt Hàng (COD)
                                </button>
                                <a href="${pageContext.request.contextPath}/cart" class="btn btn-outline-secondary">
                                    <i class="fa-solid fa-arrow-left me-1"></i>Quay Lại Giỏ Hàng
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</body>
</html>
