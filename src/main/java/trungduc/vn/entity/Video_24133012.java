package trungduc.vn.entity;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "Videos")
public class Video_24133012 implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "VideoId", length = 50, nullable = false)
    private String videoId;

    @Column(name = "Title", length = 200, nullable = false)
    private String title;

    @Column(name = "Poster", length = 500)
    private String poster;

    @Column(name = "Views", nullable = false)
    private int views;

    @Column(name = "Description", length = 500)
    private String description;

    @Column(name = "Active", nullable = false)
    private boolean active;

    @Column(name = "Price", nullable = false)
    private double price = 199000.0;

    @ManyToOne
    @JoinColumn(name = "CategoryId", nullable = false)
    private Category_24133012 category;

    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL)
    private List<Favorite_24133012> favorites;

    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL)
    private List<Share_24133012> shares;

    @Transient
    private long likeCount;

    @Transient
    private long shareCount;

    public Video_24133012() {
    }

    public Video_24133012(String videoId, String title, String poster, int views, String description, boolean active,
            Category_24133012 category) {
        this.videoId = videoId;
        this.title = title;
        this.poster = poster;
        this.views = views;
        this.description = description;
        this.active = active;
        this.category = category;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Category_24133012 getCategory() {
        return category;
    }

    public void setCategory(Category_24133012 category) {
        this.category = category;
    }

    public List<Favorite_24133012> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Favorite_24133012> favorites) {
        this.favorites = favorites;
    }

    public List<Share_24133012> getShares() {
        return shares;
    }

    public void setShares(List<Share_24133012> shares) {
        this.shares = shares;
    }

    public long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(long likeCount) {
        this.likeCount = likeCount;
    }

    public long getShareCount() {
        return shareCount;
    }

    public void setShareCount(long shareCount) {
        this.shareCount = shareCount;
    }
}
