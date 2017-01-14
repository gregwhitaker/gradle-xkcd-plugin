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

package com.github.gregwhitaker.xkcd.plugin.tasks

import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.ParallelizableTask
import org.gradle.api.tasks.TaskAction

@ParallelizableTask
@CacheableTask
class DownloadComicTask extends DefaultTask {

    private http = new HTTPBuilder('http://xkcd.com')

    @Input
    @Optional
    Integer comic

    @OutputFile
    File destination

    private boolean latest

    private boolean random

    @TaskAction
    void run() {
        // Verify Inputs
        failIfNoImageSpecified()
        failIfComicIdAndLatestOrRandomIsSpecified()

        if (comic != null) {
            downloadImage(imageUrl(comic))
        } else if (latest) {
            downloadImage(latestImageUrl())
        } else if (random) {
            downloadImage(randomUrl())
        }
    }

    void downloadLatest() {
        this.latest = true
        this.random = false
    }

    void downloadRandom() {
        this.random = true
        this.latest = false
    }

    private String latestImageUrl() {
        return http.request(Method.GET, ContentType.JSON) {
            uri.path = '/info.0.json'

            response.success = { resp, json ->
                json.img
            }
        }
    }

    private String randomUrl() {
        def latestNum = http.request(Method.GET, ContentType.JSON) {
            uri.path = '/info.0.json'

            response.success = { resp, json ->
                json.num
            }
        }

        return imageUrl(latestNum)
    }

    private String imageUrl(Integer id) {
        return http.request(Method.GET, ContentType.JSON) {
            uri.path = "${id}/info.0.json"

            response.success = { resp, json ->
                json.img
            }
        }
    }

    private void downloadImage(String url) {
        def fout = destination.newOutputStream()
        fout << new URL(url).openStream()
        fout.close()
    }

    private void failIfNoImageSpecified() {

    }

    private void failIfComicIdAndLatestOrRandomIsSpecified() {

    }

}
