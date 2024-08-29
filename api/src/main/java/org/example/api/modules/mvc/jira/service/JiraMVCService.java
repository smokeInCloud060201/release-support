package org.example.api.modules.mvc.jira.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class JiraMVCService implements CommandLineRunner {

    public void getReleasePage() throws IOException {
        Document document = Jsoup.connect("https://sp-digital.atlassian.net/projects/GSH?selectedItem=com.atlassian.jira.jira-projects-plugin%3Arelease-page").get();

       System.out.println(document);
    }

    @Override
    public void run(String... args) throws Exception {
        getReleasePage();
    }
}
