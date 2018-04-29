package com.example.yoelpc.postpc_e2;


public class SentenceAnalyzer {
    private int letterCount;
    private int wordCount;

    public SentenceAnalyzer(String sentence) {
        // We can assume that the given string is not null and not empty but whatever...
        if (sentence == null || sentence.isEmpty()) {
            letterCount = wordCount = 0;
            return;
        }

        // Split into an array of words with whitespaces as the obvious delimiter
        final String[] words = sentence.split("\\s+");
        wordCount = words.length;
        countLetters(words);
    }

    private void countLetters(String[] words) {
        letterCount = 0;

        for (String w : words) {
            for (char c : w.toCharArray()) {
                if (Character.isLetter(c)) {
                    ++letterCount;
                }
            }
        }
    }

    public int getLetterCount() {
        return letterCount;
    }

    public int getWordCount() {
        return wordCount;
    }
}
