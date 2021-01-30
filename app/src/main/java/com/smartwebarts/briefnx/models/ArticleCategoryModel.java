package com.smartwebarts.briefnx.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ArticleCategoryModel {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("sub_head")
    @Expose
    private String subHead;
    @SerializedName("image")
    @Expose
    private Object image;
    @SerializedName("video")
    @Expose
    private Object video;
    @SerializedName("views")
    @Expose
    private String views;
    @SerializedName("description")
    @Expose
    private Object description;
    @SerializedName("publish_date")
    @Expose
    private String publishDate;
    @SerializedName("bylines_id")
    @Expose
    private Object bylinesId;
    @SerializedName("self_life")
    @Expose
    private Object selfLife;
    @SerializedName("dateline")
    @Expose
    private Object dateline;
    @SerializedName("display_topic")
    @Expose
    private Object displayTopic;
    @SerializedName("share_count")
    @Expose
    private Object shareCount;
    @SerializedName("tags")
    @Expose
    private Object tags;
    @SerializedName("article_category_id")
    @Expose
    private String articleCategoryId;
    @SerializedName("created_by")
    @Expose
    private Object createdBy;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubHead() {
        return subHead;
    }

    public void setSubHead(String subHead) {
        this.subHead = subHead;
    }

    public Object getImage() {
        return image;
    }

    public void setImage(Object image) {
        this.image = image;
    }

    public Object getVideo() {
        return video;
    }

    public void setVideo(Object video) {
        this.video = video;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public Object getBylinesId() {
        return bylinesId;
    }

    public void setBylinesId(Object bylinesId) {
        this.bylinesId = bylinesId;
    }

    public Object getSelfLife() {
        return selfLife;
    }

    public void setSelfLife(Object selfLife) {
        this.selfLife = selfLife;
    }

    public Object getDateline() {
        return dateline;
    }

    public void setDateline(Object dateline) {
        this.dateline = dateline;
    }

    public Object getDisplayTopic() {
        return displayTopic;
    }

    public void setDisplayTopic(Object displayTopic) {
        this.displayTopic = displayTopic;
    }

    public Object getShareCount() {
        return shareCount;
    }

    public void setShareCount(Object shareCount) {
        this.shareCount = shareCount;
    }

    public Object getTags() {
        return tags;
    }

    public void setTags(Object tags) {
        this.tags = tags;
    }

    public String getArticleCategoryId() {
        return articleCategoryId;
    }

    public void setArticleCategoryId(String articleCategoryId) {
        this.articleCategoryId = articleCategoryId;
    }

    public Object getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Object createdBy) {
        this.createdBy = createdBy;
    }

}
