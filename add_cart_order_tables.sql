USE [24133012_made];
GO

-- Xoá dữ liệu mẫu nếu có
DELETE FROM dbo.OrderDetails;
DELETE FROM dbo.Orders;
GO

-- Thêm một số đơn hàng mẫu cho cả duchuynhct và admin với các trạng thái khác nhau
INSERT INTO dbo.Orders (Username, OrderDate, Fullname, Phone, Address, Note, TotalAmount, PaymentMethod, Status) VALUES
(N'duchuynhct', DATEADD(DAY, -7, GETDATE()), N'Huỳnh Cao Trung Đức', N'0987654321', N'Số 1 Võ Văn Ngân, TP. Thủ Đức', N'Giao giờ hành chính', 448000, N'COD', N'Đã giao'),
(N'duchuynhct', DATEADD(DAY, -4, GETDATE()), N'Huỳnh Cao Trung Đức', N'0987654321', N'Số 1 Võ Văn Ngân, TP. Thủ Đức', N'Gọi trước khi giao', 249000, N'COD', N'Vận chuyển'),
(N'duchuynhct', DATEADD(DAY, -2, GETDATE()), N'Huỳnh Cao Trung Đức', N'0987654321', N'Số 1 Võ Văn Ngân, TP. Thủ Đức', N'Chuẩn bị cẩn thận', 299000, N'COD', N'Chuẩn bị hàng'),
(N'duchuynhct', DATEADD(DAY, -1, GETDATE()), N'Huỳnh Cao Trung Đức', N'0987654321', N'Số 1 Võ Văn Ngân, TP. Thủ Đức', N'Giao sớm giúp em', 199000, N'COD', N'Đơn hàng mới'),
(N'admin', DATEADD(DAY, -3, GETDATE()), N'Huỳnh Cao Trung Đức', N'0987654321', N'TP. Hồ Chí Minh', N'Thanh toán COD', 548000, N'COD', N'Đã xác nhận');
GO

DECLARE @o1 INT, @o2 INT, @o3 INT, @o4 INT, @o5 INT;
SELECT @o1 = MIN(OrderId) FROM dbo.Orders;
SELECT @o2 = @o1 + 1, @o3 = @o1 + 2, @o4 = @o1 + 3, @o5 = @o1 + 4;

INSERT INTO dbo.OrderDetails (OrderId, VideoId, Price, Quantity) VALUES
(@o1, N'VID01', 199000, 1),
(@o1, N'VID02', 249000, 1),
(@o2, N'VID02', 249000, 1),
(@o3, N'VID03', 299000, 1),
(@o4, N'VID01', 199000, 1),
(@o5, N'VID03', 299000, 1),
(@o5, N'VID02', 249000, 1);
GO
