package kst.app.secretdiary

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings.Global.putString
import android.widget.EditText
import androidx.core.content.edit
import androidx.core.widget.addTextChangedListener

class DiaryActivity : AppCompatActivity() {

    private val diaryContent : EditText by lazy {
        findViewById<EditText>(R.id.diary_content)
    }

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary)

        val detailPreferences = getSharedPreferences("diary",Context.MODE_PRIVATE)

        diaryContent.setText(detailPreferences.getString("content",""))

        val runnable = Runnable {
            getSharedPreferences("diary",Context.MODE_PRIVATE).edit{
                putString("content",diaryContent.text.toString())
            }
        }

        diaryContent.addTextChangedListener {
            detailPreferences.edit {
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable,500)
            }
        }

    }
}