package com.example.captaincat.information.Levels

import android.content.Context
import com.example.captaincat.MyGame
import com.example.captaincat.R
import com.example.captaincat.flyingObjects.Enemies.Enemy
import com.example.captaincat.flyingObjects.Orcs.Orc2
import com.example.captaincat.information.Config.Config
import java.util.*

/*一场战役的信息*/
class Level6_1(context: Context?) : BaseLevel(context!!) {
    var reserveArmy = Array<LinkedList<Enemy>>(3) { _ -> LinkedList() }
    var readyToWar: Queue<Enemy> = LinkedList()
    var goneToWar: Queue<Enemy> = LinkedList()
    var index = 0
    override var reward: Map<Int, Int>? = null
    var report = 0
    override fun initArmy() {
        reserveArmy[0] = LinkedList()
        reserveArmy[0].add(Enemy(context, -200, -Config.SCREENHEI / 2 + 120, -1, Orc2(context), 1))
    }

    override fun getNextOrc(clock: Int): Enemy? {
        return if (reserveArmy[index].peek() != null) reserveArmy[index].poll() else null
    }

    override fun postReport(news: Int): Boolean {
        return true
    }

    /*override var level: Int
        set(level) {
            super.level = level
        }*/

    companion object {
        @JvmStatic
        val name: String
            get() = "六、归家"
        @JvmStatic
        val story: String
            get() = "朱利安已筹备好了回到喵喵星的路线，与卡托、${MyGame.pet.name}开启了回家之旅。"+
                   "奥克星的邪恶之魂被彻底击垮，猫猫星恢复了往日的和平。"+
                   "\n卡托舰长的故事后来广为传颂，成为所有猫猫们耳熟能详的英雄史诗。".trimIndent()
    }

    init {
        super.level = 0
        initArmy()
        background = R.drawable.universe//.bgr
    }
}