package com.aust.tj.plugin

import com.ctrip.ibu.autotrace.annotation.AutoTrace
import javassist.ClassPool
import javassist.CtClass
import javassist.CtMethod

import java.lang.annotation.Annotation

public class AutoInject {

    private static ClassPool pool = ClassPool.getDefault()
    private static String injectStr = "System.out.println(\"inject success\" ); ";


    static {
        pool.appendClassPath("/Users/tangjie/Downloads/MyProject/buildSrc/libs/autotracelib.jar")
    }


    public static void injectDir(String path) {
        pool.appendClassPath(path)
        File dir = new File(path)
        if (dir.isDirectory()) {
            dir.eachFileRecurse { File file ->

                String filePath = file.absolutePath
                //确保当前文件是class文件，并且不是系统自动生成的class文件
                if (filePath.endsWith(".class")
                        && !filePath.contains('R$')
                        && !filePath.contains('R.class')
                        && !filePath.contains("BuildConfig.class")) {
                    // 判断当前目录是否是在我们的应用包里面
                    int end = filePath.length() - 6 // .class = 6
                    String className = filePath.substring(filePath.indexOf("com"), end).replace('\\', '.').replace('/', '.')
                    //开始修改class文件
                    CtClass c = pool.getCtClass(className)

                    if (c.isFrozen()) {
                        c.defrost()
                    }

                    CtMethod[] methods = c.getDeclaredMethods();
                    if (methods == null || methods.length == 0) {
                        return;
                    }

                    for (CtMethod m : methods) {
                        println 'method name ' + m.getName()
                        Annotation[] annotations = m.getAnnotations();
                        if (annotations == null || annotations.length == 0) {
                            continue;
                        }

                        if (m.hasAnnotation(AutoTrace.class)){
                            println 'has auto trace method name >'+m.getName()
                            m.insertAfter(injectStr)
                        }
                    }
                    c.writeFile(path)
                    c.detach()
                }
            }
        }
    }
}