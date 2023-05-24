package com.example.captaincat.Ui.Activity

import android.content.Context
import com.example.captaincat.api.UserLevelsModel.Companion.refreshUser
import com.example.captaincat.api.UserLevelsModel.Companion.bag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import retrofit2.Call
import retrofit2.Response
import android.view.View
import com.example.captaincat.MyGame
import com.example.captaincat.Ui.Adapter.ObjAdapter
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.captaincat.model.Gem
import com.example.captaincat.model.Thing
import com.example.captaincat.MyApplication
import com.example.captaincat.Ui.Dialog.WearDialog

import com.example.captaincat.databinding.ActivityBagBinding

class BagActivity : AppCompatActivity() {
    var b: ActivityBagBinding? = null
    var context: Context = this@BagActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityBagBinding.inflate(layoutInflater)
        ////InitViewUtil.initView(this)
        //getSupportActionBar().hide();//隐藏顶部标题栏
        setContentView(b!!.root)
        refreshUser{
            initData()
        }
    }


    private fun initData() {
        b!!.ivBack.setOnClickListener { v: View? -> finish() }
        b!!.tvMoney.text = "金币：" + MyGame.user.coins + ""
        initBag()
    }

    private fun initBag() {
          MyApplication.apiService.getBag(MyGame.user.id.toString() + "")
                    .enqueue(object : retrofit2.Callback<List<Thing>>{
                        override fun onResponse(
                            call: Call<List<Thing>>,
                            response: Response<List<Thing>>
                        ) {
                            val list = response.body()?: mutableListOf()
                                bag = list as MutableList<Thing>
                                b!!.rvObj.adapter = ObjAdapter(context, list){
                                    WearDialog.showDialog(context, Gem(it.id,it.name,it.desc,it.picUrl),true){}
                                }
                                b!!.rvObj.layoutManager =
                                    StaggeredGridLayoutManager(3, RecyclerView.VERTICAL)
                        }
                        override fun onFailure(call: Call<List<Thing>>, t: Throwable) {
                            "连接错误:${t.message}"
                        }

                    })

            }

    }