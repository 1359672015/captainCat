package com.example.captaincat.api

import com.example.captaincat.MyApplication.Companion.apiService
import com.example.captaincat.MyGame.Companion.user
import com.example.captaincat.api.core.RequestType
import com.example.captaincat.event.WarParams.Companion.refreshParams
import com.example.lamstest.Common.logD
import com.example.lamstest.Common.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TimerModel {
    companion object {

        fun uploadGame(hit:Int,shoot:Int,second:Int ){

            apiService.updateGame(user.id,hit,shoot,second).enqueue(object :Callback<RequestType<String>>{
                override fun onResponse(
                    call: Call<RequestType<String>>,
                    response: Response<RequestType<String>>
                ) {

                    if(response.code()==200){
                        "进度上传成功！".logD()
                    }
                }

                override fun onFailure(call: Call<RequestType<String>>, t: Throwable) {
                    "网络连接错误!".toast()
                }

            })
        }
        fun getGame(check:(Boolean)->Unit){
            apiService.getGame(user.id).enqueue(object:Callback<RequestType<Int>>{
                override fun onResponse(
                    call: Call<RequestType<Int>>,
                    response: Response<RequestType<Int>>
                ) {
                    if(response.body()==null||response.body()!!.data==1)
                    check.invoke(true)
                    else check.invoke(false)
                }
                override fun onFailure(call: Call<RequestType<Int>>, t: Throwable) {
                    check.invoke(true)
                }
            })
        }
    }
}