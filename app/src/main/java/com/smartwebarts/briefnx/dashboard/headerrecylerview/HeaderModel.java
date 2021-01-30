package com.smartwebarts.briefnx.dashboard.headerrecylerview;

public class HeaderModel implements ListItem {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name;

    @Override
    public int getTypeItem() {
        return ListItem.TYPE_HEADER;
    }

    public HeaderModel(String name) {
        this.name = name;
    }
}
