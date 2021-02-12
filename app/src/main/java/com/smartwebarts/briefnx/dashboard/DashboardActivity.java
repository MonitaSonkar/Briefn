package com.smartwebarts.briefnx.dashboard;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Movie;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;


import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.smartwebarts.briefnx.AdapterPositionListener;
import com.smartwebarts.briefnx.BuildConfig;
import com.smartwebarts.briefnx.ContactUsActivity;
import com.smartwebarts.briefnx.LoginActivity;
import com.smartwebarts.briefnx.MainActivity;
import com.smartwebarts.briefnx.R;
import com.smartwebarts.briefnx.WebViewActivity;

import com.smartwebarts.briefnx.dashboard.TabAdapter.NewsFragment;
import com.smartwebarts.briefnx.dashboard.search.SearchActivity;
import com.smartwebarts.briefnx.dashboard.TabAdapter.TabLayoutAdapter;
import com.smartwebarts.briefnx.dashboard.TabAdapter.TopNewsViewpagerAdapter;
import com.smartwebarts.briefnx.dashboard.viewmodel.DashboardViewModel;
import com.smartwebarts.briefnx.dashboard.viewmodel.NewsViewModel;
import com.smartwebarts.briefnx.models.NewsByCategoryModel;
import com.smartwebarts.briefnx.models.NewsModel;
import com.smartwebarts.briefnx.newsdetail.model.Articles_Model;
import com.smartwebarts.briefnx.newsdetail.model.NewsModelArticle;
import com.smartwebarts.briefnx.newsdetail.model.PaymentArticleModel;
import com.smartwebarts.briefnx.newsdetail.model.SubscriptionModel;
import com.smartwebarts.briefnx.profile.ProfileActivity;
import com.smartwebarts.briefnx.retrofit.UtilMethods;
import com.smartwebarts.briefnx.retrofit.mCallBackResponse;
import com.smartwebarts.briefnx.utils.AccessToken;
import com.smartwebarts.briefnx.utils.AppSharedPreferences;
import com.smartwebarts.briefnx.utils.Toolbar_Set;
import com.smartwebarts.briefnx.utils.Urls;
import com.smartwebarts.briefnx.utils.UsefullMethods;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.smartwebarts.briefnx.retrofit.UtilMethods.INSTANCE;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, PaymentResultListener {
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private List<NewsModel> list;
    private List<NewsByCategoryModel> CotegoryModel;
    private String language_set = "hindi";
    private DashboardViewModel mViewModel;
    public TabLayout tabLayout, subCat_tablayout;/*,tabLayoutsubcategory*/
    RecyclerView tabLayoutsubcategory;
    ViewPager viewPager;
    RadioButton hindi, english;
    private LinearLayout searchll;
    private SearchView search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        mViewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);
        mViewModel.init();
        listener = new ArrayList<>();
        tabLayout = findViewById(R.id.tabLayout);
        subCat_tablayout = findViewById(R.id.subCat_tablayout);
        tabLayoutsubcategory = findViewById(R.id.tabLayoutsubcategory);
        viewPager = findViewById(R.id.viewPager);
        searchll = findViewById(R.id.searchll);
        search = findViewById(R.id.search);
