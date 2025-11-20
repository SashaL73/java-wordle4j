package ru.yandex.practicum;

import exceptions.InvalidWordException;
import exceptions.WordNotFoundInDictionary;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/*
в главном классе нам нужно:
    создать лог-файл (он должен передаваться во все классы)
    создать загрузчик словарей WordleDictionaryLoader
    загрузить словарь WordleDictionary с помощью класса WordleDictionaryLoader
    затем создать игру WordleGame и передать ей словарь
    вызвать игровой метод в котором в цикле опрашивать пользователя и передавать информацию в игру
    вывести состояние игры и конечный результат
 */
public class Wordle {

    public static void main(String[] args) {
        try (PrintWriter log = new PrintWriter(new FileWriter("log.txt"))) {
            WordleDictionaryLoader wordleDictionaryLoader = new WordleDictionaryLoader(log);
            WordleDictionary wordleDictionary = wordleDictionaryLoader.wordleDictionaryLoader();
            WordleGame wordleGame = new WordleGame(wordleDictionary, log);
            try (Scanner scanner = new Scanner(System.in)) {
                System.out.println("Введите слово из 5 букв");
                while (wordleGame.getSteps() < 6) {
                    try {
                        String word = scanner.nextLine().trim().toLowerCase();
                        if (word.isEmpty()) {
                            wordleGame.game(word);
                            String newWord = wordleGame.getNewGeneratedWord();
                            System.out.println(newWord);
                            System.out.println(wordleGame.getMask());
                            if (newWord.equals(wordleGame.getAnswer())) {
                                System.out.println("Вы угадали, загаданное слово " + wordleGame.getAnswer());
                                return;
                            }
                            wordleGame.setSteps(wordleGame.getSteps() + 1);
                        } else if (word.length() != 5 || !word.matches("[а-я]+")) {
                            throw new InvalidWordException("Слово должно состоять из 5 русских букв");
                        } else if (!wordleDictionary.getWords().contains(word)) {
                            throw new WordNotFoundInDictionary("В словаре такого слова нет");
                        } else {
                            wordleGame.game(word);
                            System.out.println(wordleGame.getMask());
                            if (word.equals(wordleGame.getAnswer())) {
                                System.out.println("Вы угадали, загаданное слово " + wordleGame.getAnswer());
                                return;
                            }
                            wordleGame.setSteps(wordleGame.getSteps() + 1);
                        }

                    } catch (InvalidWordException | WordNotFoundInDictionary e) {
                        System.out.println(e.getMessage());
                        log.println(e.getMessage());
                    }
                }
                System.out.println("Загаданное слово " + wordleGame.getAnswer());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
