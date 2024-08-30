package org.example.api.modules.restful.jira.controller;

import lombok.RequiredArgsConstructor;
import org.example.api.modules.restful.jira.dto.issue.IssueDTO;
import org.example.api.modules.restful.jira.service.JiraService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/v1/jira")
@RequiredArgsConstructor
public class JiraController {

    private final JiraService jiraService;

    @GetMapping("/decrypt")
    public ResponseEntity<String> decryptValue(@RequestParam("value") String value) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException,
            InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        return ResponseEntity.ok(jiraService.decrypt(value));
    }

    @GetMapping("/encrypt")
    public ResponseEntity<String> encryptValue(@RequestParam("value") String value) throws NoSuchPaddingException, IllegalBlockSizeException,
            NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        return ResponseEntity.ok(jiraService.encrypt(value));
    }

    @GetMapping("/issue")
    public ResponseEntity<IssueDTO> getIssue() throws ExecutionException, InterruptedException, NoSuchPaddingException,
            IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        return ResponseEntity.ok(jiraService.getIssue());
    }
}
