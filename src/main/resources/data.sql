CREATE DATABASE Poker;
USE Poker;
CREATE TABLE game_event (
                            id BIGINT PRIMARY KEY AUTO_INCREMENT,
                            event_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE player (
                        id BIGINT PRIMARY KEY AUTO_INCREMENT,
                        name VARCHAR(255) NOT NULL,
                        is_winner BOOLEAN DEFAULT FALSE,
                        game_event_id BIGINT,
                        FOREIGN KEY (game_event_id) REFERENCES game_event(id)
);
CREATE TABLE hand (
                      id BIGINT PRIMARY KEY AUTO_INCREMENT,
                      player_id BIGINT,
                      hand_type VARCHAR(255) NOT NULL,
                      cards VARCHAR(255) NOT NULL,
                      high_card VARCHAR(255),
                      FOREIGN KEY (player_id) REFERENCES player(id)
);