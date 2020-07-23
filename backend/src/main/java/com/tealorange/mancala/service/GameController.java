package com.tealorange.mancala.service;

import com.tealorange.mancala.model.GameRound;
import com.tealorange.mancala.model.MancalaGame;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RestController
@RequestMapping("/api")
public class GameController {

    private GameService service;

    public GameController(GameService aService) {
        service = aService;
    }

    @GetMapping("/games")
    public List<Integer> getGames() {
        return service.getGames().stream().map(MancalaGame::getId).collect(Collectors.toList());
    }

    @GetMapping("/newgame")
    public GameRound newGame() {
        return service.createGame();
    }

    @GetMapping("/lastgame")
    public GameRound lastgame() {
        return service.getLastGame().newRound();
    }

    @GetMapping("/game/{game}")
    public GameRound loadGame(@PathVariable("game") int aGameId) {
        return service.loadGame(aGameId);
    }

    @GetMapping("/game/{game}/emptypit/{pit}")
    public GameRound emptyPit(@PathVariable("game") int aGameId, @PathVariable("pit") String aPitId) {
        MancalaGame game = service.getGame(aGameId);
        if (game == null) {
            //FIXME
        }
        return game.emptyPit(aPitId);
    }
}