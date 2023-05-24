package com.example.captaincat.Ui.Activity

import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.example.captaincat.api.ReadyOrPetModel.Companion.foundPet
import com.example.captaincat.BaseActivity
import com.example.captaincat.MyGame.Companion.user
import com.example.captaincat.information.Tables.CharacterConfig.Companion.elves
import com.example.captaincat.model.Pet
import com.example.captaincat.R
import com.example.captaincat.Ui.Adapter.PetAdapter
import com.example.captaincat.Ui.Dialog.CommonDialog
import com.example.captaincat.databinding.ActivityGetPetBinding
class GetPetActivity : BaseActivity<ActivityGetPetBinding>() {

    var selectedPetLook = -1
    val pets = mutableListOf(
        (Pet("橙子精灵", 0)),
        (Pet("蓝莓精灵", 1)),
        (Pet("番茄精灵", 2)),
        (Pet("桃子精灵", 3)),
        (Pet("苹果精灵", 4))
    )
    override fun initViewBiding(): ViewBinding {
        return ActivityGetPetBinding.inflate(layoutInflater)
    }

    override fun initView() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        mViewBinding.recyclerViewPet.layoutManager = layoutManager
        val petAdapter = PetAdapter(pets).apply {
            onClick = {
                selectedPetLook = it.look
                Glide.with(context).load(elves[it.look]).into(mViewBinding.ivNowSel)
                this.notifyDataSetChanged()
            }

        }
        mViewBinding.recyclerViewPet.adapter = petAdapter

        mViewBinding.buttonConfirmAdopt.setOnClickListener { confirmAdopt() }
        mViewBinding.ivBack.setOnClickListener { finish() }

    }

    override fun initData() { }

    private fun confirmAdopt() {
        if (selectedPetLook == -1) {
            Toast.makeText(this, "请选择宠物", Toast.LENGTH_SHORT).show()
            return
        }
        val petName = mViewBinding.editTextPetName.text.toString().trim()
        if (petName.isEmpty()) {
            Toast.makeText(this, "请为宠物命名", Toast.LENGTH_SHORT).show()
            return
        }
        foundPet(user.id,petName,selectedPetLook)

        CommonDialog.Builder().setCharacter(R.drawable.car_julian)
            .setTitle("领养成功")
            .setContent("恭喜您成功领养了${pets[selectedPetLook].name}！快去战斗感受一下精灵带来的技能效果吧！")
            .setRightOnclickListener {
                it.dismiss()
                finish()
            }.show(supportFragmentManager)
    }

}
