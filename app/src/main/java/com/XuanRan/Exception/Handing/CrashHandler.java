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


package com.XuanRan.Exception.Handing;

import android.app.*;
import android.content.*;
import android.content.pm.*;
import android.content.res.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import java.io.*;
import java.text.*;
import java.util.*;


public class CrashHandler
{

    private static String FORMAT="%1$s (%2$d)";
    public static void init(final Application app,final String SavePath)
	{
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler(){

				@Override
				public void uncaughtException(Thread thread, Throwable throwable)
				{
					PackageInfo packageInfo=null;
                    try
					{
                        packageInfo = app.getPackageManager().getPackageInfo(app.getPackageName(), 0);
                    }
					catch (PackageManager.NameNotFoundException e)
					{}

                    StringBuffer sb=new StringBuffer();
                    sb.append("BRAND=" + Build.BRAND);
                    sb.append("\nMODEL=" + Build.MODEL);
                    sb.append("\nSDK Level=" + String.format(FORMAT, Build.VERSION.RELEASE, Build.VERSION.SDK_INT));

                    if (packageInfo != null)
					{
                        sb.append("\nVersion=" + String.format(FORMAT, packageInfo.versionName, packageInfo.versionCode));
                    }

                    sb.append("\n\n" + getStackTrace(throwable));

                    app.startActivity(new Intent(app, ExceptionActivity.class)
				                      .putExtra("Error_Log_Path",SavePath)
                                      .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                      .putExtra(Intent.EXTRA_TEXT, sb.toString()));

                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(1);
					

				}


			});}
	public static String getStackTrace(Throwable throwable)
	{
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        return sw.toString();
    }
    
}
