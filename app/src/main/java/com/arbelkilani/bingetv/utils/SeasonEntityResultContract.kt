package com.arbelkilani.bingetv.utils

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.arbelkilani.bingetv.domain.entities.season.SeasonEntity

class SeasonEntityResultContract : ActivityResultContract<Int, SeasonEntity>() {

    override fun createIntent(context: Context, input: Int?): Intent {
        TODO("Not yet implemented")
    }

    override fun parseResult(resultCode: Int, intent: Intent?): SeasonEntity {
        TODO("Not yet implemented")
    }


}