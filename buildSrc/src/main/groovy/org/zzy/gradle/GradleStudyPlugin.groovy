package org.zzy.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

class GradleStudyPlugin implements Plugin<Project>{

    @Override
    void apply(Project project) {
        project.extensions.create('releaseInfo',ReleaseInfoExt)
    }
}