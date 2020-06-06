package ru.softmachine.odyssey.backend.app.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AppVersionController {

    @Value("${version:undefined}")
    private String appVersion;

    @GetMapping(value = "/api/version")
    public Version getAppVersion() {
        return new Version(appVersion);
    }

    @Data
    public static class Version {
        private final String value;
    }
}
