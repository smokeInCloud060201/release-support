package org.example.api.modules.restful.jira.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.api.common.config.properties.JiraProperty;
import org.example.api.common.config.properties.KeyStoreProperty;
import org.example.api.common.util.EncryptUtil;
import org.example.api.modules.restful.jira.service.JiraService;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;

@Service
@Slf4j
@RequiredArgsConstructor
public class JiraImpl implements JiraService {

    private final EncryptUtil encryptUtil;
    private final JiraProperty jiraProperty;
    private final KeyStoreProperty keyStoreProperty;

    @Override
    public String getToken() throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException,
            IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        PrivateKey privateKey = encryptUtil.loadPrivateKey(keyStoreProperty.getPvk());
        return encryptUtil.decrypt(jiraProperty.getToken(), privateKey);
    }
}
