package com.ctrip.ibu.autotrace

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project


public class IBUPlugin implements Plugin<Project>{

    @Override
    void apply(Project project) {
        def android = project.extensions.findByType(AppExtension)
        android.registerTransform(new AutoTraceTransform(project))
    }
}