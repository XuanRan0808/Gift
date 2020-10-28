/* 一份礼物（Gift） Is an Android App
 * Copyright (C) 2019-2020 3135419729@qq.com
 * https://github.com/XuanRan0808/Gift
 *
 * This software is free software: you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this software.  If not, see
 * <https://www.gnu.org/licenses/>.
 */

package com.XuanRan.gift;


import android.app.*;
import android.content.*;
import android.net.*;
import android.os.*;
import android.provider.*;
import java.lang.reflect.*;
import cn.bmob.v3.*;
import cn.bmob.v3.listener.*;
import cn.bmob.v3.exception.*;
import android.widget.*;
import android.service.autofill.*;
import android.view.View.*;
import android.view.*;
import java.util.*;

/**
 * Created By XuanRan on 2020/10/27
 */
public class MainActivity extends Activity
{
    Button bn;
    EditText ed;
    List<BmobData> data;
    SharedPreferences shared;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bmob.initialize(this, "1e897577707894ea84103bf15e9bf32a");
        ed = findViewById(R.id.activitymainEditText1);
        bn = findViewById(R.id.activitymainButton1);
        shared = getSharedPreferences("XuanRanSettings", 0);

        BmobQuery<BmobData> query=new BmobQuery<BmobData>();

        query.getObject("lNwYUUUi", new QueryListener<BmobData>(){

            @Override
            public void done(BmobData p1, BmobException p2)
            {
                if (p2 == null)
                {
                    Toast.makeText(MainActivity.this, p1.getTest().toString(), Toast.LENGTH_LONG).show();
                }
                else
                {
                    getservicefail(p2);
                }
            }
        });


        bn.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View p1)
            {
                if (data == null)
                {
                    BmobQuery<BmobData> que=new BmobQuery<BmobData>();

                    que.findObjects(new FindListener<BmobData>(){

                        @Override
                        public void done(List<BmobData> dt, BmobException exce)
                        {

                            if (exce != null)
                            {
                                throw new RuntimeException("获取表内容失败！" + exce.getMessage());
                            }

                            data = dt;
                            Check(dt);
                        }
                    });
                }
                else
                {
                    Check(data);
                }

            }
        });
        bn.setOnLongClickListener(new OnLongClickListener(){

            @Override
            public boolean onLongClick(View p1)
            {
                ed.setText("123456");
                return false;
            }
        });
        if (!checkFloatPermission(this))
        {
            AlertDialog.Builder alert=new AlertDialog.Builder(this);
            alert.setTitle("权限已关闭");
            alert.setMessage("你已关闭应用的悬浮窗权限，应用无法正常使用，请前往应用权限设置修改。");
            alert.setCancelable(false);
            alert.setPositiveButton("确定", new DialogInterface.OnClickListener(){

                @Override
                public void onClick(DialogInterface p1, int p2)
                {
                    //前往系统设置
                    gotoPermission();
                    finish();
                }
            });

            alert.setNegativeButton("退出", new DialogInterface.OnClickListener(){

                @Override
                public void onClick(DialogInterface p1, int p2)
                {
                    finish();
                }
            });


            alert.show();
        }
        else
        {
            //启动服务
        }

    }
    /*
     *  数据检查
     *  param：ListData
     */
    public void Check(List<BmobData> dt)
    {
        boolean flag=false;
        //遍历数据
        for (int i=0;i < dt.size();i++)
        {

            if (dt.get(i).getYqmm().equals(ed.getText().toString()))
            {

                Intent intent=new Intent();
                intent.setClass(MainActivity.this, XuanRanService.class);
                intent.putExtra("Time", dt.get(i).getTime());
                intent.putExtra("text1", dt.get(i).getText1());
                intent.putExtra("text2", dt.get(i).getText2());
                intent.putExtra("text3", dt.get(i).getText3());
                //音量
                intent.putExtra("volume", dt.get(i).getVolume());
                //是否开启震动
                intent.putExtra("Vibrator", dt.get(i).isOpen_vibrator());
                startService(intent);
                flag = true;
                //跳出循环
                break;
            }
        }
        if (!flag)
        {
            Toast.makeText(MainActivity.this, "邀请码无效！", Toast.LENGTH_SHORT).show();
        }
    }


    public void getservicefail(final BmobException ex)
    {
        AlertDialog.Builder alert=new AlertDialog.Builder(this);
        alert.setTitle("服务器连接失败！");
        alert.setMessage("无法连接应用服务器！\n\n错误原因：" + ex.getMessage());
        alert.setCancelable(false);
        alert.setPositiveButton("帮助", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface p1, int p2)
            {

                Intent intent_d= new Intent();
                intent_d.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse("http://dwz.cn/QQ3135");

                intent_d.setData(content_url);
                startActivity(intent_d);

                finish();
            }
        });

        alert.setNegativeButton("退出", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface p1, int p2)
            {
                finish();
            }
        });
        alert.setNeutralButton("抛出", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface p1, int p2)
            {
                throw new RuntimeException(ex);
            }
        });

        //  alert.show();
    }


    /**
     * 判断 悬浮窗口权限是否打开
     *
     * @param context
     * @return true 允许  false禁止
     */
    private boolean checkFloatPermission(Context context)
    {
        if (Build.VERSION.SDK_INT >= 23)
        {//6.0以上
            boolean result = false;
            try
            {
                Class clazz = Settings.class;
                Method canDrawOverlays = clazz.getDeclaredMethod("canDrawOverlays", Context.class);
                result = (boolean) canDrawOverlays.invoke(null, context);
                //Log.e(TAG, "checkFloatPermission:-->" + result);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return result;
        }
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {//4.4-5.1
            return getAppOps(context);
        }
        else
        {//4.4以下
            return true;
        }
    }

    /*
     *    检查悬浮窗权限是否开启（4.4-5.1)
     *
     *
     *
     */
    private static boolean getAppOps(Context context)
    {
        try
        {
            Object object = context.getSystemService(context.APP_OPS_SERVICE);
            if (object == null)
            {
                return false;
            }
            Class localClass = object.getClass();
            Class[] arrayOfClass = new Class[3];
            arrayOfClass[0] = Integer.TYPE;
            arrayOfClass[1] = Integer.TYPE;
            arrayOfClass[2] = String.class;
            Method method = localClass.getMethod("checkOp", arrayOfClass);
            if (method == null)
            {
                return false;
            }
            Object[] arrayOfObject1 = new Object[3];
            arrayOfObject1[0] = Integer.valueOf(24);
            arrayOfObject1[1] = Integer.valueOf(Binder.getCallingUid());
            arrayOfObject1[2] = context.getPackageName();
            int m = ((Integer) method.invoke(object, arrayOfObject1)).intValue();
            return m == AppOpsManager.MODE_ALLOWED;
        }
        catch (Exception e)
        {
            // Log.e(TAG, "permissions judge: -->" + e.toString());
        }
        return false;
    }

    public void gotoPermission()
    {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        if (shared.getBoolean("Opened", false))
        {
            menu.add("生成邀请码").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){

                @Override
                public boolean onMenuItemClick(MenuItem p1)
                {
                    startActivity(new Intent(MainActivity.this, Invicode.class));
                    return false;
                }
            });
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        switch (keyCode)
        {
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                break;
            case KeyEvent.KEYCODE_VOLUME_UP:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }


}
