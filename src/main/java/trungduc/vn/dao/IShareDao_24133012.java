package trungduc.vn.dao;

import trungduc.vn.entity.Share_24133012;

public interface IShareDao_24133012 {
    long countByVideoId(String videoId);
    void insert(Share_24133012 share);
}
