package com.smartwebarts.briefnx.dashboard.headerrecylerview;

public class Items implements ListItem {
    public SubArticleTopNews getNews() {
        return news;
    }

    public void setNews(SubArticleTopNews news) {
        this.news = news;
    }

    SubArticleTopNews news;

    public Items( SubArticleTopNews news)
    {
        this.news=news;
    }
    @Override
    public int getTypeItem() {
        return ListItem.TYPE_ITEM;
    }
}
