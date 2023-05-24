package com.example.lamstest.Common

import android.app.Activity
import android.app.ActivityManager
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.captaincat.BuildConfig
import com.example.captaincat.MyApplication.Companion.appContext
import com.example.captaincat.R
import com.example.captaincat.Utils.message.ToastUtils
import com.google.android.material.snackbar.Snackbar
import com.qmuiteam.qmui.util.QMUIDisplayHelper
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.math.BigInteger
import java.security.MessageDigest

fun String?.toast():String?{
    ToastUtils.toast(this)
    return this
}
fun String?.toastCover(s:String = ""):String? {
    ToastUtils.toastCover(this)
        this.logD(s)
    return this
}
fun String?.toastLong():String? {
    ToastUtils.toastLong(this)
    return this
}
fun String.toMD5(): String {
    val md5 = MessageDigest.getInstance("md5")
    val bytes = md5.digest(this.toByteArray(charset("utf-8")))
    return BigInteger(1, bytes).toString(16)
}

 fun String.copyClipboardManager(): Boolean {
     if(this.isNullOrEmpty())
         return false
    return try {
        //获取剪贴板管理器
        val cm: ClipboardManager = appContext!!.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        // 创建普通字符型ClipData
        val mClipData: ClipData = ClipData.newPlainText("Label", this)
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData)
        true
    } catch (e: Exception) {
        false
    }
}
fun Bitmap.bitmapToByteArray(): ByteArray? {
        val baos = ByteArrayOutputStream()
        this.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val bytes = baos.toByteArray()
        val myBase64 = Base64.encodeToString(bytes, Base64.DEFAULT)
        return bytes
}

private fun InputStream?.inputStream2Base64(): String? {
    var data: ByteArray? = null
    try {
        val swapStream = ByteArrayOutputStream()
        val buff = ByteArray(100)
        var rc = 0
        while (this!!.read(buff, 0, 100).also { rc = it } > 0) {
            swapStream.write(buff, 0, rc)
        }
        data = swapStream.toByteArray()
    } catch (e: IOException) {
        e.printStackTrace()
    } finally {
        if (this != null) {
            try {
              this.close()
            } catch (e: IOException) {
                throw java.lang.Exception("输入流关闭异常")
            }
        }
    }
    return Base64.encodeToString(data, Base64.DEFAULT)
}
fun Bitmap.bitmapToBase64(): String? {
    val baos = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.PNG, 100, baos)
    val bytes = baos.toByteArray()
    val myBase64 = Base64.encodeToString(bytes, Base64.DEFAULT)
    return myBase64
}

fun MutableMap<String,Any>.paramsToString():String{
    val sb :StringBuilder = StringBuilder("")
    this.keys.forEach{
        sb.append("$it ---> ${this[it]}\n")
    }
    return sb.toString()
}
/**
 * 判断某个界面是否在前台
 */
fun Activity.isForeground(): Boolean {
    return isForeground(this, this.javaClass.name)
}
fun isForeground(context: Context?, className: String): Boolean {
    if (context == null || TextUtils.isEmpty(className)) return false
    val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val list = am.getRunningTasks(1)
    if (list != null && list.size > 0) {
        val cpn = list[0].topActivity
        if (className == cpn!!.className) return true
    }
    return false
}

fun String.isSubStringOf(string:String):Boolean{
    if(this.length>string.length)
        return false
    val len = this.length
    val lon = string.length
    string.toCharArray().forEachIndexed { i, c ->
        for(i in 0..lon-len)
            if(string.substring(i,len+i)==this)
                return true
    }
    return false
}

fun String.fileType():String{
    var lastPoi = this.lastIndexOf('.')
    if(lastPoi!=-1)
        return substring(lastPoi,length)
    else return ""
}
//this:
// 下载到的文件名
// file:
// 链接的文件名,
fun String.getFileNameFromType(file: String?):String{
    var name = this
    val nameType = this.fileType()
    val realType = file?.fileType()
    if(nameType.isNullOrEmpty()&&realType!!.isNotEmpty())
        name  += realType
    return name
}

/**
 * 为TextView设置左侧图标，目前常用于判断题*/
fun TextView.drawable(drawable: Drawable?){
    this.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable,null,null,null)
}
fun TextView.drawable(drawableId: Int){
    this.setCompoundDrawablesRelativeWithIntrinsicBounds(drawableId.drawable(),null,null,null)
}
fun String.isOneOf(list: List<String>):Boolean{
    list.forEach {
        if (it==this)
            return true
    }
    return false;
}

fun getColor(context: Context, color: Int): Int {
    return ContextCompat.getColor(context, color)
}
fun Int.color(): Int {
    return ContextCompat.getColor(appContext!!, this)
}
fun Int.drawable():Drawable{
    return ContextCompat.getDrawable(
        appContext!!,
        this
    )?:this.toDrawable()
}
fun getColorStateList(context: Context, color: Int): ColorStateList {
    return ContextCompat.getColorStateList(context, color)!!
}
fun sp2px(context: Context, size: Int): Int {
    return QMUIDisplayHelper.sp2px(context, size)
}

