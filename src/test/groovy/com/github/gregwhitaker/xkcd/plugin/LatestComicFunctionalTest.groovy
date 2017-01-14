package com.github.gregwhitaker.xkcd.plugin

import org.gradle.testkit.runner.GradleRunner
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

import static org.gradle.testkit.runner.TaskOutcome.SUCCESS


class LatestComicFunctionalTest extends Specification {

    @Rule
    final TemporaryFolder testProjectDir = new TemporaryFolder()

    File buildFile

    def setup() {
        buildFile = testProjectDir.newFile('build.gradle')
    }

    def "retrieves latest comic metadata"() {
        given:
        buildFile << """
            import com.github.gregwhitaker.xkcd.plugin.tasks.DownloadComicTask
            
            plugins {
                id 'com.github.gregwhitaker.xkcd'
            }

            task('latestComic', type: DownloadComicTask) {
                destination = file('${testProjectDir.root}/images/latest.png')
                downloadLatest()
            }   
        """

        when:
        def result = GradleRunner.create()
                .withDebug(true)
                .withProjectDir(testProjectDir.root)
                .withArguments('latestComic')
                .withPluginClasspath()
                .build()

        then:
        result.task(":latestComic").outcome == SUCCESS
    }
}
