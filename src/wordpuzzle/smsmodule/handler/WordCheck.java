package wordpuzzle.smsmodule.handler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import wordpuzzle.mappers.UsersMapper;
import wordpuzzle.mappers.WordsMapper;
import wordpuzzle.models.Users;
import wordpuzzle.models.Words;

public class WordCheck {

	public String sendNewWord(Users user) throws SQLException {
		
		String puzzleWord = null;
		
		WordsMapper wordsMapper = new WordsMapper();
		ArrayList<Words> wordsList = wordsMapper.getWords();
		
		int lastSent = user.getLastSent();
		
		if(wordsList.size() >= lastSent+1) {
			puzzleWord = wordsList.get(lastSent).getWord();
			System.out.println(puzzleWord);
			puzzleWord = this.generatePuzzleWord(puzzleWord);
			System.out.println(puzzleWord);
		}
		
		UsersMapper usersMapper = new UsersMapper();
		usersMapper.setLastSent(user.getMsisdn(), wordsList.get(lastSent).getId());
		
		return puzzleWord;
	}
	
	public String checkReceivedWord(Users user, String filledWord) throws SQLException {
		
		String infoMessage = null;
		
		WordsMapper wordsMapper = new WordsMapper();
		ArrayList<Words> wordsList = wordsMapper.getWords();
		
		int lastSent = user.getLastSent();
		
		String fullWord = wordsList.get(lastSent - 1).getWord();
		
		if(filledWord.equalsIgnoreCase(fullWord)) {
			infoMessage = "You guessed the word correctly. 1 score added";
		} else {
			infoMessage = "Guessed word is wrong. Please try a new word";
		}
		
		return infoMessage;
	}
	
	public String generatePuzzleWord(String puzzleWord) {
		
		int firstRandomNum = new Random().nextInt(puzzleWord.length())  + 1;
		
		StringBuilder changedWord = new StringBuilder(puzzleWord);
		changedWord.setCharAt(firstRandomNum -1, '_');
		
		int secondRandomNum = 0;
		
		while (true) {
			secondRandomNum = new Random().nextInt(puzzleWord.length())  + 1;
			
			if(secondRandomNum != firstRandomNum) {
				break;
			}
		}
		
		changedWord = new StringBuilder(changedWord.toString());
		changedWord.setCharAt(secondRandomNum - 1, '_');
		
		return changedWord.toString();
	}
}
