package com.entjava.poker.model;

import com.entjava.poker.card.Card;
import com.entjava.poker.game.Player;
import com.entjava.poker.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="games")
public class GameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Player> players = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<Player> winners = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<Player> losers = new ArrayList<>();

    public GameEntity() {}

    public GameEntity(List<Player> players, List<Player> winners, List<Player> losers) {
        this.players = players;
        this.winners = winners;
        this.losers = losers;
    }

}
