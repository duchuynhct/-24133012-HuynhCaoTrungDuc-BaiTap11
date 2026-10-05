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
@Table(name = "Favorites")
public class Favorite_24133012 implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FavoriteId")
    private int favoriteId;

    @Temporal(TemporalType.DATE)
    @Column(name = "LikedDate", nullable = false)
    private Date likedDate;

    @ManyToOne
    @JoinColumn(name = "VideoId", nullable = false)
    private Video_24133012 video;

    @ManyToOne
    @JoinColumn(name = "Username", nullable = false)
    private User_24133012 user;

    public Favorite_24133012() {
    }

    public Favorite_24133012(Date likedDate, Video_24133012 video, User_24133012 user) {
        this.likedDate = likedDate;
        this.video = video;
        this.user = user;
    }

    public int getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(int favoriteId) {
        this.favoriteId = favoriteId;
    }

    public Date getLikedDate() {
        return likedDate;
    }

    public void setLikedDate(Date likedDate) {
        this.likedDate = likedDate;
    }

    public Video_24133012 getVideo() {
        return video;
    }

    public void setVideo(Video_24133012 video) {
        this.video = video;
    }

    public User_24133012 getUser() {
        return user;
    }

    public void setUser(User_24133012 user) {
        this.user = user;
    }
}
