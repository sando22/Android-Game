package manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import activity.Menu;
import model.Question;

public class QuestionManager {
    private final int QUESTION_SIZE_IN_FILE = 6;
    private final int QUESTION_INDEX = 0;
    private final int DIFFICULTY_INDEX = 1;
    private final int ANSWER_START_INDEX = 2;
    private final int ANSWER_END_INDEX = 6;

    private ArrayList<Question> questionList = new ArrayList<>();
    private FileManager fileManager = Menu.fileManager;
    private Random random = new Random();
    private ArrayList<String> wholeFile;

    public void generateQuestionList() {
        Thread generateQuestionThread = new Thread(new Runnable() {
            @Override
            public void run() {
                int currentIndex = 0;
                questionList.clear();
                if (wholeFile == null) {
                    wholeFile = fileManager.readQuestionFile();
                }
                while (currentIndex < wholeFile.size()) {
                    questionList.add(createQuestion(wholeFile.subList(currentIndex, currentIndex + QUESTION_SIZE_IN_FILE)));
                    currentIndex += QUESTION_SIZE_IN_FILE;
                }
            }
        });
        generateQuestionThread.start();
        try {
            generateQuestionThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Question getNextQuestion(int diff) {
        int currentRandomPick;
        Question nextQuestion;
        do {
            currentRandomPick = random.nextInt(questionList.size() - 1);
            nextQuestion = questionList.get(currentRandomPick);
        } while (nextQuestion.getDifficulty() != diff);
        questionList.remove(currentRandomPick);
        nextQuestion.shuffleAnswers();

        return nextQuestion;
    }

    private Question createQuestion(List<String> questionLines) {
        String question;
        ArrayList<String> answers = new ArrayList<>();
        int difficulty;
        question = questionLines.get(QUESTION_INDEX);
        difficulty = Integer.parseInt(questionLines.get(DIFFICULTY_INDEX));
        answers.addAll(questionLines.subList(ANSWER_START_INDEX, ANSWER_END_INDEX));
        return new Question(question, answers, difficulty);
    }

    public boolean hasMoreQuestions() {
        return !questionList.isEmpty();
    }

}
