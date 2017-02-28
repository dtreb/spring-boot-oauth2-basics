package com.dtreb.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Simple heartbeat endpoint.
 *
 * @author dtreb
 */
@RestController
@RequestMapping("/ping")
public class PingController {

    @RequestMapping
    public String ping() {
        return "pong";
    }
}
