package com.sampsolution.contactlessdining.service

import android.util.ArrayMap
import com.sampsolution.contactlessdining.model.BaseResponse
import com.sampsolution.contactlessdining.model.addCartModel.AddCartResponse
import com.sampsolution.contactlessdining.model.checkOutModel.CheckOutResponse
import com.sampsolution.contactlessdining.model.signUpModel.SignUpResponse
import com.sampsolution.contactlessdining.model.homeModel.HomeResponse
import com.sampsolution.contactlessdining.model.loginModel.LoginResponse
import com.sampsolution.contactlessdining.model.menuCategoryModel.MenuCategoryResponse
import com.sampsolution.contactlessdining.model.menuModel.MenuResponse
import com.sampsolution.contactlessdining.model.myCartModel.MyCartResponse
import com.sampsolution.contactlessdining.model.orderDetailModel.OrderDetailsResponse
import com.sampsolution.contactlessdining.model.orderStatusModel.OrderStatusResponse
import com.sampsolution.contactlessdining.model.profileModel.ProfileResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface DigitalServiceAPI {

    //Change your baseURL here
    companion object {
        private const val BASE_URL = "https://sampsolution.com/dinning/"
        const val API_BASE_URL = "${BASE_URL}api/"
    }

    @POST("login")
    @FormUrlEncoded
    fun getLogin(@FieldMap params: ArrayMap<String?, Any?>): Call<LoginResponse?>?

    @POST("signup")
    @FormUrlEncoded
    fun getSignUP(@FieldMap params: ArrayMap<String?, Any?>): Call<SignUpResponse?>?

    @GET("restaurants")
    fun getHomeData(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double
    ): Call<HomeResponse?>?

    @GET("category")
    fun getMenuCategoryData(@Query("qr_code") qrCode: String): Call<MenuCategoryResponse?>?

    @GET("menu")
    fun getMenuData(
        @Query("restaurant_id") restaurantId: String,
        @Query("category_id") categoryId: String,
        @Query("branch_id") branchId: String
    ): Call<MenuResponse?>?

    @POST("cart")
    @FormUrlEncoded
    fun getAddCart(@FieldMap params: ArrayMap<String?, Any?>): Call<AddCartResponse?>?


    @GET("mycart")
    fun getMyCart(
        @Query("user_id") userId: String,
    ): Call<MyCartResponse?>?

    @POST("update-cart")
    @FormUrlEncoded
    fun getUpdateCart(@FieldMap params: ArrayMap<String?, Any?>): Call<BaseResponse?>?

    @POST("checkout")
    @FormUrlEncoded
    fun getCheckOut(@FieldMap params: ArrayMap<String?, Any?>): Call<CheckOutResponse?>?


    @GET("order-details")
    fun getOrderDetails(@Query("order_number") orderNumber: String): Call<OrderDetailsResponse?>?

    @GET("order-status")
    fun getOrderStatus(@Query("user_id") userId: String,
                       @Query("order_status") orderStatus: String): Call<OrderStatusResponse?>?

    @POST("upload-image")
    @Multipart
    fun getProfileImage(@Part("user_id") userId: RequestBody,
                        @Part profile: MultipartBody.Part? = null): Call<ProfileResponse?>?

}