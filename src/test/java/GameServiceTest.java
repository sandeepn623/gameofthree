import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.liferando.domain.Game;
import org.liferando.domain.Player;
import org.liferando.model.GameResponse;
import org.liferando.repository.GameRepository;
import org.liferando.repository.PlayerRepository;
import org.liferando.service.GameService;
import org.liferando.util.MessageProperties;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class GameServiceTest {
    @Mock
    private GameRepository gameRepo;

    @Mock
    private PlayerRepository playerRepo;

    @Mock
    private MessageProperties messageProperties;

    @InjectMocks
    private GameService service;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void test_start_auto_game_single_player_joins_ok() {
        service.setActiveProfile("player1");
        service.setMaxMovesRange(5000);

        Game activeGame = getSinglePlayerInGame();
        when(gameRepo.findActiveGame()).thenReturn(null);
        Optional<Game> optionalActiveGame = Optional.of(activeGame);
        when(gameRepo.findById(any())).thenReturn(optionalActiveGame);

        when(messageProperties.toLocale(any()))
                .thenReturn("Please wait for the other player to join!");

        GameResponse gameResponse = service.startGame(true ,null);
        assertEquals(HttpStatus.OK.value(), gameResponse.getHttpStatus().value());
    }

    @Test
    public void test_start_auto_game_when_another_player_joins_ok() {
        service.setActiveProfile("player2");
        service.setMaxMovesRange(5000);

        Game activeGame = getSinglePlayerInGame();
        when(gameRepo.findActiveGame()).thenReturn(null);
        Optional<Game> optionalActiveGame = Optional.of(activeGame);
        when(gameRepo.findById(any())).thenReturn(optionalActiveGame);
        when(messageProperties.toLocale(any())).thenReturn("The game begins now!");
        GameResponse gameResponse = service.startGame(true ,null);
        assertEquals(HttpStatus.OK.value(), gameResponse.getHttpStatus().value());
    }

    @Test
    public void test_start_auto_game_when_single_player_joins_again_not_allowed() {
        service.setActiveProfile("player2");
        service.setMaxMovesRange(5000);

        Game activeGame = getSinglePlayerInGame();
        when(gameRepo.findActiveGame()).thenReturn(null);
        Optional<Game> optionalActiveGame = Optional.of(activeGame);
        when(gameRepo.findById(any())).thenReturn(optionalActiveGame);
        when(messageProperties.toLocale(any())).thenReturn("The game begins now!");
        GameResponse gameResponse = service.startGame(true ,null);
        assertEquals(HttpStatus.OK.value(), gameResponse.getHttpStatus().value());
    }

    @Test
    public void test_start_auto_game_when_more_than_two_player_joins_not_allowed() {
        service.setActiveProfile("player3");
        service.setMaxMovesRange(5000);

        Game activeGame = getTwoPlayerInGame();
        when(gameRepo.findActiveGame()).thenReturn(activeGame);
        Optional<Game> optionalActiveGame = Optional.of(activeGame);

        when(messageProperties.toLocale(any())).thenReturn("");
        GameResponse gameResponse = service.startGame(true ,null);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), gameResponse.getHttpStatus().value());
    }

    @Test
    public void test_start_manual_game_single_player_joins_ok() {
        service.setActiveProfile("player1");
        service.setMaxMovesRange(5000);

        Game activeGame = getSinglePlayerInGame();
        when(gameRepo.findActiveGame()).thenReturn(null);
        Optional<Game> optionalActiveGame = Optional.of(activeGame);
        when(gameRepo.findById(any())).thenReturn(optionalActiveGame);

        when(messageProperties.toLocale(any()))
                .thenReturn("Please wait for the other player to join!");

        GameResponse gameResponse = service.startGame(false ,2000);
        assertEquals(Integer.valueOf(2000), Integer.valueOf(gameResponse.getMove()));
        assertEquals(HttpStatus.OK.value(), gameResponse.getHttpStatus().value());
    }

    @Test
    public void test_play_sequence_win() {
        int nextMove = 3000;
        while(nextMove != 1) {
            nextMove = service.getNextMove(nextMove);
        }
        assertEquals(Integer.valueOf(1), Integer.valueOf(nextMove));
    }

    private Player getPlayer1() {
        Player player1 = new Player();
        player1.setPlayerId("player1");
        player1.setMove(2000);
        player1.setGameId("123455");
        player1.setId("121242");
        return player1;
    }

    private Player getPlayer2() {
        Player player2 = new Player();
        player2.setPlayerId("player2");
        player2.setMove(2000);
        player2.setGameId("123455");
        player2.setId("121312");
        return player2;
    }

    private Game getSinglePlayerInGame(){
        List<Player> playerList = new ArrayList<>();
        Game singlePlayerInGame = new Game();
        Player player1 = getPlayer1();
        playerList.add(player1);
        singlePlayerInGame.setPlayerCount(1);
        singlePlayerInGame.setPlayers(playerList);
        return singlePlayerInGame;
    }

    private Game getTwoPlayerInGame(){
        List<Player> playerList = new ArrayList<>();
        Game twoPlayerInGame = new Game();
        Player player1 = getPlayer1();
        Player player2 = getPlayer2();
        playerList.add(player1);
        playerList.add(player2);
        twoPlayerInGame.setPlayerCount(2);
        twoPlayerInGame.setPlayers(playerList);
        return twoPlayerInGame;
    }
}
