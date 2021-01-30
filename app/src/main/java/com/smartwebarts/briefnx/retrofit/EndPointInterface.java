package com.smartwebarts.briefnx.retrofit;


import com.smartwebarts.briefnx.models.ArticleCategoryModel;
import com.smartwebarts.briefnx.models.DashboardDetailsModel;
import com.smartwebarts.briefnx.models.LoginData;
import com.smartwebarts.briefnx.models.LoginData1;
import com.smartwebarts.briefnx.models.MessageModel;
import com.smartwebarts.briefnx.models.NewsByCategoryModel;
import com.smartwebarts.briefnx.models.NewsForFegmentFront;
import com.smartwebarts.briefnx.models.NewsModel;
import com.smartwebarts.briefnx.models.NewsSubCategoryModel;
import com.smartwebarts.briefnx.models.OTPModel;
import com.smartwebarts.briefnx.models.ProfileModel;
import com.smartwebarts.briefnx.models.SignUpModel;
import com.smartwebarts.briefnx.models.StateModel;
import com.smartwebarts.briefnx.models.VersionModel;
import com.smartwebarts.briefnx.models.YoutubeMainModel;
import com.smartwebarts.briefnx.newsdetail.model.Articles_Model;
import com.smartwebarts.briefnx.newsdetail.model.PaymentResponse;
import com.smartwebarts.briefnx.newsdetail.model.SubscriptionModel;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface EndPointInterface {

    @POST("controller/api1/logiin_logout/LoginByApp.php")
    @FormUrlEncoded
    Call<LoginData1> login(@Field("tbname") String tbname,
                           @Field("userid") String userid,
                           @Field("password") String password);

    @POST("controller/api/common/selectOne.php")
    @FormUrlEncoded
    Call<List<ProfileModel>> userdetail(@Field("tbname") String tbname,
                                        @Field("loginid") String loginid,
                                        @Field("api_key") String api_key,
                                        @Field("id") String id);

    @POST("controller/api1/common/selectAll.php")
    @FormUrlEncoded
    Call<List<NewsModel>>newsCurrent(@Field("tbname") String tbname);
//for supercategory
    @POST("controller/api1/common/selectAll.php")
    @FormUrlEncoded
    Call<List<NewsSubCategoryModel>>subCategory(@Field("tbname") String tbname);

    //for subcategory
@POST("controller/api1/common/selectOneByColumn.php")
    @FormUrlEncoded
    Call<List<NewsByCategoryModel>>topCurrentNews(@Field("tbname") String tbname,
                                                   @Field("column") String column,
                                                   @Field("id") String id);

//for news article
//    @POST("controller/api1/common/selectOneByColumn.php")
    @POST("controller/api1/common/getArticle.php")
    @FormUrlEncoded

    Call<Articles_Model>article(@Field("user_id") String tbname,
                                @Field("super_category_id") String super_category_id,
                                @Field("article_category_id") String article_category_id,
                                @Field("lang") String lang,
                                @Field("search") String search_text
                                           );


    @POST("controller/api1/common/insert2.php")
    @FormUrlEncoded

    Call<PaymentResponse>getPaymentApi(@Field("user_id") String user_id,
                                       @Field("tbname") String tbname,
                                       @Field("packeges_id") String packeges_id,
                                       @Field("payment_id") String payment_id,
                                       @Field("order_id") String order_id,
                                       @Field("signature") String signature,
                                       @Field("status") String status
                                           );


    @POST("controller/api1/common/selectAll.php")
    @FormUrlEncoded
    Call<List<SubscriptionModel>>getPackage_Api(@Field("tbname") String tbname);

//for youtube
    @GET("youtube/v3/search")
    Call <YoutubeMainModel>youtube(@Query("part") String part,
                                        @Query("channelId") String channelId,
                                        @Query("pageToken") String page_token,
                                        @Query("key") String key);


    @POST("controller/api1/common/insert.php")
    @FormUrlEncoded
    Call<SignUpModel> signup(@Field("tbname") String tbname,
                             @Field("role") String role,
                             @Field("name") String name,
                             @Field("father_name") String father_name,
                             @Field("contact") String mobile,
                             @Field("email") String email,
                             @Field("address") String address,
                             @Field("password") String password,
                             @Field("status") String status,
                             @Field("states_id") String states_id,
                             @Field("cities_id") String cities_id
    );

    @Multipart
    @POST("controller/api/common/update.php")
    Call<MessageModel> updateImage(@Part("id") RequestBody id,
                                   @Part("loginid") RequestBody loginid,
                                   @Part("api_key") RequestBody api_key,
                                   @Part("tbname") RequestBody tbname,
                                   @Part("name") RequestBody name,
                                   @Part MultipartBody.Part image);

    @POST("controller/api1/common/UpdateData.php")
    @FormUrlEncoded
    Call<MessageModel> updateAccessToken(@Field("id") String id,
                                       /* @Field("loginid") String loginid,
                                        @Field("api_key") String api_key,*/
                                        @Field("tbname") String tbname,
                                        @Field("access_token") String _token);
    @Multipart
    @POST("controller/api/common/update.php")
    Call<MessageModel> updateStatus(@Part("id") RequestBody id,
                                    @Part("loginid") RequestBody loginid,
                                    @Part("api_key") RequestBody api_key,
                                    @Part("tbname") RequestBody tbname,
                                    @Part("status") RequestBody status,
                                    @Part("venorid") RequestBody vendorid);



    @POST("controller/api/common/dashboardDetails.php")
    @FormUrlEncoded
    Call<DashboardDetailsModel> dashboardDetails(@Field("loginid") String loginid,
                                                 @Field("api_key") String api_key);


    @POST("controller/api/common/version.php")
    @FormUrlEncoded
    Call<List<VersionModel>> version(@Field("colval") String colval);

    @Multipart
    @POST("controller/api/common/updatestatus.php")
    Call<MessageModel> updateStatus(@Part("id") RequestBody id,
                                    @Part("loginid") RequestBody loginid,
                                    @Part("api_key") RequestBody api_key,
                                    @Part("tbname") RequestBody tbname,
                                    @Part("status") RequestBody status,
                                    @Part("reason") RequestBody reason,
                                    @Part("venorid") RequestBody vendorid);


    @POST("controller/api1/common/changePassword.php")
    @FormUrlEncoded
    Call<OTPModel> changePassword(@Field("contact") String number,
                                  @Field("password") String newpassword);


    @POST("controller/api1/common/sms.php")
    @FormUrlEncoded
    Call<OTPModel> otp(@Field("mobile") String mobile,
                       @Field("sms") String sms);

    @POST("controller/api1/common/selectAll.php")
    @FormUrlEncoded
    Call<List<StateModel>> getState(@Field("tbname")String states);


    @POST("controller/api1/common/selectOneByColumn.php")
    @FormUrlEncoded
    Call<List<StateModel>> getCity(
            @Field("id")String states, @Field("tbname")String cities, @Field("column")String state_id1);
}
