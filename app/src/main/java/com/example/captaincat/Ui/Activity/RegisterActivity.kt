package com.example.captaincat.Ui.Activity

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.captaincat.MyApplication
import com.example.captaincat.Utils.message.ToastUtil.Companion.Logi
import com.example.captaincat.Utils.message.ToastUtil.Companion.ToastLong
import com.example.captaincat.Utils.message.ToastUtil.Companion.ToastMsg
import com.example.captaincat.api.core.RequestType
import com.example.captaincat.databinding.ActivityRegisterBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    var binding: ActivityRegisterBinding? = null
    var context: Context = this@RegisterActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        initListener()
    }

    private fun initListener() {
        binding!!.btnRegister.setOnClickListener { v: View ->
            register(
                binding!!.etName.text.toString().trim { it <= ' ' },
                binding!!.etAccount.text.toString().trim { it <= ' ' },
                binding!!.etPw1.text.toString().trim { it <= ' ' },
                binding!!.etPw2.text.toString().trim { it <= ' ' },
                binding!!.etEmail.text.toString().trim { it <= ' ' },
                binding!!.etIntroduce.text.toString().trim { it <= ' ' },
                v
            )
        }
    }

    private fun register(
        name: String,
        account: String,
        pw1: String,
        pw2: String,
        email: String,
        introduce: String,
        view: View
    ) {
        if (name.length == 0) {
            ToastMsg(context, "请输入昵称!")
            return
        }
        if (account.length < 2) {
            ToastMsg(context, "账号长度必须大于2")
            return
        }
        if (pw1.length < 2) {
            ToastMsg(context, "密码长度必须大于2.")
            return
        }
        if (pw2.isEmpty()) {
            ToastMsg(context, "请再次确认密码!")
            return
        }
        if (pw1 != pw2) {
            ToastMsg(context, "两次输入密码不一致!")
            return
        }
        MyApplication.apiService.register(account, pw1, name, email, introduce)
            .enqueue(object : Callback<RequestType<*>> {
                override fun onResponse(
                    call: Call<RequestType<*>>,
                    response: Response<RequestType<*>>
                ) {
                    if (response == null) {
                        ToastLong(context, "注册失败!")
                        return
                    }
                    if (response.body()!!.status == 200) {
                        ToastLong(context, response.body()!!.message)
                        finish()
                    } else ToastLong(context, response.body()!!.message)
                    Logi(response.toString())
                }

                override fun onFailure(call: Call<RequestType<*>>, t: Throwable) {
                    ToastLong(context, "网络错误")
                    Logi(t.toString())
                }
            })
    }
}