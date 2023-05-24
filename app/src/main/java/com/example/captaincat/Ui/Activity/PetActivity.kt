package com.example.captaincat.Ui.Activity

import FeetBottomSheet
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.example.captaincat.BaseActivity
import com.example.captaincat.MyGame.Companion.pet
import com.example.captaincat.MyGame.Companion.petData
import com.example.captaincat.MyGame.Companion.user
import com.example.captaincat.Utils.MMKVUtils
import com.example.captaincat.Utils.TimeUtil
import com.example.captaincat.api.ReadyOrPetModel
import com.example.captaincat.databinding.ActivityPetBinding
import com.example.captaincat.information.Tables.CharacterConfig
import com.example.lamstest.Common.toastLong
import kotlinx.android.synthetic.main.activity_pet.*

class PetActivity : BaseActivity<ActivityPetBinding>() {

    override fun initViewBiding(): ViewBinding {
        return ActivityPetBinding.inflate(layoutInflater)
    }

    override fun initView() {
       ReadyOrPetModel.getPet(user.id){
          initData()
       }
         initListener()
    }
    fun initListener(){
        btnPlay.setOnClickListener{
            val nowDay = TimeUtil.getDay()
            val lastPlay = MMKVUtils.decodeString("LAST_PLAY")
            if(lastPlay==nowDay){
                "${pet.name}今天已经玩耍过了,主人明天再来找我玩吧!".toastLong()
                return@setOnClickListener
            }
            "宠物心情得到提高! ^-^".toastLong()
            ReadyOrPetModel.feedPet(pet.id,-5,15)
            MMKVUtils.encode("LAST_PLAY",nowDay)
            initData()
        }
        val mBottomSheetDialog = FeetBottomSheet()
        btnFeed.setOnClickListener {
            mBottomSheetDialog.show(supportFragmentManager,mBottomSheetDialog.tag)
        }
    }
    override fun initData() {
        mViewBinding.run{
            ivBack.setOnClickListener { finish() }
            tvPetName.text  = pet.name
            petData.observeForever {
                pbEnergy.progress = it.energy
                pbMood.progress = it.mood
                petStateTextView.text = it.status
            }
            Glide.with(context).load(CharacterConfig.elves[pet.look]).into(mViewBinding.petPhotoImageView)
        }
    }
}