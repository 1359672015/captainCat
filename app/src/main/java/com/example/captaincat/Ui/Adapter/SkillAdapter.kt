package com.example.captaincat.Ui.Adapter

import android.annotation.SuppressLint
import android.content.Context
import com.example.captaincat.Utils.message.ToastUtil.Companion.showText
import androidx.recyclerview.widget.RecyclerView
import com.example.captaincat.Ui.Adapter.SkillAdapter.MyHolder
import com.example.captaincat.model.ItemPractice
import com.example.captaincat.MyGame
import com.example.captaincat.information.Tables.MyTables
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.example.captaincat.model.Thing
import com.example.captaincat.information.Tables.FireTakesTable
import com.example.captaincat.Ui.Dialog.MenuDialog
import com.example.captaincat.api.UserLevelsModel
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.captaincat.information.Config.Config
import com.example.captaincat.information.Tables.MyTables.moveSpeed
import com.example.captaincat.information.Tables.ObjectTable
import com.example.captaincat.MyApplication.Companion.appContext
import com.example.captaincat.R
import java.util.ArrayList

class SkillAdapter(var context: Context) : RecyclerView.Adapter<MyHolder>() {
    var bagList: String = ""
    var userFires = ""
    var list: MutableList<ItemPractice> = ArrayList()
    fun initData() {
        list = ArrayList()
        userFires = MyGame.user.firesLevel
        for (i in 0 until userFires.length) {
            if (userFires[i] != '0') {
                list.add(getItemPractice(i))
            }
        }
        val lifeL = MyGame.user.lifeLevel
        list.add(
            ItemPractice(
                Config.LIFE_ID,
                "生命值",
                lifeL,
                R.drawable.life,
                getLifeInfo(lifeL),
                if (lifeL < Config.MAX_LIFE) getLifeInfo(lifeL + 1) else "已是最高等级!",
                lifeL < Config.MAX_LIFE,
                null
            )
        )
        val speedL = MyGame.user.speedLevel
        list.add(
            ItemPractice(
                Config.SPEED_ID,
                "移动速度",
                speedL,
                R.drawable.move,
                getSpeedInfo(speedL),
                if (speedL < Config.MAX_SPEED) getSpeedInfo(speedL + 1) else "已是最高等级!",
                speedL < Config.MAX_SPEED,
                null
            )
        )
        notifyDataSetChanged()
    }

    fun getItemPractice(i: Int): ItemPractice {
        val name = MyTables.getFireName(i)
        val icon = MyTables.FIRE_ICON[i]
        val level = userFires[i].code - 48
        val isMax = level >= MyTables.firesPower[0].size-1
        val nextInfo: String
        nextInfo = if (!isMax) {
            getFireInfo(i, level + 1)
        } else "已是最高等级!"
        val info = getFireInfo(i, level)
        return ItemPractice(i, name, level, icon, info, nextInfo, isMax, null)
    }

    fun getLifeInfo(level: Int): String {
        return "生命值:" + MyTables.life[level] + ""
    }

    fun getSpeedInfo(level: Int): String {
        return "速度:" + moveSpeed[level] + ""
    }

    fun getFireInfo(id: Int, level: Int): String {
        val power = MyTables.getFirePower(id, level)
        val speed = MyTables.getFireSpeed(id, level)
        val supply = MyTables.getSupply(id, level)
        val first = MyTables.getFirstNum(id, level)
        return "伤害:$power\n速度:$speed\n补给:$supply/s\n初值:$first"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(LayoutInflater.from(context).inflate(R.layout.item_practice, parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.name.text = list[position].name + " : Lv." + list[position].level
        holder.info.text = list[position].info + ""
        holder.nextInfo.text = list[position].nextInfo + ""
          Glide.with(context).load(list[position].iconUrl).into(holder.icon);
        holder.upLevel.setOnClickListener {
            if (list[position].id < 10) onUpFire(
                position,
                holder
            ) else onUpAttribute(holder, position, list[position].id)
        }
    }

    private fun onUpAttribute(holder: MyHolder, position: Int, id: Int) {
        val key: String
        val max: Int
        val level: Int
        val things: List<Thing>
        if (id == Config.SPEED_ID) {
            key = Config.KEY_SPEED
            max = Config.MAX_SPEED
            level = MyGame.user.speedLevel
        } else {
            key = Config.KEY_LIFE
            max = Config.MAX_LIFE
            level = MyGame.user.lifeLevel
        }
        if (level < max) {
            things = if (id == Config.SPEED_ID) FireTakesTable.getUpLevelSpeedNeeds(
                list[position].level + 1
            ) else FireTakesTable.getUpLevelLifeNeeds(list[position].level + 1)
            val info = ObjectTable.turnIntoDiaItemForPra(things)
            MenuDialog.showDialog(
                context,
                info.dialogItemList,
                "此次升级将会消耗:",
                Config.TAKES
            ) { vw: View? ->
                if (info.isEnough) {
                    UserLevelsModel.upLevelAttribute(level + 1, context, things, key)
                    //initUserInfo();
                    //holder.info.setText(list.get(position).getInfo());
                    //holder.nextInfo.setText(list.get(position).getNextInfo());
                    MenuDialog.getInstance().dismiss()
                } else showText(context, "材料不足!")
            }
        } else {
            // initUserInfo();
            // holder.info.setText(list.get(position).getInfo());
            // holder.nextInfo.setText(list.get(position).getNextInfo());
            showText(context, "已是最高等级,无法升级!")
        }
    }

    private fun onUpFire(position: Int, holder: MyHolder) {
        val level = MyGame.user.firesLevel[position].code - 48
        if (level < Config.MAX_FIRE) {
            val things = FireTakesTable.getNeeds(list[position].id, list[position].level + 1)
            val info = ObjectTable.turnIntoDiaItemForPra(things)
            MenuDialog.showDialog(
                context,
                info.dialogItemList,
                "此次升级将会消耗:",
                Config.TAKES
            ) { vw: View? ->
                if (info.isEnough) {
                    UserLevelsModel.upLevelFire(list[position].id, context, things)
                    // initUserInfo();
                    // String s  = list.get(position).toString();
                    // Log.d("Lama",s);
                    // holder.info.setText(list.get(position).getInfo());
                    // holder.nextInfo.setText(list.get(position).getNextInfo());
                    MenuDialog.getInstance().dismiss()
                } else showText(context, "材料不足!")
            }
        } else {
            //initUserInfo();
            //holder.info.setText(list.get(position).getInfo());
            //holder.nextInfo.setText(list.get(position).getNextInfo());
            showText(context, "已是最高等级,无法升级!")
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nextInfo: TextView
        var info: TextView
        var icon: ImageView
        var upLevel: Button
        var name: TextView

        init {
            nextInfo = itemView.findViewById<View>(R.id.tv_next) as TextView
            info = itemView.findViewById<View>(R.id.tv_info) as TextView
            upLevel = itemView.findViewById(R.id.btn_up_level)
            icon = itemView.findViewById(R.id.iv_icon)
            name = itemView.findViewById<View>(R.id.tv_name) as TextView
        }
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        @JvmStatic
          var adapter: SkillAdapter = SkillAdapter(appContext!!)
    }

    init {
        initData()
        adapter = this
    }
}