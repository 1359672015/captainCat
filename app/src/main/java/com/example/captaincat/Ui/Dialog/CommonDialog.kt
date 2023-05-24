package com.example.captaincat.Ui.Dialog

import android.app.Dialog
import android.view.Gravity
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.viewbinding.ViewBinding
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.captaincat.R
import com.example.captaincat.databinding.DialogCommonBinding
import com.example.captaincat.databinding.DialogMessageCharacterBinding
import com.example.lamstest.Common.*
import com.qmuiteam.qmui.util.QMUIDisplayHelper

class CommonDialog(private val builder: Builder) :BaseDialog() {

    private val mViewBinding: DialogMessageCharacterBinding by viewBinding(CreateMethod.INFLATE)
    override fun initView(dialog: Dialog) {
        mViewBinding.run {
            tvTitle.text=builder.title
            tvContent.text=builder.content
            if(builder.charDrawable!=0){
            Glide.with(context).load(builder.charDrawable).into(ivChar)
                 ivChar.show()
            }
            if (builder.isGoneLeftBtn){
                btnCancel.visibility=View.GONE
                btnConfirm.layoutParams.width=QMUIDisplayHelper.dp2px(context,135)
            }

            btnCancel.run {
                if(builder.leftText.isNullOrEmpty()){
                    this.hide()
                    return@run
                }
                text=builder.leftText
                setOnClickListener {
                    setBgData(getColorStateList(context,builder.leftBtnBgColor))
                    setTextColor(getColor(context,builder.leftBtnTextColor))
                    dismissAllowingStateLoss()
                    builder.mLeftOnclick?.invoke(this@CommonDialog)
                }
            }
            btnConfirm.run {
                setBgData(getColorStateList(context,builder.rightBtnBgColor))
                setTextColor(getColor(context,builder.rightBtnTextColor))
                text=builder.rightText
                setOnClickListener{
                    dismissAllowingStateLoss()
                    builder.mRightOnclick?.invoke(this@CommonDialog)
                }
            }
        }
        this.isCancelable = false
    }
    override fun getLayoutView(): View {
        return mViewBinding.root
    }

    override fun getGravity(): Int {
        return Gravity.CENTER
    }

    override fun settingWidth(): Int {
        return dp2px(requireContext(),270)
    }

    class Builder {
        internal var rightBtnBgColor = R.color.blue1
        internal var rightText: String = "确定"
        internal var leftBtnBgColor = R.color.blue1
        internal var leftBtnTextColor = R.color.blue1
        internal var rightBtnTextColor = R.color.white
        internal var leftText: String  = ""
        internal var title: String = "您好"
        internal var charDrawable: Int = 0
        internal var content: CharSequence = "欢迎进入"
        internal var isGoneLeftBtn=false
        internal var mLeftOnclick:((CommonDialog)->Unit)?=null
        internal var mRightOnclick:((CommonDialog)->Unit)?=null
        fun setRightBtnBgColor(rightBtnBgColor: Int): Builder {
            this.rightBtnBgColor = rightBtnBgColor
            return this
        }

        fun setRightBtnTextColor(rightBtnTextColor:Int):Builder{
            this.rightBtnTextColor=rightBtnTextColor
            return this
        }

        fun setLeftBtnTextColor(leftBtnTextColor:Int):Builder{
            this.leftBtnTextColor=leftBtnTextColor
            return this
        }

        fun setRightText(rightText: String ): Builder {
            this.rightText = rightText
            return this
        }

        fun setLeftBtnBgColor(leftBtnBgColor: Int): Builder {
            this.leftBtnBgColor = leftBtnBgColor
            return this
        }

        fun setLeftText(leftText: String ): Builder {
            this.leftText = leftText
            return this
        }

        fun setTitle(title: String): Builder {
            this.title = title
            return this
        }

        fun setLeftOnclickListener(init: (CommonDialog) -> Unit): Builder{
            this.mLeftOnclick=init
            return this
        }

        fun setContent(content: String): Builder {
            this.content = content
            return this
        }
        fun setCharacter(drawable:Int):Builder{
            this.charDrawable = drawable
            return this
        }
        fun setContent(content:CharSequence):Builder{
            this.content=content
            return this
        }

        fun setRightOnclickListener(init: (CommonDialog) -> Unit): Builder{
            this.mRightOnclick=init
            return this
        }

        fun setGoneLeftBtn(gone:Boolean): Builder{
            this.isGoneLeftBtn=gone
            return this
        }


        fun show(manager:FragmentManager) {
            val dialog=CommonDialog(this)
            dialog.show(manager,"CommonDialog")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        builder.mLeftOnclick=null
    }



}