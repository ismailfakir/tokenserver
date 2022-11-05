package net.cloudcentrik.autolink.tokenserver.controller;

import org.springframework.beans.factory.annotation.Value;
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

    @Value("${git.commit.message.short}")
    private String commitMessage;

    @Value("${git.branch}")
    private String branch;

    @Value("${git.commit.id.abbrev}")
    private String commitId;

    @Value("${git.build.version}")
    private String buildVersion;

    @GetMapping("/serverinfo")
    public Map<String, String> serverInfo() {
        LocalDateTime timeNow = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        HashMap<String, String> response = new HashMap<>();
        //response.put("serverName", "tokenserver");
        response.put("CommitId", commitId);
        response.put("CommitMessage", commitMessage);
        response.put("branch", branch);
        response.put("version", buildVersion);
        //response.put("time", dateTimeFormatter.format(timeNow));
        return response;

    }

}