package com.smartwebarts.briefnx.newsdetail.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.smartwebarts.briefnx.dashboard.headerrecylerview.SubArticleTopNews;

import java.util.List;

public class Articles_Model {
    @SerializedName("payment_data")
    @Expose
    private PaymentArticleModel paymentData;
    @SerializedName("news")
    @Expose
    private List<NewsModelArticle> news = null;

    @SerializedName("sub_article_data")
    @Expose
    private List<NewsModelArticle> subArticleData = null;

    public PaymentArticleModel getPaymentData() {
        return paymentData;
    }

    public void setPaymentData(PaymentArticleModel paymentData) {
        this.paymentData = paymentData;
    }

    public List<NewsModelArticle> getNews() {
        return news;
    }

    public void setNews(List<NewsModelArticle> news) {
        this.news = news;
    }
    public List<NewsModelArticle> getSubArticleData() {
        return subArticleData;
    }

    public void setSubArticleData(List<NewsModelArticle> subArticleData) {
        this.subArticleData = subArticleData;
    }
}
