package trungduc.vn.utils;

import java.util.Properties;
import java.util.Random;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class EmailUtil_24133012 {

    /**
     * Sinh mã OTP ngẫu nhiên gồm 6 chữ số
     */
    public static String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    /**
     * Gửi email mã OTP thực tế qua Gmail SMTP đồng thời in ra console log server
     */
    public static boolean sendOtpEmail(String recipientEmail, String otp) {
        // Luôn in ra console log để bạn và giáo viên dễ dàng kiểm thử ngay cả khi offline
        System.out.println("=================================================================");
        System.out.println(">> [24133012_made] GỬI MÃ OTP ĐẾN EMAIL: " + recipientEmail);
        System.out.println(">> [24133012_made] MÃ OTP CỦA BẠN LÀ: ===> [ " + otp + " ] <===");
        System.out.println("=================================================================");

        // Nếu chưa cấu hình mật khẩu email thì bỏ qua bước gửi mạng, trả về true vì đã có mã ở console
        if (Constant_24133012.EMAIL_PASSWORD == null || Constant_24133012.EMAIL_PASSWORD.trim().isEmpty()) {
            System.out.println(">> [Thông báo]: Chưa điền EMAIL_PASSWORD trong Constant_24133012.java. Vui lòng lấy mã OTP hiển thị ở trên.");
            return true;
        }

        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", Constant_24133012.SMTP_HOST);
            props.put("mail.smtp.port", Constant_24133012.SMTP_PORT);
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");

            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(Constant_24133012.EMAIL_FROM, Constant_24133012.EMAIL_PASSWORD);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(Constant_24133012.EMAIL_FROM, "Web Lập Trình - Đề 03 (24133012)"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("[24133012 - Huỳnh Cao Trung Đức] Mã OTP xác thực tài khoản");

            String htmlContent = "<div style='font-family: Arial, sans-serif; padding: 20px; border: 1px solid #ddd; max-width: 600px; margin: auto;'>"
                    + "<h2 style='color: #0d6efd; text-align: center;'>XÁC THỰC TÀI KHOẢN</h2>"
                    + "<p>Chào bạn,</p>"
                    + "<p>Bạn vừa thực hiện đăng ký tài khoản trên hệ thống bài tập Web Lập Trình (Đề số 03 - MSSV: 24133012).</p>"
                    + "<p>Mã OTP kích hoạt tài khoản của bạn là:</p>"
                    + "<div style='background-color: #f8f9fa; border: 2px dashed #0d6efd; text-align: center; padding: 15px; font-size: 28px; font-weight: bold; letter-spacing: 5px; color: #dc3545;'>"
                    + otp + "</div>"
                    + "<p style='margin-top: 15px;'>Mã xác thực có hiệu lực trong vòng <b>5 phút</b>. Vui lòng không chia sẻ mã này cho bất kỳ ai.</p>"
                    + "<hr style='border: none; border-top: 1px solid #eee;' />"
                    + "<p style='font-size: 12px; color: #6c757d; text-align: center;'>Sinh viên: Huỳnh Cao Trung Đức - MSSV: 24133012 - Đề 03</p>"
                    + "</div>";

            message.setContent(htmlContent, "text/html; charset=UTF-8");
            Transport.send(message);
            System.out.println(">> [24133012_made] Đã gửi email OTP thực tế thành công tới: " + recipientEmail);
            return true;
        } catch (Exception e) {
            System.err.println(">> [24133012_made] Lỗi khi gửi email qua Internet: " + e.getMessage());
            // Vẫn trả về true để cho phép người dùng tiếp tục nhập OTP đã in ra console
            return true;
        }
    }
}
