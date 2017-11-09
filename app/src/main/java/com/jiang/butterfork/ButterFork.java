package com.jiang.butterfork;

import android.app.Activity;
import android.view.View;

import com.jiang.butterfork.annotation.Unbinder;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by knowing on 2017/11/8.
 */

public class ButterFork {


    public static void bind(Activity target) {
        Class cls = target.getClass();
        String clsName = cls.getName();
        try {

            Class<?> bindingClass = cls.getClassLoader().loadClass(clsName + "_ViewBinding");

            Constructor bindingCtor = bindingClass.getConstructor(cls);

            bindingCtor.newInstance(target);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
