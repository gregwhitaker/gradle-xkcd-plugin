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

import com.github.gregwhitaker.xkcd.plugin.tasks.model.Metadata
import groovy.json.JsonSlurper
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.ParallelizableTask
import org.gradle.api.tasks.TaskAction

@ParallelizableTask
@CacheableTask
class ComicTask extends DefaultTask {

    @OutputFile
    File destFile

    private HTTPBuilder http = new HTTPBuilder('http://xkcd.com')

    @TaskAction
    void run() {
        def metadata = getLatestMetadata()
        boolean test = true
    }

    Metadata getLatestMetadata() {
        return http.request(Method.GET, ContentType.JSON) {
            uri.path = '/info.0.json'

            response.success = { resp, json ->
                def test = JsonSlurper.parseText(json)
                json.responseData.results.each {
                    println "  ${it.titleNoFormatting} : ${it.visibleUrl}"
                }
            }
        }
    }

    Metadata getMetadata(String id) {
        return http.request(Method.GET, ContentType.JSON) {
            uri.path = "/$id/info.0.json"

            response.success = { resp, json ->
                def test = JsonSlurper.parseText(json)
                json.responseData.results.each {
                    println "  ${it.titleNoFormatting} : ${it.visibleUrl}"
                    json = ""
                }
            }
        }
    }
}
