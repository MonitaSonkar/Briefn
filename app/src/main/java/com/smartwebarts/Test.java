package com.smartwebarts;

import com.smartwebarts.briefnx.dashboard.headerrecylerview.SubArticleTopNews;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Test {

    List<SubArticleTopNews> subArticleTopNews;
    List<String> headers = new ArrayList<>();
    HashMap<String, ArrayList<SubArticleTopNews>> hashmap = new HashMap<String, ArrayList<SubArticleTopNews>>();

    public Test(List<SubArticleTopNews> subArticleTopNews) {
        this.subArticleTopNews = subArticleTopNews;


        for (SubArticleTopNews subArticleTopNews1: subArticleTopNews) {

            if (!headers.contains(subArticleTopNews1.getHeader())) {
                headers.add(subArticleTopNews1.getHeader());
            }
        }

        for (String s: headers) {
            ArrayList<SubArticleTopNews> subarticles=new ArrayList<>();
            for (SubArticleTopNews subArticleTopNews1: subArticleTopNews) {

                if (s.equalsIgnoreCase(subArticleTopNews1.getHeader())) {
                    subarticles.add(subArticleTopNews1);
                }
            }
            hashmap.put(s,subarticles);
        }
    }
}


