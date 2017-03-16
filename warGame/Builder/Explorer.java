package warGame.Builder;

public class Explorer {

	private CharacterBuilder builder;
	
	public void setBuilder(CharacterBuilder newCharacterBuilder){
		builder = newCharacterBuilder;
	}
	
	public void constructCharacter(){
		builder.createNewCharacter();
		builder.buildCharisma();
		builder.buildConstitution();
		builder.buildDexterity();
		builder.buildIntelligence();
		builder.buildStrength();
		builder.buildWisdom();
	}
	
	public Character getCharacter(){
		return builder.getCharacter();
	}
}
