package com.entjava.poker.game;

import com.entjava.poker.card.BlankCard;
import com.entjava.poker.card.Card;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

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

	@GetMapping("/nextAction")
	public String nextAction() {
		if (game.hasEnded()) {
			game.startNewGame();
		} else {
			game.nextAction();
		}

		return "redirect:/";
	}

	@GetMapping("/start_game/{id}")
	@ResponseBody
	//Had some help from ChatGPT in debugging and fixing errors
	public Map<String, Object> startGame(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();

		game.startNewGame();  // Start a new game

		// Create a list to hold player data
		List<Map<String, String>> playersList = new ArrayList<>();

		// Loop through players and add their details to the response
		List<Player> players = game.getPlayers();
		for (Player player : players) {
			Map<String, String> playerData = new HashMap<>();
			playerData.put("name", player.getName());

			// Check if the player's hand is available after the game logic
			List<Card> playerHand = player.getHand();
			if (playerHand != null && !playerHand.isEmpty()) {
				String handDescription = formatHand(playerHand);
				playerData.put("hand", handDescription);
			} else {
				playerData.put("hand", "No cards dealt yet");
			}

			playersList.add(playerData);
		}

		// Add players list to the response map
		response.put("players", playersList);

		// Determine and add the winner(s) to the response map
		List<Player> winners = game.getWinners();
		if (winners != null && winners.size() == 1) {
			response.put("winner", winners.get(0).getName());
		} else if (winners != null && winners.size() > 1) {
			List<String> winnerNames = new ArrayList<>();
			for (Player winner : winners) {
				winnerNames.add(winner.getName());
			}
			response.put("winner", "Tie between: " + String.join(", ", winnerNames));
		} else {
			response.put("winner", "No winner yet");
		}

		return response;
	}

	// Helper method to format the hand description
	// Help from ChatGPT
	private String formatHand(List<Card> hand) {
		StringBuilder handDescription = new StringBuilder();

		// Format each card (this is just an example, modify it according to your actual Card class)
		for (Card card : hand) {
			handDescription.append(card.getRank()).append(" of ").append(card.getSuit()).append(", ");
		}

		// Remove trailing comma and space
		if (handDescription.length() > 0) {
			handDescription.setLength(handDescription.length() - 2);
		}

		return handDescription.toString();
	}
}
