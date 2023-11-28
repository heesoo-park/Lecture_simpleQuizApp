package com.example.quizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.quizapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    private var currentPosition: Int = 1
    private var selectedOption: Int = 0
    private var score: Int = 0

    private lateinit var questionList: ArrayList<Question>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 질문 리스트 가져오기
        questionList = QuestionData.getQuestion()

        // 화면 세팅
        getQuestionData()

        binding.option1Text.setOnClickListener(this)
        binding.option2Text.setOnClickListener(this)
        binding.option3Text.setOnClickListener(this)
        binding.option4Text.setOnClickListener(this)

        // 답변 체크 이벤트
        binding.submitBtn.setOnClickListener {
            if (selectedOption != 0) {
                val question = questionList[currentPosition - 1]
                // 정답 체크
                // 오답
                if (selectedOption != question.correct_answer) {
                    setColor(selectedOption, R.drawable.wrong_option_background)

                    callDialog("오답", "정답 ${question.correct_answer}")
                } else {
                    score++
                }
                // 정답
                setColor(question.correct_answer, R.drawable.correct_option_background)

                if (currentPosition == questionList.size) {
                    binding.submitBtn.text = getString(R.string.submit, "끝")
                } else {
                    binding.submitBtn.text = getString(R.string.submit, "다음")
                }
            } else { // 선택지를 선택하지 않았다면
                currentPosition++
                when {
                    // 전체 문제 숫자가 현재 위치보다 크면 다으 문제로 세팅
                    currentPosition <= questionList.size -> {
                        // 다음 문제 세팅
                        getQuestionData()
                    }
                    else -> {
                        // 결과 화면으로 이동
                        val intent = Intent(this@MainActivity, ResultActivity::class.java)
                        intent.putExtra("score", score)
                        intent.putExtra("totalSize", questionList.size)
                        startActivity(intent)
                    }
                }
            }
            // 선택값 초기화
            selectedOption = 0
        }
    }

    private fun callDialog(alertTitle: String, correctAnswer: String) {
        AlertDialog.Builder(this)
            .setTitle(alertTitle)
            .setMessage("정답: $correctAnswer")
            .setPositiveButton("OK") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            .setCancelable(false)
            .show()
    }

    private fun setColor(opt: Int, color: Int) {
        when (opt) {
            1 -> binding.option1Text.background = ContextCompat.getDrawable(this, color)
            2 -> binding.option2Text.background = ContextCompat.getDrawable(this, color)
            3 -> binding.option3Text.background = ContextCompat.getDrawable(this, color)
            4 -> binding.option4Text.background = ContextCompat.getDrawable(this, color)
        }
    }

    private fun getQuestionData() {
        setOptionStyle()

        // 질문 리스트에 있는 질문 하나를 변수에 담기
        val question = questionList[currentPosition - 1]

        // 상태바 진행도
        binding.progressBar.progress = currentPosition
        // 상태바 최대값
        binding.progressBar.max = questionList.size
        // 상태바 진행도 텍스트
        binding.progressText.text = getString(R.string.count_label, currentPosition, questionList.size)
        // 질문 텍스트
        binding.questionText.text = question.question
        // 답변들 텍스트
        binding.option1Text.text = question.option_one
        binding.option2Text.text = question.option_two
        binding.option3Text.text = question.option_three
        binding.option4Text.text = question.option_four

        setSubmitBtn("제출")
    }

    private fun setSubmitBtn(name: String) {
        // 버튼 텍스트
        binding.submitBtn.text = getString(R.string.submit, name)
    }

    private fun setOptionStyle() {
        var optionList: ArrayList<TextView> = arrayListOf()
        optionList.add(0, binding.option1Text)
        optionList.add(0, binding.option2Text)
        optionList.add(0, binding.option3Text)
        optionList.add(0, binding.option4Text)

        for (op in optionList) {
            op.setTextColor(Color.parseColor("#555151"))
            op.background = ContextCompat.getDrawable(this, R.drawable.option_background)
            op.typeface = Typeface.DEFAULT
        }
    }

    private fun selectedOptionStyle(view: TextView, opt: Int) {
        // 선택지 스타일 초기화
        setOptionStyle()

        selectedOption = opt
        view.setTextColor(Color.parseColor("#5F00FF"))
        view.background = ContextCompat.getDrawable(this, R.drawable.selected_option_background)
        view.typeface = Typeface.DEFAULT_BOLD
    }

    // 액티비티 내의 클릭이벤트가 많을 때 중복코드를 최소화하기 위해 View.OnClickListener를 상속받아 오버라이딩
    override fun onClick(view: View) {
        when (view.id) {
            R.id.option1_text -> selectedOptionStyle(binding.option1Text, 1)
            R.id.option2_text -> selectedOptionStyle(binding.option2Text, 2)
            R.id.option3_text -> selectedOptionStyle(binding.option3Text, 3)
            R.id.option4_text -> selectedOptionStyle(binding.option4Text, 4)
        }
    }
}