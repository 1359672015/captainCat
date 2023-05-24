package com.example.captaincat

import androidx.lifecycle.MutableLiveData
import com.example.captaincat.api.UserBagModel.Companion.money
import com.example.captaincat.event.GemWearEvent
import com.example.captaincat.model.Pet
import com.example.captaincat.model.User

// TODO: 2022/2/15 战斗结束对话框：炮火解锁信息
// TODO: 2022/2/15 情报对话框：穿插在关卡进行的前中后
// TODO: 2023/2/18 护盾 补血 时间 命中率 遥感 暂停挡位
// TODO: 2022/2/15 护盾、补血
// TODO: 2022/2/15 眩晕、穿透
// TODO: 2022/2/15 技能：护盾、补血、抛物线、回弹穿透弹（宠物）、双倍金币卡
// TODO: 2022/2/15 每日夺宝
// TODO: 2023/3/8  画面成就/连击x/百发百中/
//降低攻击频率，奇怪士兵，
class  MyGame {
    companion object{

    lateinit var user: User
      var pet: Pet = Pet(id = -1,name ="宠物")
    var petData: MutableLiveData<Pet> = MutableLiveData()
    lateinit var gemWearEvent: GemWearEvent

     fun setTheUser(user:User){
        money.postValue(user.coins)
         this.user = user
     }
    }
}

// finished: 2022/2/15 连击 加速
// finished: 2022/2/9 将敌人发射导弹逻辑抽取到orc类中，返回的导弹才显示在空战活动
// finished: 2022/2/15 敌人非同时发射导弹
// finished: 2022/2/11 建造抽屉
// finished: 2022/2/12 为主菜单页面设置点击事件
// finished: 2022/2/9 我的炮火信息、战斗参数全部取自全局user对象
// finished: 2022/2/9 尝试将敌人列表抽取到关卡信息中
// finished: 2022/2/15 抽离增加敌军的逻辑形成关卡类，形成关卡-战斗的架构
// finished: 2023/3/8 工具类：音乐播放者
// finished: 2023/3/8 音乐播放者逻辑改为 异步消息处理 避免主线程等待时间长，影响帧率
//4:17，降低物价
//4-22六级技能，减少无限士兵密度，降低挑战模式难度，减少体,boss实力


//  2022/2/15 构建全部子弹和敌人
//  2022/2/17 战败弹窗
//  2022/2/17 设计更多关卡
//  2022/2/17 挑战模式
//  2022/2/15 根据user提供选择炮火界面
//  2022/2/15 形成多个示例关卡
//  2022/2/15 形成根据user_level选择关卡的界面;

