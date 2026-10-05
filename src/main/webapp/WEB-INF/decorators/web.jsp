<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><sitemesh:write property='title'/> - Web Đề 03 (24133012)</title>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            min-height: 100vh;
            display: flex;
            flex-direction: column;
        }
        .main-content {
            flex: 1 0 auto;
        }
        .navbar-brand {
            font-weight: 700;
            color: #0d6efd !important;
        }
        .footer {
            flex-shrink: 0;
            background-color: #212529;
            color: #adb5bd;
            padding: 25px 0;
            margin-top: 50px;
        }
        .video-card {
            transition: transform 0.2s ease, box-shadow 0.2s ease;
            height: 100%;
        }
        .video-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 10px 20px rgba(0,0,0,0.12) !important;
        }
        .video-poster-wrapper {
            position: relative;
            width: 100%;
            height: 200px;
            overflow: hidden;
            background-color: #000;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .video-poster-wrapper img {
            width: 100%;
            height: 100%;
            object-fit: cover;
        }
    </style>
    <sitemesh:write property='head'/>
</head>
<body>

    <!-- Header Navigation -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark sticky-top shadow-sm">
        <div class="container">
            <a class="navbar-brand text-primary" href="${pageContext.request.contextPath}/home">
                <i class="fa-solid fa-play-circle text-danger me-2"></i>VideoHub 24133012
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#mainNavbar">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="mainNavbar">
                <!-- Menu yêu cầu: Trang Chủ, Sản phẩm, Đăng nhập, Trang quản trị (admin mới có chức năng này) -->
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/home">
                            <i class="fa-solid fa-house me-1"></i>Trang Chủ
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/home#products">
                            <i class="fa-solid fa-layer-group me-1"></i>Sản phẩm
                        </a>
                    </li>
                    <!-- Trang quản trị: Chỉ Admin mới có chức năng này -->
                    <c:if test="${not empty sessionScope.currentUser and sessionScope.currentUser.admin}">
                        <li class="nav-item">
                            <a class="nav-link text-warning fw-semibold" href="${pageContext.request.contextPath}/admin/videos">
                                <i class="fa-solid fa-gauge-high me-1"></i>Trang quản trị
                            </a>
                        </li>
                    </c:if>
                </ul>

                <ul class="navbar-nav ms-auto mb-2 mb-lg-0 align-items-center">
                    <!-- Nút Giỏ Hàng -->
                    <li class="nav-item me-2">
                        <a class="btn btn-outline-light btn-sm position-relative px-3" href="${pageContext.request.contextPath}/cart">
                            <i class="fa-solid fa-cart-shopping me-1 text-warning"></i>Giỏ Hàng
                            <c:if test="${not empty sessionScope.cartTotalQuantity and sessionScope.cartTotalQuantity > 0}">
                                <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">
                                    ${sessionScope.cartTotalQuantity}
                                </span>
                            </c:if>
                        </a>
                    </li>

                    <c:choose>
                        <c:when test="${empty sessionScope.currentUser}">
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/login">
                                    <i class="fa-solid fa-right-to-bracket me-1"></i>Đăng nhập
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="btn btn-outline-primary btn-sm ms-2" href="${pageContext.request.contextPath}/register">
                                    <i class="fa-solid fa-user-plus me-1"></i>Đăng ký
                                </a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle text-light" href="#" role="button" data-bs-toggle="dropdown">
                                    <i class="fa-solid fa-user-circle me-1"></i>Xin chào, <b>${sessionScope.currentUser.fullname != null ? sessionScope.currentUser.fullname : sessionScope.currentUser.username}</b>
                                </a>
                                <ul class="dropdown-menu dropdown-menu-end shadow">
                                    <li>
                                        <a class="dropdown-item" href="${pageContext.request.contextPath}/order/history">
                                            <i class="fa-solid fa-clock-rotate-left me-2 text-primary"></i>Lịch sử đặt hàng
                                        </a>
                                    </li>
                                    <li>
                                        <a class="dropdown-item" href="${pageContext.request.contextPath}/cart">
                                            <i class="fa-solid fa-cart-shopping me-2 text-warning"></i>Giỏ hàng của tôi
                                        </a>
                                    </li>
                                    <c:if test="${sessionScope.currentUser.admin}">
                                        <li><hr class="dropdown-divider"></li>
                                        <li>
                                            <a class="dropdown-item text-warning" href="${pageContext.request.contextPath}/admin/videos">
                                                <i class="fa-solid fa-wrench me-2"></i>Trang Quản Trị
                                            </a>
                                        </li>
                                    </c:if>
                                    <li><hr class="dropdown-divider"></li>
                                    <li>
                                        <a class="dropdown-item text-danger" href="${pageContext.request.contextPath}/logout">
                                            <i class="fa-solid fa-right-from-bracket me-2"></i>Đăng xuất
                                        </a>
                                    </li>
                                </ul>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>
        </div>
    </nav>

    <!-- Main Content -->
    <main class="main-content">
        <sitemesh:write property='body'/>
    </main>

    <!-- Footer chứa thông tin: Họ tên, MSSV, Mã đề theo yêu cầu Câu 1 -->
    <footer class="footer">
        <div class="container text-center">
            <div class="row align-items-center">
                <div class="col-md-4 text-md-start mb-2 mb-md-0">
                    <span class="fw-bold text-light"><i class="fa-solid fa-graduation-cap me-2"></i>Đề thi Quá trình - HK1</span>
                </div>
                <div class="col-md-8 text-md-end">
                    <p class="mb-1 text-light">
                        <b>Họ tên:</b> Huỳnh Cao Trung Đức &nbsp;|&nbsp; 
                        <b>MSSV:</b> 24133012 &nbsp;|&nbsp; 
                        <b>Mã đề:</b> Đề số 03
                    </p>
                    <small class="text-secondary">Môn: Lập Trình Web - Bộ môn Công Nghệ Phần Mềm</small>
                </div>
            </div>
        </div>
    </footer>

    <!-- Bootstrap 5 Bundle JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
