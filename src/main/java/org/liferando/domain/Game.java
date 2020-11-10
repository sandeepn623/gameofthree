package org.liferando.domain;

import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.jdo.annotations.PrimaryKey;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "game")
public class Game {
    @Id
    @PrimaryKey
    @NotNull
    @Column(name = "id")
    private String id;

    @Column(name="player_count")
    private int playerCount;

    @OneToMany(mappedBy = "game", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @NotFound(action = NotFoundAction.IGNORE)
    private List<Player> players;

    public Game() {
        this.id = UUID.randomUUID().toString();
    }
}
