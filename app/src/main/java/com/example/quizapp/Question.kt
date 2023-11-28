package com.example.quizapp

// 문제에 대한 정보를 담고 있는 data class
data class Question(
    var id: Int,
    var question: String,
    var option_one: String,
    var option_two: String,
    var option_three: String,
    var option_four: String,
    var correct_answer: Int,
)
