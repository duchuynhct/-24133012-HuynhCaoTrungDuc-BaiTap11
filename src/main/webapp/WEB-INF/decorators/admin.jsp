<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><sitemesh:write property='title'/> - Quản Trị Hệ Thống (24133012)</title>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f4f6f9;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            min-height: 100vh;
            display: flex;
            flex-direction: column;
        }
        .admin-sidebar {
            width: 260px;
            background: #212529;
            min-height: calc(100vh - 56px);
            color: #c2c7d0;
        }
        .admin-sidebar .nav-link {
            color: #c2c7d0;
            padding: 12px 20px;
            font-weight: 500;
            border-radius: 6px;
            margin: 4px 10px;
            transition: all 0.2s;
        }
        .admin-sidebar .nav-link:hover, .admin-sidebar .nav-link.active {
            background: #0d6efd;
            color: #fff;
        }
        .admin-content {
            flex: 1;
            padding: 30px;
        }
        .footer {
            background: #212529;
            color: #adb5bd;
            padding: 20px 0;
            margin-top: auto;
        }
    </style>
    <sitemesh:write property='head'/>
</head>
<body>

    <!-- Admin Top Navbar -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark border-bottom border-secondary">
        <div class="container-fluid px-4">
            <a class="navbar-brand fw-bold text-primary" href="${pageContext.request.contextPath}/admin/videos">
                <i class="fa-solid fa-shield-halved text-warning me-2"></i>ADMIN PANEL 24133012
            </a>
            <div class="d-flex align-items-center">
                <span class="text-light me-3">
                    <i class="fa-solid fa-user-shield text-warning me-1"></i>
                    Admin: <b>${sessionScope.currentUser.fullname != null ? sessionScope.currentUser.fullname : sessionScope.currentUser.username}</b>
                </span>
                <a href="${pageContext.request.contextPath}/home" class="btn btn-outline-info btn-sm me-2" target="_blank">
                    <i class="fa-solid fa-arrow-up-right-from-square me-1"></i>Xem trang User
                </a>
                <a href="${pageContext.request.contextPath}/logout" class="btn btn-outline-danger btn-sm">
                    <i class="fa-solid fa-right-from-bracket me-1"></i>Đăng xuất
                </a>
            </div>
        </div>
    </nav>

    <!-- Main Wrapper with Sidebar -->
    <div class="d-flex flex-grow-1">
        <!-- Sidebar Navigation -->
        <aside class="admin-sidebar py-3">
            <ul class="nav flex-column">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/home">
                        <i class="fa-solid fa-house me-2"></i>Về Trang Chủ
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" href="${pageContext.request.contextPath}/admin/videos">
                        <i class="fa-solid fa-video me-2"></i>Quản lý Videos (CRUD)
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/admin/video/add">
                        <i class="fa-solid fa-plus-circle me-2"></i>Thêm Video Mới
                    </a>
                </li>
                <li class="nav-item mt-4">
                    <a class="nav-link text-danger" href="${pageContext.request.contextPath}/logout">
                        <i class="fa-solid fa-power-off me-2"></i>Đăng Xuất
                    </a>
                </li>
            </ul>
        </aside>

        <!-- Admin Page Content -->
        <main class="admin-content">
            <sitemesh:write property='body'/>
        </main>
    </div>

    <!-- Admin Footer chứa thông tin sinh viên và mã đề theo yêu cầu Câu 1 -->
    <footer class="footer">
        <div class="container-fluid px-4 text-center">
            <p class="mb-1 text-light">
                <b>Họ tên:</b> Huỳnh Cao Trung Đức &nbsp;|&nbsp; 
                <b>MSSV:</b> 24133012 &nbsp;|&nbsp; 
                <b>Mã đề:</b> Đề số 03
            </p>
            <small class="text-secondary">Trang quản trị hệ thống - Đề thi Quá trình Môn Lập Trình Web</small>
        </div>
    </footer>

    <!-- Bootstrap 5 Bundle JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
