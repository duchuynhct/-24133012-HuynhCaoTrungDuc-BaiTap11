<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
    <title>Quản Lý Videos (Phân Trang 6 Video/Trang)</title>
</head>
<body>
    <div class="d-flex justify-content-between align-items-center mb-4">
        <div>
            <h3 class="fw-bold text-dark mb-0">
                <i class="fa-solid fa-list-check text-primary me-2"></i>Danh Sách Quản Trị Videos
            </h3>
            <p class="text-muted small mb-0">Quản lý CRUD dữ liệu bảng Videos - Phân trang 6 video/trang (Câu 2 - 2.5đ)</p>
        </div>
        <a href="${pageContext.request.contextPath}/admin/video/add" class="btn btn-primary shadow-sm fw-semibold">
            <i class="fa-solid fa-plus-circle me-1"></i>Thêm Video Mới
        </a>
    </div>

    <!-- Alert notifications -->
    <c:if test="${not empty success}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            <i class="fa-solid fa-circle-check me-2"></i>${success}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            <i class="fa-solid fa-circle-exclamation me-2"></i>${error}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    </c:if>

    <div class="card shadow-sm border-0 mb-4">
        <div class="card-body p-0">
            <div class="table-responsive">
                <table class="table table-hover align-middle mb-0">
                    <thead class="table-dark">
                        <tr>
                            <th class="text-center" style="width: 80px;">Poster</th>
                            <th style="width: 100px;">Mã Video</th>
                            <th>Tiêu Đề</th>
                            <th>Thể Loại</th>
                            <th class="text-center" style="width: 100px;">Lượt Xem</th>
                            <th class="text-center" style="width: 110px;">Trạng Thái</th>
                            <th class="text-center" style="width: 150px;">Thao Tác</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${not empty videos}">
                                <c:forEach items="${videos}" var="item">
                                    <tr>
                                        <td class="text-center">
                                            <c:choose>
                                                <c:when test="${not empty item.poster and item.poster.startsWith('http')}">
                                                    <img src="${item.poster}" alt="${item.title}" class="rounded shadow-sm" style="width: 60px; height: 40px; object-fit: cover;">
                                                </c:when>
                                                <c:when test="${not empty item.poster}">
                                                    <img src="${pageContext.request.contextPath}/image?fname=${item.poster}" alt="${item.title}" class="rounded shadow-sm" style="width: 60px; height: 40px; object-fit: cover;">
                                                </c:when>
                                                <c:otherwise>
                                                    <img src="https://picsum.photos/seed/${item.videoId}/120/80" alt="${item.title}" class="rounded shadow-sm" style="width: 60px; height: 40px; object-fit: cover;">
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td><span class="badge bg-secondary font-monospace">${item.videoId}</span></td>
                                        <td class="fw-semibold text-dark">
                                            <a href="${pageContext.request.contextPath}/video/detail?id=${item.videoId}" target="_blank" class="text-decoration-none text-dark">
                                                ${item.title}
                                            </a>
                                        </td>
                                        <td>
                                            <span class="badge bg-info text-dark">${item.category.categoryname}</span>
                                        </td>
                                        <td class="text-center fw-bold text-secondary">
                                            <i class="fa-solid fa-eye me-1 text-muted"></i>${item.views}
                                        </td>
                                        <td class="text-center">
                                            <c:choose>
                                                <c:when test="${item.active}">
                                                    <span class="badge bg-success"><i class="fa-solid fa-check me-1"></i>Active</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="badge bg-danger"><i class="fa-solid fa-xmark me-1"></i>Hidden</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td class="text-center">
                                            <div class="btn-group" role="group">
                                                <a href="${pageContext.request.contextPath}/admin/video/edit?id=${item.videoId}" class="btn btn-outline-warning btn-sm" title="Chỉnh sửa">
                                                    <i class="fa-solid fa-pen-to-square"></i>
                                                </a>
                                                <form action="${pageContext.request.contextPath}/admin/video/delete" method="post" style="display:inline;"
                                                      onsubmit="return confirm('Bạn có chắc chắn muốn xóa video [${item.videoId}] không?');">
                                                    <input type="hidden" name="id" value="${item.videoId}">
                                                    <button type="submit" class="btn btn-outline-danger btn-sm" title="Xóa video">
                                                        <i class="fa-solid fa-trash-can"></i>
                                                    </button>
                                                </form>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <td colspan="7" class="text-center py-4 text-muted">
                                        <i class="fa-solid fa-film fa-2x mb-2 d-block"></i>
                                        Không có video nào trong hệ thống!
                                    </td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </div>
        </div>

        <!-- Phân trang 6 video/trang theo yêu cầu đề Câu 2b -->
        <div class="card-footer bg-white d-flex justify-content-between align-items-center py-3">
            <span class="text-muted small">
                Hiển thị trang <b>${currentPage}</b> / <b>${totalPages}</b> (Tổng số <b>${totalVideos}</b> video, 6 video/trang)
            </span>
            <nav aria-label="Page navigation">
                <ul class="pagination pagination-sm mb-0">
                    <!-- Nút Previous -->
                    <li class="page-item ${currentPage <= 1 ? 'disabled' : ''}">
                        <a class="page-link" href="${pageContext.request.contextPath}/admin/videos?page=${currentPage - 1}">
                            <i class="fa-solid fa-angles-left"></i>
                        </a>
                    </li>

                    <!-- Các số trang -->
                    <c:forEach begin="1" end="${totalPages}" var="i">
                        <li class="page-item ${currentPage == i ? 'active' : ''}">
                            <a class="page-link" href="${pageContext.request.contextPath}/admin/videos?page=${i}">${i}</a>
                        </li>
                    </c:forEach>

                    <!-- Nút Next -->
                    <li class="page-item ${currentPage >= totalPages ? 'disabled' : ''}">
                        <a class="page-link" href="${pageContext.request.contextPath}/admin/videos?page=${currentPage + 1}">
                            <i class="fa-solid fa-angles-right"></i>
                        </a>
                    </li>
                </ul>
            </nav>
        </div>
    </div>
</body>
</html>
