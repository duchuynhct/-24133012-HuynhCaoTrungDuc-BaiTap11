<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<!DOCTYPE html>
<html>
<head>
    <title>${video.title} - Chi Tiết Video (24133012)</title>
    <style>
        .detail-box {
            border: 2px solid #333;
            background: #fff;
            padding: 20px;
            margin-top: 25px;
            border-radius: 4px;
        }
        .poster-box {
            border: 1px solid #999;
            background-color: #000;
            display: flex;
            align-items: center;
            justify-content: center;
            height: 280px;
            overflow: hidden;
            border-radius: 4px;
        }
        .poster-box img {
            width: 100%;
            height: 100%;
            object-fit: cover;
        }
        .info-item {
            font-size: 1.15rem;
            margin-bottom: 12px;
        }
        .desc-box {
            border-top: 1px solid #ddd;
            margin-top: 20px;
            padding-top: 15px;
            font-size: 1rem;
            line-height: 1.6;
        }
    </style>
</head>
<body>
    <div class="container my-4">
        <!-- Breadcrumb navigation -->
        <nav aria-label="breadcrumb">
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/home" class="text-decoration-none">Trang Chủ</a></li>
                <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/home?categoryId=${video.category.categoryId}" class="text-decoration-none">${video.category.categoryname}</a></li>
                <li class="breadcrumb-item active" aria-current="page">${video.title}</li>
            </ol>
        </nav>

        <c:if test="${param.shared == 'true'}">
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <i class="fa-solid fa-circle-check me-2"></i>Chia sẻ video thành công!
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>

        <!-- Khung hiển thị chi tiết 01 video chuẩn theo mẫu Câu 3 đề thi -->
        <div class="detail-box shadow-sm">
            <div class="row align-items-center">
                <!-- [poster] bên trái -->
                <div class="col-md-5">
                    <div class="poster-box">
                        <c:choose>
                            <c:when test="${not empty video.poster and video.poster.startsWith('http')}">
                                <img src="${video.poster}" alt="${video.title}">
                            </c:when>
                            <c:when test="${not empty video.poster}">
                                <img src="${pageContext.request.contextPath}/image?fname=${video.poster}" alt="${video.title}">
                            </c:when>
                            <c:otherwise>
                                <img src="https://picsum.photos/seed/${video.videoId}/400/280" alt="${video.title}">
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>

                <!-- Thông tin chi tiết bên phải theo đúng đề thi -->
                <div class="col-md-7 ps-md-4 mt-3 mt-md-0">
                    <div class="info-item">
                        <b>Tiêu đề:</b> <span class="text-primary fw-bold">${video.title}</span>
                    </div>

                    <div class="info-item">
                        <b>Mã video:</b> <span class="badge bg-secondary font-monospace">${video.videoId}</span>
                    </div>

                    <div class="info-item">
                        <b>Category name:</b> <span class="badge bg-info text-dark">${video.category.categoryname}</span>
                    </div>

                    <div class="info-item">
                        <b>View:</b> <span class="fw-bold text-danger">${video.views}</span> lượt xem
                    </div>

                    <div class="info-item">
                        <b>Giá bán:</b> 
                        <span class="fs-4 fw-bold text-success ms-2">
                            <fmt:formatNumber value="${video.price}" type="currency" currencySymbol="₫" maxFractionDigits="0"/>
                        </span>
                    </div>

                    <!-- Nút Thêm vào giỏ & Mua ngay -->
                    <div class="info-item mt-3 d-flex flex-wrap gap-2 align-items-center">
                        <a href="${pageContext.request.contextPath}/cart/add?id=${video.videoId}&redirect=detail" 
                           class="btn btn-primary px-3 py-2 fw-semibold shadow-sm">
                            <i class="fa-solid fa-cart-plus me-1"></i>Thêm Vào Giỏ Hàng
                        </a>
                        <a href="${pageContext.request.contextPath}/cart/add?id=${video.videoId}" 
                           class="btn btn-warning px-3 py-2 fw-semibold text-dark shadow-sm">
                            <i class="fa-solid fa-bolt me-1"></i>Mua Ngay (COD)
                        </a>
                    </div>

                    <!-- Share(10) và Like(10) theo đúng format đề thi -->
                    <div class="info-item mt-3 pt-2 border-top d-flex gap-3 align-items-center">
                        <!-- Nút Share(count) -->
                        <button type="button" class="btn btn-sm btn-outline-primary" data-bs-toggle="modal" data-bs-target="#shareModal">
                            <i class="fa-solid fa-share-nodes me-1"></i>Share(${video.shareCount})
                        </button>

                        <!-- Nút Like(count) -->
                        <a href="${pageContext.request.contextPath}/video/like?id=${video.videoId}" 
                           class="btn btn-sm ${isLiked ? 'btn-danger' : 'btn-outline-danger'}">
                            <i class="fa-${isLiked ? 'solid' : 'regular'} fa-heart me-1"></i>Like(${video.likeCount})
                        </a>
                    </div>
                </div>
            </div>

            <!-- description nằm phía dưới theo đúng đề thi -->
            <div class="desc-box">
                <h5 class="fw-bold text-dark"><i class="fa-solid fa-align-left me-2 text-secondary"></i>description:</h5>
                <p class="text-secondary mb-0">
                    ${not empty video.description ? video.description : 'Không có mô tả chi tiết cho video này.'}
                </p>
            </div>
        </div>

        <div class="mt-4">
            <a href="${pageContext.request.contextPath}/home" class="btn btn-outline-secondary">
                <i class="fa-solid fa-arrow-left me-1"></i>Về Trang Chủ
            </a>
        </div>
    </div>

    <!-- Modal Chia Sẻ Video -->
    <div class="modal fade" id="shareModal" tabindex="-1" aria-labelledby="shareModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <form action="${pageContext.request.contextPath}/video/share" method="post">
                    <input type="hidden" name="videoId" value="${video.videoId}">
                    <div class="modal-header">
                        <h5 class="modal-title" id="shareModalLabel"><i class="fa-solid fa-share-nodes me-2 text-primary"></i>Chia Sẻ Video</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <p class="text-muted">Nhập email người nhận để chia sẻ video <b>${video.title}</b>:</p>
                        <div class="mb-3">
                            <label class="form-label fw-semibold">Email người nhận:</label>
                            <input type="email" name="email" class="form-control" placeholder="friend@example.com" required>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                        <button type="submit" class="btn btn-primary">Chia Sẻ Ngay</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
