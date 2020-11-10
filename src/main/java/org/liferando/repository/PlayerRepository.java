package org.liferando.repository;

import org.liferando.domain.Player;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository  extends CrudRepository<Player, String> {
    @Query("SELECT p from Player p WHERE p.gameId=:gameId ORDER BY p.updatedAt DESC")
    List<Player> findPlayersByGameId(@Param("gameId")String gameId);
}
