package org.liferando.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@Table(name = "player")
public class Player {

    @Id
    @NotNull
    @Column(name = "id")
    private String id;

    @Column(name = "player_id")
    private String playerId;

    @Column(name = "game_id", insertable = false, updatable = false)
    private String gameId;

    @Column(name = "move")
    private Integer move;

    @Column(name = "updated_at")
    private Date updatedAt = new Date();

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="game_id")
    private Game game;

    public Player() {
        this.id = UUID.randomUUID().toString();
    }

    public Player(Game game) {
        this();
        this.game = game;
    }
}
