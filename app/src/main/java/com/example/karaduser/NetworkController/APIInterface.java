package com.example.karaduser.NetworkController;


import androidx.annotation.Nullable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface APIInterface
{
    //registerpage
    @Multipart
    @POST("API_User/add_user.php")
    Call<ResponseBody> regiternew(@Part("user_name") @Nullable RequestBody user_name,
                                  @Part("user_mobile") @Nullable RequestBody user_mobile,
                                  @Part("user_email") @Nullable RequestBody user_email,
                                  @Part("state") @Nullable RequestBody state,
                                  @Part("city") @Nullable RequestBody city,
                                  @Part("district") @Nullable RequestBody district,
                                  @Part("taluka") @Nullable RequestBody taluka,
                                  @Part @Nullable MultipartBody.Part file);

    //Login page
    @FormUrlEncoded
    @POST("API_User/login.php")
    Call<ResponseBody> getlogin(@Field("user_mobile") String user_mobile,
                                @Field("user_token") String user_token);

    // otp page
    @FormUrlEncoded
    @POST("API_User/checkotp.php")
    Call<ResponseBody> getotp(@Field("user_mobile") String user_mobile,
                              @Field("user_otp") String user_otp);

    //Home page
    @FormUrlEncoded
    @POST("API_User/home_slider.php")
    Call<ResponseBody> getslider(@Field("slider_advertise_id") String slider_advertise_id);


    @GET("API_User/feature_list.php")
    Call<ResponseBody>getfeatureads();

    //Show comments
    @FormUrlEncoded
    @POST("API_User/rating_view.php")
    Call<ResponseBody> getcomment(@Field("business_info_id") String business_info_id,
                                  @Field("fld_business_id") String fld_business_id);

    @FormUrlEncoded
    @POST("API_User/dashboard_count.php")
    Call<ResponseBody> getcounts(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("API_User/notification_count.php")
    Call<ResponseBody> getnoti_counts(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("API_User/notification_update_count.php")
    Call<ResponseBody> getnoti_countszero(@Field("user_id") String user_id,
                                            @Field("readnotification") String readnotification);

    //appointment page
    @FormUrlEncoded
    @POST("API_User/appointment_service_slider.php")
    Call<ResponseBody> getappintmentsilder(@Field("business_info_id") String business_info_id);


    @FormUrlEncoded
    @POST("API_User/appointment_list.php")
    Call<ResponseBody> getappintmentList(@Field("fld_country_id") String fld_country_id,
                                         @Field("fld_state_id") String fld_state_id,
                                         @Field("fld_district_id") String fld_district_id,
                                         @Field("fld_taluka_id") String fld_taluka_id,
                                         @Field("fld_city_id") String fld_city_id,
                                         @Field("fld_area_id") String fld_area_id,
                                         @Field("fld_business_category_id") String fld_business_category_id);

    @FormUrlEncoded
    @POST("API_User/service_list.php")
    Call<ResponseBody> getServiceList(@Field("fld_country_id") String fld_country_id,
                                      @Field("fld_state_id") String fld_state_id,
                                      @Field("fld_district_id") String fld_district_id,
                                      @Field("fld_taluka_id") String fld_taluka_id,
                                      @Field("fld_city_id") String fld_city_id,
                                      @Field("fld_area_id") String fld_area_id,
                                      @Field("fld_business_category_id") String fld_business_category_id);

    @FormUrlEncoded
    @POST("API_User/appointment_service_variant_list.php")
    Call<ResponseBody> getappintment_varient_List(@Field("business_info_id") String business_info_id);

    //History page
    @FormUrlEncoded
    @POST("API_User/appointment_service_history.php")
    Call<ResponseBody> getappintment_history_List(@Field("user_id") String user_id);

    //notification
    @FormUrlEncoded
    @POST("API_User/notification_list.php")
    Call<ResponseBody> getnofication(@Field("user_id") String user_id);


    //Terms

    @GET("API_User/term_condition.php")
    Call<ResponseBody> getterms();

    //PrivacyPolicy

    @GET("API_User/privacy_policy.php")
    Call<ResponseBody> getprivacy_policy();


    //updateProfile

    @Multipart
    @POST("API_User/update_user.php")
    Call<ResponseBody> updateprofile(@Part("user_id") RequestBody user_id,
                                     @Part("user_name") RequestBody user_name,
                                     @Part("user_mobile") RequestBody user_mobile,
                                     @Part("user_email") RequestBody user_email,
                                     @Part("state") RequestBody state,
                                     @Part("city") RequestBody city,
                                     @Part("district") RequestBody district,
                                     @Part("taluka") RequestBody taluka,
                                     @Part MultipartBody.Part fld_user_photo);


    //TIme Activity
    @GET("API_User/date_list.php")
    Call<ResponseBody> getDays();

    @FormUrlEncoded
    @POST("API_User/time_slot.php")
    Call<ResponseBody> getMorningList(@Field("business_info_id") String business_info_id,
                                      @Field("date_variable") String date_variable);


    //book Appointment
    @FormUrlEncoded
    @POST("API_User/appointment_book.php")
    Call<ResponseBody> book_Appointment(@Field("user_id") String user_id,
                                        @Field("fld_business_id") String fld_business_id,
                                        @Field("business_info_id") String business_info_id,
                                        @Field("vendor_id") String vendor_id,
                                        @Field("booking_date") String booking_date,
                                        @Field("fld_actual_booking_slot") String fld_actual_booking_slot,
                                        @Field("fld_actual_booking_slot_no") String fld_actual_booking_slot_no);


    @FormUrlEncoded
    @POST("API_User/service_enquiry.php")
    Call<ResponseBody> Send_Enquriy(@Field("user_id") String user_id,
                                    @Field("fld_business_id") String fld_business_id,
                                    @Field("business_info_id") String business_info_id,
                                    @Field("vendor_id") String vendor_id,
                                    @Field("fld_business_details_id") String fld_business_details_id,
                                    @Field("allocatedService") String allocatedService);


    //about page

    @GET("API_User/about.php")
    Call<ResponseBody> getaboutdata();

    @GET("API_User/business_appointment_category_list.php")
    Call<ResponseBody> getAppiontment_category();

    @GET("API_User/business_service_category_list.php")
    Call<ResponseBody> getservice_category();

    @FormUrlEncoded
    @POST("API_User/stateList.php")
    Call<ResponseBody> getstate(@Field("fld_country_id") String fld_country_id);

    @FormUrlEncoded
    @POST("API_User/view_district.php")
    Call<ResponseBody> getDistrict(@Field("fld_country_id") String fld_country_id,
                                   @Field("fld_state_id") String fld_state_id);

    @FormUrlEncoded
    @POST("API_User/view_taluka.php")
    Call<ResponseBody> gettaluka(@Field("fld_district_id") String fld_district_id);

    @FormUrlEncoded
    @POST("API_User/view_city.php")
    Call<ResponseBody> getvillage(@Field("fld_country_id") String fld_country_id,
                                  @Field("fld_state_id") String fld_state_id,
                                  @Field("fld_district_id") String fld_district_id,
                                  @Field("fld_taluka_id") String fld_taluka_id);

    @FormUrlEncoded
    @POST("API_User/view_area.php")
    Call<ResponseBody> getArea(@Field("fld_city_id") String fld_city_id);

    @FormUrlEncoded
    @POST("API_User/view_landmark.php")
    Call<ResponseBody> getLandmark(@Field("fld_city_id") String fld_city_id,
                                   @Field("fld_area_id") String fld_area_id);


    @FormUrlEncoded
    @POST("API_User/view_user.php")
    Call<ResponseBody>getuser(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("API_User/update_cancel_status.php")
    Call<ResponseBody>cancelstatus(@Field("user_id") String user_id,
                                    @Field("vendor_id") String vendor_id,
                                    @Field("business_info_id") String business_info_id,
                                    @Field("fld_service_issuedorreturned") String fld_service_issuedorreturned,
                                    @Field("fld_user_issued_servicesApp") String fld_user_issued_servicesApp);


    @FormUrlEncoded
    @POST("API_User/rating_add.php")
    Call<ResponseBody>ratingadd(@Field("user_id") String user_id,
                                @Field("fld_business_id") String fld_business_id,
                                @Field("business_info_id") String business_info_id,
                                @Field("vendor_id") String vendor_id,
                                @Field("review_star") String review_star,
                                @Field("review_text") String review_text,
                                @Field("review_date") String review_date);

}


