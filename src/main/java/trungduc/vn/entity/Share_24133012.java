package trungduc.vn.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "Shares")
public class Share_24133012 implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ShareId")
    private int shareId;

    @Column(name = "Emails", length = 100)
    private String emails;

    @Temporal(TemporalType.DATE)
    @Column(name = "SharedDate", nullable = false)
    private Date sharedDate;

    @ManyToOne
    @JoinColumn(name = "Username", nullable = false)
    private User_24133012 user;

    @ManyToOne
    @JoinColumn(name = "VideoId", nullable = false)
    private Video_24133012 video;

    public Share_24133012() {
    }

    public Share_24133012(String emails, Date sharedDate, User_24133012 user, Video_24133012 video) {
        this.emails = emails;
        this.sharedDate = sharedDate;
        this.user = user;
        this.video = video;
    }

    public int getShareId() {
        return shareId;
    }

    public void setShareId(int shareId) {
        this.shareId = shareId;
    }

    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }

    public Date getSharedDate() {
        return sharedDate;
    }

    public void setSharedDate(Date sharedDate) {
        this.sharedDate = sharedDate;
    }

    public User_24133012 getUser() {
        return user;
    }

    public void setUser(User_24133012 user) {
        this.user = user;
    }

    public Video_24133012 getVideo() {
        return video;
    }

    public void setVideo(Video_24133012 video) {
        this.video = video;
    }
}
