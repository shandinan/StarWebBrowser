package com.star.starwebbrowser.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;
import com.star.starwebbrowser.utils.ActivityStack;

public class SuperActivity extends Activity implements View.OnClickListener {
    private final String TAG = "MainActivity";
    /** 资源对象 */
    public Resources res;
    /** 应用对象 */
    public FTApplication app;
    /** 弹出框 **/
    public ProgressDialog pd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        res = this.getResources();
        app = (FTApplication)getApplication();
        ActivityStack.add(this);
    }


    /**
     * 销毁本activity
     */
    protected void onDestroy() {
        super.onDestroy();
        ActivityStack.remove(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_ENTER:     //确定键enter
            case KeyEvent.KEYCODE_DPAD_CENTER:
               // Log.d(TAG,"enter--->");

                break;
            case KeyEvent.KEYCODE_BACK:    //返回键
                Log.d(TAG,"back--->");

                return true;   //这里由于break会退出，所以我们自己要处理掉 不返回上一层
            case KeyEvent.KEYCODE_SETTINGS: //设置键
                Log.d(TAG,"setting--->");

                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:   //向下键

                /*    实际开发中有时候会触发两次，所以要判断一下按下时触发 ，松开按键时不触发
                 *    exp:KeyEvent.ACTION_UP
                 */
                if (event.getAction() == KeyEvent.ACTION_DOWN){

                    Log.d(TAG,"down--->");
                }
                break;
            case KeyEvent.KEYCODE_DPAD_UP:   //向上键
                Log.d(TAG,"up--->");

                break;
            case     KeyEvent.KEYCODE_0:   //数字键0
                Log.d(TAG,"0--->");

                break;
            case KeyEvent.KEYCODE_DPAD_LEFT: //向左键
                Log.d(TAG,"left--->");

                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:  //向右键
                Log.d(TAG,"right--->");
                break;
            case KeyEvent.KEYCODE_INFO:    //info键
                Log.d(TAG,"info--->");

                break;
            case KeyEvent.KEYCODE_PAGE_DOWN:     //向上翻页键
            case KeyEvent.KEYCODE_MEDIA_NEXT:
                Log.d(TAG,"page down--->");

                break;
            case KeyEvent.KEYCODE_PAGE_UP:     //向下翻页键
            case KeyEvent.KEYCODE_MEDIA_PREVIOUS:
                Log.d(TAG,"page up--->");

                break;
            case KeyEvent.KEYCODE_VOLUME_UP:   //调大声音键
                Log.d(TAG,"voice up--->");

                break;
            case KeyEvent.KEYCODE_VOLUME_DOWN: //降低声音键
                Log.d(TAG,"voice down--->");

                break;
            case KeyEvent.KEYCODE_VOLUME_MUTE: //禁用声音
                Log.d(TAG,"voice mute--->");
                break;
            break;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);

    }


    /**
     * 切换Activity
     *
     * @param c
     *            需要切换到的Activity
     */
    public void changeActivity(Class<?> c) {
        Intent intent = new Intent(this, c);
        this.startActivity(intent);
    }

    /**
     * 切换Activity
     *
     * @param c
     *            需要切换到的Activity
     * @param type
     *            类型
     */
    public void changeActivity(Class<?> c, int type) {
        Intent intent = new Intent(this, c);
        intent.putExtra("type", type);
        this.startActivity(intent);
    }

    /**
     * 显示toast提示
     *
     * @param info
     *            信息内容
     */
    public void showToast(String info) {
        Toast.makeText(this, info, Toast.LENGTH_LONG).show();
    }

    /**
     * 显示toast提示
     *
     * @param infoId
     *            信息内容id
     */
    public void showToast(int infoId) {
        Toast.makeText(this, infoId, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

    }


}
