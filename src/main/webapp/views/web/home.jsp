<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt"%>
<!DOCTYPE html>
<html>
<head>
    <title>Trang Chủ - VideoHub (24133012)</title>
    <style>
        .category-tab-btn {
            font-size: 1.05rem;
            font-weight: 600;
            padding: 10px 20px;
            border-radius: 30px;
            transition: all 0.2s;
        }
        .video-box-table {
            border: 1px solid #dee2e6;
            background: #fff;
            padding: 15px;
            border-radius: 6px;
            height: 100%;
        }
        .poster-frame {
            border: 1px solid #bbb;
            background: #000;
            height: 180px;
            display: flex;
            align-items: center;
            justify-content: center;
            overflow: hidden;
            margin-bottom: 12px;
            border-radius: 4px;
        }
        .poster-frame img {
            width: 100%;
            height: 100%;
            object-fit: cover;
        }
        .video-info-line {
            margin-bottom: 6px;
            font-size: 0.95rem;
        }
    </style>
</head>
<body>
    <div class="container my-4" id="products">

        <!-- Banner Chào Mừng -->
        <div class="p-4 mb-4 bg-primary text-white rounded-3 shadow-sm">
            <div class="container-fluid py-2">
                <h2 class="display-6 fw-bold"><i class="fa-solid fa-play text-warning me-3"></i>Khám Phá Các Video Học Tập &amp; Giải Trí</h2>
                <p class="col-md-9 fs-5 mb-0">Hệ thống bài tập Lập Trình Web - Đề số 03 | Sinh viên: Huỳnh Cao Trung Đức - MSSV: 24133012</p>
            </div>
        </div>

        <!-- Thanh danh mục Category hiển thị số lượng theo Câu 5 (Đếm số lượng Video theo từng Category) -->
        <div class="card shadow-sm border-0 mb-4">
            <div class="card-body">
                <h5 class="fw-bold text-secondary mb-3">
                    <i class="fa-solid fa-tags me-2 text-primary"></i>Chọn Danh Mục Video (Câu 5 - Đếm số lượng video):
                </h5>
                <div class="d-flex flex-wrap gap-2">
                    <c:forEach items="${categories}" var="cat">
                        <a href="${pageContext.request.contextPath}/home?categoryId=${cat.categoryId}" 
                           class="btn category-tab-btn ${selectedCategory.categoryId == cat.categoryId ? 'btn-primary text-white shadow-sm' : 'btn-outline-primary'}">
                            <i class="fa-solid fa-folder-open me-1"></i>${cat.categoryname} 
                            <!-- Đếm số lượng video: Category Name (Count) -->
                            <span class="badge ${selectedCategory.categoryId == cat.categoryId ? 'bg-light text-primary' : 'bg-primary text-white'} ms-1">
                                ${cat.videoCount}
                            </span>
                        </a>
                    </c:forEach>
                </div>
            </div>
        </div>

        <!-- Khối hiển thị theo mẫu đề thi Câu 4 & Câu 5:
             Category Name (20)
             [poster] [poster] [poster]
             ...
             << 1 2 3 4 5 >>
        -->
        <c:if test="${not empty selectedCategory}">
            <div class="card shadow-sm border-0 mb-4">
                <div class="card-header bg-white py-3 border-bottom">
                    <!-- Tiêu đề đúng theo định dạng đề bài: Category Name (20) -->
                    <h3 class="fw-bold text-dark mb-0">
                        <i class="fa-solid fa-folder text-warning me-2"></i>${selectedCategory.categoryname} (${totalVideosInCat})
                    </h3>
                </div>

                <div class="card-body p-4">
                    <c:choose>
                        <c:when test="${not empty videos}">
                            <!-- Hiển thị 3 video trên 1 hàng (3 video / trang) theo mẫu đề thi -->
                            <div class="row g-4">
                                <c:forEach items="${videos}" var="video">
                                    <div class="col-md-4">
                                        <div class="video-box-table shadow-sm">
                                            <!-- [poster] -->
                                            <div class="poster-frame">
                                                <a href="${pageContext.request.contextPath}/video/detail?id=${video.videoId}">
                                                    <c:choose>
                                                        <c:when test="${not empty video.poster and video.poster.startsWith('http')}">
                                                            <img src="${video.poster}" alt="${video.title}">
                                                        </c:when>
                                                        <c:when test="${not empty video.poster}">
                                                            <img src="${pageContext.request.contextPath}/image?fname=${video.poster}" alt="${video.title}">
                                                        </c:when>
                                                        <c:otherwise>
                                                            <img src="https://picsum.photos/seed/${video.videoId}/300/200" alt="${video.title}">
                                                        </c:otherwise>
                                                    </c:choose>
                                                </a>
                                            </div>

                                            <!-- Các dòng thông tin chi tiết đúng theo mẫu đề thi -->
                                            <div class="video-info-line">
                                                <b>Tiêu đề:</b> 
                                                <a href="${pageContext.request.contextPath}/video/detail?id=${video.videoId}" class="text-decoration-none fw-bold text-dark d-block text-truncate" title="${video.title}">
                                                    ${video.title}
                                                </a>
                                            </div>

                                            <div class="video-info-line">
                                                <b>Mã video:</b> <span class="badge bg-secondary font-monospace">${video.videoId}</span>
                                            </div>

                                            <div class="video-info-line">
                                                <b>Category name:</b> <span class="badge bg-info text-dark">${video.category.categoryname}</span>
                                            </div>

                                            <div class="video-info-line d-flex justify-content-between align-items-center">
                                                <span><b>View:</b> <span class="text-danger fw-semibold">${video.views}</span></span>
                                                <span><b>Giá:</b> <span class="text-success fw-bold"><fmt:formatNumber value="${video.price}" type="currency" currencySymbol="₫" maxFractionDigits="0"/></span></span>
                                            </div>

                                            <div class="video-info-line d-flex justify-content-between align-items-center mt-3 pt-2 border-top">
                                                <a href="${pageContext.request.contextPath}/video/share?id=${video.videoId}&redirect=home&categoryId=${selectedCategory.categoryId}&page=${currentPage}" 
                                                   class="btn btn-sm btn-outline-primary" title="Bấm để tăng lượt Share thực tế">
                                                    <i class="fa-solid fa-share-nodes me-1"></i>Share(${video.shareCount})
                                                </a>
                                                <a href="${pageContext.request.contextPath}/video/like?id=${video.videoId}&redirect=home&categoryId=${selectedCategory.categoryId}&page=${currentPage}" 
                                                   class="btn btn-sm btn-outline-danger" title="Bấm để Like/Bỏ Like thực tế">
                                                    <i class="fa-solid fa-heart me-1"></i>Like(${video.likeCount})
                                                </a>
                                                <a href="${pageContext.request.contextPath}/video/detail?id=${video.videoId}" class="btn btn-sm btn-outline-dark">
                                                    Chi tiết <i class="fa-solid fa-arrow-right ms-1"></i>
                                                </a>
                                            </div>

                                            <div class="mt-2">
                                                <a href="${pageContext.request.contextPath}/cart/add?id=${video.videoId}&redirect=home" 
                                                   class="btn btn-sm btn-primary w-100 fw-semibold shadow-sm">
                                                    <i class="fa-solid fa-cart-plus me-1"></i>Thêm Vào Giỏ
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>

                            <!-- Thanh phân trang << 1 2 3 4 5 >> theo mẫu đề thi -->
                            <div class="d-flex justify-content-center mt-5">
                                <nav aria-label="Category pagination">
                                    <ul class="pagination pagination-lg">
                                        <!-- << Nút Previous -->
                                        <li class="page-item ${currentPage <= 1 ? 'disabled' : ''}">
                                            <a class="page-link" href="${pageContext.request.contextPath}/home?categoryId=${selectedCategory.categoryId}&page=${currentPage - 1}" aria-label="Previous">
                                                <span aria-hidden="true">&laquo;</span>
                                            </a>
                                        </li>

                                        <!-- Các số trang 1 2 3 4 5 -->
                                        <c:forEach begin="1" end="${totalPages}" var="i">
                                            <li class="page-item ${currentPage == i ? 'active' : ''}">
                                                <a class="page-link" href="${pageContext.request.contextPath}/home?categoryId=${selectedCategory.categoryId}&page=${i}">
                                                    ${i}
                                                </a>
                                            </li>
                                        </c:forEach>

                                        <!-- >> Nút Next -->
                                        <li class="page-item ${currentPage >= totalPages ? 'disabled' : ''}">
                                            <a class="page-link" href="${pageContext.request.contextPath}/home?categoryId=${selectedCategory.categoryId}&page=${currentPage + 1}" aria-label="Next">
                                                <span aria-hidden="true">&raquo;</span>
                                            </a>
                                        </li>
                                    </ul>
                                </nav>
                            </div>
                            <div class="text-center text-muted small mt-1">
                                Phân trang 3 video / trang (Đang xem trang ${currentPage} trên tổng số ${totalPages} trang)
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="text-center py-5 text-muted">
                                <i class="fa-solid fa-film fa-3x mb-3 text-secondary"></i>
                                <p class="fs-5">Chưa có video nào trong danh mục này.</p>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </c:if>

    </div>
</body>
</html>
