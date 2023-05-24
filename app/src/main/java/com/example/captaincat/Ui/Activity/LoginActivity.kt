package com.example.captaincat.Ui.Activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.captaincat.BuildConfig.DEBUG
import com.example.captaincat.MyApplication
import com.example.captaincat.MyGame
import com.example.captaincat.Utils.cache.MMKVUtils
import com.example.captaincat.Utils.cache.SharePreferenceUtil
import com.example.captaincat.Utils.message.ToastUtil.Companion.Logd
import com.example.captaincat.Utils.message.ToastUtil.Companion.Logi
import com.example.captaincat.Utils.message.ToastUtil.Companion.ToastLong
import com.example.captaincat.Utils.message.ToastUtil.Companion.ToastMsg
import com.example.captaincat.Utils.netwoek.NetWorkUtil
import com.example.captaincat.api.ReadyOrPetModel
import com.example.captaincat.api.UserLevelsModel
import com.example.captaincat.api.core.RequestType
import com.example.captaincat.databinding.ActivityLoginBinding
import com.example.captaincat.model.User
import com.example.lamstest.Common.isNotNull
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class LoginActivity : AppCompatActivity() {
    var binding: ActivityLoginBinding? = null
    var context: Context = this@LoginActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        val a  = MMKVUtils.decodeString("account","")
        val p  = MMKVUtils.decodeString("password","")
        if(a.isNotNull())
        binding!!.etAccount.setText(a)
        if(p.isNotNull())
        binding!!.etPassword.setText(p)
        binding!!.btnLogin.setOnClickListener { v: View? ->
            if (NetWorkUtil.checkNetWork(this)) login(
                binding!!.etAccount.text.toString().trim { it <= ' ' },
                binding!!.etPassword.text.toString().trim { it <= ' ' }) else ToastMsg(
                context,
                "网络错误,请检查网络设置"
            )
        }
        binding!!.tvRegister.setOnClickListener { v: View? ->
            startActivity(
                Intent(
                    context,
                    RegisterActivity::class.java
                )
            )
        }
    }

    fun login(ac: String, pw: String) {
        var account = ac
        var password = pw
        if(DEBUG&&account.isEmpty())
        {
            account = "emo01"
        }
         if(DEBUG&&password.isEmpty())
            password="123456"
         if (account.length < 5 || password.length < 5) {
            ToastMsg(context, "账号密码须大于5位!")
            return
        }
        MyApplication.apiService.login(account, password).enqueue(object : Callback<RequestType<User>> {
            override fun onResponse(
                call: Call<RequestType<User>>,
                response: Response<RequestType<User>>
            ) {
                val status = response.body()!!.status
                if (status == 200) {
                    val user = response.body()!!.data
                    MyGame.setTheUser(user)
                    UserLevelsModel.userId = user.id.toString() + ""
                    SharePreferenceUtil.setUserId(user.id.toString() + "")
                    MMKVUtils.encode("account",account)
                    MMKVUtils.encode("password",password)
                    Logd("写入Sp:" + user.id + "----" + SharePreferenceUtil.getUserId())
                    val intent = Intent(context, MainActivity::class.java)
                    ToastMsg(context, "登陆成功!")
                    ReadyOrPetModel.getPet(user.id){}
                    startActivity(intent)
                    finish()
                } else ToastLong(context, response.body()!!.message)
            }

            override fun onFailure(call: Call<RequestType<User>>, t: Throwable) {
                ToastMsg(context, "网络错误，" + t.message)
            }
        })
    }

    /**同步请求,需要线程转化:1.在子线程发起耗时任务,2.得到响应之后回到主线程 */
    fun login2(account: String?, password: String?) {
        if (account == null || password == null) {
            ToastMsg(context, "请输入账号密码!")
            return
        }
        if (account.length == 0 || password.length == 0) {
            ToastMsg(context, "请输入账号密码!")
            return
        }
        Thread {
            var response: Response<RequestType<User>>? = null
            try {
                response = MyApplication.apiService.login(account, password).execute()
            } catch (e: IOException) {
                Logi(response!!.body()!!.data.toString())
                e.printStackTrace()
            }
            val user = response!!.body()!!.data
            Logi(user.toString())
        }.start()
    }
}