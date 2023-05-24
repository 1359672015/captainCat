package com.example.captaincat.Utils.media;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.example.captaincat.Utils.message.ToastUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;

//功能待测试
public class FileUtil {
   static public void initData() {
        String filePath = "/sdcard/FileUtil/";
        String fileName = "log.txt";

        writeTxtToFile("txt content", filePath, fileName);
    }

    // 将字符串写入到文本文件中
    static public void writeTxtToFile(String strcontent, String filePath, String fileName) {
        //生成文件夹之后，再生成文件，不然会出错
        makeFilePath(filePath, fileName);

        String strFilePath = filePath+fileName;
        // 每次写入时，都换行写
        String strContent = strcontent + "\r\n";
        try {
            File file = new File(strFilePath);
            if (!file.exists()) {
                Log.d("TestFile", "Create the file:" + strFilePath);
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            RandomAccessFile raf = new RandomAccessFile(file, "rwd");
            raf.seek(file.length());
            raf.write(strContent.getBytes());
            raf.close();
        } catch (Exception e) {
            Log.e("TestFile", "Error on write File:" + e);
        }
    }

    // 生成文件
   static public File makeFilePath(String filePath, String fileName) {
        File file = null;
        makeRootDirectory(filePath);
        try {
            file = new File(filePath + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    // 生成文件夹
    public static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {
            Log.i("error:", e+"");
        }
    }

    /** 保存二维码图片到本地*/
    static public boolean saveCode(Bitmap bitmap, Context context) {
        SimpleDateFormat df1 = new SimpleDateFormat("mmss");
        String time = df1.format(new Date());
        String path = context.getFilesDir().getAbsolutePath();
        makeFilePath(Environment.getExternalStorageDirectory()+"/family",time);
        File file = new File(Environment.getExternalStorageDirectory(),"捐款二维码"+time+".JPEG");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            Bitmap bitMap = bitmap;
            //((BitmapDrawable) ivQRCode.getDrawable()).getBitmap();//通过强制转化weiBitmapDrable然后获取Bitmap
            // ToastUtil.Companion.ToastLong(context,s);
            boolean saved = bitMap.compress(Bitmap.CompressFormat.JPEG,
                    100,fileOutputStream);//然后按照指定的图片格式转换，并以stream方式保存文件
            fileOutputStream.close();
            if (saved) {
                ToastUtil.Companion.ToastMsg(context,"保存成功!");
                sendBroadcastToScanFile(file,context); //广播扫描文件
                return true;
            }
            else{
                ToastUtil.Companion.ToastMsg(context,"保存失败!请重试");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtil.Companion.ToastMsg(context,"保存失败!请重试"+e.getMessage());
            return  false;
        }
    }

   // 发起广播让系统扫描该file
    public static void sendBroadcastToScanFile(File file, Context context){
        Intent intentScan = new Intent();
        intentScan.setAction(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intentScan.setData(Uri.fromFile(file));
        context.sendBroadcast(intentScan);
    }

}
