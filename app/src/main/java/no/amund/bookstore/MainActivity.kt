package no.amund.bookstore

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import no.amund.bookstore.model.AppDatabase

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onDestroy() {
        super.onDestroy()
        AppDatabase.destroyDatabase()
    }
}
