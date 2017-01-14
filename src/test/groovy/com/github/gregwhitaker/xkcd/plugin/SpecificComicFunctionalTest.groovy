/*
 * Copyright 2017 Greg Whitaker
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.gregwhitaker.xkcd.plugin

import org.gradle.testkit.runner.GradleRunner
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

import static org.gradle.testkit.runner.TaskOutcome.SUCCESS

class SpecificComicFunctionalTest extends Specification {

    @Rule
    final TemporaryFolder testProjectDir = new TemporaryFolder()

    File buildFile

    def setup() {
        buildFile = testProjectDir.newFile('build.gradle')
    }

    def "downloads the comic with the specified id"() {
        given:
        File image = new File("${testProjectDir.root}/images/1.png")

        buildFile << """
            import com.github.gregwhitaker.xkcd.plugin.tasks.DownloadComicTask
            
            plugins {
                id 'com.github.gregwhitaker.xkcd'
            }

            task('generateComic', type: DownloadComicTask) {
                destination = file('${testProjectDir.root}/images/1.png')
                comic = 1
            }   
        """

        when:
        def result = GradleRunner.create()
                .withDebug(true)
                .withProjectDir(testProjectDir.root)
                .withArguments('generateComic')
                .withPluginClasspath()
                .build()

        then:
        image.exists()
        result.task(":generateComic").outcome == SUCCESS
    }

}
