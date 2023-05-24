package com.example.captaincat.api

import com.example.captaincat.MyApplication.Companion.apiService
import com.example.captaincat.MyGame.Companion.gemWearEvent
import com.example.captaincat.MyGame.Companion.pet
import com.example.captaincat.MyGame.Companion.petData
import com.example.captaincat.MyGame.Companion.user
import com.example.captaincat.api.core.RequestType
import com.example.captaincat.event.GemWearEvent
import com.example.captaincat.information.Tables.GemsTables.Companion.allGems
import com.example.captaincat.model.Gem
import com.example.captaincat.model.Pet
import com.example.lamstest.Common.divideWith
import com.example.lamstest.Common.toast
import com.example.lamstest.Common.toastCover
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.absoluteValue

class ReadyOrPetModel {
    companion object {
        fun getGems(success:()->Unit){
            apiService.allGems().enqueue(object :Callback<List<Gem>>{
                override fun onResponse(call: Call<List<Gem>>, response: Response<List<Gem>>) {
                    if(response.body().isNullOrEmpty())
                        "初始化失败!".toast()
                    else
                    {
                        allGems = response.body()!!
                        getWearStatus(user.id){
                            success.invoke()
                        }
                    }
                }
                override fun onFailure(call: Call<List<Gem>>, t: Throwable) {
                        "初始化失败! ${t.message}".toast()
                }

            })
        }

        fun feedPet(pId:Int,energy:Int,mood:Int){
            if(pId==-1)
                return
            val addEnergy   =   if(pet.energy+energy>100)   100-pet.energy else energy
            val addMood     =   if(pet.mood+energy>100)     100-pet.mood else mood
             apiService.feedPet(pId,addMood,addEnergy).enqueue(object : Callback<RequestType<*>?> {
                override fun onResponse(
                    call: Call<RequestType<*>?>,
                    response: Response<RequestType<*>?>
                ) {
                    pet.energy += addEnergy
                    pet.mood += addMood
                    petData.postValue(pet)
                    ("宠物体力${if(addEnergy>=0) "增加了" else "消耗了"}${addEnergy.absoluteValue}，" +
                            "心情${if(addMood>=0) "增加了" else "消耗了"}了${addMood.absoluteValue}").toastCover()
                }
                override fun onFailure(call: Call<RequestType<*>?>, t: Throwable) {
                    "${t.message}".toastCover()
                }
            })
        }

        fun foundPet(uid:Int,name:String,look:Int){
            apiService.foundPet(uid,name,look).enqueue(object : Callback<RequestType<Pet>?> {
                override fun onResponse(
                    call: Call<RequestType<Pet>?>,
                    response: Response<RequestType<Pet>?>
                ) {
                    if(response.code()==200)
                        "领养成功!".toast()
                         petData.postValue(response.body()?.data)
                        pet = response.body()?.data!!
                }
                override fun onFailure(call: Call<RequestType<Pet>?>, t: Throwable) {
                    "${t.message}".toastCover()
                }
            })
        }

        fun getPet(uid:Int,success:()->Unit){
            apiService.getPet(uid).enqueue(object : Callback<RequestType<Pet?>?> {
                override fun onResponse(
                    call: Call<RequestType<Pet?>?>,
                    response: Response<RequestType<Pet?>?>
                ) {
                    if(response.body()?.data==null)
                        return
                    petData.postValue(response.body()?.data)
                    pet = response.body()?.data!!
                    success.invoke()
                }
                override fun onFailure(call: Call<RequestType<Pet?>?>, t: Throwable) {
                    "${t.message}".toastCover()
                }

            })
        }
        fun getWearStatus(uid:Int,success:()->Unit){
            apiService.getWearStatus(uid).enqueue(object :Callback<RequestType<String>>{
                override fun onResponse(
                    call: Call<RequestType<String>>,
                    response: Response<RequestType<String>>
                ) {
                    val gem2 = response.body()!!.data!!.divideWith()
                    gemWearEvent = GemWearEvent(gem2[0].toInt(),gem2[1].toInt())
                    success.invoke()
                }
                override fun onFailure(call: Call<RequestType<String>>, t: Throwable) {
                    "网络连接错误!".toast()
                }
            })
        }
        fun wear(gId:Int,place:Int,success:()->Unit){
            apiService.wear(user.id,gId,place).enqueue(object :Callback<RequestType<String>>{
                override fun onResponse(
                    call: Call<RequestType<String>>,
                    response: Response<RequestType<String>>
                ) {
                    val gem2 = response.body()!!.data!!.divideWith()
                    gemWearEvent = GemWearEvent(gem2[0].toInt(),gem2[1].toInt())
                    "装备成功!".toastCover()
                    success.invoke()
                }

                override fun onFailure(call: Call<RequestType<String>>, t: Throwable) {
                    "网络连接错误!".toast()
                }

            })
        }
        fun tookOff( place:Int,success:()->Unit){
            apiService.tookOff(user.id,place).enqueue(object :Callback<RequestType<String>>{
                override fun onResponse(
                    call: Call<RequestType<String>>,
                    response: Response<RequestType<String>>
                ) {
                    if(response.code()==200){
                    val gem2 = response.body()!!.data!!.divideWith()
                        //更新装备状态
                    gemWearEvent = GemWearEvent(gem2[0].toInt(),gem2[1].toInt())

                    success.invoke()
                    }
                }

                override fun onFailure(call: Call<RequestType<String>>, t: Throwable) {
                    "网络连接错误!".toast()
                }

            })
        }
    }
}