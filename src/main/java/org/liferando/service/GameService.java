package org.liferando.service;

import lombok.Setter;
import org.liferando.domain.Game;
import org.liferando.domain.Player;
import org.liferando.domain.PlayerId;
import org.liferando.restclient.PlayGameClient;
import org.liferando.model.GameResponse;
import org.liferando.model.GameRequest;
import org.liferando.repository.GameRepository;
import org.liferando.repository.PlayerRepository;
import org.liferando.util.Constant;
import org.liferando.util.MessageProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PlayGameClient playGameClient;

    @Value("${spring.profiles.active}")
    @Setter
    private String activeProfile;

    @Value("${game.player.max.moves:5000}")
    @Setter
    private int maxMovesRange;

    @Autowired
    private MessageProperties messageProperties;

    private static final Logger LOGGER = LoggerFactory.getLogger(GameService.class);

    public GameResponse startGame(boolean autoPlay, Integer manualInput) {
        Game game =  gameRepository.findActiveGame();
        if(game == null) {
            game = new Game();
            game.setPlayerCount(1);
            Player player1 = new Player(game);
            player1.setPlayerId(PlayerId.valueOf(activeProfile).name());
            LOGGER.info("maxMovesRange: {}", maxMovesRange);
            player1.setMove(autoPlay ? new Random().nextInt(maxMovesRange) : manualInput);
            player1.setUpdatedAt(new Date());
            gameRepository.save(game);
            playerRepository.save(player1);
            //please wait for the player to join
            return GameResponse.builder().httpStatus(HttpStatus.OK).message(messageProperties.toLocale(Constant.PLAYER_WAITING))
                    .gameId(game.getId()).move(player1.getMove()).build();
        } else if(game.getPlayerCount() == 1 && !game.getPlayers().get(0).getPlayerId().equals(activeProfile)) {
            game.setPlayerCount(2);
            gameRepository.save(game);
            initiatePlay(game.getId());
            return GameResponse.builder().httpStatus(HttpStatus.OK).message(Constant.GAME_BEGINS).gameId(game.getId()).build();
        } else if(game.getPlayerCount() == 1 && game.getPlayers().get(0).getPlayerId().equals(activeProfile)){
            return GameResponse.builder().httpStatus(HttpStatus.BAD_REQUEST).message(messageProperties
                    .toLocale(Constant.PLAYER_ALREADY_WAITING)).gameId(game.getId()).build();
        }
        return new GameResponse(HttpStatus.INTERNAL_SERVER_ERROR, messageProperties.toLocale(Constant.CRITICAL_ERROR), game.getId());
    }

    @Async
    private void initiatePlay(String gameId) {
        play(gameId);
    }

    public GameResponse play(String gameId) {
        List<Player> players = playerRepository.findPlayersByGameId(gameId);

        Objects.requireNonNull(players);

        Player previousPlayer = players.get(0);
        int mostRecentPlayerMove = previousPlayer.getMove();

        //This is only needed for mapping the game id due to entity relationship
        Game game = gameRepository.findById(gameId).get();
        Player currentPlayer = new Player(game);

        int nextMove = getNextMove(mostRecentPlayerMove);
        currentPlayer.setMove(nextMove);

        currentPlayer.setGameId(previousPlayer.getGameId());
        currentPlayer.setPlayerId(getCurrentPlayerId(previousPlayer));
        playerRepository.save(currentPlayer);
        LOGGER.debug(messageProperties.toLocale(Constant.PLAYER_STATUS, currentPlayer.getPlayerId(), currentPlayer.getMove()));
        if(currentPlayer.getMove()==1) {
            LOGGER.debug(messageProperties.toLocale(Constant.PLAYER_WON, currentPlayer.getPlayerId()));
            return responseBuilder(HttpStatus.OK, messageProperties.toLocale(Constant.PLAYER_WON, currentPlayer.getPlayerId()),gameId, currentPlayer.getMove());
        } else {
            //make a new request with player info
            GameRequest gameRequest = new GameRequest(currentPlayer.getGameId());
            GameResponse response = playGameClient.play(gameRequest);
            if(response.getMove() == 1) {
                // display the winner to the other user!
                LOGGER.debug(response.getMessage());
            }
        }
        return responseBuilder(HttpStatus.OK, messageProperties.toLocale(Constant.PLAYER_STATUS, currentPlayer.getPlayerId(), currentPlayer.getMove()),
                                gameId, currentPlayer.getMove());
    }


    public int getNextMove(int previousMove) {
        if(previousMove % 3 == 0) {
            return previousMove/3;
        } else if((previousMove + 1) % 3 == 0) {
            return (previousMove+1)/3;
        }
        return (previousMove - 1)/3;
    }

    private String getCurrentPlayerId(Player previousPlayer) {
        return previousPlayer.getPlayerId().
                equals(PlayerId.player1.name()) ?
                PlayerId.player2.name() : PlayerId.player1.name();
    }

    private GameResponse responseBuilder(HttpStatus httpStatus, String message, String gameId, int move) {
        return GameResponse.builder().httpStatus(httpStatus).message(message).gameId(gameId).move(move).build();
    }
}
