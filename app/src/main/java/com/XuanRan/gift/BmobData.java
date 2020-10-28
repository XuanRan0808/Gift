/* 一份礼物（Gift） is an Android App
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

import cn.bmob.v3.BmobObject;

/**
 * Created By XuanRan on 2020/10/27
 */
public class BmobData extends BmobObject {

    String test;
    String yqmm;
    String time;
    //页面显示内容
    String text1, text2, text3;
    //音量
    String volume;
    //是否开启震动
    boolean open_vibrator;

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getVolume() {
        return volume;
    }

    public void setOpen_vibrator(boolean open_vibrator) {
        this.open_vibrator = open_vibrator;
    }

    public boolean isOpen_vibrator() {
        return open_vibrator;
    }


    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getText1() {

        return text1;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public String getText2() {
        return text2;
    }

    public void setText3(String text3) {
        this.text3 = text3;
    }

    public String getText3() {
        return text3;
    }


    public void setTest(String test) {
        this.test = test;
    }

    public String getTest() {
        return test;
    }

    public void setYqmm(String yqmm) {
        this.yqmm = yqmm;
    }

    public String getYqmm() {
        return yqmm;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }
}