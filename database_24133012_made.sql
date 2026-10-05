USE master;
GO

IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = N'24133012_made')
BEGIN
    CREATE DATABASE [24133012_made];
END
GO

USE [24133012_made];
GO

-- Xoá bảng cũ nếu tồn tại (theo thứ tự quan hệ khoá ngoại)
IF OBJECT_ID(N'dbo.Shares', N'U') IS NOT NULL DROP TABLE dbo.Shares;
IF OBJECT_ID(N'dbo.Favorites', N'U') IS NOT NULL DROP TABLE dbo.Favorites;
IF OBJECT_ID(N'dbo.Videos', N'U') IS NOT NULL DROP TABLE dbo.Videos;
IF OBJECT_ID(N'dbo.Category', N'U') IS NOT NULL DROP TABLE dbo.Category;
IF OBJECT_ID(N'dbo.Users', N'U') IS NOT NULL DROP TABLE dbo.Users;
GO

-- 1. Bảng Users
CREATE TABLE dbo.Users (
    Username NVARCHAR(50) NOT NULL PRIMARY KEY,
    Password NVARCHAR(50) NOT NULL,
    Phone NVARCHAR(15) NULL,
    Fullname NVARCHAR(50) NULL,
    Email NVARCHAR(150) NULL,
    Admin BIT NOT NULL DEFAULT 0,
    Active BIT NOT NULL DEFAULT 1,
    Images NVARCHAR(500) NULL
);
GO

-- 2. Bảng Category (Tất cả ID đều tăng tự động)
CREATE TABLE dbo.Category (
    CategoryId INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    Categoryname NVARCHAR(100) NOT NULL,
    Categorycode NVARCHAR(100) NULL,
    Images NVARCHAR(500) NULL,
    Status BIT NOT NULL DEFAULT 1
);
GO

-- 3. Bảng Videos
CREATE TABLE dbo.Videos (
    VideoId NVARCHAR(50) NOT NULL PRIMARY KEY,
    Title NVARCHAR(200) NOT NULL,
    Poster NVARCHAR(500) NULL,
    Views INT NOT NULL DEFAULT 0,
    Description NVARCHAR(500) NULL,
    Active BIT NOT NULL DEFAULT 1,
    CategoryId INT NOT NULL,
    CONSTRAINT FK_Videos_Category FOREIGN KEY (CategoryId) REFERENCES dbo.Category(CategoryId) ON DELETE CASCADE
);
GO

-- 4. Bảng Favorites
CREATE TABLE dbo.Favorites (
    FavoriteId INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    LikedDate DATE NOT NULL DEFAULT GETDATE(),
    VideoId NVARCHAR(50) NOT NULL,
    Username NVARCHAR(50) NOT NULL,
    CONSTRAINT FK_Favorites_Videos FOREIGN KEY (VideoId) REFERENCES dbo.Videos(VideoId) ON DELETE CASCADE,
    CONSTRAINT FK_Favorites_Users FOREIGN KEY (Username) REFERENCES dbo.Users(Username) ON DELETE CASCADE
);
GO

-- 5. Bảng Shares
CREATE TABLE dbo.Shares (
    ShareId INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    Emails NVARCHAR(100) NULL,
    SharedDate DATE NOT NULL DEFAULT GETDATE(),
    Username NVARCHAR(50) NOT NULL,
    VideoId NVARCHAR(50) NOT NULL,
    CONSTRAINT FK_Shares_Users FOREIGN KEY (Username) REFERENCES dbo.Users(Username) ON DELETE CASCADE,
    CONSTRAINT FK_Shares_Videos FOREIGN KEY (VideoId) REFERENCES dbo.Videos(VideoId) ON DELETE CASCADE
);
GO

-- DỮ LIỆU MẪU (SEED DATA)
-- 1. Users
INSERT INTO dbo.Users (Username, Password, Phone, Fullname, Email, Admin, Active, Images) VALUES
(N'admin', N'123456', N'0987654321', N'Huỳnh Cao Trung Đức', N'admin@gmail.com', 1, 1, N'admin.png'),
(N'guest', N'123456', N'0900000000', N'Khách vãng lai', N'guest@gmail.com', 0, 1, N'guest.png'),
(N'user1', N'123456', N'0912345678', N'Nguyễn Văn A', N'nguyenvana@gmail.com', 0, 1, N'user1.png'),
(N'user2', N'123456', N'0923456789', N'Trần Thị B', N'tranthib@gmail.com', 0, 1, N'user2.png');
GO

