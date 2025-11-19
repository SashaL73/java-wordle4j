package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class WordleTest {
    PrintWriter log = new PrintWriter(System.out);
    WordleDictionary dictionary;
    WordleGame newGame;


    @BeforeEach
    void test() {
        dictionary = new WordleDictionary(List.of("горец", "банан","карта"), log);
        newGame = new WordleGame(dictionary,log);
    }
    @Test
    void testGenerateWord(){
        String generatedWord = dictionary.generatedWord(dictionary.getWords());
        assertTrue(dictionary.getWords().contains(generatedWord));
    }

    @Test
    void testStringMask(){
        String word = "свеча";
        newGame.stringMask("горец",word);
        assertEquals("--^--",newGame.getMask());
    }

    @Test
    void testFilterWordsByPositions(){
        Map<Integer,Character> correctPosition = new LinkedHashMap<>();
        Set<Character> set = new HashSet<>();
        correctPosition.put(0,'г');
        List<String> list = new ArrayList<>(newGame.updateDictionary(dictionary.getWords(),correctPosition,set));
        assertTrue(list.contains("горец"));
        assertFalse(list.contains("банан"));
    }

    @Test
    void testFindLetters(){
        String word = "горец";
        char[] wordMassive = word.toCharArray();
        newGame.setAnswer("банан");
        newGame.findLetters(newGame.getAnswer(), wordMassive);
        Set<Character> set = newGame.getCorrectLetters();
        Set<Character> set1 = newGame.getDenyLetters();
        assertTrue(set.size() == 0);
        assertEquals(5,set1.size());

    }


}
