package com.smartwebarts.briefnx.models;

import android.content.ClipData;
import android.graphics.pdf.PdfDocument;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class YoutubeMainModel {

    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("etag")
    @Expose
    private String etag;
    @SerializedName("nextPageToken")
    @Expose
    private String nextPageToken;
    @SerializedName("regionCode")
    @Expose
    private String regionCode;
    @SerializedName("pageInfo")
    @Expose
    private PdfDocument.PageInfo pageInfo;
    @SerializedName("items")
    @Expose
    private List<YoutubeItemsModels> items = null;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public PdfDocument.PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PdfDocument.PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public List<YoutubeItemsModels> getItems() {
        return items;
    }

    public void setItems(List<YoutubeItemsModels> items) {
        this.items = items;
    }

}
