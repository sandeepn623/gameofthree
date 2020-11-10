package org.liferando.restclient;

import org.liferando.model.GameRequest;
import org.liferando.model.GameResponse;
import org.liferando.service.GameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PlayGameClient {
    @Value("${game.url}")
    private String url;

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayGameClient.class);

    private final RestTemplate restTemplate;

    public PlayGameClient(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.build();
    }

    public GameResponse play(GameRequest gameRequest) {
        return restTemplate.postForObject(url, gameRequest, GameResponse.class);
    }
}
