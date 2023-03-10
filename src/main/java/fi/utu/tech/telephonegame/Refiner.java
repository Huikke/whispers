package fi.utu.tech.telephonegame;

import java.util.Random;
import fi.utu.tech.telephonegame.util.Words;

import java.util.ArrayList;
import java.util.Arrays;

public class Refiner {

	/*
		The default implementation of Refiner uses word lists defined in fi.utu.tech.telephonegame.util.Words
		You can create your own lists either here or in the Words class.
	 */
	/*
	private static final String[] subjects = (
			"Lorem ipsum dolor sit amet, consectetur adipiscing elit Aliquam laoreet vitae lectus id vehicula ")
					.split(" ");

	private static final String[] predicates = (
			"Lorem ipsum dolor sit amet, consectetur adipiscing elit Aliquam laoreet vitae lectus id vehicula ")
			.split(" ");

	private static final String[] objects = (
			"Lorem ipsum dolor sit amet, consectetur adipiscing elit Aliquam laoreet vitae lectus id vehicula ")
			.split(" ");
	 */
	private static final String[] conjunctions  = (
			"että, jotta, koska, kun, jos, vaikka, kunnes, mikäli, ja")
			.split(",");

	/*
		If you decide to use the lists above, comment out the following three lines.
	 */
	private static final String[] subjects = Words.subjects;
	private static final String[] predicates = Words.predicates;
	private static final String[] objects = Words.subjects;

	private static final Random rnd = new Random();


	/*
	 * The refineText method is used to change the message
	 * Now it is time invent something fun!
	 *
	 * In the example implementation a random work from a word list is added to the end of the message.
	 * But you do you!
	 *
	 * Please keep the message readable. No ROT13 etc, please
	 *
	 */
	public static String refineText(String inText) {
		String outText = inText;

		// Change the content of the message here.
		
		//
		ArrayList<String> inWords = new ArrayList<> (Arrays.asList(inText.split(" ")));
		
		//otetaan alkukirjain pois jostain satunnaisesta sanasta
		/*int shorteningIndex = rnd.nextInt(inWords.size());
		inWords.set(shorteningIndex,inWords.get(shorteningIndex).substring(1));*/
		
		//liikutetaan jokin satunnainen sana johonkin satunnaiseen paikkaan
		int fromIndex = rnd.nextInt(inWords.size());
		int toIndex = rnd.nextInt(inWords.size());
		String word = inWords.get(fromIndex);
		if (toIndex >= fromIndex){
			inWords.add(toIndex, word);
			inWords.remove(fromIndex);
		} else {
			inWords.remove(fromIndex);
			inWords.add(toIndex, word);
		}
		
		
		//duplikoidaan joku satunnainen sana
		int duplicationIndex = rnd.nextInt(inWords.size());
		inWords.add(duplicationIndex,inWords.get(duplicationIndex));
		
		// lisätään joku satunnainen sana
		int newWordIndex = rnd.nextInt(inWords.size());
		int wordType = rnd.nextInt(3);
		int randomIndex;
		switch (wordType) {
			case 0:
				randomIndex = rnd.nextInt(subjects.length);
				inWords.add(newWordIndex, subjects[randomIndex]);
				break;
			case 1:
				randomIndex = rnd.nextInt(predicates.length);
				inWords.add(newWordIndex, predicates[randomIndex]);
				break;
			case 2:
				randomIndex = rnd.nextInt(objects.length);
				inWords.add(newWordIndex, objects[randomIndex]);
				break;
			default:
				break;
		}
		
		outText = inWords.toString();

		// poistetaan sulut ja ylimääräiset pilkut
		outText = outText.substring(1, outText.length()-1);
		outText = outText.replace(", ", " ");
		
		// lisätään sanoja perään
		outText = outText + " " +
				conjunctions[rnd.nextInt(conjunctions.length)] + " " +
				subjects[rnd.nextInt(subjects.length)] + " " +
				predicates[rnd.nextInt(predicates.length)]+ " " +
				objects[rnd.nextInt(objects.length)];

		return outText;
	}


	/*
	 * This method changes the color. No editing needed.
	 *
	 * The color hue value is an integer between 0 and 360
	 */
	public static Integer refineColor(Integer inColor) {
		return (inColor + 20) < 360 ? (inColor + 20) : 0;
	}

}
