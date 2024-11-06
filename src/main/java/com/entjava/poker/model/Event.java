package com.entjava.poker.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<EventPlayer> eventPlayers = new HashSet<>();
    @JsonManagedReference

    public Integer getId() {
        return id;
    }

}