fun dp2px(context: Context, size: Int): Int {
    return QMUIDisplayHelper.dp2px(context, size)
}

fun FragmentActivity.changeFragment(viewId:Int, fragment: Fragment){
    this.supportFragmentManager.beginTransaction().replace(viewId,fragment).commit()
}

fun View.viewVisibility(value: Boolean=false){
    this.visibility=if (value) View.VISIBLE else View.GONE
}

fun View.viewInVisibility(value: Boolean=false){
    this.visibility=if (value) View.VISIBLE else View.INVISIBLE
}
fun String.beginWith(s:String):Boolean{
    for(i in s.indices){
        if(s[i]!=this[i])
            return false
    }
    return true
}
fun List<String?>?.isEmptyList():Boolean{
    if(this==null)
        return true
    this.forEach {
        if(it!=null&&it.trim()!="")
            return false
    }
    return true
}
/*打印并返回字符串本身*/
fun String?.logD(tag:String = ""):String{
    if(BuildConfig.DEBUG)
        Log.d("LamLog --> $tag",this?:"")
    return this?:""
}

fun String?.getFileNameFromUrl():String{
    if(this==null)
        return ""
    return this.substring(
        this.lastIndexOf("/")+1,
        this.length)


}
/** 手动替换后端传来的符号* */
fun String.replaceStrangeToken():String{
    var str = this
    str = str.replace("&lt;","<")
    str = str.replace("&gt;",">")
    str = str.replace("&amp;","&")
    str = str.replace("&#39;","'")
    str = str.replace("&quot;","\"")
    return str
}
fun Activity.setStatusColor(colorId:Int){
    window.statusBarColor = resources.getColor(colorId) //设置状态栏颜色
}
fun String.nameLimit():String{
    var s = ""
    val length = this.length
    s = if(length>10) {
        this.substring(0,3)+"..."+this.substring(length-4,length)
    } else this
    return s
}
fun couldCourseWare(){

}
fun <T> List<T?>.removeNull():List<T>{
    val list:MutableList<T> = mutableListOf()
    this.forEach {
        if(it!=null)
            list.add(it)
    }
    return list
}
fun String.jsonStrVal(key:String):String{
    val jo = JSONObject(this)
    return jo.getString(key)?:""
}
fun String.jsonIntVal(key:String):Int{
    val jo = JSONObject(this)
    return jo.getInt(key)?:-1
}
fun String.jsonObjVal(key:String): JSONObject {
    val jo = JSONObject(this)
    return jo.getJSONObject(key)
}
fun String.belongsTo(arrays:Array<String>):Boolean{
    arrays.forEach {
        if(it==this)
            return true
    }
    return false
}
fun TextView.setDrawText(drawableId:Int,s:String = ""){
    this.text .run {
            drawable(drawableId.drawable())
                text = s
    }


}


fun ViewGroup.hide(){
    this.visibility = View.GONE
}
fun View.hide() {
    this.visibility = View.GONE
}
fun ViewGroup.show(){
    this.visibility = View.VISIBLE
}
fun View.show() {
    this.visibility = View.VISIBLE
}
fun showSnack(view: View?, message: String?) {
    Snackbar.make(view!!, message!!, Snackbar.LENGTH_LONG)
        .setAction("Action", null).show()
}
fun <T> T.isOneOf(list:List<T>):Boolean{
    list.forEach {
        if(this==it)
            return true
    }
    return false
}
fun <T> T.isNotOneOf(list:List<T>):Boolean{
    list.forEach {
        if(this==it)
            return false
    }
    return true
}
fun View.showSlow(fromLeft:Boolean = false,fromBottom:Boolean = true){

    var s = R.anim.anim_set
    var animation = AnimationUtils.loadAnimation(
        appContext,s)
    this.startAnimation(animation);
    this.visibility = View.VISIBLE
}
fun View.showQuick(){
    var s = R.anim.anim_set2
    var animation = AnimationUtils.loadAnimation(
        appContext,s)
    this.startAnimation(animation);
    this.visibility = View.VISIBLE
}
fun View.showGrowing(){
    var s = R.anim.anim_show_fire
    var animation = AnimationUtils.loadAnimation(
        appContext,s)
    this.startAnimation(animation);
    this.visibility = View.VISIBLE
}
fun View.shake(){
    var s = R.anim.anim_shake
    var animation = AnimationUtils.loadAnimation(
        appContext,s)
    this.startAnimation(animation);
    this.visibility = View.VISIBLE
}
fun  String.divideWith( ):List<String> {
    this.toCharArray().forEachIndexed { p, c ->
        if (c == ':')
            return mutableListOf(this.substring(0, p), this.substring(p + 1, this.length))
    }
    return mutableListOf("0", "0")
}
fun String?.isNotNull():Boolean {
    return this!=null&& this.isNotEmpty()
}