//        search.setIconified(false);
        searchll.setVisibility(View.GONE);
        UsefullMethods.hideKeyboard(DashboardActivity.this);
        //set toolbar in  activity
        Toolbar_Set.INSTANCE.setToolbar(this);
        Toolbar_Set.INSTANCE.setBottomNav(DashboardActivity.this, searchll, search);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (Urls.isFirst) {
            Urls.isFirst = false;
            setLangRecreate("hi");
        } else {
            AppSharedPreferences preferences = new AppSharedPreferences(getApplication());
            if (!TextUtils.isEmpty(preferences.getLanguage())) {
                Log.e("Language===", "" + preferences.getLanguage());
                if (preferences.getLanguage().equalsIgnoreCase("hi")) {
                    language_set = "hindi";
                } else {
                    language_set = "english";
                }
            } else {
                language_set = "hindi";
                Log.e("Language===", "" + language_set);
            }
            updateAccessToken();
            getObserver();
            //set navigation toolbar
            navigationView = (NavigationView) findViewById(R.id.nav_view);
            setNavigationDrawer();
            navigationView.setNavigationItemSelectedListener(this);
//        SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Log.e("DashBoard==", "onQueryTextSubmit");
                    Intent i = new Intent(DashboardActivity.this, SearchActivity.class);
                    i.putExtra("search_text", query);
                    startActivity(i);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    Log.e("DashBoard==", "onQueryTextChange");
                    return true;
                }
            });
            search.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {
//                Toast.makeText(DashboardActivity.this, "close", Toast.LENGTH_SHORT).show();
                    searchll.setVisibility(View.GONE);
                    return false;
                }
            });


            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    searchll.setVisibility(View.GONE);
                    int index = (tabLayout.getSelectedTabPosition());
                    if (index != 0) {
                        Log.e("addOnTabSelected",""+tabLayout.getTabAt(index).getTag());
                        int position = index - 1;
                        String id = list.get(position).getId();
                        myDarta(id);
                    } else {
                        setTopNewsViewpager();
                    }

                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
            subCat_tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    searchll.setVisibility(View.GONE);
                    if (tabLayout.getSelectedTabPosition() != 0) {
                        int index = (subCat_tablayout.getSelectedTabPosition());
//                    String id = CotegoryModel.get(index).getId();
                        dataUpdated(CotegoryModel.get(index).getId(), CotegoryModel.get(index).getSuperCategoryId());
                    }

                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        }


    }

    private void getObserver() {
        Observer observer = new Observer() {
            @Override
            public void onChanged(Object o) {
                list = (List<NewsModel>) o;
                setAdapter(list);
                setTopNewsViewpager();
            }
        };
        mViewModel.getCategory().observe(DashboardActivity.this, observer);

        Observer observersubcat = new Observer() {
            @Override
            public void onChanged(Object o) {

                CotegoryModel = (List<NewsByCategoryModel>) o;
                if (CotegoryModel != null) {

                    viewPager.setVisibility(View.VISIBLE);
                    setSubCatAdapter(CotegoryModel);
                    dataUpdated(CotegoryModel.get(0).getId(), CotegoryModel.get(0).getSuperCategoryId());
                }

            }
        };
        mViewModel.getSubCategory().observe(DashboardActivity.this, observersubcat);

        if (UtilMethods.INSTANCE.isNetworkAvialable(DashboardActivity.this)) {
            Dialog dialog = UtilMethods.getCommonProgressDialog(DashboardActivity.this);
            dialog.show();
            mViewModel.callCategory(dialog, new mCallBackResponse() {
                @Override
                public void success(String from, String message) {

                }

                @Override
                public void fail(String from) {
                    UsefullMethods.showMessage(DashboardActivity.this, SweetAlertDialog.ERROR_TYPE, "Error", from, "OK", () -> {

                    });

                }
            });
        } else {
            UtilMethods.INSTANCE.internetNotAvailableMessage(DashboardActivity.this);
        }
    }


    //    public AdapterPositionListener listener;
    private ArrayList<AdapterPositionListener> listener;

    public void registerlistener(AdapterPositionListener mlistner) {
//        this.listener = listener;
        if (listener == null) {
            listener = new ArrayList<>();
        }
        listener.add(mlistner);
//        listener.add(mlistner);
    }

    public synchronized void unregisterDataUpdateListener(AdapterPositionListener mlistner) {
        listener.remove(mlistner);
    }

    public synchronized void dataUpdated(String id, String superCategoryId) {
        for (AdapterPositionListener mlistener : listener) {
            mlistener.onClick(id, superCategoryId);
        }
    }

    //for set data in adapter Super Category
    private void setAdapter(List<NewsModel> list) {

        tabLayout.addTab(tabLayout.newTab().setCustomView(createTabView("", getResources().getString(R.string.top_news))));
        if (list != null && list.size() > 0) {
            if (language_set.equalsIgnoreCase("english")) {
                for (int i = 0; i < list.size(); i++) {
                    if (!TextUtils.isEmpty(list.get(i).getTitle())) {
                        String image_url = Urls.NEWS_imgesIcon + list.get(i).getImage();
                        String title = list.get(i).getTitle();
                        tabLayout.addTab(tabLayout.newTab().setCustomView(createTabView(image_url, title)).setTag(list.get(i).getId()));
//                        tabLayout.getTabAt(i).setTag(list.get(i).getId());
                    }
                }
            } else {
                for (int i = 0; i < list.size(); i++) {
                    if (!TextUtils.isEmpty(list.get(i).getNameHi())) {
                        String image_url = Urls.NEWS_imgesIcon + list.get(i).getImage();
                        String title = list.get(i).getNameHi();
                        tabLayout.addTab(tabLayout.newTab().setCustomView(createTabView(image_url, title)).setTag(list.get(i).getId()));
                    }
                }
            }

        }
    }


    private View createTabView(String imagUri, String title) {
        View view = LayoutInflater.from(this).inflate(R.layout.custom_tab_layout, null);
        TextView tabtitle = view.findViewById(R.id.tabtitle);
        ImageView tabimage = view.findViewById(R.id.tabimage);
        tabtitle.setText(title);
        return view;
    }

    private void setNavigationDrawer() {

        View headerLayout = navigationView.getHeaderView(0);
        Menu nav_Menu = navigationView.getMenu();
        TextView tvUser = headerLayout.findViewById(R.id.tvName);
        TextView tvEmail = headerLayout.findViewById(R.id.tvEmail);

        AppSharedPreferences preferences = new AppSharedPreferences(getApplication());

        if (preferences.getLoginUserName() != null && !preferences.getLoginUserName().isEmpty()) {
            String[] s = preferences.getLoginUserName().trim().toUpperCase().split("\\s+");
            tvUser.setText(String.format("Welcome %s", s[0]));
            tvEmail.setText(s[0]);
        } else {
            nav_Menu.findItem(R.id.nav_gallery).setVisible(false);
            nav_Menu.findItem(R.id.logout).setVisible(false);
        }

        if (preferences.getLoginEmail() != null && !preferences.getLoginEmail().isEmpty()) {
            tvEmail.setText(String.format("%s", preferences.getLoginEmail()));
        } else {
            tvEmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
                }
            });
        }

    }


    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finish();
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = false;
                    }
                },
                2000
        );
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        // Handle navigation view item clicks here.
        //close navigation drawer
        drawer.closeDrawer(GravityCompat.START);

        switch (item.getItemId()) {


            case R.id.nav_MyAccount: {
                Intent p = new Intent(DashboardActivity.this, ProfileActivity.class);
                startActivity(p);
                break;
            }
            case R.id.terms: {
                Intent intent = new Intent(DashboardActivity.this, WebViewActivity.class);
                intent.putExtra(WebViewActivity.DATA, Urls.TERMS_CONDITION);
                intent.putExtra(WebViewActivity.TITLE, "Terms & Conditions");
                startActivity(intent);
                break;
            }
            case R.id.privacy_policy: {
                Intent intent = new Intent(DashboardActivity.this, WebViewActivity.class);
                intent.putExtra(WebViewActivity.DATA, Urls.PRIVACY_POLICY);
                intent.putExtra(WebViewActivity.TITLE, "Privacy Policy");
                startActivity(intent);
                break;
            }
            case R.id.refund_policy: {
                Intent intent = new Intent(DashboardActivity.this, WebViewActivity.class);
                intent.putExtra(WebViewActivity.DATA, Urls.REFUND_POLICY);
                intent.putExtra(WebViewActivity.TITLE, "Refund Policy");
                startActivity(intent);
                break;
            }
            case R.id.about_us: {
                Intent intent = new Intent(DashboardActivity.this, WebViewActivity.class);
                intent.putExtra(WebViewActivity.DATA, Urls.ABOUT_US);
                intent.putExtra(WebViewActivity.TITLE, "About Us");
                startActivity(intent);
                break;
            }
            case R.id.live_chat: {
                try {
                    String url = "https://chat.whatsapp.com/";
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                } catch (Exception e) {
                    Toast.makeText(this, "Unable to open your whatsapp", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.share_app: {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                break;
            }
            case R.id.contact_us: {
                startActivity(new Intent(DashboardActivity.this, ContactUsActivity.class));
//                finish();
                break;
            }
            case R.id.faq: {
                Intent intent = new Intent(DashboardActivity.this, WebViewActivity.class);
                intent.putExtra(WebViewActivity.DATA, Urls.FAQ);
                intent.putExtra(WebViewActivity.TITLE, "FAQs");
                startActivity(intent);
                break;
            }

            case R.id.selectLanguage: {
                Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.select_languag_layout);
                dialog.show();
                hindi = dialog.findViewById(R.id.hindi);
                english = dialog.findViewById(R.id.english);
                AppSharedPreferences preferences = new AppSharedPreferences(getApplication());
                if (!TextUtils.isEmpty(preferences.getLanguage())) {
                    Log.e("Language===", "" + preferences.getLanguage());
                    if (preferences.getLanguage().equalsIgnoreCase("hi")) {
                        hindi.setChecked(true);
                    } else {
                        english.setChecked(true);
                    }

                }
                english.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        setLangRecreate("en");
                    }
                });
                hindi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        setLangRecreate("hi");
                    }
                });
                break;
            }
            case R.id.logout: {
                logout();
                break;
            }
        }
        return true;
    }

    // logout code
    private void logout() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.default_progress_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        ProgressBar progressBar = (ProgressBar) dialog.findViewById(R.id.progress);
        DoubleBounce doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);
        Urls.isFirst = false;
        dialog.show();
        AppSharedPreferences preferences = new AppSharedPreferences(DashboardActivity.this.getApplication());
        preferences.logout(DashboardActivity.this);
        dialog.dismiss();
    }


    public void openDrawer(View view) {
        drawer.openDrawer(GravityCompat.START);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }


    @Override
    protected void onResume() {

        super.onResume();
        search.setFocusable(false);
//        UsefullMethods.hideKeyboard(DashboardActivity.this);
//        Toolbar_Set.INSTANCE.getCartList(this);
    }

    private void updateAccessToken() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.e("TAG", "getInstanceId failed", task.getException());
                            return;
                        }
                        AppSharedPreferences preferences = new AppSharedPreferences(getApplication());
                        // Get new Instance ID token
                        String token = Objects.requireNonNull(task.getResult()).getToken();
                        new AccessToken().setAccess_token(getApplicationContext(), token);
                        mViewModel.updateAccessToken(preferences.getLoginUserId(), preferences.getLoginApiKey(), token,
                                new mCallBackResponse() {
                                    @Override
                                    public void success(String from, String message) {
//                                        Toast.makeText(DashboardActivity.this, message, Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void fail(String from) {
//                                        Toast.makeText(DashboardActivity.this, from, Toast.LENGTH_SHORT).show();
                                    }
                                });

                    }
                });
    }

    public void myDarta(String id) {
        if (UtilMethods.INSTANCE.isNetworkAvialable(DashboardActivity.this)) {
            Dialog dialog = UtilMethods.getCommonProgressDialog(DashboardActivity.this);
            dialog.show();
            mViewModel.callToSubcategoryApi(id, dialog, new mCallBackResponse() {
                @Override
                public void success(String from, String message) {
                    subCat_tablayout.setVisibility(View.VISIBLE);
                    viewPager.setVisibility(View.VISIBLE);
                }

                @Override
                public void fail(String from) {
                    subCat_tablayout.setVisibility(View.GONE);
                    viewPager.setVisibility(View.GONE);
                    UsefullMethods.showMessage(DashboardActivity.this, SweetAlertDialog.ERROR_TYPE, "Error", from, "OK", () -> {
                    });
                }
            });
        } else {
            subCat_tablayout.setVisibility(View.GONE);
            viewPager.setVisibility(View.GONE);
            UtilMethods.INSTANCE.internetNotAvailableMessage(DashboardActivity.this);
        }

//set data subcategory
//                        UtilMethods.INSTANCE.TopnewsCureent(DashboardActivity.this, id, new mCallBackResponse() {
//                            @Override
//                            public void success(String from, String message) {
//                                Type type = new TypeToken<List<NewsByCategoryModel>>() {
//                                }.getType();
//                                subCat_tablayout.setVisibility(View.VISIBLE);
//                                viewPager.setVisibility(View.VISIBLE);
//                                CotegoryModel = new Gson().fromJson(message, type);
//                                setSubCatAdapter(CotegoryModel);
//                                dataUpdated(CotegoryModel.get(0).getId(), CotegoryModel.get(0).getSuperCategoryId());
////                listener.onClick(CotegoryModel.get(0).getId(), CotegoryModel.get(0).getSuperCategoryId());
////                setSubcategory(CotegoryModel);
//                            }
//
//                            @Override
//                            public void fail(String from) {
//                                subCat_tablayout.setVisibility(View.GONE);
//                                viewPager.setVisibility(View.GONE);
//
//                            }
//                        });

    }

    private void setSubCatAdapter(List<NewsByCategoryModel> cotegoryModel) {
        ArrayList<NewsByCategoryModel> newsByCategoryModelArrayListeng;
        ArrayList<NewsByCategoryModel> newsByCategoryModelArrayListhin;
        if (language_set.equalsIgnoreCase("english")) {
            newsByCategoryModelArrayListeng = new ArrayList<>();
            for (int i = 0; i < cotegoryModel.size(); i++) {
                if (!TextUtils.isEmpty(cotegoryModel.get(i).getTitle())) {
                    newsByCategoryModelArrayListeng.add(cotegoryModel.get(i));
                }
            }
            setTablayout(newsByCategoryModelArrayListeng);
        } else {
            newsByCategoryModelArrayListhin = new ArrayList<>();
            for (int i = 0; i < cotegoryModel.size(); i++) {
                if (!TextUtils.isEmpty(cotegoryModel.get(i).getNameHi())) {
                    newsByCategoryModelArrayListhin.add(cotegoryModel.get(i));
                }
            }
            setTablayout(newsByCategoryModelArrayListhin);
        }
    }

    private void setTablayout(ArrayList<NewsByCategoryModel> newsByCategoryModelArrayListeng) {
        if (newsByCategoryModelArrayListeng.size() > 0) {
            viewPager.removeAllViews();
//                            subCat_tablayout.setVisibility(View.VISIBLE);
            TabLayoutAdapter adapter = new TabLayoutAdapter(getSupportFragmentManager(), newsByCategoryModelArrayListeng, language_set);
            if (newsByCategoryModelArrayListeng != null && newsByCategoryModelArrayListeng.size() > 0) {
                for (int i = 0; i < newsByCategoryModelArrayListeng.size(); i++) {
//                NewsFragment newsFragment = new NewsFragment(cotegoryModel.get(i));
                    NewsFragment newsFragment = new NewsFragment();
//                newsFragment.setNewsByCategoryModel(cotegoryModel.get(i));
                    adapter.add(newsFragment);
                }
            }
            viewPager.setAdapter(adapter);
            subCat_tablayout.setupWithViewPager(viewPager);
        } else {
            subCat_tablayout.setVisibility(View.GONE);
        }

    }

    private void setTopNewsViewpager() {
        viewPager.removeAllViews();
        TopNewsViewpagerAdapter adapter = new TopNewsViewpagerAdapter(getSupportFragmentManager());
        subCat_tablayout.setVisibility(View.GONE);
        viewPager.setVisibility(View.VISIBLE);
        NewsFragment newsFragment = new NewsFragment();
        adapter.add(newsFragment);
        viewPager.setAdapter(adapter);
        dataUpdated("", "");
    }


    public void setLangRecreate(String langval) {
        AppSharedPreferences preferences = new AppSharedPreferences(getApplication());
        preferences.setlanguage(langval);
        Configuration config = getBaseContext().getResources().getConfiguration();
        Locale locale = new Locale(langval);
        Locale.setDefault(locale);
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        Intent intent = new Intent(DashboardActivity.this, DashboardActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
//                        recreate();
    }


    public void startPayment() {
        /**
         * Instantiate Checkout
         */
        Checkout checkout = new Checkout();
        checkout.setKeyID("" + getString(R.string.razor_api_key));


        /**
         * Set your logo here
         */
        checkout.setImage(R.drawable.logo);

        /**
         * Reference to current activity
         */
        final Activity activity = this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            /**
             * Merchant Name
             * eg: ACME Corp || HasGeek etc.
             */
            options.put("name", getString(R.string.app_name));

            /**
             * Description can be anything
             * eg: Reference No. #123123 - This order number is passed by you for your internal reference. This is not the `razorpay_order_id`.
             *     Invoice Payment
             *     etc.
             */
            options.put("description", "Reference No. #123456");
            options.put("image", getDrawable(R.drawable.logo));
//            options.put("order_id", ""+model.getOrderid());
            options.put("currency", "INR");

            /**
             * Amount is always passed in currency subunits
             * Eg: "500" = INR 5.00
             */

//            int dAmount = Integer.parseInt("0"+amount);
            int dAmount = Integer.parseInt("0" + 50);
            dAmount *= 100;

            options.put("amount", "" + dAmount);

            checkout.open(this, options);
        } catch (Exception e) {
            Log.e("DashBoard===", "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
//        buildJson(s);
    }

    @Override
    public void onPaymentError(int i, String s) {

    }


    /*private void buildJson( String orderid) {
        AppSharedPreferences preferences = new AppSharedPreferences(getApplication());
        List<DeliveryProductDetails> products = new ArrayList<>();
        for (Task t: list) {
            DeliveryProductDetails d = new DeliveryProductDetails(preferences.getLoginUserLoginId(),
                    t.getQuantity(),
                    t.getId(),
                    t.getCurrentprice(),
                    t.getName(),
                    t.getUnit(),
                    t.getUnitIn(),
                    t.getThumbnail(),
                    preferences.getLoginMobile(),
                    orderid,
                    "1",
                    paymentmethod,
                    address,
                    landmark,
                    pincode,
                    date,
                    time);
            products.add(d);

            if (hashMap.get(d.getProId())!=null && !hashMap.get(d.getProId()).isEmpty()) {
                d.setAmount(hashMap.get(d.getProId()));
            }else {
                try {
                    int a = Integer.parseInt("0"+d.getAmount().trim());
                    int b = Integer.parseInt("0"+d.getQty().trim());
                    int f = a*b;
                    d.setAmount(f+"");
                } catch (Exception ignore){}
            }
        }

        Log.e("DeliveryDetails", new Gson().toJson(products));

        hitServiceOrder(products);
    }

    private void hitServiceOrder(List<DeliveryProductDetails> products) {
        if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {

            int tempTotal = 0;
            int discount = 0;
            String deliverycharge = "0";
            try {

                for (DeliveryProductDetails product : products){
                    tempTotal = tempTotal + Integer.parseInt("0"+product.getAmount());
                }

                SharedPreferences preferences = getSharedPreferences(ApplicationConstants.INSTANCE.DELIVERY_PREFS, MODE_PRIVATE);
                String strResponse = preferences.getString(ApplicationConstants.INSTANCE.DELIVERY_CHARGES, "");
                Type type = new TypeToken<List<DeliveryChargesModel>>(){}.getType();
                List<DeliveryChargesModel> chargesModelList = new Gson().fromJson(strResponse, type);
                for (DeliveryChargesModel model : chargesModelList) {
                    int min = Integer.parseInt("0"+model.getMinAmt());
                    int max = Integer.parseInt("0"+model.getMaxAmt());
                    int deliveryCharges = Integer.parseInt("0"+model.getAmt());
                    if (tempTotal>min && tempTotal <max) {
                        tempTotal = tempTotal + deliveryCharges;
                        deliverycharge = deliveryCharges + "";
                        break;
                    }
                }
                discount = Integer.parseInt(amount) - tempTotal;
            }
            catch (Exception e) {
                tempTotal = 0;
                discount = 0;
            }
//            final Dialog dialog = new Dialog(this);
//            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            dialog.setContentView(R.layout.default_progress_dialog);
//            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//            ProgressBar progressBar = (ProgressBar)dialog.findViewById(R.id.progress);
//            DoubleBounce doubleBounce = new DoubleBounce();
//            progressBar.setIndeterminateDrawable(doubleBounce);
//            dialog.show();

            UtilMethods.INSTANCE.order(this, products.get(0), amount, ""+discount, deliverycharge, new Gson().toJson(products), new mCallBackResponse() {
                @Override
                public void success(String from, String message) {

                    OrderedResponse response = new Gson().fromJson(message, OrderedResponse.class);
                    if (response != null && response.getMessage() !=null && response.getMessage().equalsIgnoreCase("success")) {
                        showSuccessMessage(response);
                    } else {
                        showErrorMessage(response);
                    }
                }

                @Override
                public void fail(String from) {
                    showFailMessage(null);
                }
            });

            for (DeliveryProductDetails product : products){


            }
        } else {
            UtilMethods.INSTANCE.internetNotAvailableMessage(this);
        }
    }*/

  /*  @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
//        outState.putString("listner", String.valueOf(listener));
        outState.putParcelableArrayList("listner", (ArrayList<? extends Parcelable>) listener);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        listener = savedInstanceState.getParcelableArrayList("listner");
    }*/
}
