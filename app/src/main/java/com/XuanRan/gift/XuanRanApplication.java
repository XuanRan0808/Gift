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

import android.app.Application;
import android.os.Environment;

/**
 * Created By XuanRan on 2020/10/27
 */
public class XuanRanApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        com.XuanRan.Exception.Handing.CrashHandler.init(this, Environment.getExternalStorageDirectory().getPath()+" /XuanRan/"+"Gift"+"/Log/");
    }
}
