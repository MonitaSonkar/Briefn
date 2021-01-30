package com.smartwebarts.briefnx.dashboard.TabAdapter;



import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.smartwebarts.briefnx.models.NewsByCategoryModel;
import com.smartwebarts.briefnx.models.NewsModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TabLayoutAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment>fragments=new ArrayList<>();
    List<NewsByCategoryModel>strings;
    String language_set;

    public TabLayoutAdapter(@NonNull FragmentManager fm, List<NewsByCategoryModel> list, String language_set) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.strings=list;
        this.language_set=language_set;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
//        Log.e("TablayoutAdapter==",""+position);
//        Bundle bundle=new Bundle();
//        bundle.putSerializable("list", (Serializable) strings.get(position));
//        fragments.get(position).setArguments(bundle);
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
public void add(Fragment fr)
{

    fragments.add(fr);
//    strings.add(str);
}


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(strings!=null&strings.size()>0)
        {
            if (language_set.equalsIgnoreCase("english")) {
                return strings.get(position).getTitle();
            } else {
                return strings.get(position).getNameHi();
            }
        }
      return null;

    }
}
