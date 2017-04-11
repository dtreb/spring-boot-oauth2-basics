package com.dtreb.controller;

import com.codahale.metrics.annotation.Timed;
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
    @Timed
    public String ping() {
        return "pong";
    }
}
