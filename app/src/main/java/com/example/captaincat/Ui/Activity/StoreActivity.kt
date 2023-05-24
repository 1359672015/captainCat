package com.example.captaincat.Ui.Activity

import com.example.captaincat.information.Tables.ObjectTable.Companion.allThing
import com.example.captaincat.MyGame
import com.example.captaincat.Ui.Adapter.ProductAdapter
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.captaincat.api.UserBagModel.Companion.money
import com.example.captaincat.BaseActivity
import com.example.captaincat.MyApplication.Companion.musicUtil
import com.example.captaincat.Utils.media.MusicUtil.Companion.LIGHT_TREE
import com.example.captaincat.databinding.ActivityStoreBinding

class StoreActivity : BaseActivity<ActivityStoreBinding>() {

    fun refresh() {
        mViewBinding.tvMoney.text = "金币：" + MyGame.user.coins
    }

    override  fun initData() {
        mViewBinding.ivBack.setOnClickListener { finish() }
        money.observe(this){
        mViewBinding.tvMoney.text = "金币：$it"
        }
        // TODO: 2023/2/18 立刻更新 liveData
        initStore()
    }

    private fun initStore() {
        val list = allThing.subList(1, allThing.size)
        mViewBinding.rvObj.adapter = ProductAdapter(context, list){

        }
        mViewBinding.rvObj.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
    }

    companion object {
        var activity: StoreActivity? = null
    }

    override fun initViewBiding(): ViewBinding {
      return ActivityStoreBinding.inflate(layoutInflater)
    }

    override fun initView() {
        musicUtil.play(LIGHT_TREE)
        activity = this
    }
}