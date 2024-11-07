package com.entjava.poker.game;

import com.entjava.poker.card.BlankCard;
import com.entjava.poker.card.Card;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class GameController {

	private Game game;

	public GameController(Game game) {
		this.game = game;
	}

	@GetMapping("/getPlayers")
	@ResponseBody
	public List<Player> player()
	{
		return game.getPlayers();
	}

	@GetMapping("/start_game/{id}")
	public ResponseEntity<Map<String, Object>> startGame(@PathVariable long id) {
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

	@PostMapping("/start_game")
	@ResponseBody
	public void startGame(@RequestBody List<Player> playerInput) {

		List<Player> registeredPlayers = game.getPlayers();
		List<String> playerNames = new ArrayList<>();

		for (Player player : registeredPlayers) {
			playerNames.add(player.getName());
		}

		for (Player player : playerInput) {
			if (playerNames.contains(player.getName())) {
				System.out.println(player + " is registered.");
			}
			else
			{
				System.out.println(player + " is not registered.");
			}
		}
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
}
