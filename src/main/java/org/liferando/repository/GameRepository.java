package org.liferando.repository;

import org.liferando.domain.Game;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends CrudRepository<Game, String> {
    @Query("SELECT g from Game g WHERE g.playerCount<2")
    Game findActiveGame();
}
