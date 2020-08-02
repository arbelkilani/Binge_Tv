package com.arbelkilani.bingetv.presentation.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.arbelkilani.bingetv.R

class WalkthroughActivity : AppCompatActivity() {

    companion object {
        const val TAG = "WalkthroughActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_walkthrough)
    }
}