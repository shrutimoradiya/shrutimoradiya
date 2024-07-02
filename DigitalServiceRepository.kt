package com.sampsolution.contactlessdining.service

import android.content.Context
import android.util.ArrayMap
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.sampsolution.contactlessdining.R
import com.sampsolution.contactlessdining.app.ServiceApp
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
import com.sampsolution.contactlessdining.utils.Constant
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

class DigitalServiceRepository {
    private var hotVideoService: DigitalServiceAPI? = null

    private val context = ServiceApp.context

    companion object {
        private var VPNRepository: DigitalServiceRepository? = null

        @Synchronized
        fun getInstance(): DigitalServiceRepository? {
            if (VPNRepository == null) {
                VPNRepository = DigitalServiceRepository()
            }
            return VPNRepository
        }
    }

    init {
        val okHttp = OkHttpClient.Builder()
            .connectTimeout(2, TimeUnit.MINUTES)
//            .writeTimeout(10, TimeUnit.SECONDS)
//            .readTimeout(30, TimeUnit.SECONDS)
            .cache(null)

        okHttp.addInterceptor(Interceptor {
            val newRequest = it.request().newBuilder()
                .build()
            return@Interceptor it.proceed(newRequest)
        })

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        okHttp.addInterceptor(logging)

        val okHttpClient = okHttp.build()
        val gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .baseUrl(DigitalServiceAPI.API_BASE_URL)
            .build()

        hotVideoService = retrofit.create(DigitalServiceAPI::class.java)

    }

