package com.smartwebarts.briefnx.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewsForFegmentFront {

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
    private Object image;
    @SerializedName("video")
    @Expose
    private Object video;
    @SerializedName("views")
    @Expose
    private Object views;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("priority")
    @Expose
    private String priority;
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
    private Object shareCount;
    @SerializedName("tags")
    @Expose
    private Object tags;
    @SerializedName("article_category_id")
    @Expose
    private String articleCategoryId;
    @SerializedName("super_category_id")
    @Expose
    private String superCategoryId;
    @SerializedName("created_by")
    @Expose
    private Object createdBy;
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

    public Object getViews() {
        return views;
    }

    public void setViews(Object views) {
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

    public String getSuperCategoryId() {
        return superCategoryId;
    }

    public void setSuperCategoryId(String superCategoryId) {
        this.superCategoryId = superCategoryId;
    }

    public Object getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Object createdBy) {
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

}
