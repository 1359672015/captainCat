package com.example.captaincat.Ui.Activity

import com.example.captaincat.Ui.Adapter.SkillAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.example.captaincat.BaseActivity
import com.example.captaincat.databinding.ActivityPraticeBinding

class PracticeActivity : BaseActivity<ActivityPraticeBinding>() {


    override fun initView() {
        mViewBinding.run {
        ivBack.setOnClickListener { finish() }
        rv.adapter = SkillAdapter(context)
        rv.layoutManager = LinearLayoutManager(context)
        }
    }

    override fun initData() {

    }
    override fun initViewBiding(): ViewBinding {
        return  ActivityPraticeBinding.inflate(layoutInflater)
    }
}