package com.sunsandsports.network

import com.google.gson.JsonIOException
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.sunsandsports.BuildConfig
import com.sunsandsports.network.Client.ApiInterface
import com.sunsandsports.network.Client.HttpHandleIntercept
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {

    companion object {

        private var okHttpClient: OkHttpClient? = null
        var retrofits: Retrofit? = null
        var myapiInterface: ApiInterface? = null

        /**
         * This is the generic method which will create retrofit object as singleton.
         */
        fun initRetrofit() {
            if (retrofits == null) {
                retrofits = getRetrofit()
                myapiInterface = retrofits?.create(ApiInterface::class.java)!!
            }
        }

        /**
         * Return API interface
         *
         */
        fun getApiInterface(): ApiInterface {
            if (myapiInterface != null) {
                return myapiInterface!!
            }
            myapiInterface = retrofits?.create(ApiInterface::class.java)!!
            return myapiInterface as ApiInterface
        }

        /**
         * Generate Retrofit Client
         */
        private fun getRetrofit(): Retrofit {
            /* val gson1 = GsonBuilder()
                 .registerTypeAdapter(ResponseWrapper::class.java, MyResponseDeserializer())
                 .create()*/

            val builder = Retrofit.Builder()
            builder.baseUrl("https://randomuser.me/api/")
            builder.addConverterFactory(GsonConverterFactory.create())
            builder.addCallAdapterFactory(CoroutineCallAdapterFactory())
            builder.client(getOkHttpClient())
            return builder.build()
        }

        /**
         * generate OKhttp client
         */
        private fun getOkHttpClient(): OkHttpClient {
            if (okHttpClient == null) {
                val logging = HttpLoggingInterceptor()
                logging.level = HttpLoggingInterceptor.Level.BODY
                val builder = OkHttpClient.Builder()
                if (BuildConfig.DEBUG) {
                    builder.addInterceptor(logging)
                }
                builder.readTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .addInterceptor(HttpHandleIntercept())
                    .build()
                okHttpClient = builder.build()
            }
            return okHttpClient!!
        }

        /**
         * generate custom response for exception
         */
        fun generateCustomResponse(code: Int, message: String, request: Request): Response? {
            try {
                val body = ResponseBody.create(
                    "application/json".toMediaTypeOrNull(),
                    getJSONObjectForException(message, code).toString()
                )
                return Response.Builder()
                    .code(code)
                    .request(request)
                    .protocol(Protocol.HTTP_1_1)
                    .body(body)
                    .message(message)
                    .build()
            } catch (ex: JsonIOException) {
//                DebugLog.print(ex)
                return null
            }
        }

        /**
         * generate JSON object for error case
         */
        private fun getJSONObjectForException(message: String, code: Int): JSONObject {

            try {
                val jsonMainObject = JSONObject()

                val `object` = JSONObject()
                `object`.put("status", false)
                `object`.put("message", message)
                `object`.put("message_code", code)
                `object`.put("status_code", code)

                jsonMainObject.put("meta", `object`)

                val obj = JSONObject()
                obj.put("error", JSONArray().put(message))

                jsonMainObject.put("errors", obj)

                return jsonMainObject
            } catch (e: JSONException) {
//                DebugLog.print(e)
                return JSONObject()
            }
        }
    }
}
