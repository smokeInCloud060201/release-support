package org.example.api.modules.restful.jira.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.api.common.config.WebClientConfiguration;
import org.example.api.common.config.properties.JiraProperty;
import org.example.api.common.config.properties.KeyStoreProperty;
import org.example.api.common.dto.request.QueryParam;
import org.example.api.common.dto.request.URLComponent;
import org.example.api.common.enums.WebClientType;
import org.example.api.common.util.EncryptUtil;
import org.example.api.modules.restful.jira.dto.issue.IssueDTO;
import org.example.api.modules.restful.jira.service.JiraService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
@RequiredArgsConstructor
public class JiraImpl implements JiraService {

    private final WebClientConfiguration webClientConfiguration;
    private final EncryptUtil encryptUtil;
    private final KeyStoreProperty keyStoreProperty;
    private final JiraProperty jiraProperty;

    @Override
    public IssueDTO getIssue() throws ExecutionException, InterruptedException, NoSuchPaddingException, IllegalBlockSizeException,
            NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {

        URLComponent urlComponent = URLComponent.builder()
                .host(decryptValue(jiraProperty.getProject().getDomain()))
                .path("/rest/api/2/search")
                .queryParam(QueryParam.builder()
                        .key("jql")
                        .value("fixVersion=v1.5.0")
                        .build())
                .queryParam(QueryParam.builder()
                        .key("maxResults")
                        .value("100")
                        .build())
                .build();
        IssueDTO result = webClientConfiguration.getRequest(urlComponent, new ParameterizedTypeReference<IssueDTO>() {
        }, WebClientType.JIRA).get();
        return result;
    }

    @Override
    public String encrypt(String value) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException,
            IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        PublicKey publicKey = encryptUtil.loadPublicKey(keyStoreProperty.getPk());
        return encryptUtil.encrypt(value, publicKey);
    }

    @Override
    public String decrypt(String value) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException,
            IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        return decryptValue(value);
    }

    private String decryptValue(String value) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException,
            IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        PrivateKey privateKey = encryptUtil.loadPrivateKey(keyStoreProperty.getPvk());
        return encryptUtil.decrypt(value, privateKey);
    }
}