-- 2. Category
INSERT INTO dbo.Category (Categoryname, Categorycode, Images, Status) VALUES
(N'Lập Trình Web', N'WEB', N'cat_web.png', 1),
(N'Trí Tuệ Nhân Tạo', N'AI', N'cat_ai.png', 1),
(N'Điện Toán Đám Mây', N'CLOUD', N'cat_cloud.png', 1),
(N'Âm Nhạc Giải Trí', N'MUSIC', N'cat_music.png', 1);
GO

-- 3. Videos
-- Category 1: Lập Trình Web (Id: 1)
INSERT INTO dbo.Videos (VideoId, Title, Poster, Views, Description, Active, CategoryId) VALUES
(N'VID01', N'Khóa học Java Servlet & JSP từ cơ bản đến nâng cao', N'vid01.jpg', 1520, N'Hướng dẫn chi tiết xây dựng ứng dụng web với Java Servlet, JSP, JSTL và mô hình MVC chuẩn chỉnh.', 1, 1),
(N'VID02', N'Lập trình Web với Spring Boot 3 & RESTful API', N'vid02.jpg', 2340, N'Tìm hiểu kiến trúc Spring Boot 3, Hibernate Data JPA, Swagger UI và xây dựng hệ thống REST API.', 1, 1),
(N'VID03', N'Thiết kế giao diện hiện đại với Bootstrap 5', N'vid03.jpg', 980, N'Học cách thiết kế Responsive Web Design đẹp mắt, tối ưu trên cả điện thoại và máy tính.', 1, 1),
(N'VID04', N'Xây dựng kiến trúc 3 lớp MVC trong dự án Java Web', N'vid04.jpg', 1150, N'Tách lớp Controller, Service, DAO một cách khoa học giúp mã nguồn dễ bảo trì và mở rộng.', 1, 1),
(N'VID05', N'Tích hợp SiteMesh 3 Decorators cho Java Web', N'vid05.jpg', 870, N'Hướng dẫn cấu hình SiteMesh 3 để quản lý Header, Footer đồng bộ cho toàn bộ website.', 1, 1),
(N'VID06', N'Bảo mật ứng dụng web với Session & Cookie', N'vid06.jpg', 1600, N'Các nguyên tắc bảo mật, chống tấn công CSRF, quản lý phiên đăng nhập và phân quyền User/Admin.', 1, 1),
(N'VID07', N'Tối ưu hóa hiệu năng cơ sở dữ liệu với JPA Hibernate', N'vid07.jpg', 750, N'Cách sử dụng Lazy Loading, Fetch Join, 2nd Level Cache giúp tăng tốc độ truy vấn cơ sở dữ liệu.', 1, 1);

-- Category 2: Trí Tuệ Nhân Tạo (Id: 2)
INSERT INTO dbo.Videos (VideoId, Title, Poster, Views, Description, Active, CategoryId) VALUES
(N'VID08', N'Tổng quan về Trí tuệ nhân tạo và Machine Learning', N'vid08.jpg', 3200, N'Khái niệm cốt lõi về AI, các giải thuật học máy có giám sát và không giám sát phổ biến nhất.', 1, 2),
(N'VID09', N'Deep Learning và mạng nơ-ron tích chập CNN', N'vid09.jpg', 1890, N'Ứng dụng thị giác máy tính và CNN trong phân loại hình ảnh và nhận diện khuôn mặt.', 1, 2),
(N'VID10', N'Xử lý ngôn ngữ tự nhiên với kiến trúc Transformer', N'vid10.jpg', 2750, N'Tìm hiểu cơ chế Self-Attention và cách hoạt động của các mô hình ngôn ngữ lớn LLMs hiện nay.', 1, 2),
(N'VID11', N'Xây dựng trợ lý ảo AI với Spring AI và OpenAI API', N'vid11.jpg', 1430, N'Kết nối ứng dụng Java backend với mô hình ngôn ngữ lớn để trả lời câu hỏi tự động.', 1, 2),
(N'VID12', N'Nhập môn Thị giác máy tính với OpenCV', N'vid12.jpg', 920, N'Hướng dẫn xử lý hình ảnh số, lọc nhiễu, phát hiện biên cạnh và vật thể với thư viện OpenCV.', 1, 2);

