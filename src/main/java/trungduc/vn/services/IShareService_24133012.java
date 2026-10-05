package trungduc.vn.services;

public interface IShareService_24133012 {
    long countByVideoId(String videoId);
    void shareVideo(String username, String videoId, String recipientEmail);
}
