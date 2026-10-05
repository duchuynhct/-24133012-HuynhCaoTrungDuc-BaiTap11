<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng Ký Tài Khoản - Xác Thực OTP (24133012)</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #1e3c72 0%, #2a5298 100%);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            padding: 30px 0;
        }
        .card-register {
            width: 100%;
            max-width: 500px;
            border-radius: 12px;
            box-shadow: 0 15px 35px rgba(0,0,0,0.3);
            border: none;
        }
    </style>
</head>
<body>
    <div class="container d-flex justify-content-center">
        <div class="card card-register bg-white p-4">
            <div class="text-center mb-3">
                <i class="fa-solid fa-user-shield text-primary fa-3x mb-2"></i>
                <h3 class="fw-bold text-dark mb-1">ĐĂNG KÝ TÀI KHOẢN</h3>
                <p class="text-muted small">Kích hoạt bằng mã OTP gửi về Email (Câu 2 - Đề 03)</p>
            </div>

            <c:if test="${not empty error}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    <i class="fa-solid fa-circle-exclamation me-2"></i>${error}
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
            </c:if>

            <form action="${pageContext.request.contextPath}/register" method="post">
                <div class="mb-3">
                    <label class="form-label fw-semibold">Tên đăng nhập (Username): <span class="text-danger">*</span></label>
                    <div class="input-group">
                        <span class="input-group-text"><i class="fa-solid fa-user"></i></span>
                        <input type="text" name="username" class="form-control" placeholder="Nhập username mong muốn" value="${param.username}" required autofocus>
                    </div>
                </div>

                <div class="mb-3">
                    <label class="form-label fw-semibold">Mật khẩu: <span class="text-danger">*</span></label>
                    <div class="input-group">
                        <span class="input-group-text"><i class="fa-solid fa-lock"></i></span>
                        <input type="password" name="password" class="form-control" placeholder="Nhập mật khẩu" required>
                    </div>
                </div>

                <div class="mb-3">
                    <label class="form-label fw-semibold">Họ và tên:</label>
                    <div class="input-group">
                        <span class="input-group-text"><i class="fa-solid fa-id-card"></i></span>
                        <input type="text" name="fullname" class="form-control" placeholder="Nhập họ và tên đầy đủ" value="${param.fullname}">
                    </div>
                </div>

                <div class="mb-3">
                    <label class="form-label fw-semibold">Email nhận mã OTP: <span class="text-danger">*</span></label>
                    <div class="input-group">
                        <span class="input-group-text"><i class="fa-solid fa-envelope"></i></span>
                        <input type="email" name="email" class="form-control" placeholder="VD: yourname@gmail.com" value="${param.email}" required>
                    </div>
                    <small class="text-muted fst-italic">Hệ thống sẽ gửi mã OTP kích hoạt đến địa chỉ email này.</small>
                </div>

                <div class="mb-4">
                    <label class="form-label fw-semibold">Số điện thoại:</label>
                    <div class="input-group">
                        <span class="input-group-text"><i class="fa-solid fa-phone"></i></span>
                        <input type="tel" name="phone" class="form-control" placeholder="Nhập số điện thoại" value="${param.phone}">
                    </div>
                </div>

                <div class="d-grid gap-2 mb-3">
                    <button type="submit" class="btn btn-primary btn-lg fw-bold shadow-sm">
                        <i class="fa-solid fa-paper-plane me-2"></i>Đăng Ký &amp; Nhận Mã OTP
                    </button>
                </div>

                <div class="text-center">
                    <span class="text-muted">Đã có tài khoản? </span>
                    <a href="${pageContext.request.contextPath}/login" class="fw-bold text-decoration-none">Đăng nhập</a>
                </div>
            </form>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
