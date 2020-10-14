/*******************************************************************************
 * Copyright 2020 Marcelo Riss
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.mriss.products.mailprocessorservice.clockin.service.api;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * Hello world method to test login checker activities.
 *
 * @author riss
 */
@RestController
@RequestMapping(path = "/backend/service")
public class HelloworldController {

    Logger LOGGER = LoggerFactory.getLogger(HelloworldController.class);

    @Value("${app.version}")
    private String appVersion;

    /**
     * Simple method
     *
     * @param authorizationHeader authorization header will always be mandatory at
     *                            the format
     *                            <code>Authorization: Bearer [TOKEN_ID]</code>.
     * @param request             the servlet request, mainly to get the URL path to
     *                            return.
     * @return service version in the format TIMESTAMP:API_VERSION.
     */
    @ApiOperation(value = "A hello world POST method to ping the API. Payload in case of success is a string in the format TIMESTAMP:API_VERSION.")
    @PostMapping("/hello")
    @ResponseBody
    String helloPost(
            @ApiParam(value = "Bearer [AUTHORIZATION_TOKEN]", required = false, example = "Bearer AUTHORIZATION_TOKEN") @RequestHeader(required = false, value = "Authorization") String authorizationHeader,
            HttpServletRequest request) {
        return new StringBuffer(String.valueOf(new Date().getTime())).append(":").append(appVersion).toString();
    }

    /**
     * Simple method
     *
     * @param authorizationHeader authorization header will always be mandatory at
     *                            the format
     *                            <code>Authorization: Bearer [TOKEN_ID]</code>.
     * @param request             the servlet request, mainly to get the URL path to
     *                            return.
     * @return service version in the format TIMESTAMP:API_VERSION.
     */
    @ApiOperation(value = "A hello world GET method to ping the API. Payload in case of success is a string in the format TIMESTAMP:API_VERSION.")
    @GetMapping("/hello")
    @ResponseBody
    String helloGet(
            @ApiParam(value = "Bearer [AUTHORIZATION_TOKEN]", required = false, example = "Bearer AUTHORIZATION_TOKEN") 
            @RequestHeader(required = false, value = "Authorization") String authorizationHeader,
            HttpServletRequest request) {
        return new StringBuffer(String.valueOf(new Date().getTime())).append(":").append(appVersion).toString();
    }

}
