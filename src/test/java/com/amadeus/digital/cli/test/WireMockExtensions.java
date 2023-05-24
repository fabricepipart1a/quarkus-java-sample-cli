package com.amadeus.digital.cli.test;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

import java.util.HashMap;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

public class WireMockExtensions implements QuarkusTestResourceLifecycleManager {

  private WireMockServer wireMockServer;

  @Override
  public Map<String, String> start() {
    wireMockServer = new WireMockServer(8089);
    wireMockServer.start();
    configureFor(8089);

    stubBranches();
    stubCommits();

    Map<String, String> config = new HashMap<>();
    config.put("quarkus.rest-client.gh.url", wireMockServer.baseUrl());

    return config;
  }

  private void stubBranches() {
    stubFor(get(urlEqualTo("/fabricepipart1a/kubernetes-plugin/branches"))
            .willReturn(aResponse()
                    .withHeader("Content-Type", "application/json")
                    .withBodyFile("branches.json")));
    stubFor(get(urlEqualTo("/fp/repo/branches"))
            .willReturn(aResponse()
                    .withHeader("Content-Type", "application/json")
                    .withBodyFile("branches2.json")));
    stubFor(get(urlEqualTo("/fp/empty/branches"))
            .willReturn(aResponse()
                    .withHeader("Content-Type", "application/json")
                    .withBodyFile("empty.json")));
  }

  private void stubCommits() {
    stubFor(get(urlEqualTo("/fabricepipart1a/kubernetes-plugin/commits"))
            .willReturn(aResponse()
                    .withHeader("Content-Type", "application/json")
                    .withBodyFile("commits.json")));
    stubFor(get(urlEqualTo("/fp/repo/commits"))
            .willReturn(aResponse()
                    .withHeader("Content-Type", "application/json")
                    .withBodyFile("commits2.json")));
    stubFor(get(urlEqualTo("/fp/empty/commits"))
            .willReturn(aResponse()
                    .withHeader("Content-Type", "application/json")
                    .withBodyFile("empty.json")));
  }

  @Override
  public void stop() {
    if (null != wireMockServer) {
      wireMockServer.stop();
    }
  }
}