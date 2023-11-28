package com.example.quizapp

object QuestionData {
    // 문제 데이터들을 넘겨주는 object
    fun getQuestion(): ArrayList<Question> {
        val queList: ArrayList<Question> = arrayListOf()

        val q1 = Question(
            1,
            "1 + 2 = ?",
            "1",
            "2",
            "3",
            "4",
            3,
        )

        val q2 = Question(
            1,
            "1 * 4 = ?",
            "1",
            "2",
            "3",
            "4",
            4,
        )

        val q3 = Question(
            1,
            "1 + 1 = ?",
            "1",
            "2",
            "3",
            "4",
            2,
        )

        queList.add(q1)
        queList.add(q2)
        queList.add(q3)

        return queList
    }
}