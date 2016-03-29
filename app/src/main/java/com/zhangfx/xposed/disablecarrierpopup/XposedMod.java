package com.zhangfx.xposed.disablecarrierpopup;

import android.content.Intent;
import android.os.SystemClock;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class XposedMod implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        if (!loadPackageParam.packageName.equals("com.android.stk")) {
            return;
        }

        XposedHelpers.findAndHookMethod("com.android.stk.StkDialogActivity", loadPackageParam.classLoader, "initFromIntent", Intent.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                if (SystemClock.elapsedRealtime() > 1000 * 60 * 5) {
                    XposedBridge.log(loadPackageParam.packageName + ": dialog enabled");
                } else {
                    XposedBridge.log(loadPackageParam.packageName + ": dialog disabled");

                    param.args[0] = null;
                }
            }
        });
    }
}
