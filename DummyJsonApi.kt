package org.mycode.mykotlinapplication

import retrofit2.Call
import retrofit2.http.GET

interface DummyJsonApi {
    @GET("products")
    fun getProducts(): Call<ProductsResponse>
}