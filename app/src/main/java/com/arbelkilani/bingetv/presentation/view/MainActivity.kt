package com.arbelkilani.bingetv.presentation.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.arbelkilani.bingetv.R
import com.arbelkilani.bingetv.presentation.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName

    private val mainActivityViewModel: MainActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onResume() {
        super.onResume()

        mainActivityViewModel.fetchData()

        mainActivityViewModel.currentName.observe(this, Observer {
            when(it) {
                true -> Log.i(TAG, "pass to next screen")
                false -> Log.i(TAG, "show error message")
            }
        })

        /*mainActivityViewModel.getGenres().observe(this, Observer {
            when (it.code) {
                200 -> test_tv.text = it.data.toString()
                else -> test_tv.text = it.message
            }
        })*/

        /*
        mainActivityViewModel.genres.observe(this, Observer {
            test_tv.text = test_tv.text.toString() + it.toString()
        })

        mainActivityViewModel.airingToday.observe(this, Observer {
            test_tv.text = test_tv.text.toString() + it.toString()
        }) */

        /*mainActivityViewModel.getGenres().observe(this, Observer {
            Log.i(TAG, "getGenres START\n $it")
            test_tv.text = it.toString()
            Log.i(TAG, "getGenres END\n $it")
        })


        mainActivityViewModel.getAiringToday().observe(this, Observer {
            Log.i(TAG, "getAiringToday START\n $it")
            test_tv.text = "$it${test_tv.text}"
            Log.i(TAG, "getAiringToday END\n $it")
        })*/
    }

}
