package com.mriss.products.mailprocessorservice.clockin.service.api;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

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
        return new StringBuffer(String.valueOf(new Date().getTime())).append(appVersion).toString();
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
