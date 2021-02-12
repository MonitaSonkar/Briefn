package com.smartwebarts.briefnx.dashboard.TabAdapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.smartwebarts.briefnx.R;
import com.smartwebarts.briefnx.SplashScreenActivity;
import com.smartwebarts.briefnx.dashboard.DashboardActivity;
import com.smartwebarts.briefnx.dashboard.viewmodel.DashboardViewModel;
import com.smartwebarts.briefnx.dashboard.viewmodel.NewsViewModel;
import com.smartwebarts.briefnx.newsdetail.NewsDetailsActivity;
import com.smartwebarts.briefnx.newsdetail.adapter.SubscriptionAdapter;
import com.smartwebarts.briefnx.newsdetail.model.NewsModelArticle;
import com.smartwebarts.briefnx.newsdetail.model.SubscriptionModel;
import com.smartwebarts.briefnx.retrofit.UtilMethods;
import com.smartwebarts.briefnx.retrofit.mCallBackResponse;
import com.smartwebarts.briefnx.utils.AppSharedPreferences;
import com.smartwebarts.briefnx.utils.UsefullMethods;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SubscribtionDailog extends AppCompatActivity implements PaymentResultListener {
    public List<SubscriptionModel> list;
    public List<NewsModelArticle> newslist;
    private NewsViewModel viewModel;
    private String pacakge_id="";
    private TextView ok;
    private AppCompatTextView cancel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(NewsViewModel.class);
        viewModel.init();
        setContentView(R.layout.subscription_dailog_layout);
        RecyclerView recycler_view=findViewById(R.id.recycler_view);
        ok=findViewById(R.id.ok_tv);
        cancel=findViewById(R.id.cancel_tv);
        LinearLayoutManager mLayoutManagerVideo = new LinearLayoutManager(SubscribtionDailog.this);
        mLayoutManagerVideo.setOrientation(LinearLayoutManager.VERTICAL);
        recycler_view.setLayoutManager(mLayoutManagerVideo);
        newslist = null;
     /*   if(getIntent().hasExtra("Subscription"))
        {
            list = (List<SubscriptionModel>) getIntent().getSerializableExtra("Subscription");
            Log.e("Subscription Dialog===",""+list.size());
        }*/
        if(getIntent().hasExtra("newslist"))
        {
            newslist = (List<NewsModelArticle>) getIntent().getSerializableExtra("newslist");
            Log.e("Subscription1:",""+newslist.size());
        }
        Observer observer1 = new Observer() {
            @Override
            public void onChanged(Object o) {

//                UtilMethods.subscribtion_plans = (List<SubscriptionModel>) o;
                list = (List<SubscriptionModel>) o;

                Log.e("Subscription Dialog===",""+list.size());
                SubscriptionAdapter adapter =new SubscriptionAdapter(SubscribtionDailog.this,list);
                recycler_view.setAdapter(adapter);
//                Log.e("SubscribtionDailog===",""+UtilMethods.subscribtion_plans.size());
//                Toast.makeText(SplashScreenActivity.this, ""+UtilMethods.subscribtion_plans.size(), Toast.LENGTH_SHORT).show();
            }
        };
        viewModel.getPackages().observe(SubscribtionDailog.this, observer1);

        if (UtilMethods.INSTANCE.isNetworkAvialable(SubscribtionDailog.this)) {
            Dialog dialog = UtilMethods.getCommonProgressDialog(SubscribtionDailog.this);
            dialog.show();
            viewModel.callPackage("packeges", dialog);
        } else {
            UtilMethods.INSTANCE.internetNotAvailableMessage(SubscribtionDailog.this);
        }
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Subscription Ok===1",""/*+list.get(i).isSelected()*/);
                finish();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TAG", "onClick: " );
                Log.e("Subscription Ok","hello");
                int index = -1;
                for(int i=0;i<list.size();i++)
                {
                    if(list.get(i).isSelected())
                    {
                        index=i;
                        Log.e("Subscription Ok===",""+list.get(i).isSelected()+"::"+list.get(i).getName());
                    }
                }

                if(index>-1)
                {
                    pacakge_id=list.get(index).getId();

                    startPayment(list.get(index));
                }
                else
                {
                    Toast.makeText(SubscribtionDailog.this,"Select the subscription package",Toast.LENGTH_LONG).show();
                }


            }
        });

    }
    public void startPayment(SubscriptionModel subscriptionModel) {
        /**
         * Instantiate Checkout
         */
        Checkout checkout = new Checkout();
        checkout.setKeyID(""+getString(R.string.razor_api_key));


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
//            int dAmount = Integer.parseInt("0"+subscriptionModel.getPrice());
            int dAmount = Integer.parseInt("0"+"1");
            dAmount*=100;

            options.put("amount", ""+dAmount);

            checkout.open(this, options);
        } catch(Exception e) {
            Log.e("DashBoard===", "Error in starting Razorpay Checkout", e);
        }
    }
    private String order_id="",signature="",status="";
    @Override
    public void onPaymentSuccess(String payment_id) {
        Log.e("DashBoard===", "SuccesFull="+payment_id);
//        buildJson(s);
        status="sucess";
        AppSharedPreferences preferences = new AppSharedPreferences(getApplication());
        if (UtilMethods.INSTANCE.isNetworkAvialable(SubscribtionDailog.this))
        {
            Dialog dialog = UtilMethods.getCommonProgressDialog(SubscribtionDailog.this);
            dialog.show();
            viewModel.callPayment(preferences.getLoginUserId(),preferences.getLoginApiKey(),pacakge_id,payment_id,order_id,signature,status,dialog, new mCallBackResponse() {
                @Override
                public void success(String from, String message) {
                   /* Intent i=new Intent(SubscribtionDailog.this,NewsDetailsActivity.class);
                    i.putExtra("newslist", (Serializable) newslist);
                    i.putExtra("position_id", getIntent().getStringExtra("position"));
//                    i.putExtra("newslist", (Serializable) list);
                    startActivity(i);
                    finish();*/
                    Intent intent = new Intent(SubscribtionDailog.this, DashboardActivity.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(0, 0);
                }

                @Override
                public void fail(String from) {
                    UsefullMethods.showMessage(SubscribtionDailog.this, SweetAlertDialog.ERROR_TYPE, "Error", from, "OK", () -> {
                        finish();
                    });
                }
            });
        }
        else {
            UtilMethods.INSTANCE.internetNotAvailableMessage(SubscribtionDailog.this);
        }
    }

    @Override
    public void onPaymentError(int i, String s) {
        Log.e("DashBoard===", "Fail="+s);

    }

    public void startProcess(View view) {
        Log.e("Subscription Ok===1","");
        for(int i=0;i<list.size();i++)
        {
            if(list.get(i).isSelected())
            {
                Log.e("Subscription Ok===",""+list.get(i).isSelected());
            }
        }
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
}
