<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Xác Thực Mã OTP - Kích Hoạt Tài Khoản (24133012)</title>
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
            padding: 20px 0;
        }
        .card-otp {
            width: 100%;
            max-width: 460px;
            border-radius: 12px;
            box-shadow: 0 15px 35px rgba(0,0,0,0.3);
            border: none;
        }
        .otp-input {
            letter-spacing: 12px;
            font-size: 28px;
            font-weight: 700;
            text-align: center;
        }
    </style>
</head>
<body>
    <div class="container d-flex justify-content-center">
        <div class="card card-otp bg-white p-4 text-center">
            <div class="mb-3">
                <i class="fa-solid fa-key text-warning fa-3x mb-2"></i>
                <h3 class="fw-bold text-dark mb-1">XÁC THỰC MÃ OTP</h3>
                <p class="text-muted small">
                    Mã kích hoạt đã được gửi tới email: <b>${sessionScope.userEmail}</b><br>
                    <span class="text-info fst-italic">(Kiểm tra hòm thư email hoặc màn hình Console của Server)</span>
                </p>
            </div>

            <c:if test="${not empty message}">
                <div class="alert alert-info alert-dismissible fade show text-start" role="alert">
                    <i class="fa-solid fa-info-circle me-2"></i>${message}
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
            </c:if>

            <c:if test="${not empty error}">
                <div class="alert alert-danger alert-dismissible fade show text-start" role="alert">
                    <i class="fa-solid fa-circle-exclamation me-2"></i>${error}
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
            </c:if>

            <form action="${pageContext.request.contextPath}/verify-otp" method="post" class="mt-2">
                <div class="mb-4">
                    <label class="form-label fw-semibold text-secondary">Nhập mã 6 chữ số:</label>
                    <input type="text" name="otp" class="form-control form-control-lg otp-input text-primary" maxlength="6" placeholder="000000" required autofocus>
                </div>

                <div class="d-grid gap-2 mb-3">
                    <button type="submit" class="btn btn-success btn-lg fw-bold shadow-sm">
                        <i class="fa-solid fa-circle-check me-2"></i>Kích Hoạt Tài Khoản
                    </button>
                </div>

                <div class="d-flex justify-content-between align-items-center mt-3 pt-3 border-top">
                    <a href="${pageContext.request.contextPath}/resend-otp" class="text-decoration-none small text-primary fw-semibold">
                        <i class="fa-solid fa-rotate-right me-1"></i>Gửi lại mã OTP
                    </a>
                    <a href="${pageContext.request.contextPath}/register" class="text-decoration-none small text-secondary">
                        <i class="fa-solid fa-arrow-left me-1"></i>Đăng ký lại
                    </a>
                </div>
            </form>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
