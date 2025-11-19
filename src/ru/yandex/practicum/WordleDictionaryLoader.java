package ru.yandex.practicum;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/*
этот класс содержит в себе всю рутину по работе с файлами словарей и с кодировками
    ему нужны методы по загрузке списка слов из файла по имени файла
    на выходе должен быть класс WordleDictionary
 */
public class WordleDictionaryLoader {
    private final PrintWriter log;

    public WordleDictionaryLoader(PrintWriter log) {
        this.log = log;
    }

    public WordleDictionary wordleDictionaryLoader() throws IOException {
        WordleDictionary wordleDictionary = null;
        List<String> words = new ArrayList<>();
        try {
            try (BufferedReader br = new BufferedReader(new FileReader("words_ru.txt", StandardCharsets.UTF_8))) {
                while (br.ready()) {
                    String line = br.readLine();
                    words.add(line);
                }
                wordleDictionary = new WordleDictionary(words,log);
                return wordleDictionary;
            }
        } catch (IOException e) {
            log.println("Ошибка при загрузке словаря: " + e.getMessage());
        }
        return wordleDictionary;
    }
}
