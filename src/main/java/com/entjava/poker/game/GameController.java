package com.entjava.poker.game;

import com.entjava.poker.card.BlankCard;
import com.entjava.poker.card.Card;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Iterator;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


@Controller
public class GameController {

	private Game game;

	public GameController(Game game) {
		this.game = game;
	}

	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("game", game);

		List<Player> players = game.getPlayers();
		for (int i = 0; i < players.size(); i++) {
			int playerNumber = i + 1;
			model.addAttribute("player" + playerNumber, players.get(i));
		}

		Iterator<Card> communityCardIterator = game.getCommunityCards().iterator();
		for (int communityCardNumber = 1; communityCardNumber <= 5; communityCardNumber++) {
			model.addAttribute("communityCard" + communityCardNumber, fetchNextCommunityCard(communityCardIterator));
		}

		return "index";
	}

	private Card fetchNextCommunityCard(Iterator<Card> communityCardIterator) {
		if (communityCardIterator.hasNext()) {
			return communityCardIterator.next();
		} else {
			return new BlankCard();
		}
	}

	@GetMapping("/start_game/{id}")
	public ResponseEntity<Map<String, Object>> startGame(@PathVariable int id) {
		game.startNewGame();
		List<Player> players = game.getPlayers();
		List<Map<String, String>> playerDetails = players.stream().map(player -> {

			Map<String, String> playerMap = new HashMap<>();
			playerMap.put("name", player.getName());

			return playerMap;

		}).collect(Collectors.toList());

		Map<String, Object> response = new HashMap<>();
		response.put("players", playerDetails);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/nextAction")
	public String nextAction() {
		if (game.hasEnded()) {
			game.startNewGame();
		} else {
			game.nextAction();
		}

		return "redirect:/";
	}

	@GetMapping("/event/{id}")
	public ResponseEntity<Map<String, Object>> getEvent(@PathVariable int id) {
		try {
			List<Player> players = game.getPlayers();

			if (!game.hasEnded()) {
				game.nextAction(); // This will deal community cards and identify hands
			}

			// Now use getWinningHand() to get the winning hand and player
			String winnerName = game.getWinningHand()
					.map(hand -> hand.getPlayer() != null ? hand.getPlayer().getName() : "Unknown Player")
					.orElse("No winner determined");

			List<Map<String, String>> playerDetails = players.stream().map(player -> {
				Map<String, String> playerMap = new HashMap<>();
				playerMap.put("name", player.getName());
				playerMap.put("hand", player.getHand() != null ? player.getHand().toString() : "No hand");
				// Mark the winner and loser
				playerMap.put("isWinner", player.getName().equals(winnerName) ? "true" : "false");
				playerMap.put("isLoser", !player.getName().equals(winnerName) ? "true" : "false");
				return playerMap;
			}).collect(Collectors.toList());

			Map<String, Object> response = new HashMap<>();
			response.put("players", playerDetails);
			response.put("winner", winnerName);

			return ResponseEntity.ok(response);

		} catch (Exception e) {
			Map<String, Object> errorResponse = new HashMap<>();
			errorResponse.put("error", "An unexpected error occurred");
			errorResponse.put("details", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		}
	}
}