package com.github.NikBenson.RoleplayBot.modules.player;

import com.github.NikBenson.RoleplayBot.Bot;
import net.dv8tion.jda.api.entities.User;
import org.json.simple.JSONObject;

public class Player {
	/*private List<Character> characters = new LinkedList<>();
	private int currentCharacter = 0;

	private int characterCreationPhase = -1;
	private Map<String, String> creatingCharacterSheet;
	private Team creatingCharacterTeam;*/

	private final User USER;

	public Player(User user) {
		USER = user;
	}

	public Player(JSONObject json) {
		USER = Bot.getJDA().getUserById((String) json.get("id"));

		/*JSONArray charactersJson = (JSONArray) json.get("characters");

		for (int i = 0; i < charactersJson.size(); i++) {
			JSONObject characterJson = (JSONObject) charactersJson.get(i);

			characters.add(new Character(characterJson));
		}*/
	}

	public User getUser() {
		return USER;
	}

	/*public Character getCurrentCharacter() {
		return characters.get(currentCharacter);
	}

	public String startCharacterCreation() {
		characterCreationPhase = 0;
		creatingCharacterSheet = new JSONObject();
		return TeamsManager.getInstance().getQuestion();
	}
	public void cancelCharacterCreation() {
		characterCreationPhase = -1;
		creatingCharacterSheet = null;
		creatingCharacterTeam = null;
	}
	public String characterCreationAnswer(String answer) {
		if(creatingCharacterTeam == null) {
			creatingCharacterTeam = TeamsManager.getInstance().findTeam(answer);

			if(creatingCharacterTeam != null) {
				return SheetBlueprint.getInstanceOrCreate().getSheetQuestion(characterCreationPhase);
			} else {
				return "Invalid team. Please try again!";
			}
		} else {
			creatingCharacterSheet.put(SheetBlueprint.getInstanceOrCreate().getSheetAttribute(characterCreationPhase), answer);
			characterCreationPhase++;

			if (SheetBlueprint.getInstanceOrCreate().getSheetAttribute(characterCreationPhase) != null) {
				return SheetBlueprint.getInstanceOrCreate().getSheetQuestion(characterCreationPhase);
			} else {
				characters.add(new Character(creatingCharacterSheet, creatingCharacterTeam));
				cancelCharacterCreation();
				return "finished!";
			}
		}
	}
	public boolean isCreatingCharacter() {
		return characterCreationPhase >= 0;
	}*/

	public JSONObject getJSON() {
		JSONObject json = new JSONObject();

		json.put("id", USER.getIdLong());

		/*JSONArray charactersJson = new JSONArray();
		for(Character character : characters) {
			charactersJson.add(character.getJson());
		}

		json.put("characters", charactersJson);*/

		return json;
	}
}
