package com.example.matvey.data;

public class TestQuestion {
    private long id;
    private String questionText;
    private String option1;
    private String option2;
    private String option3;
    private int correctAnswer;
    private long testId;

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getQuestionText() { return questionText; }
    public void setQuestionText(String questionText) { this.questionText = questionText; }

    public String getOption1() { return option1; }
    public void setOption1(String option1) { this.option1 = option1; }

    public String getOption2() { return option2; }
    public void setOption2(String option2) { this.option2 = option2; }

    public String getOption3() { return option3; }
    public void setOption3(String option3) { this.option3 = option3; }

    public int getCorrectAnswer() { return correctAnswer; }
    public void setCorrectAnswer(int correctAnswer) { this.correctAnswer = correctAnswer; }

    public long getTestId() { return testId; }
    public void setTestId(long testId) { this.testId = testId; }
}
