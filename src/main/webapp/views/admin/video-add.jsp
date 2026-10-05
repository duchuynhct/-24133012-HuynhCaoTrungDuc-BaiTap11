<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
    <title>Thêm Video Mới - Admin 24133012</title>
</head>
<body>
    <div class="d-flex justify-content-between align-items-center mb-4">
        <div>
            <h3 class="fw-bold text-dark mb-0">
                <i class="fa-solid fa-square-plus text-primary me-2"></i>Thêm Video Mới
            </h3>
            <p class="text-muted small mb-0">Nhập đầy đủ thông tin để tạo mới một video vào hệ thống</p>
        </div>
        <a href="${pageContext.request.contextPath}/admin/videos" class="btn btn-outline-secondary">
            <i class="fa-solid fa-arrow-left me-1"></i>Quay lại Danh Sách
        </a>
    </div>

    <c:if test="${not empty error}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            <i class="fa-solid fa-circle-exclamation me-2"></i>${error}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    </c:if>

    <div class="card shadow-sm border-0">
        <div class="card-body p-4">
            <form action="${pageContext.request.contextPath}/admin/video/add" method="post" enctype="multipart/form-data">
                <div class="row">
                    <!-- Cột trái: Thông tin cơ bản -->
                    <div class="col-md-7">
                        <div class="mb-3">
                            <label class="form-label fw-semibold">Mã Video (VideoId): <span class="text-danger">*</span></label>
                            <input type="text" name="videoId" class="form-control" placeholder="VD: VID21" value="${param.videoId}" required autofocus>
                            <small class="text-muted">Mã video là duy nhất (Primary Key).</small>
                        </div>

                        <div class="mb-3">
                            <label class="form-label fw-semibold">Tiêu Đề Video: <span class="text-danger">*</span></label>
                            <input type="text" name="title" class="form-control" placeholder="Nhập tiêu đề video" value="${param.title}" required>
                        </div>

                        <div class="mb-3">
                            <label class="form-label fw-semibold">Danh Mục (Category): <span class="text-danger">*</span></label>
                            <select name="categoryId" class="form-select" required>
                                <c:forEach items="${categories}" var="cat">
                                    <option value="${cat.categoryId}" ${param.categoryId == cat.categoryId ? 'selected' : ''}>${cat.categoryname} (${cat.categorycode})</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="mb-3">
                            <label class="form-label fw-semibold">Mô Tả (Description):</label>
                            <textarea name="description" rows="4" class="form-control" placeholder="Nhập mô tả nội dung video...">${param.description}</textarea>
                        </div>
                    </div>

                    <!-- Cột phải: Poster & Trạng thái -->
                    <div class="col-md-5">
                        <div class="mb-3">
                            <label class="form-label fw-semibold">Tải Lên Ảnh Poster:</label>
                            <input type="file" name="posterFile" class="form-control" accept="image/*">
                            <small class="text-muted">Định dạng hỗ trợ: jpg, png, webp.</small>
                        </div>

                        <div class="mb-3">
                            <label class="form-label fw-semibold">Hoặc Nhập URL Poster Trực Tiếp:</label>
                            <input type="text" name="posterUrl" class="form-control" placeholder="https://example.com/poster.jpg">
                        </div>

                        <div class="form-check form-switch mt-4 mb-3">
                            <input class="form-check-input" type="checkbox" name="active" id="activeCheck" checked>
                            <label class="form-check-label fw-semibold" for="activeCheck">Kích hoạt hiển thị (Active)</label>
                        </div>
                    </div>
                </div>

                <div class="mt-4 pt-3 border-top text-end">
                    <a href="${pageContext.request.contextPath}/admin/videos" class="btn btn-secondary me-2">Hủy Bỏ</a>
                    <button type="submit" class="btn btn-primary px-4 fw-semibold shadow-sm">
                        <i class="fa-solid fa-floppy-disk me-2"></i>Lưu Video
                    </button>
                </div>
            </form>
        </div>
    </div>
</body>
</html>
