package trungduc.vn.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import trungduc.vn.utils.Constant_24133012;

@WebServlet(urlPatterns = { "/image" })
public class ImageController_24133012 extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fileName = req.getParameter("fname");
        if (fileName == null || fileName.trim().isEmpty()) {
            fileName = Constant_24133012.DEFAULT_POSTER;
        }

        // Chặn Path Traversal: chỉ lấy tên file thuần, loại bỏ đường dẫn ../../
        fileName = java.nio.file.Paths.get(fileName).getFileName().toString();

        // Kiểm tra trong thư mục upload của ứng dụng
        String uploadPath = req.getServletContext().getRealPath("/") + Constant_24133012.UPLOAD_DIRECTORY;
        File file = new File(uploadPath, fileName);

        if (!file.exists()) {
            // Nếu không tìm thấy trong runtime path, tìm trong static/images hoặc dùng ảnh mẫu
            file = new File(req.getServletContext().getRealPath("/static/images"), fileName);
        }

        if (file.exists()) {
            String mimeType = req.getServletContext().getMimeType(file.getName());
            if (mimeType == null) {
                mimeType = "image/jpeg";
            }
            resp.setContentType(mimeType);
            resp.setContentLengthLong(file.length());

            try (FileInputStream in = new FileInputStream(file); OutputStream out = resp.getOutputStream()) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            }
        } else {
            // Nếu file không tồn tại trên máy chủ (ví dụ dữ liệu mẫu vid01.jpg), tự động chuyển hướng sang ảnh mẫu Picsum để tránh lỗi vỡ ảnh
            resp.sendRedirect("https://picsum.photos/seed/" + fileName + "/400/250");
        }
    }
}
