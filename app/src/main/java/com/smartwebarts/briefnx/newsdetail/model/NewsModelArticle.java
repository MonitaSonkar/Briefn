package com.smartwebarts.briefnx.newsdetail.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NewsModelArticle implements Serializable {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("lang")
    @Expose
    private String lang;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("sub_head")
    @Expose
    private String subHead;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("video")
    @Expose
    private String video;
    @SerializedName("views")
    @Expose
    private String views;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("priority")
    @Expose
    private String priority;
    @SerializedName("top_priority")
    @Expose
    private String topPriority;
    @SerializedName("publish_date")
    @Expose
    private String publishDate;
    @SerializedName("bylines_id")
    @Expose
    private String bylinesId;
    @SerializedName("self_life")
    @Expose
    private String selfLife;
    @SerializedName("dateline")
    @Expose
    private String dateline;
    @SerializedName("display_topic")
    @Expose
    private String displayTopic;
    @SerializedName("share_count")
    @Expose
    private String shareCount;
    @SerializedName("tags")
    @Expose
    private String tags;
    @SerializedName("article_category_id")
    @Expose
    private String articleCategoryId;
    @SerializedName("super_category_id")
    @Expose
    private String superCategoryId;
    @SerializedName("created_by")
    @Expose
    private String createdBy;
    @SerializedName("g_title")
    @Expose
    private String gTitle;
    @SerializedName("g_url")
    @Expose
    private String gUrl;
    @SerializedName("g_description")
    @Expose
    private String gDescription;
    @SerializedName("g_keyword")
    @Expose
    private String gKeyword;
    @SerializedName("trending_tags")
    @Expose
    private String trendingTags;
    @SerializedName("location_tag")
    @Expose
    private String locationTag;
    @SerializedName("people_tag")
    @Expose
    private String peopleTag;
    @SerializedName("brand_tag")
    @Expose
    private String brandTag;
    @SerializedName("event_tag")
    @Expose
    private String eventTag;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getTopPriority() {
        return topPriority;
    }

    public void setTopPriority(String topPriority) {
        this.topPriority = topPriority;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getBylinesId() {
        return bylinesId;
    }

    public void setBylinesId(String bylinesId) {
        this.bylinesId = bylinesId;
    }

    public String getSelfLife() {
        return selfLife;
    }

    public void setSelfLife(String selfLife) {
        this.selfLife = selfLife;
    }

    public String getDateline() {
        return dateline;
    }

    public void setDateline(String dateline) {
        this.dateline = dateline;
    }

    public String getDisplayTopic() {
        return displayTopic;
    }

    public void setDisplayTopic(String displayTopic) {
        this.displayTopic = displayTopic;
    }

    public String getShareCount() {
        return shareCount;
    }

    public void setShareCount(String shareCount) {
        this.shareCount = shareCount;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getArticleCategoryId() {
        return articleCategoryId;
    }

    public void setArticleCategoryId(String articleCategoryId) {
        this.articleCategoryId = articleCategoryId;
    }

    public String getSuperCategoryId() {
        return superCategoryId;
    }

    public void setSuperCategoryId(String superCategoryId) {
        this.superCategoryId = superCategoryId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getGTitle() {
        return gTitle;
    }

    public void setGTitle(String gTitle) {
        this.gTitle = gTitle;
    }

    public String getGUrl() {
        return gUrl;
    }

    public void setGUrl(String gUrl) {
        this.gUrl = gUrl;
    }

    public String getGDescription() {
        return gDescription;
    }

    public void setGDescription(String gDescription) {
        this.gDescription = gDescription;
    }

    public String getGKeyword() {
        return gKeyword;
    }

    public void setGKeyword(String gKeyword) {
        this.gKeyword = gKeyword;
    }

    public String getTrendingTags() {
        return trendingTags;
    }

    public void setTrendingTags(String trendingTags) {
        this.trendingTags = trendingTags;
    }

    public String getLocationTag() {
        return locationTag;
    }

    public void setLocationTag(String locationTag) {
        this.locationTag = locationTag;
    }

    public String getPeopleTag() {
        return peopleTag;
    }

    public void setPeopleTag(String peopleTag) {
        this.peopleTag = peopleTag;
    }

    public String getBrandTag() {
        return brandTag;
    }

    public void setBrandTag(String brandTag) {
        this.brandTag = brandTag;
    }

    public String getEventTag() {
        return eventTag;
    }

    public void setEventTag(String eventTag) {
        this.eventTag = eventTag;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
    @SerializedName("is_paid")
    @Expose
    private String isPaid;
    @SerializedName("pmt_status")
    @Expose
    private String pmtStatus;

    public String getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(String isPaid) {
        this.isPaid = isPaid;
    }

    public String getPmtStatus() {
        return pmtStatus;
    }

    public void setPmtStatus(String pmtStatus) {
        this.pmtStatus = pmtStatus;
    }

}

