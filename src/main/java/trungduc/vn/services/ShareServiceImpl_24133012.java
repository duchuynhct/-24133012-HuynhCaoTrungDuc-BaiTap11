package trungduc.vn.services;

import java.util.Date;

import trungduc.vn.dao.IShareDao_24133012;
import trungduc.vn.dao.IUserDao_24133012;
import trungduc.vn.dao.IVideoDao_24133012;
import trungduc.vn.dao.ShareDaoImpl_24133012;
import trungduc.vn.dao.UserDaoImpl_24133012;
import trungduc.vn.dao.VideoDaoImpl_24133012;
import trungduc.vn.entity.Share_24133012;
import trungduc.vn.entity.User_24133012;
import trungduc.vn.entity.Video_24133012;

public class ShareServiceImpl_24133012 implements IShareService_24133012 {

    private IShareDao_24133012 shareDao = new ShareDaoImpl_24133012();
    private IUserDao_24133012 userDao = new UserDaoImpl_24133012();
    private IVideoDao_24133012 videoDao = new VideoDaoImpl_24133012();

    @Override
    public long countByVideoId(String videoId) {
        return shareDao.countByVideoId(videoId);
    }

    @Override
    public void shareVideo(String username, String videoId, String recipientEmail) {
        User_24133012 user = userDao.findById(username);
        Video_24133012 video = videoDao.findById(videoId);
        if (user != null && video != null) {
            Share_24133012 share = new Share_24133012(recipientEmail, new Date(), user, video);
            shareDao.insert(share);
        }
    }
}
