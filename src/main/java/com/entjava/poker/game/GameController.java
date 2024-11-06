package com.entjava.poker.game;

import com.entjava.poker.card.BlankCard;
import com.entjava.poker.card.Card;
import com.entjava.poker.dto.EventResultDTO;
import com.entjava.poker.service.EventPlayerService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;



@Controller
public class GameController {

	private Game game;
	private final EventPlayerService eventPlayerService;

	public GameController(Game game, EventPlayerService eventPlayerService) {
		this.eventPlayerService = eventPlayerService;
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
		
		Map<String, Long> totalWins = eventPlayerService.getTotalWinsByPlayer();
		model.addAttribute("totalWins", totalWins);
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

	@GetMapping("/event/{id}")
	public ResponseEntity<EventResultDTO> custom_getEventById(@PathVariable int id) {
		EventResultDTO event = eventPlayerService.custom_getEventById(id).orElse(null);
		return ResponseEntity.ok(event);
	}

	@PostMapping("/start_game/{playerCount}")
	public String startGame(@RequestBody Map<String, List<Map<String, String>>> request, Model model) {


		List<Map<String, String>> players = request.get("players");
		List<String> playerNames = players.stream()
				.map(player -> player.get("name"))
				.collect(Collectors.toList());

		List<String> missingNames = eventPlayerService.checkPlayerNamesExist(playerNames);

		if (missingNames.size() == 1) {
			System.out.println(
				"User \"" + missingNames.get(0) + "\" is not registered"
			);
		} else if (missingNames.size() > 1) {
			System.out.println(
				"Users \"" + String.join("\", \"", missingNames) + "\" are not registered"
			);
		}

		playerNames.removeAll(missingNames);
		game.startNewGameWithPlayers(playerNames);

		return "redirect:/";
	}
}
