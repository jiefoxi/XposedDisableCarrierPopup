package com.zhangfx.xposed.disablecarrierpopup;

import android.content.Intent;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

public class XposedMod implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        if (!loadPackageParam.packageName.contains("com.android.stk")) {
            return;
        }

        findAndHookMethod("com.android.stk.StkDialogActivity", loadPackageParam.classLoader, "initFromIntent", Intent.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                param.args[0] = null;
            }
        });
    }
}
