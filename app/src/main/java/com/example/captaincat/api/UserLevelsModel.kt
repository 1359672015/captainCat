package com.example.captaincat.api

import android.content.Context
import com.example.captaincat.MyApplication
import com.example.captaincat.MyGame
import com.example.captaincat.Ui.Adapter.SkillAdapter.Companion.adapter
import com.example.captaincat.Utils.message.ToastUtil.Companion.Logd
import com.example.captaincat.Utils.message.ToastUtil.Companion.ToastLong
import com.example.captaincat.api.UserBagModel.Companion.getBag
import com.example.captaincat.api.UserBagModel.Companion.userSpendThings
import com.example.captaincat.api.core.RequestType
import com.example.captaincat.information.Config.Config
import com.example.captaincat.information.Tables.MyTables
import com.example.captaincat.model.Thing
import com.example.captaincat.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

// TODO: 优化:传入成功后调用的方法,一律弹窗输出失败信息
class UserLevelsModel {
    companion object {
        var userId: String? = null
        @JvmStatic
        var bag: MutableList<Thing>? = null
        @JvmStatic
        fun refreshUser(success:()->Unit={}) {
            MyApplication.apiService.getUserById(userId)
                .enqueue(object : Callback<RequestType<User>> {
                    override fun onResponse(
                        call: Call<RequestType<User>>,
                        response: Response<RequestType<User>>
                    ) {
                        val status = response.body()!!.status
                        if (status == 200) {
                            val user = response.body()!!.data
                            MyGame.setTheUser(user)
                            userId = user.id.toString() + ""
                            Logd("getById:" + MyGame.user.toString())
                            success.invoke()
                        }
                    }

                    override fun onFailure(call: Call<RequestType<User>>, t: Throwable) {}
                })
            getBag()
        }

        @JvmStatic
        fun changeInfo(name: String?, intro: String?) {
            Thread {
                try {
                    MyApplication.apiService.update(userId, "name", name).execute()
                    MyApplication.apiService.update(userId, "introduce", intro).execute()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }.start()
        }


        /*玩家赢了某一关*/
        fun userWinLevel(level: Int, context: Context?) {
            if (MyGame.user.level == level) {
                MyApplication.apiService
                    .update(MyGame.user.id.toString() + "", "level", (level+1).toString())
                    .enqueue(object : Callback<RequestType<*>?> {
                        override fun onResponse(
                            call: Call<RequestType<*>?>,
                            response: Response<RequestType<*>?>
                        ) {
                            MyGame.user.level = level + 1
                            ToastLong(context, "第" + (level + 1) + "个关卡已解锁!")
                        }
                        override fun onFailure(call: Call<RequestType<*>?>, t: Throwable) {
                            Logd(t.toString())
                        }
                    })
            }
        }
        /**
         * 技能槽增加
         * */
        fun setFireNum(num: Int, context: Context?) {
            if (MyGame.user.ownFireNum < num) {
                MyApplication.apiService.update(userId, "ownFireNum", num.toString() + "")
                    .enqueue(object : Callback<RequestType<*>?> {
                        override fun onResponse(
                            call: Call<RequestType<*>?>,
                            response: Response<RequestType<*>?>
                        ) {
                            ToastLong(context, "您现在已可以选择" + num + "个技能!")
                            MyGame.user.ownFireNum = num
                        }

                        override fun onFailure(call: Call<RequestType<*>?>, t: Throwable) {}
                    })
            }
        }
        /**
         *  属性升级
         * */
        fun upLevelAttribute(toLevel: Int, context: Context?, list: List<Thing>, key: String) {
            MyApplication.apiService
                .update(MyGame.user.id.toString() + "", key, toLevel.toString() + "")
                .enqueue(object : Callback<RequestType<*>> {
                    override fun onResponse(
                        call: Call<RequestType<*>>,
                        response: Response<RequestType<*>>
                    ) {
                        if (response.body()!!.status == 200) {
                            userSpendThings(list)
                            var name = ""
                            if (key == Config.KEY_LIFE) {
                                MyGame.user.lifeLevel = toLevel
                                name = "生命值"
                            } else {
                                MyGame.user.speedLevel = toLevel
                                name = "速度"
                            }
                            adapter.initData()
                            ToastLong(context, name + "已升级到" + toLevel + "级!")
                        } else ToastLong(context, "技能升级失败!")
                        Logd(response.body().toString())
                    }

                    override fun onFailure(call: Call<RequestType<*>>, t: Throwable) {
                        Logd(t.toString())
                        ToastLong(context, "技能升级失败!")
                    }
                })
        }

        //下标从开始:
        fun upLevelFire(fireId: Int, context: Context?, list: List<Thing>) {
            var s = MyGame.user.firesLevel
                val c = s.toCharArray()
                c[fireId]++
                val stringBuilder = StringBuilder("")
                for (c1 in c) {
                    stringBuilder.append(c1)
                }
                s = stringBuilder.toString()
                val finalS = s
                MyApplication.apiService.update(MyGame.user.id.toString() + "", "firesLevel", s)
                    .enqueue(object : Callback<RequestType<*>> {
                        override fun onResponse(
                            call: Call<RequestType<*>>,
                            response: Response<RequestType<*>>
                        ) {
                            if (response.body()!!.status == 200) {
                                userSpendThings(list)
                                ToastLong(
                                    context,
                                    "技能\"" + MyTables.getFireNames()[fireId] + "\"已升级到" + finalS[fireId] + "级!"
                                )
                                MyGame.user.firesLevel = finalS
                                adapter.initData()
                            } else ToastLong(context, "技能升级失败!")
                            Logd(response.body().toString())
                        }

                        override fun onFailure(call: Call<RequestType<*>>, t: Throwable) {
                            Logd(t.toString())
                            ToastLong(context, "技能升级失败!")
                        }
                    })

        }

        //下标从0开始
        fun unlockFire(fireId: Int, context: Context?) {
            var s = MyGame.user.firesLevel
            if (s[fireId] == '0') {
                val c = s.toCharArray()
                c[fireId] = '1'
                val stringBuilder = StringBuilder("")
                for (c1 in c) {
                    stringBuilder.append(c1)
                }
                s = stringBuilder.toString()
                Logd(MyGame.user.toString())
                val finalS = s
                MyApplication.apiService.update(MyGame.user.id.toString() + "", "firesLevel", s)
                    .enqueue(object : Callback<RequestType<*>> {
                        override fun onResponse(
                            call: Call<RequestType<*>>,
                            response: Response<RequestType<*>>
                        ) {
                            if (response.body()!!.status == 200) {
                                MyGame.user.firesLevel = finalS
                                ToastLong(
                                    context,
                                    "技能\"" + MyTables.getFireNames()[fireId] + "\"已解锁!"
                                )
                            } else Logd(response.body().toString())
                        }

                        override fun onFailure(call: Call<RequestType<*>>, t: Throwable) {
                            Logd(t.toString())
                        }
                    })
            }
        }

    }
}