    fun loginApi(context: Context, map: ArrayMap<String?, Any?>): LiveData<LoginResponse?> {
        val data: MutableLiveData<LoginResponse?> = MutableLiveData()

        hotVideoService?.getLogin(map)
            ?.enqueue(object : Callback<LoginResponse?> {
                override fun onResponse(
                    call: Call<LoginResponse?>,
                    response: Response<LoginResponse?>
                ) {
                    response.body()?.let {
                        data.value = it
                    } ?: run {
                        data.value = null
                        Toast.makeText(
                            context,
                            context.resources?.getString(R.string.somthing_wrong),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<LoginResponse?>, t: Throwable) {
                    t.printStackTrace()
                    data.value = null
                }
            })
        return data
    }

    fun signUpApi(context: Context, map: ArrayMap<String?, Any?>): LiveData<SignUpResponse?> {
        val data: MutableLiveData<SignUpResponse?> = MutableLiveData()
        hotVideoService?.getSignUP(map)?.enqueue(object : Callback<SignUpResponse?> {
            override fun onResponse(
                call: Call<SignUpResponse?>,
                response: Response<SignUpResponse?>
            ) {
                response.body()?.let {
                    data.value = it
                } ?: run {
                    data.value = null
                    Toast.makeText(
                        context,
                        context.resources.getString(R.string.somthing_wrong),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<SignUpResponse?>, t: Throwable) {
                data.value = null
            }
        })
        return data
    }

    fun homeApi(latitude: Double, longitude: Double): LiveData<HomeResponse?> {
        val data: MutableLiveData<HomeResponse?> = MutableLiveData()

        hotVideoService?.getHomeData(latitude, longitude)
            ?.enqueue(object : Callback<HomeResponse?> {
                override fun onResponse(
                    call: Call<HomeResponse?>,
                    response: Response<HomeResponse?>
                ) {
                    response.body()?.let {
                        data.value = it
                    } ?: run {
                        data.value = null
                        Toast.makeText(
                            context,
                            context?.resources?.getString(R.string.somthing_wrong),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<HomeResponse?>, t: Throwable) {
                    t.printStackTrace()
                    data.value = null
                }
            })
        return data
    }

    fun menuCategoryApi(id: String): LiveData<MenuCategoryResponse?> {
        val data: MutableLiveData<MenuCategoryResponse?> = MutableLiveData()

        hotVideoService?.getMenuCategoryData(id)
            ?.enqueue(object : Callback<MenuCategoryResponse?> {
                override fun onResponse(
                    call: Call<MenuCategoryResponse?>,
                    response: Response<MenuCategoryResponse?>
                ) {
                    response.body()?.let {
                        data.value = it
                    } ?: run {
                        data.value = null
                        Toast.makeText(
                            context,
                            context?.resources?.getString(R.string.somthing_wrong),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<MenuCategoryResponse?>, t: Throwable) {
                    t.printStackTrace()
                    data.value = null
                }
            })
        return data
    }

    fun menuApi(
        restaurant_id: String,
        category_id: String,
        branchId: String
    ): LiveData<MenuResponse?> {
        val data: MutableLiveData<MenuResponse?> = MutableLiveData()

        hotVideoService?.getMenuData(restaurant_id, category_id, branchId)
            ?.enqueue(object : Callback<MenuResponse?> {
                override fun onResponse(
                    call: Call<MenuResponse?>,
                    response: Response<MenuResponse?>
                ) {
                    response.body()?.let {
                        data.value = it
                    } ?: run {
                        data.value = null
                        Toast.makeText(
                            context,
                            context?.resources?.getString(R.string.somthing_wrong),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<MenuResponse?>, t: Throwable) {
                    t.printStackTrace()
                    data.value = null
                }
            })
        return data
    }

    fun addCartApi(context: Context, map: ArrayMap<String?, Any?>): LiveData<AddCartResponse?> {
        val data: MutableLiveData<AddCartResponse?> = MutableLiveData()
        hotVideoService?.getAddCart(map)?.enqueue(object : Callback<AddCartResponse?> {
            override fun onResponse(
                call: Call<AddCartResponse?>,
                response: Response<AddCartResponse?>
            ) {
                response.body()?.let {
                    data.value = it
                } ?: run {
                    data.value = null
                    Toast.makeText(
                        context,
                        context.resources.getString(R.string.somthing_wrong),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<AddCartResponse?>, t: Throwable) {
                data.value = null
            }
        })
        return data
    }

    fun myCartApi(userId: String): LiveData<MyCartResponse?> {
        val data: MutableLiveData<MyCartResponse?> = MutableLiveData()

        hotVideoService?.getMyCart(userId)
            ?.enqueue(object : Callback<MyCartResponse?> {
                override fun onResponse(
                    call: Call<MyCartResponse?>,
                    response: Response<MyCartResponse?>
                ) {
                    response.body()?.let {
                        data.value = it
                    } ?: run {
                        data.value = null
                        Toast.makeText(
                            context,
                            context?.resources?.getString(R.string.somthing_wrong),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<MyCartResponse?>, t: Throwable) {
                    t.printStackTrace()
                    data.value = null
                }
            })
        return data
    }

    fun updateCartApi(context: Context, map: ArrayMap<String?, Any?>): LiveData<BaseResponse?> {
        val data: MutableLiveData<BaseResponse?> = MutableLiveData()
        hotVideoService?.getUpdateCart(map)?.enqueue(object : Callback<BaseResponse?> {
            override fun onResponse(
                call: Call<BaseResponse?>,
                response: Response<BaseResponse?>
            ) {
                response.body()?.let {
                    data.value = it
                } ?: run {
                    data.value = null
                    Toast.makeText(
                        context,
                        context.resources.getString(R.string.somthing_wrong),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<BaseResponse?>, t: Throwable) {
                data.value = null
            }
        })
        return data
    }


    fun checkOutApi(map: ArrayMap<String?, Any?>): LiveData<CheckOutResponse?> {
        val data: MutableLiveData<CheckOutResponse?> = MutableLiveData()
        hotVideoService?.getCheckOut(map)
            ?.enqueue(object : Callback<CheckOutResponse?> {
                override fun onResponse(
                    call: Call<CheckOutResponse?>,
                    response: Response<CheckOutResponse?>
                ) {
                    response.body()?.let {
                        data.value = it
                    } ?: run {
                        data.value = null
                        Toast.makeText(
                            context,
                            context?.resources?.getString(R.string.somthing_wrong),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<CheckOutResponse?>, t: Throwable) {
                    data.value = null
                }
            })
        return data
    }

    fun orderDetailApi(order_number: String): LiveData<OrderDetailsResponse?> {
        val data: MutableLiveData<OrderDetailsResponse?> = MutableLiveData()
        hotVideoService?.getOrderDetails(order_number)
            ?.enqueue(object : Callback<OrderDetailsResponse?> {
                override fun onResponse(
                    call: Call<OrderDetailsResponse?>,
                    response: Response<OrderDetailsResponse?>
                ) {
                    response.body()?.let {
                        data.value = it
                    } ?: run {
                        data.value = null
                        Toast.makeText(
                            context,
                            context?.resources?.getString(R.string.somthing_wrong),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<OrderDetailsResponse?>, t: Throwable) {
                    data.value = null
                }
            })
        return data
    }

    fun checkStatusApi(userId: String, orderStatus: String): LiveData<OrderStatusResponse?> {
        val data: MutableLiveData<OrderStatusResponse?> = MutableLiveData()
        hotVideoService?.getOrderStatus(userId, orderStatus)
            ?.enqueue(object : Callback<OrderStatusResponse?> {
                override fun onResponse(
                    call: Call<OrderStatusResponse?>,
                    response: Response<OrderStatusResponse?>
                ) {
                    response.body()?.let {
                        data.value = it
                    } ?: run {
                        data.value = null
                        Toast.makeText(
                            context,
                            context?.resources?.getString(R.string.somthing_wrong),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<OrderStatusResponse?>, t: Throwable) {
                       data.value = null
                }
            })
        return data
    }

    fun profileImageUploadApi(context: Context, userId: String,photo: File): LiveData<ProfileResponse?> {
        val data: MutableLiveData<ProfileResponse?> = MutableLiveData()
        val requestFile: RequestBody = RequestBody.create(
            "image/*".toMediaTypeOrNull(),
            photo
        )
        val fileName: String = photo.name

        val imageRequest = MultipartBody.Part.createFormData("image", fileName, requestFile)
        hotVideoService?.getProfileImage(Constant.getBodyData(userId),imageRequest)?.enqueue(object : Callback<ProfileResponse?> {
            override fun onResponse(
                call: Call<ProfileResponse?>,
                response: Response<ProfileResponse?>
            ) {
                response.body()?.let {
                    data.value = it
                } ?: run {
                    data.value = null
                    Toast.makeText(
                        context,
                        context.resources.getString(R.string.somthing_wrong),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<ProfileResponse?>, t: Throwable) {
                data.value = null
            }
        })
        return data
    }

}