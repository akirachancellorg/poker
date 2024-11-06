DROP TABLE IF EXISTS event_player;
DROP TABLE IF EXISTS event;
DROP TABLE IF EXISTS player;

CREATE TABLE player (
    id serial NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE event (
   id serial NOT NULL PRIMARY KEY
);

CREATE TABLE event_player (
    id serial NOT NULL PRIMARY KEY,
    event_id INTEGER NOT NULL,
    player_id INTEGER NOT NULL,
    hand VARCHAR(255) NOT NULL,
    is_winner BOOLEAN NOT NULL,
    FOREIGN KEY (event_id) REFERENCES event(id),
    FOREIGN KEY (player_id) REFERENCES player(id)
);

INSERT INTO player (id, name) VALUES (1, 'ed');
INSERT INTO player (id, name) VALUES (2, 'dan');
INSERT INTO player (id, name) VALUES (3, 'mon');
INSERT INTO player (id, name) VALUES (4, 'jm');
INSERT INTO player (id, name) VALUES (5, 'kai');
INSERT INTO player (id, name) VALUES (6, 'kent');
INSERT INTO player (id, name) VALUES (7, 'vin');
INSERT INTO player (id, name) VALUES (8, 'liam');

-- INSERT INTO event (id) VALUES (1);
-- INSERT INTO event (id) VALUES (2);
-- INSERT INTO event (id) VALUES (3);
-- INSERT INTO event (id) VALUES (4);
--
-- INSERT INTO event_player (id, event_id, player_id, is_winner, hand) VALUES (1, 1, 1, true, 'rock');
-- INSERT INTO event_player (id, event_id, player_id, is_winner, hand) VALUES (2, 1, 2, false, 'scissors');
-- INSERT INTO event_player (id, event_id, player_id, is_winner, hand) VALUES (3, 1, 3, false, 'rock');
-- INSERT INTO event_player (id, event_id, player_id, is_winner, hand) VALUES (4, 1, 4, false, 'scissors');
-- INSERT INTO event_player (id, event_id, player_id, is_winner, hand) VALUES (5, 1, 5, false, 'rock');
-- INSERT INTO event_player (id, event_id, player_id, is_winner, hand) VALUES (6, 2, 1, false, 'scissors');
-- INSERT INTO event_player (id, event_id, player_id, is_winner, hand) VALUES (7, 2, 2, true,  'rock');
-- INSERT INTO event_player (id, event_id, player_id, is_winner, hand) VALUES (8, 2, 3, false, 'scissors');

