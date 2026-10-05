<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
    <title>Chỉnh Sửa Video - Admin 24133012</title>
</head>
<body>
    <div class="d-flex justify-content-between align-items-center mb-4">
        <div>
            <h3 class="fw-bold text-dark mb-0">
                <i class="fa-solid fa-pen-to-square text-warning me-2"></i>Chỉnh Sửa Thông Tin Video
            </h3>
            <p class="text-muted small mb-0">Cập nhật thông tin cho video [${video.videoId}]</p>
        </div>
        <a href="${pageContext.request.contextPath}/admin/videos" class="btn btn-outline-secondary">
            <i class="fa-solid fa-arrow-left me-1"></i>Quay lại Danh Sách
        </a>
    </div>

    <div class="card shadow-sm border-0">
        <div class="card-body p-4">
            <form action="${pageContext.request.contextPath}/admin/video/edit" method="post" enctype="multipart/form-data">
                <div class="row">
                    <!-- Cột trái: Thông tin -->
                    <div class="col-md-7">
                        <div class="mb-3">
                            <label class="form-label fw-semibold">Mã Video (VideoId):</label>
                            <input type="text" name="videoId" class="form-control bg-light" value="${video.videoId}" readonly>
                            <small class="text-muted">Mã video là khóa chính, không được thay đổi.</small>
                        </div>

                        <div class="mb-3">
                            <label class="form-label fw-semibold">Tiêu Đề Video: <span class="text-danger">*</span></label>
                            <input type="text" name="title" class="form-control" value="${video.title}" required>
                        </div>

                        <div class="mb-3">
                            <label class="form-label fw-semibold">Danh Mục (Category): <span class="text-danger">*</span></label>
                            <select name="categoryId" class="form-select" required>
                                <c:forEach items="${categories}" var="cat">
                                    <option value="${cat.categoryId}" ${cat.categoryId == video.category.categoryId ? 'selected' : ''}>
                                        ${cat.categoryname} (${cat.categorycode})
                                    </option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="mb-3">
                            <label class="form-label fw-semibold">Mô Tả (Description):</label>
                            <textarea name="description" rows="4" class="form-control">${video.description}</textarea>
                        </div>
                    </div>

                    <!-- Cột phải: Poster & Trạng thái -->
                    <div class="col-md-5">
                        <div class="mb-3">
                            <label class="form-label fw-semibold d-block">Poster Hiện Tại:</label>
                            <div class="border rounded p-2 text-center bg-light mb-2">
                                <c:choose>
                                    <c:when test="${not empty video.poster and video.poster.startsWith('http')}">
                                        <img src="${video.poster}" alt="${video.title}" class="img-fluid rounded" style="max-height: 140px;">
                                    </c:when>
                                    <c:when test="${not empty video.poster}">
                                        <img src="${pageContext.request.contextPath}/image?fname=${video.poster}" alt="${video.title}" class="img-fluid rounded" style="max-height: 140px;">
                                    </c:when>
                                    <c:otherwise>
                                        <img src="https://picsum.photos/seed/${video.videoId}/200/120" alt="${video.title}" class="img-fluid rounded" style="max-height: 140px;">
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>

                        <div class="mb-3">
                            <label class="form-label fw-semibold">Thay Đổi Poster (Upload file):</label>
                            <input type="file" name="posterFile" class="form-control" accept="image/*">
                        </div>

                        <div class="mb-3">
                            <label class="form-label fw-semibold">Hoặc Đổi Bằng URL Ảnh:</label>
                            <input type="text" name="posterUrl" class="form-control" value="${video.poster}">
                        </div>

                        <div class="form-check form-switch mt-3 mb-3">
                            <input class="form-check-input" type="checkbox" name="active" id="activeCheck" ${video.active ? 'checked' : ''}>
                            <label class="form-check-label fw-semibold" for="activeCheck">Kích hoạt hiển thị (Active)</label>
                        </div>
                    </div>
                </div>

                <div class="mt-4 pt-3 border-top text-end">
                    <a href="${pageContext.request.contextPath}/admin/videos" class="btn btn-secondary me-2">Hủy Bỏ</a>
                    <button type="submit" class="btn btn-warning px-4 fw-semibold shadow-sm">
                        <i class="fa-solid fa-floppy-disk me-2"></i>Cập Nhật Video
                    </button>
                </div>
            </form>
        </div>
    </div>
</body>
</html>
