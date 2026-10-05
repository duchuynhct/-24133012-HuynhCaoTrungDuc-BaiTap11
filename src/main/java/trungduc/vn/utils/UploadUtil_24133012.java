package trungduc.vn.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;

import jakarta.servlet.http.Part;

public class UploadUtil_24133012 {

    /**
     * Lưu file upload từ Part lên server và trả về tên file đã lưu
     */
    public static String processUpload(Part part, String uploadPath) throws IOException {
        if (part == null || part.getSize() == 0) {
            return null;
        }

        String submittedFileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
        if (submittedFileName == null || submittedFileName.trim().isEmpty()) {
            return null;
        }

        // Đảm bảo thư mục upload tồn tại
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // Tạo tên file duy nhất tránh trùng lặp
        String extension = "";
        int dotIndex = submittedFileName.lastIndexOf('.');
        if (dotIndex >= 0) {
            extension = submittedFileName.substring(dotIndex);
        }

        String uniqueFileName = UUID.randomUUID().toString() + extension;
        String filePath = uploadPath + File.separator + uniqueFileName;
        part.write(filePath);

        return uniqueFileName;
    }
}
