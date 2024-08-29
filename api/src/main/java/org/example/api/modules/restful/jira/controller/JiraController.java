package org.example.api.modules.restful.jira.controller;

import lombok.RequiredArgsConstructor;
import org.example.api.modules.restful.jira.service.JiraService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@RestController
@RequestMapping("/api/v1/jira")
@RequiredArgsConstructor
public class JiraController {

    private final JiraService jiraService;

    @GetMapping("/token")
    public ResponseEntity<String> getJiraToken() throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException,
            InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        return ResponseEntity.ok(jiraService.getToken());
    }
}
