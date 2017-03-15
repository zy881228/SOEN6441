package warGame;

import java.util.Random;

public abstract class CharacterBuilder {

	protected Character characterProduct;
	protected int score[] = new int[6];
	public Character getCharacter(){
		return characterProduct;
	}
	public void get4d6Number(){
		for(int i = 0; i<6;i++)
		{
			Random rand = new Random();
			int randNum[] = new int[4];
			randNum[0] = rand.nextInt(6)+1;
			randNum[1] = rand.nextInt(6)+1;
			randNum[2] = rand.nextInt(6)+1;
			randNum[3] = rand.nextInt(6)+1;
			score[i] = randNum[0]+randNum[1]+randNum[2]+randNum[3];
		}
		for(int i =0;i<6;i++)
		{
			for(int j=i+1;j<6;j++)
			{
				if(score[i]<score[j])
				{
					int buffer = score[i];
					score[i] = score[j];
					score[j] = buffer;
				}
			}
		}
	}
	
	public void createNewCharacter(){
		get4d6Number();
		characterProduct = new Character();
	}
	abstract void buildStrength();
	abstract void buildDexterity();
	abstract void buildConstitution();
	abstract void buildIntelligence();
	abstract void buildWisdom();
	abstract void buildCharisma();
}
