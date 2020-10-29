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
import android.os.*;
import android.widget.*;
import android.widget.SeekBar.*;
import android.view.*;

import cn.bmob.v3.listener.*;
import cn.bmob.v3.exception.*;

/**
 * Created by XuanRan on 2020/10/27
 */
public class Invicode extends Activity {
    EditText time, volume, yqmm, text1, text2, text3;
    SeekBar timeseekbar, volume_seekbar;
    Button bn;
    CheckBox ck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nvicode);

        setTitle("生成邀请码");
        init();
        addlistliner();
    }

    private void addlistliner() {
        timeseekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar p1, int p2, boolean p3) {
                time.setText(String.valueOf(p2));
            }

            @Override
            public void onStartTrackingTouch(SeekBar p1) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar p1) {
            }
        });
        volume_seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar p1, int p2, boolean p3) {
                volume.setText(String.valueOf(p2));
            }

            @Override
            public void onStartTrackingTouch(SeekBar p1) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar p1) {
            }
        });
        bn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View p1) {
                if (yqmm.getText().toString().trim().length() > 3 && Integer.valueOf(time.getText().toString()) < 60 && Integer.valueOf(volume.getText().toString()) < 100) {
                    BmobData bm = new BmobData();
                    bm.setYqmm(yqmm.getText().toString());
                    bm.setTime(time.getText().toString());
                    bm.setVolume(volume.getText().toString());
                    bm.setText1(text1.getText().toString());
                    bm.setText2(text2.getText().toString());
                    bm.setText3(text3.getText().toString());
                    bm.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e == null) {
                                Toast.makeText(Invicode.this, "保存成功", Toast.LENGTH_LONG).show();
                            } else {
                                AlertDialog.Builder al = new AlertDialog.Builder(Invicode.this);
                                al.setTitle("数据加载失败");
                                al.setMessage("数据加载失败，错误原因：" + e.getMessage());
                                al.setCancelable(false);
                                al.setPositiveButton("确定", null);
                                al.show();
                            }
                        }

                    });
                } else {
                    AlertDialog.Builder al = new AlertDialog.Builder(Invicode.this);
                    al.setTitle("数据错误");
                    al.setMessage("你填写的内容未通过系统审核，请检查是否违反以下规则。" + "\n\n" + "邀请码必须大于3位字符\n锁定时间必须小于60秒\n音量设置必须小于100");
                    al.setCancelable(false);
                    al.setPositiveButton("确定", null);
                    al.show();

                }
            }
        });

    }

    private void init() {
        time = findViewById(R.id.time);
        volume = findViewById(R.id.volume);
        yqmm = findViewById(R.id.yymm);
        text1 = findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);
        text3 = findViewById(R.id.text3);
        timeseekbar = findViewById(R.id.timeSeekBar);
        volume_seekbar = findViewById(R.id.volume_seekbar);
        bn = findViewById(R.id.Generate);
        ck = findViewById(R.id.InvicodeCheckBox1);
        ck.setEnabled(false);
        timeseekbar.setMax(60);
        volume_seekbar.setMax(15);
    }

}
