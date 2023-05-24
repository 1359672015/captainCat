package com.example.captaincat.Ui.Activity

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.example.captaincat.api.ReadyOrPetModel.Companion.getGems
import com.example.captaincat.api.ReadyOrPetModel.Companion.tookOff
import com.example.captaincat.api.ReadyOrPetModel.Companion.wear
import com.example.captaincat.api.UserBagModel
import com.example.captaincat.api.UserLevelsModel.Companion.bag
import com.example.captaincat.BaseActivity
import com.example.captaincat.MyGame.Companion.gemWearEvent
import com.example.captaincat.information.Tables.GemsTables.Companion.allGems
import com.example.captaincat.information.Tables.GemsTables.Companion.getGemById
import com.example.captaincat.R
import com.example.captaincat.Ui.Adapter.ObjAdapter
import com.example.captaincat.Ui.Dialog.CommonDialog
import com.example.captaincat.Ui.Dialog.WearDialog
import com.example.captaincat.databinding.ActivityBackUpBinding
import com.example.lamstest.Common.hide
import com.example.lamstest.Common.show
import com.example.lamstest.Common.toastCover
import kotlinx.android.synthetic.main.activity_back_up.*
import kotlinx.android.synthetic.main.activity_back_up.view.*
import kotlinx.android.synthetic.main.bottom_feed.*

class BackUpActivity : BaseActivity<ActivityBackUpBinding>() {

    lateinit var adapter :ObjAdapter
    var nowPlace = 0
        override fun initViewBiding(): ViewBinding {
        return ActivityBackUpBinding.inflate(layoutInflater)
    }

    override fun initView() {
        mViewBinding.ivBack.setOnClickListener {
            finish()
        }
        mViewBinding.tvKnow.setOnClickListener{
            CommonDialog.Builder().setCharacter(R.drawable.cat_ferrer)
                .setTitle("提示")
                .setContent("先点击选中其中一个技能槽,再点击下面的符咒或宝石,即可装备。装备可给战斗带来额外的属性加成。宝物可通过战斗或商店购买获得。")
                .setRightOnclickListener {
                    it.dismiss()
                }.show(supportFragmentManager)
        }
        mViewBinding.run{
            ivGemI.setOnClickListener {
                vGemI.show()
                vGemII.hide()
                nowPlace=1
        }
            ivGemII.setOnClickListener {
                vGemI.hide()
                vGemII.show()
                nowPlace=2
            }
            btnTookI.setOnClickListener {
                tookOff(1){
                    UserBagModel.getBag{
                        initGems()
                    }
                    iniWear()
                }
            }
            btnTookII.setOnClickListener {
                tookOff(2){
                    UserBagModel.getBag{
                        initGems()
                    }
                    iniWear()
                }
            }
            btnLookI.setOnClickListener {
                val gem = getGemById(gemWearEvent.iPlaceGemId)
                if(gem!=null){
                WearDialog.showDialog(context, gem,true){}
            }
            }
            btnLookII.setOnClickListener {
                val gem = getGemById(gemWearEvent.iiPlaceGemId)
                if(gem!=null){
                WearDialog.showDialog(context,gem ,true){}
            }
            }
        }

    }

    override fun initData() {
        getGems{
            UserBagModel.getBag{
                initGems()
            }
            iniWear()
        }
    }

    fun iniWear(){
            Glide.with(context).load(getGemById(gemWearEvent.iPlaceGemId)?.picUrl).into(mViewBinding.ivGemI)
            Glide.with(context).load(getGemById(gemWearEvent.iiPlaceGemId)?.picUrl).into(mViewBinding.ivGemII)
    }
    fun initGems(){
        val list =   bag!!.filter { it.type==3 }
        mViewBinding.recyclerView.adapter = ObjAdapter(context,list,false){
            if(nowPlace!=1&&nowPlace!=2){
                "请选择装备槽!".toastCover()
                return@ObjAdapter
            }
            allGems.forEach { gem->
                if(it.id == gem.id){
                    WearDialog.showDialog(context,gem,false){
                        wear(gem.id,nowPlace){
                            iniWear()
                            UserBagModel.getBag{
                                initGems()
                        } } }
                    return@ObjAdapter
            }
        }
    }
        mViewBinding.recyclerView.layoutManager =
            StaggeredGridLayoutManager(3, RecyclerView.VERTICAL)
}
}