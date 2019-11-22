package com.star.starwebbrowser.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;
import com.star.starwebbrowser.utils.ActivityStack;

public class SuperActivity extends Activity implements View.OnClickListener {

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

    public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent) {
        if (paramInt == 4)
            new AlertDialog.Builder(this)
                    .setTitle("确认")
                    .setMessage("是否退出该操作？")
                    .setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        DialogInterface paramAnonymousDialogInterface,
                                        int paramAnonymousInt) {
                                    System.exit(0);
                                }
                            })
                    .setNegativeButton("取消",
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        DialogInterface paramAnonymousDialogInterface,
                                        int paramAnonymousInt) {
                                }
                            }).create().show();
        return super.onKeyDown(paramInt, paramKeyEvent);
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
