package com.smartwebarts.briefnx.dashboard.TabAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.smartwebarts.briefnx.models.NewsByCategoryModel;

import java.util.ArrayList;
import java.util.List;

public class TopNewsViewpagerAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> fragments=new ArrayList<>();

    public TopNewsViewpagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
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
    }



}
