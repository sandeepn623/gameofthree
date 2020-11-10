package org.liferando.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.liferando.model.GameRequest;
import org.liferando.model.GameResponse;
import org.liferando.service.GameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/game")
public class GameOfThreeController {

    @Autowired
    private GameService service;

    private static final Logger LOGGER = LoggerFactory.getLogger(GameOfThreeController.class);

    @ApiOperation(value = "This api is used to start the game. The game begins only when there are 2 players in the game.")
    @GetMapping(path = "/start", produces = {"application/json"})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 400, message = "BAD REQUEST"),
                            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR")})
    public GameResponse startGame(@RequestParam(value = "auto")
                                      @ApiParam(value = "set this to automatically choose a random number and start the game")
                                              boolean autoplay, @RequestParam(required = false, value = "manual")
                                                    @ApiParam(value = "set this to manually choose a number and start the game")
                                                        Optional<Integer> manualInput) {
        LOGGER.debug("Start the game!");
        return service.startGame(autoplay, manualInput.orElse(null));
    }

    @ApiOperation(value = "player to use this end point to communicate with his move.")
    @PostMapping("/play")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK.")})
    public GameResponse play(@RequestBody GameRequest gameRequest) {
        LOGGER.debug("Playing the game!");
        return service.play(gameRequest.getGameId());
    }
}
