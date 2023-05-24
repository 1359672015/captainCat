package com.example.captaincat.api

import android.app.Activity
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.captaincat.MyApplication
import com.example.captaincat.MyGame
import com.example.captaincat.Ui.Activity.MainActivity
import com.example.captaincat.Ui.Dialog.MenuDialog
import com.example.captaincat.Utils.message.ToastUtil.Companion.ToastError
import com.example.captaincat.Utils.message.ToastUtil.Companion.ToastMsg
import com.example.captaincat.api.UserLevelsModel.Companion.bag
import com.example.captaincat.api.UserLevelsModel.Companion.userId
import com.example.captaincat.api.core.RequestType
import com.example.captaincat.information.Config.Config
import com.example.captaincat.information.Levels.BaseLevel
import com.example.captaincat.information.Tables.ObjectTable.Companion.allThing
import com.example.captaincat.information.Tables.ObjectTable.Companion.turnThingsIntoDiaItem
import com.example.captaincat.model.Thing
import com.example.lamstest.Common.toastCover
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

// TODO: 优化:传入成功后调用的方法,一律弹窗输出失败信息
class UserBagModel {
    companion object {
        var money = MutableLiveData<Int>()
        fun initThings() {
            MyApplication.apiService.allThings.enqueue(object : Callback<List<Thing>> {
                override fun onResponse(
                    call: Call<List<Thing>>,
                    response: Response<List<Thing>>
                ) {
                    val list = response.body()!!
                    allThing = list
                }
                override fun onFailure(call1: Call<List<Thing>>, t: Throwable) {
                    "${t.message}".toastCover()

                }
            })
        }



      fun getBag() {
            Thread {
                try {
                    val list = MyApplication.apiService.getBag(MyGame.user.id.toString() + "")
                        .execute().body()
                    if (list != null) {
                        bag = list
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }.start()
        }

        fun getBag(finish:()->Unit){
            MyApplication.apiService.getBag(MyGame.user.id.toString() + "")
                .enqueue(object : retrofit2.Callback<List<Thing>>{
                    override fun onResponse(
                        call: Call<List<Thing>>,
                        response: Response<List<Thing>>
                    ) {
                        val list = response.body()?: mutableListOf()
                        bag = list as MutableList<Thing>
                         finish.invoke()

                    }
                    override fun onFailure(call: Call<List<Thing>>, t: Throwable) {
                        "连接错误:${t.message}"
                    }

                })
        }
        fun getBagThingNum(id: Int): Int {
            for (thing in bag!!) {
                if (thing.id == id) return thing.num
            }
            return 0
        }

        fun userSpendThings(things: List<Thing>) {
            for (thing in things) {
                MyApplication.apiService
                    .addThingNum(MyGame.user.id.toString() + "", thing.id.toString(), -thing.num)
                    .enqueue(object : Callback<RequestType<*>?> {
                        override fun onResponse(
                            call: Call<RequestType<*>?>,
                            response: Response<RequestType<*>?>
                        ) {
                            for (i in bag!!.indices) {
                                val t = bag!![i]
                                if (t.id == thing.id) {
                                    t.num = t.num - thing.num
                                    bag!![i] = t
                                    break
                                }
                            }
                        }

                        override fun onFailure(call: Call<RequestType<*>?>, t: Throwable) {}
                    })
            }
        }

        fun userGetRewars(context: Context?, level: BaseLevel, activity: Activity) {
            val list = level.rewards
            val dialogItemList = turnThingsIntoDiaItem(list)
            for (thing in list) {
                if (thing.id == 0) {
                    userGetCoins(thing.num)
                } else {
                    MyApplication.apiService
                        .addThingNum(MyGame.user.id.toString() + "", thing.id.toString(), thing.num)
                        .enqueue(object : Callback<RequestType<*>?> {
                            override fun onResponse(
                                call: Call<RequestType<*>?>, response: Response<RequestType<*>?>
                            )
                            {}
                            override fun onFailure(call: Call<RequestType<*>?>, t: Throwable)
                            {}
                        })
                }
            }
            MenuDialog.showDialog(context, dialogItemList, "恭喜您获得下列奖励:", Config.REWARD) {
                MainActivity.backToMain(context!!)
                activity.finish()
            }
        }

        fun userBuyThing(thing: Thing, context: Context?) {
            MyApplication.apiService.addThingNum(userId, thing.id.toString(), thing.num)
                .enqueue(object : Callback<RequestType<*>> {
                    override fun onResponse(
                        call: Call<RequestType<*>>,
                        response: Response<RequestType<*>>
                    ) {
                        if (response.body()!!.status == 200) {
                            ToastMsg(context, "购买成功！")
                            userGetCoins(-thing.price * thing.num)

                        } else ToastMsg(context, "购买失败!")
                    }

                    override fun onFailure(call: Call<RequestType<*>>, t: Throwable) {
                        ToastError(context)
                    }
                })
        }

        fun userGetCoins(money: Int) {
            //传入接口之后，coins -= money
            MyApplication.apiService.spendMoney(MyGame.user.id.toString() + "", -money)
                .enqueue(object : Callback<RequestType<*>?> {
                    override fun onResponse(
                        call: Call<RequestType<*>?>,
                        response: Response<RequestType<*>?>
                    ) {
                        UserBagModel.money.postValue(MyGame.user.coins + money)
                    }
                    override fun onFailure(call: Call<RequestType<*>?>, t: Throwable) {}
                })
        }
    }



    fun getBagThingNum(id: Int): Int {
        for (thing in UserLevelsModel.bag!!) {
            if (thing.id == id) return thing.num
        }
        return 0
    }

    fun userSpendThings(things: List<Thing>) {
        for (thing in things) {
            MyApplication.apiService
                .addThingNum(MyGame.user.id.toString() + "", thing.id.toString(), -thing.num)
                .enqueue(object : Callback<RequestType<*>?> {
                    override fun onResponse(
                        call: Call<RequestType<*>?>,
                        response: Response<RequestType<*>?>
                    ) {
                        for (i in UserLevelsModel.bag!!.indices) {
                            val t = UserLevelsModel.bag!![i]
                            if (t.id == thing.id) {
                                t.num = t.num - thing.num
                                UserLevelsModel.bag!![i] = t
                                break
                            }
                        }
                    }

                    override fun onFailure(call: Call<RequestType<*>?>, t: Throwable) {}
                })
        }
    }

}