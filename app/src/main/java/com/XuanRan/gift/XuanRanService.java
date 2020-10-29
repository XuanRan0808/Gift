package com.XuanRan.gift;

import android.app.*;
import android.bluetooth.*;
import android.content.*;
import android.graphics.*;
import android.media.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.view.WindowManager.*;
import android.widget.*;
import java.util.*;

/**
 *
 *    Created by XuanRan on 2020/10/27
 *
 */
public class XuanRanService extends  Service
{
    private WindowManager.LayoutParams wmParams;
    private WindowManager mWindowManager;
    private View mFloatLayout;
    String open_time;
    //音量
    int volume;
    //震动是否开启
    boolean vibrator;
    String text[];
    Button testbn;
    TextView timeview;
    TextView text1,text2,text3;
    int time;
    AudioManager audiomanager;

    @Override
    public IBinder onBind(Intent p1)
    {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {

        if (intent != null)
        {

            open_time = intent.getStringExtra(("Time"));//时间
            volume = Integer.valueOf(intent.getStringExtra("volume"));
            //   volume = Integer.valueOf(intent.getStringExtra("volume"));
            // vibrator = intent.getBooleanExtra("Vibrator", false);
            text = new String[3];
            for (int i=0;i < 3;i++)
            {
                text[i] = intent.getStringExtra("text" + i);
            }
        }
        else
        {
            throw new RuntimeException("XuanRanService Intent空指针异常");
        }
        //由于服务器不支持int等基本类型数据，需将String强制转换为int
        time = Integer.valueOf(open_time);
        try
        {
            Open_Music();
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
        //检查耳机连接
        AudioManager localAudioManager = (AudioManager)this.getSystemService(Context.AUDIO_SERVICE);

        boolean isHeadSetOn = localAudioManager.isWiredHeadsetOn();

        if (isHeadSetOn)
        {
            //耳机已连接，设置volume为6

        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
        {
            audiomanager.setMode(AudioManager.MODE_IN_COMMUNICATION);
        }
        else
        {
            audiomanager.setMode(AudioManager.MODE_IN_CALL);
        }
        audiomanager.setBluetoothScoOn(false);
        audiomanager.setSpeakerphoneOn(true);           //默认为扬声器播放


        //设置系统音量
        setVolume(volume);

        Show();

        return super.onStartCommand(intent, flags, startId);
    }

    private void setVolume(int vo)
    {
        audiomanager.setStreamVolume(AudioManager.STREAM_MUSIC, vo, 0);
    }

    private void open_vibrator()
    {
        Vibrator b;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        audiomanager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
    }

    private void Open_Music() throws Throwable
    {
        MediaPlayer mp = MediaPlayer.create(this, R.raw.a);
        mp.prepare();
        mp.setLooping(true);
        mp.start();
    }

    private void Show()
    {
        /*
         lp.type = 2010;
         r0_service.lp.flags = 1280;
         r0_service.lp.format = 1;
         r0_service.lp.width = -1;
         r0_service.lp.height = -1;
         r0_service.lp.gravity = 17;
         */
        wmParams = new WindowManager.LayoutParams();
        //获取的是WindowManagerImpl.CompatModeWrapper
        mWindowManager = (WindowManager)getApplication().getSystemService(getApplication().WINDOW_SERVICE);
        //Log.i(TAG, "mWindowManager--->"+ mWindowManager);
        //设置window type
        wmParams.type = LayoutParams. TYPE_SYSTEM_ERROR;
        // 该类型提供与用户交互，置于所有应用程序上方，但是在状态栏后面
        // TYPE_TOAST TYPE_SYSTEM_OVERLAY 在其他应用上层 在通知栏下层 位置不能动鸟
        // TYPE_PHONE 在其他应用上层 在通知栏下层
        // TYPE_PRIORITY_PHONE TYPE_SYSTEM_ALERT 在其他应用上层 在通知栏上层 没试出来区别是啥
        // TYPE_SYSTEM_ERROR 最顶层(通过对比360和天天动听歌词得出)
        //
        //设置图片格式，效果为背景透明
        wmParams.format = PixelFormat.RGBA_8888;

        //wmParams.format=1;
        //设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作）
        //实现悬浮窗到状态栏
        //wmParams.flags = LayoutParams.FLAG_LOCAL_FOCUS_MODE | LayoutParams.FLAG_FULLSCREEN | LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        wmParams.flags = 1280;
        //LayoutParams.FLAG_NOT_FOCUSABLE     //少此代码屏幕无法获取下层东西

        //LayoutParams.TYPE_SYSTEM_ERROR    |

        //LayoutParams.FLAG_FULLSCREEN| LayoutParams.FLAG_LAYOUT_IN_SCREEN
        //调整悬浮窗显示的停靠位置为左侧置顶
        wmParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.TOP;
        // 以屏幕左上角为原点，设置x、y初始值，相对于gravity
        wmParams.x = 0;
        wmParams.y = 0;
        //设置悬浮窗口长宽数据
        wmParams.width = WindowManager.LayoutParams.FILL_PARENT;
        wmParams.height = WindowManager.LayoutParams.FILL_PARENT;
        //wmParams.height=500;
        // 设置悬浮窗口长宽数据
        //wmParams.width = 200;
        //wmParams.height = 80;
        LayoutInflater inflater = LayoutInflater.from(getApplication());
        //获取浮动窗口视图所在布局
        mFloatLayout = inflater.inflate(R.layout.newone, null);

        //添加mFloatLayout
        mWindowManager.addView(mFloatLayout, wmParams);
        //初始化控件对象
        testbn = mFloatLayout.findViewById(R.id.newoneButton1);
        timeview = mFloatLayout.findViewById(R.id.time);
        text1 = mFloatLayout.findViewById(R.id.text1);
        text2 = mFloatLayout.findViewById(R.id.text2);
        text3 = mFloatLayout.findViewById(R.id.text3);
        text1.setText(text[0]);
        text2.setText(text[1]);
        text3.setText(text[2]);
        testbn.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(android.view.View p1)
            {
                mWindowManager.removeView(mFloatLayout);
                stopSelf();
            }
        });

        time1();
    }

    private  void time1()
    {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run()
            {
                time--;
                //每过一秒调整一次音量
                setVolume(volume);
                //换回主线程
                new Handler(XuanRanService.this.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run()
                    {
                        timeview.setText("剩余时间：" + time + "秒");
                    }
                });

                if (time <= 0)
                {
                    SharedPreferences sh=getSharedPreferences("XuanRanSettings", 0);
                    SharedPreferences.Editor ed=sh.edit();
                    ed.putBoolean("Opened", true);
                    ed.commit();
                    int id=android.os.Process.myPid();
                    android.os.Process.killProcess(id);
                    //子线程不能进行ui更新
                    //mWindowManager.removeView(mFloatLayout);
                    stopSelf();
                }

            }
        }, 0, 1000);

    }







}

