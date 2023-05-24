package com.example.captaincat.Utils.media

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import android.os.Message
import com.example.captaincat.information.Config.Config.musicSourceUrl
import com.example.captaincat.information.Config.Config.shootMusicPlay
import com.example.captaincat.MyApplication.Companion.appContext
import com.example.captaincat.Utils.cache.MMKVUtils
import com.example.lamstest.Common.logD
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

class MusicUtil( ) {
   companion object{
       var BATTLE = "battle.mp3"
       var BATTLE_EVE = "battleeve.mp3"
       var NERVOURS_BATTLE = "nervouse.m4a"
       var TREE_HOUSE = "treehouse.mp3"
       var LIGHT_TREE = "lighttree.mp3"
       var SHOOT_BIU = "biu1.m4a"
       var HURTBIU = "biu2.m4a"
   }
    private lateinit var mediaPlayerBg: MediaPlayer
    private lateinit var mediaPlayerShoot: MediaPlayer
    private lateinit var mediaPlayerHurt: MediaPlayer
    private lateinit var fisShoot:FileInputStream
    private lateinit var fisHurt:FileInputStream
    private lateinit var handler: Handler
    private lateinit var looper: Looper

    fun init() {
        mediaPlayerBg = MediaPlayer()
        mediaPlayerShoot = MediaPlayer()
        mediaPlayerHurt = MediaPlayer()
        mediaPlayerBg.reset()
        mediaPlayerShoot.reset()
        mediaPlayerHurt.reset()
        mediaPlayerBg.isLooping = true
        mediaPlayerShoot.isLooping = false
        mediaPlayerHurt.isLooping = false

        val bytes1 = readAudioFile(appContext, SHOOT_BIU)
        val bytes2 = readAudioFile(appContext, HURTBIU)
        val tempMp3 = File.createTempFile("kurchina2", "mp3")
        tempMp3.deleteOnExit()
        val fos = FileOutputStream(tempMp3)
        fos.write(bytes1)
          fisShoot = FileInputStream(tempMp3)
        fos.write(bytes2)
          fisHurt = FileInputStream(tempMp3)
        fos.close()
        Thread {
            /**
             * 在子线程中创建looper处理播放音乐任务，避免主线程执行速度受影响*/
            run {
                Looper.prepare()
                handler = @SuppressLint("HandlerLeak")
                object : Handler() {
                    //如果前面没有写Looper.prepare(),则执行此行代码会报错：java.lang.RuntimeException: Can't create handler inside thread Thread[Thread-2,5,main] that has not called Looper.prepare()
                    override fun handleMessage(msg: Message) {
                            val name = when(msg.what){
                                1 -> BATTLE
                                2 -> BATTLE_EVE
                                3 -> NERVOURS_BATTLE
                                4 -> TREE_HOUSE
                                5 -> LIGHT_TREE
                                6 -> SHOOT_BIU
                                7 -> HURTBIU
                                else -> LIGHT_TREE
                            }
                            when(name){
                                SHOOT_BIU-> playShootMusic()
                                HURTBIU-> playHurtMusic()
                                else -> playBgMusic(name)
                            }
                        }
                }
                looper = Looper.myLooper()!!
                Looper.loop()
                 "loop()方法执行完了".logD("loop")
            }
        }.start()
    }

    fun play(name:String= TREE_HOUSE) {
        val message = Message()
        message.what = when(name){
        BATTLE ->1
        BATTLE_EVE ->2
        NERVOURS_BATTLE->3
        TREE_HOUSE->4
        LIGHT_TREE->5
        SHOOT_BIU->6
        HURTBIU->7
        else -> 4
        }
       // handler.sendMessage(message)
    }
   private fun playBgMusic(name:String = TREE_HOUSE){
        mediaPlayerBg.reset()
        val url = "$musicSourceUrl$name"
        mediaPlayerBg.setDataSource(url)
        mediaPlayerBg.prepare()
        mediaPlayerBg.isLooping = true
        mediaPlayerBg.start()
    }

    private fun playShootMusic( ){
        mediaPlayerShoot.reset()
        mediaPlayerShoot.setDataSource(fisShoot.fd)
        mediaPlayerShoot.prepare()
        mediaPlayerShoot.isLooping = false
        mediaPlayerShoot.start()
    }

    private fun playHurtMusic( ){
        mediaPlayerHurt.reset()
        mediaPlayerHurt.setDataSource(fisHurt.fd)
        mediaPlayerHurt.prepare()
        mediaPlayerHurt.isLooping = false
        mediaPlayerHurt.start()
    }
    fun startBg(){
        if(MMKVUtils.decodeBool(shootMusicPlay,true))
            mediaPlayerBg.start()
    }
    fun stopAll() {
        mediaPlayerBg.stop()
        mediaPlayerShoot.reset()
    }
    fun pause(){
        mediaPlayerBg.pause()
        mediaPlayerShoot.stop()
    }

    private fun playLocalBg(context: Context,name:String) {
        val bytes = readAudioFile(context,name)
        try {
            // create temp file that will hold byte array
            val tempMp3 = File.createTempFile("kurchina", "mp3")
            tempMp3.deleteOnExit()
            val fos = FileOutputStream(tempMp3)
            fos.write(bytes)
            fos.close()
            val fis = FileInputStream(tempMp3)
            mediaPlayerBg.setDataSource(fis.fd)
            mediaPlayerBg.prepare()
            mediaPlayerBg.isLooping = true
            mediaPlayerBg.start()
        } catch (ex: IOException) {
            val s = ex.toString()
            ex.printStackTrace()
        }
    }
    private fun playLocalShoot(context:Context,type:Int) {
        try {
            val bytes1 = readAudioFile(appContext, SHOOT_BIU)

            val tempMp3 = File.createTempFile("kurchina2", "mp3")
            tempMp3.deleteOnExit()
            val fos = FileOutputStream(tempMp3)
            fos.write(bytes1)
            val fis = FileInputStream(tempMp3)
            fos.close()
            mediaPlayerShoot.setDataSource(fis.fd)
            mediaPlayerShoot.prepare()
            mediaPlayerShoot.isLooping = false
            mediaPlayerShoot.start()

        } catch (ex: IOException) {
            val s = ex.toString()
            ex.printStackTrace()
        }
    }
    /**
     * 读取asset目录下音频文件。
     *
     * @return 二进制文件数据
     */
    private fun readAudioFile(context: Context, filename: String): ByteArray? {
        try {
            val ins = context.assets.open(filename)
            val data = ByteArray(ins.available())
            ins.read(data)
            ins.close()
            return data
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }


    fun release(){
        fisHurt.close()
        fisShoot.close()
        mediaPlayerBg.release()
        mediaPlayerShoot.release()
        looper.quit()
    }

}