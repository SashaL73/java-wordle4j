package ru.yandex.practicum;

import java.io.PrintWriter;
import java.util.*;

/*
этот класс содержит в себе список слов List<String>
    его методы похожи на методы списка, но учитывают особенности игры
    также этот класс может содержать рутинные функции по сравнению слов, букв и т.д.
 */
public class WordleDictionary {

    private List<String> words;
    private final PrintWriter log;
    Random random = new Random();

    public WordleDictionary(List<String> words, PrintWriter log) {
        this.words = newListFiveLetters(words);
        this.log = log;
    }

    public List<String> getWords() {
        return words;
    }

    public List<String> newListFiveLetters(List<String> list) {
        List<String> fiveLetter = new ArrayList<>();
            for (String string : list) {
                if (string.length() == 5) {
                    fiveLetter.add(string.toLowerCase());
                }
            }
            for (int i = 0; i < fiveLetter.size(); i++) {
                if (fiveLetter.get(i).contains("ё")) {
                    fiveLetter.set(i, fiveLetter.get(i).replace('ё', 'е'));
                }
            }
            return fiveLetter;
    }

    public String generatedWord(List<String> list) {
        try {
            if (list == null || list.isEmpty()) {
                throw new NullPointerException("Пустой список или равен null");
            }
            String word = list.get(random.nextInt(list.size()));
            return word;
        } catch (NullPointerException e) {
            log.println(e.getMessage());
        }
        return null;
    }

}