-- Category 3: Điện Toán Đám Mây (Id: 3)
INSERT INTO dbo.Videos (VideoId, Title, Poster, Views, Description, Active, CategoryId) VALUES
(N'VID13', N'Nhập môn AWS Cloud Practitioner cho người mới', N'vid13.jpg', 1200, N'Tìm hiểu các dịch vụ cơ bản của Amazon Web Services: EC2, S3, RDS, IAM và VPC.', 1, 3),
(N'VID14', N'Triển khai ứng dụng Docker Container lên Cloud', N'vid14.jpg', 1980, N'Đóng gói mã nguồn Java web vào Docker image và deploy lên hệ thống đám mây dễ dàng.', 1, 3),
(N'VID15', N'Kubernetes căn bản: Quản lý cụm Container', N'vid15.jpg', 1450, N'Kiến trúc Kubernetes, Pods, Services, Deployments và cơ chế tự động mở rộng Pods.', 1, 3),
(N'VID16', N'Tự động hóa CI/CD với GitHub Actions', N'vid16.jpg', 1650, N'Xây dựng pipeline tự động kiểm thử mã nguồn, build file WAR/JAR và deploy liên tục.', 1, 3);

-- Category 4: Âm Nhạc Giải Trí (Id: 4)
INSERT INTO dbo.Videos (VideoId, Title, Poster, Views, Description, Active, CategoryId) VALUES
(N'VID17', N'Tuyển tập Acoustic Lo-fi thư giãn học tập', N'vid17.jpg', 5400, N'Những giai điệu acoustic nhẹ nhàng, êm dịu giúp bạn tập trung làm việc và lập trình hiệu quả.', 1, 4),
(N'VID18', N'Nhạc Piano không lời nhẹ nhàng cho giấc ngủ ngon', N'vid18.jpg', 4300, N'Giai điệu piano du dương sâu lắng, xua tan căng thẳng mệt mỏi sau những giờ học tập.', 1, 4),
(N'VID19', N'Những bản nhạc Indie Việt hay nhất mọi thời đại', N'vid19.jpg', 3800, N'Tổng hợp các ca khúc Indie Việt Nam mang đậm cảm xúc chân thật, mộc mạc và hoài niệm.', 1, 4),
(N'VID20', N'Nhạc điện tử EDM tập gym tăng năng lượng đỉnh cao', N'vid20.jpg', 6100, N'Những bản nhạc EDM sôi động bùng nổ, truyền cảm hứng và tiếp thêm năng lượng tích cực.', 1, 4);
GO

-- 4. Favorites (Lượt thích mẫu)
INSERT INTO dbo.Favorites (LikedDate, VideoId, Username) VALUES
('2026-09-01', N'VID01', N'admin'),
('2026-09-02', N'VID01', N'user1'),
('2026-09-03', N'VID01', N'user2'),
('2026-09-01', N'VID02', N'admin'),
('2026-09-02', N'VID02', N'user1'),
('2026-09-01', N'VID08', N'admin'),
('2026-09-02', N'VID08', N'user2'),
('2026-09-01', N'VID17', N'user1'),
('2026-09-02', N'VID17', N'user2'),
('2026-09-03', N'VID17', N'admin');
GO

-- 5. Shares (Lượt chia sẻ mẫu)
INSERT INTO dbo.Shares (Emails, SharedDate, Username, VideoId) VALUES
(N'friend1@gmail.com', '2026-09-01', N'admin', N'VID01'),
(N'friend2@gmail.com', '2026-09-02', N'user1', N'VID01'),
(N'colleague@company.com', '2026-09-01', N'user2', N'VID02'),
(N'classmate@edu.vn', '2026-09-02', N'admin', N'VID08'),
(N'music_fan@gmail.com', '2026-09-03', N'user1', N'VID17');
GO
