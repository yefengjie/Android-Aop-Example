/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package org.android10.gintonic.aspect;

import android.util.Log;
import android.view.View;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * Aspect representing the cross cutting-concern: Method and Constructor Tracing.
 */
@Aspect
public class TraceAspect {

//    private static final String POINTCUT_METHOD =
//            "execution(* *.*(..))";

    //    private static final String POINTCUT_METHOD =
//            "execution(public void org.android10.viewgroupperformance.activity.MainActivity.test(..))";
    private static final String POINTCUT_METHOD =
            "execution(public void android.view.View.OnClickListener.onClick(..))";


    private static final String POINTCUT_CONSTRUCTOR =
            "execution(@org.android10.gintonic.annotation.DebugTrace *.new(..))";

    @Pointcut(POINTCUT_METHOD)
    public void methodAnnotatedWithDebugTrace() {
    }

    @Pointcut(POINTCUT_CONSTRUCTOR)
    public void constructorAnnotatedDebugTrace() {
    }

    @Around("methodAnnotatedWithDebugTrace() || constructorAnnotatedDebugTrace()")
    public Object weaveJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();
        methodSignature.getParameterNames();
        methodSignature.getParameterTypes();
        methodSignature.getDeclaringType().getSimpleName();
        methodSignature.getDeclaringTypeName();
        methodSignature.getExceptionTypes();
        methodSignature.getName();
        Log.e("do it", "do it before");
        Log.e("do it", "" + className + " _" + methodName);
//        final StopWatch stopWatch = new StopWatch();
//        stopWatch.start();
        Object result = joinPoint.proceed();
        Log.e("do it", "do it after");
//        stopWatch.stop();
//
//        DebugLog.log(className, buildLogMessage(methodName, stopWatch.getTotalTimeMillis()));
        Object[] args = joinPoint.getArgs();
        if (null != args && args.length > 0) {
            View view = (View) args[0];
            Log.e("do it", "view id :" + view.getId());
            Log.e("do it", "" + args.length);
            Log.e("do it", "" + view.getContext().getResources().getResourceEntryName(view.getId()));
            Log.e("do it", "" + view.getContext().getResources().getResourceName(view.getId()));
            Log.e("do it", "" + view.getContext().getResources().getResourcePackageName(view.getId()));
            Log.e("do it", "" + view.getContext().getResources().getResourceTypeName(view.getId()));
        }
        return result;
    }

    @Before("methodAnnotatedWithDebugTrace() || constructorAnnotatedDebugTrace()")
    public void before(ProceedingJoinPoint joinPoint) {
        Log.e("do it", "before");
    }

    @After("methodAnnotatedWithDebugTrace() || constructorAnnotatedDebugTrace()")
    public void after(ProceedingJoinPoint joinPoint) {
        Log.e("do it", "after");
    }

    /**
     * Create a log message.
     *
     * @param methodName     A string with the method name.
     * @param methodDuration Duration of the method in milliseconds.
     * @return A string representing message.
     */
    private static String buildLogMessage(String methodName, long methodDuration) {
        StringBuilder message = new StringBuilder();
        message.append("Gintonic --> ");
        message.append(methodName);
        message.append(" --> ");
        message.append("[");
        message.append(methodDuration);
        message.append("ms");
        message.append("]");

        return message.toString();
    }
}
