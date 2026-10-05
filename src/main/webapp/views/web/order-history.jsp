<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<!DOCTYPE html>
<html>
<head>
    <title>Lịch Sử Đặt Hàng - VideoHub (24133012)</title>
    <style>
        .nav-tabs .nav-link {
            font-weight: 600;
            color: #495057;
            padding: 10px 14px;
            white-space: nowrap;
        }
        .nav-tabs .nav-link.active {
            color: #0d6efd;
            border-bottom: 3px solid #0d6efd;
            background-color: #f8f9fa;
        }
        .order-item-img {
            width: 70px;
            height: 48px;
            object-fit: cover;
            border-radius: 4px;
        }
    </style>
</head>
<body>
    <div class="container my-4">
        <!-- Breadcrumb -->
        <nav aria-label="breadcrumb">
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/home" class="text-decoration-none">Trang Chủ</a></li>
                <li class="breadcrumb-item active" aria-current="page">Lịch Sử Đặt Hàng</li>
            </ol>
        </nav>

        <c:if test="${param.placed == 'true'}">
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <i class="fa-solid fa-circle-check me-2"></i>
                <b>Đặt hàng thành công!</b> Đơn hàng mã <b>#${param.orderId}</b> của bạn đã được ghi nhận với phương thức thanh toán COD.
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>

        <div class="d-flex justify-content-between align-items-center mb-3">
            <div>
                <h3 class="fw-bold text-dark mb-1">
                    <i class="fa-solid fa-clock-rotate-left text-primary me-2"></i>Lịch Sử Đặt Hàng
                </h3>
                <p class="text-muted small mb-0">Theo dõi trạng thái và chi tiết các đơn hàng của bạn</p>
            </div>
            <a href="${pageContext.request.contextPath}/home" class="btn btn-outline-primary btn-sm">
                <i class="fa-solid fa-plus-circle me-1"></i>Mua Thêm Sản Phẩm
            </a>
        </div>

        <!-- Khung hướng dẫn thay đổi trạng thái trong CSDL -->
        <div class="alert alert-info border-0 shadow-sm py-2 px-3 mb-4">
            <small>
                <i class="fa-solid fa-circle-info me-1 text-primary"></i>
                <b>Mẹo kiểm tra trạng thái:</b> Bạn có thể vào database cập nhật cột <code>Status</code> trong bảng <code>Orders</code> (ví dụ: <code>UPDATE dbo.Orders SET Status = N'Đã giao' WHERE OrderId = 1</code>) rồi tải lại trang để quan sát đơn hàng tự động chuyển sang tab tương ứng.
            </small>
        </div>

        <!-- 8 Tabs lọc trạng thái + Tab Tất cả -->
        <div class="card shadow-sm border-0 mb-4">
            <div class="card-header bg-white p-0 border-bottom">
                <ul class="nav nav-tabs nav-fill flex-nowrap overflow-auto" id="orderStatusTabs" role="tablist">
                    <!-- Tab Tất cả -->
                    <li class="nav-item">
                        <a class="nav-link ${selectedStatus == 'ALL' ? 'active' : ''}" 
                           href="${pageContext.request.contextPath}/order/history?status=ALL">
                            Tất cả
                            <span class="badge ${selectedStatus == 'ALL' ? 'bg-primary' : 'bg-secondary'} ms-1">
                                ${statusCountMap['ALL'] != null ? statusCountMap['ALL'] : 0}
                            </span>
                        </a>
                    </li>
                    <!-- 1. Đơn hàng mới -->
                    <li class="nav-item">
                        <a class="nav-link ${selectedStatus == 'Đơn hàng mới' ? 'active' : ''}" 
                           href="${pageContext.request.contextPath}/order/history?status=Đơn hàng mới">
                            Đơn hàng mới
                            <span class="badge ${selectedStatus == 'Đơn hàng mới' ? 'bg-primary' : 'bg-secondary'} ms-1">
                                ${statusCountMap['Đơn hàng mới'] != null ? statusCountMap['Đơn hàng mới'] : 0}
                            </span>
                        </a>
                    </li>
                    <!-- 2. Đã xác nhận -->
                    <li class="nav-item">
                        <a class="nav-link ${selectedStatus == 'Đã xác nhận' ? 'active' : ''}" 
                           href="${pageContext.request.contextPath}/order/history?status=Đã xác nhận">
                            Đã xác nhận
                            <span class="badge ${selectedStatus == 'Đã xác nhận' ? 'bg-primary' : 'bg-secondary'} ms-1">
                                ${statusCountMap['Đã xác nhận'] != null ? statusCountMap['Đã xác nhận'] : 0}
                            </span>
                        </a>
                    </li>
                    <!-- 3. Chuẩn bị hàng -->
                    <li class="nav-item">
                        <a class="nav-link ${selectedStatus == 'Chuẩn bị hàng' ? 'active' : ''}" 
                           href="${pageContext.request.contextPath}/order/history?status=Chuẩn bị hàng">
                            Chuẩn bị hàng
                            <span class="badge ${selectedStatus == 'Chuẩn bị hàng' ? 'bg-primary' : 'bg-secondary'} ms-1">
                                ${statusCountMap['Chuẩn bị hàng'] != null ? statusCountMap['Chuẩn bị hàng'] : 0}
                            </span>
                        </a>
                    </li>
                    <!-- 4. Vận chuyển -->
                    <li class="nav-item">
                        <a class="nav-link ${selectedStatus == 'Vận chuyển' ? 'active' : ''}" 
                           href="${pageContext.request.contextPath}/order/history?status=Vận chuyển">
                            Vận chuyển
                            <span class="badge ${selectedStatus == 'Vận chuyển' ? 'bg-primary' : 'bg-secondary'} ms-1">
                                ${statusCountMap['Vận chuyển'] != null ? statusCountMap['Vận chuyển'] : 0}
                            </span>
                        </a>
                    </li>
                    <!-- 5. Giao hàng -->
                    <li class="nav-item">
                        <a class="nav-link ${selectedStatus == 'Giao hàng' ? 'active' : ''}" 
                           href="${pageContext.request.contextPath}/order/history?status=Giao hàng">
                            Giao hàng
                            <span class="badge ${selectedStatus == 'Giao hàng' ? 'bg-primary' : 'bg-secondary'} ms-1">
                                ${statusCountMap['Giao hàng'] != null ? statusCountMap['Giao hàng'] : 0}
                            </span>
                        </a>
                    </li>
                    <!-- 6. Đã giao -->
                    <li class="nav-item">
                        <a class="nav-link ${selectedStatus == 'Đã giao' ? 'active' : ''}" 
                           href="${pageContext.request.contextPath}/order/history?status=Đã giao">
                            Đã giao
                            <span class="badge ${selectedStatus == 'Đã giao' ? 'bg-primary' : 'bg-secondary'} ms-1">
                                ${statusCountMap['Đã giao'] != null ? statusCountMap['Đã giao'] : 0}
                            </span>
                        </a>
                    </li>
                    <!-- 7. Đơn hàng hủy -->
                    <li class="nav-item">
                        <a class="nav-link ${selectedStatus == 'Đơn hàng hủy' ? 'active' : ''}" 
                           href="${pageContext.request.contextPath}/order/history?status=Đơn hàng hủy">
                            Đơn hàng hủy
                            <span class="badge ${selectedStatus == 'Đơn hàng hủy' ? 'bg-primary' : 'bg-secondary'} ms-1">
                                ${statusCountMap['Đơn hàng hủy'] != null ? statusCountMap['Đơn hàng hủy'] : 0}
                            </span>
                        </a>
                    </li>
                    <!-- 8. Đơn hàng hoàn -->
                    <li class="nav-item">
                        <a class="nav-link ${selectedStatus == 'Đơn hàng hoàn' ? 'active' : ''}" 
                           href="${pageContext.request.contextPath}/order/history?status=Đơn hàng hoàn">
                            Đơn hàng hoàn
                            <span class="badge ${selectedStatus == 'Đơn hàng hoàn' ? 'bg-primary' : 'bg-secondary'} ms-1">
                                ${statusCountMap['Đơn hàng hoàn'] != null ? statusCountMap['Đơn hàng hoàn'] : 0}
                            </span>
                        </a>
                    </li>
                </ul>
            </div>

            <!-- Danh sách đơn hàng trong Tab được chọn -->
            <div class="card-body p-4 bg-light">
                <c:choose>
                    <c:when test="${not empty orders}">
                        <div class="d-flex flex-column gap-4">
                            <c:forEach items="${orders}" var="order">
                                <div class="card shadow-sm border-0 bg-white">
                                    <!-- Header Đơn hàng -->
                                    <div class="card-header bg-white py-3 d-flex flex-wrap justify-content-between align-items-center border-bottom">
                                        <div>
                                            <span class="fw-bold text-dark fs-6 me-2">Đơn hàng #${order.orderId}</span>
                                            <span class="text-muted small">
                                                <i class="fa-regular fa-calendar-days me-1"></i>
                                                <fmt:formatDate value="${order.orderDate}" pattern="dd/MM/yyyy HH:mm"/>
                                            </span>
                                        </div>
                                        <div>
                                            <!-- Badge trạng thái 8 màu -->
                                            <c:choose>
                                                <c:when test="${order.status == 'Đơn hàng mới'}">
                                                    <span class="badge bg-primary fs-6 px-3 py-2"><i class="fa-solid fa-sparkles me-1"></i>Đơn hàng mới</span>
                                                </c:when>
                                                <c:when test="${order.status == 'Đã xác nhận'}">
                                                    <span class="badge bg-info text-dark fs-6 px-3 py-2"><i class="fa-solid fa-clipboard-check me-1"></i>Đã xác nhận</span>
                                                </c:when>
                                                <c:when test="${order.status == 'Chuẩn bị hàng'}">
                                                    <span class="badge bg-warning text-dark fs-6 px-3 py-2"><i class="fa-solid fa-box-archive me-1"></i>Chuẩn bị hàng</span>
                                                </c:when>
                                                <c:when test="${order.status == 'Vận chuyển'}">
                                                    <span class="badge bg-secondary fs-6 px-3 py-2"><i class="fa-solid fa-truck me-1"></i>Vận chuyển</span>
                                                </c:when>
                                                <c:when test="${order.status == 'Giao hàng'}">
                                                    <span class="badge bg-primary text-white fs-6 px-3 py-2" style="background-color: #6f42c1 !important;"><i class="fa-solid fa-motorcycle me-1"></i>Giao hàng</span>
                                                </c:when>
                                                <c:when test="${order.status == 'Đã giao'}">
                                                    <span class="badge bg-success fs-6 px-3 py-2"><i class="fa-solid fa-circle-check me-1"></i>Đã giao</span>
                                                </c:when>
                                                <c:when test="${order.status == 'Đơn hàng hủy'}">
                                                    <span class="badge bg-danger fs-6 px-3 py-2"><i class="fa-solid fa-ban me-1"></i>Đơn hàng hủy</span>
                                                </c:when>
                                                <c:when test="${order.status == 'Đơn hàng hoàn'}">
                                                    <span class="badge bg-dark fs-6 px-3 py-2"><i class="fa-solid fa-rotate-left me-1"></i>Đơn hàng hoàn</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="badge bg-secondary fs-6 px-3 py-2">${order.status}</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>

                                    <!-- Danh sách sản phẩm trong đơn -->
                                    <div class="card-body p-3">
                                        <div class="table-responsive">
                                            <table class="table table-borderless align-middle mb-0">
                                                <tbody>
                                                    <c:forEach items="${order.orderDetails}" var="detail">
                                                        <tr class="border-bottom">
                                                            <td style="width: 80px;">
                                                                <c:choose>
                                                                    <c:when test="${not empty detail.video.poster and detail.video.poster.startsWith('http')}">
                                                                        <img src="${detail.video.poster}" alt="${detail.video.title}" class="order-item-img shadow-sm">
                                                                    </c:when>
                                                                    <c:when test="${not empty detail.video.poster}">
                                                                        <img src="${pageContext.request.contextPath}/image?fname=${detail.video.poster}" alt="${detail.video.title}" class="order-item-img shadow-sm">
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <img src="https://picsum.photos/seed/${detail.video.videoId}/100/70" alt="${detail.video.title}" class="order-item-img shadow-sm">
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </td>
                                                            <td>
                                                                <a href="${pageContext.request.contextPath}/video/detail?id=${detail.video.videoId}" class="text-decoration-none fw-bold text-dark">
                                                                    ${detail.video.title}
                                                                </a>
                                                                <div class="small text-muted">
                                                                    Mã video: <span class="badge bg-light text-dark border font-monospace">${detail.video.videoId}</span>
                                                                </div>
                                                            </td>
                                                            <td class="text-center text-muted" style="width: 120px;">
                                                                Số lượng: <b>x${detail.quantity}</b>
                                                            </td>
                                                            <td class="text-end fw-semibold" style="width: 140px;">
                                                                <fmt:formatNumber value="${detail.price}" type="currency" currencySymbol="₫" maxFractionDigits="0"/>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>

                                    <!-- Footer Đơn hàng: Thông tin nhận hàng & Tổng tiền -->
                                    <div class="card-footer bg-white py-3 border-top">
                                        <div class="row align-items-center">
                                            <div class="col-md-7 small text-muted">
                                                <div><i class="fa-solid fa-user me-1 text-primary"></i>Người nhận: <b>${order.fullname}</b> | SĐT: <b>${order.phone}</b></div>
                                                <div><i class="fa-solid fa-location-dot me-1 text-danger"></i>Địa chỉ: ${order.address}</div>
                                                <div><i class="fa-solid fa-money-bill me-1 text-success"></i>Thanh toán: <span class="badge bg-light text-dark border">COD (Tiền mặt khi nhận hàng)</span></div>
                                                <c:if test="${not empty order.note}">
                                                    <div><i class="fa-solid fa-comment-dots me-1 text-secondary"></i>Ghi chú: <i>${order.note}</i></div>
                                                </c:if>
                                            </div>
                                            <div class="col-md-5 text-md-end mt-2 mt-md-0">
                                                <span class="text-muted me-2">Tổng tiền:</span>
                                                <span class="fs-4 fw-bold text-danger">
                                                    <fmt:formatNumber value="${order.totalAmount}" type="currency" currencySymbol="₫" maxFractionDigits="0"/>
                                                </span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </c:when>

                    <c:otherwise>
                        <div class="text-center py-5">
                            <i class="fa-solid fa-box-open fa-3x text-muted mb-3"></i>
                            <h5 class="fw-bold text-dark">Chưa có đơn hàng nào trong trạng thái "${selectedStatus}"</h5>
                            <p class="text-muted small mb-3">Bạn có thể chọn tab khác hoặc vào cơ sở dữ liệu thay đổi trạng thái của đơn hàng để kiểm tra.</p>
                            <a href="${pageContext.request.contextPath}/order/history?status=ALL" class="btn btn-outline-primary btn-sm">
                                Xem Tất Cả Đơn Hàng
                            </a>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</body>
</html>
