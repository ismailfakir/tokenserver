package net.cloudcentrik.autolink.tokenserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ServerInfoController {

    @GetMapping("/serverinfo")
    public Map<String, String> serverInfo() {
        LocalDateTime timeNow = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        HashMap<String, String> response = new HashMap<>();
        response.put("serverName", "tokenserver");
        response.put("commit", "commit");
        response.put("time", dateTimeFormatter.format(timeNow));
        return response;

    }

}