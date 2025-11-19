package ru.yandex.practicum;

import java.io.PrintWriter;
import java.util.*;
/*
в этом классе хранится словарь и состояние игры
    текущий шаг
    всё что пользователь вводил
    правильный ответ

в этом классе нужны методы, которые
    проанализируют совпадение слова с ответом
    предложат слово-подсказку с учётом всего, что вводил пользователь ранее

не забудьте про специальные типы исключений для игровых и неигровых ошибок
 */
public class WordleGame {

    private final PrintWriter log;

    private HashMap<Integer, Character> correctPositions = new LinkedHashMap<>();

    private String mask;
    private String newGeneratedWord;

    private String answer;

    private int steps;

    private WordleDictionary dictionary;

    private Set<Character> denyLetters = new HashSet<>();
    private Set<Character> correctLetters = new HashSet<>();

    private List<String> updatedDictionary;

    public WordleGame(WordleDictionary wordleDictionary, PrintWriter log) {
        this.log = log;
        this.dictionary = wordleDictionary;
        steps = 0;
        answer = wordleDictionary.generatedWord(dictionary.getWords());
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public String getMask() {
        return mask;
    }

    public String getNewGeneratedWord() {
        return newGeneratedWord;
    }

    public Set<Character> getCorrectLetters() {
        return correctLetters;
    }

    public Set<Character> getDenyLetters() {
        return denyLetters;
    }

    public String getAnswer() {
        return answer;
    }

    public int getSteps() {
        return steps;
    }

    public void stringMask(String answer, String word) {
        StringBuilder sb = new StringBuilder();
        char[] answerMassive = answer.toCharArray();
        char[] wordMassive = word.toCharArray();
        findLetters(answer, wordMassive);
        for (int i = 0; i < answerMassive.length; i++) {
            if (answerMassive[i] == wordMassive[i]) {
                sb.append("+");
                correctPositions.put(i, wordMassive[i]);
            } else {
                char target = wordMassive[i];
                boolean found = false;
                for (char letter : answerMassive) {
                    if (letter == target) {
                        found = true;
                    }
                }
                if (found) {
                    sb.append("^");
                } else {
                    sb.append("-");
                }
            }
        }
        mask = sb.toString();
    }

    public void findLetters(String answer, char[] word) {
        Set<Character> characters = new HashSet<>();
        for (char letter : word) {
            if (answer.indexOf(letter) == -1) {
                characters.add(letter);
            } else {
                correctLetters.add(letter);
            }
        }
        denyLetters = characters;
    }

    public List<String> updateDictionary(List<String> currentDictionary, Map<Integer, Character> correctPositions,
                                         Set<Character> wrongLetters) {
        try {
            List<String> updatedDictionary = new ArrayList<>();
            for (String word : currentDictionary) {
                boolean isValid = true;
                for (Map.Entry<Integer, Character> entry : correctPositions.entrySet()) {
                    if (word.charAt(entry.getKey()) != entry.getValue()) {
                        isValid = false;
                        break;
                    }
                }
                if (isValid) {
                    for (char letter : wrongLetters) {
                        if (word.contains(String.valueOf(letter))) {
                            isValid = false;
                            break;
                        }
                    }
                }
                if (isValid && findWordWithCorrectLetters(word, correctLetters)) {
                    updatedDictionary.add(word);
                }
            }
            return updatedDictionary;
        } catch (NullPointerException | StringIndexOutOfBoundsException e) {
            log.println(e.getMessage());
        }
        return updatedDictionary;
    }

    private boolean findWordWithCorrectLetters(String word, Set<Character> correctLetters) {
        for (char c : correctLetters) {
            if (!word.contains(String.valueOf(c))) {
                return false;
            }
        }
        return true;
    }

    public String game(String word) {
        if (word.isEmpty()) {
            if (steps == 0) {
                String generatedWord = dictionary.generatedWord(dictionary.getWords());
                newGeneratedWord = generatedWord;
                stringMask(answer, generatedWord);
                updatedDictionary = updateDictionary(dictionary.getWords(), correctPositions, denyLetters);
                if (updatedDictionary.contains(generatedWord)) {
                    updatedDictionary.remove(generatedWord);
                    return mask;
                }

            } else {
                String generatedWord = dictionary.generatedWord(updatedDictionary);
                newGeneratedWord = generatedWord;
                stringMask(answer, generatedWord);
                updatedDictionary = updateDictionary(updatedDictionary, correctPositions, denyLetters);
                return mask;
            }
        } else {
            if (steps == 0) {
                stringMask(answer, word);
                updatedDictionary = updateDictionary(dictionary.getWords(), correctPositions, denyLetters);
                if (updatedDictionary.contains(word)) {
                    updatedDictionary.remove(word);
                    return mask;
                }
            } else {
                stringMask(answer, word);
                updatedDictionary = updateDictionary(updatedDictionary, correctPositions, denyLetters);
                return mask;
            }

        }
        return mask;
    }
}
