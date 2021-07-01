package kst.app.secretdiary

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.edit

class MainActivity : AppCompatActivity() {

    private val firstPw : NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.first_pw)
            .apply {
            minValue = 0
            maxValue = 9
        }
    }

    private val secondPw : NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.second_pw)
            .apply {
            minValue = 0
            maxValue = 9
        }
    }

    private val thirdPw : NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.third_pw)
            .apply {
            minValue = 0
            maxValue = 9
        }
    }

    private val openBt : AppCompatButton by lazy {
        findViewById<AppCompatButton>(R.id.open_bt)

    }

    private val pwChangeBt : AppCompatButton by lazy {
        findViewById<AppCompatButton>(R.id.pw_change_bt)
    }

    private var changePWFlag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firstPw
        secondPw
        thirdPw

        openBt.setOnClickListener{

            if (changePWFlag){
                Toast.makeText(this,"비밀번호 변경 중입니다.",Toast.LENGTH_LONG).show()
                return@setOnClickListener

            }
            val passwordPreferences =  getSharedPreferences("password",Context.MODE_PRIVATE)

            val passwordFromUser ="${firstPw.value}${secondPw.value}${thirdPw.value}"

            if(passwordPreferences.getString("password","000") .equals(passwordFromUser)){
                //TODO 다이어리 페이지 작성 후 실행
                startActivity(Intent(this,DiaryActivity::class.java))
            } else {
                shoErrorAlertDialog()
            }
        }

        pwChangeBt.setOnClickListener{
            val passwordPreferences =  getSharedPreferences("password",Context.MODE_PRIVATE)
            val passwordFromUser ="${firstPw.value}${secondPw.value}${thirdPw.value}"
            if (changePWFlag){
                //번호 저장
                passwordPreferences.edit {
                    putString("password",passwordFromUser)
                    commit()
                }
                changePWFlag = false
                pwChangeBt.setBackgroundColor(Color.BLACK)
            } else {
                //비밀번호 변경 모드 전
                if(passwordPreferences.getString("password","000") .equals(passwordFromUser)){
                    //TODO 비밀번호 변경실행
                    changePWFlag = true
                    Toast.makeText(this,"비밀버호 변경 모드가 활성화 되었습니다.",Toast.LENGTH_LONG).show()
                    pwChangeBt.setBackgroundColor(Color.RED)
                } else {
                    shoErrorAlertDialog()
                }
            }
        }
    }

    private fun shoErrorAlertDialog(){
        AlertDialog.Builder(this)
            .setTitle("실패!!")
            .setMessage("비밀번호가 잘못되었습니다.")
            .setPositiveButton("확인"){ _, _ -> }
            .create()
            .show()
    }
}