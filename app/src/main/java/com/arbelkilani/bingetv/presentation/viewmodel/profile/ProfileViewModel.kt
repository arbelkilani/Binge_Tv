package com.arbelkilani.bingetv.presentation.viewmodel.profile

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arbelkilani.bingetv.R
import com.arbelkilani.bingetv.domain.entities.genre.GenreEntity
import com.arbelkilani.bingetv.domain.entities.profile.StatisticsEntity
import com.arbelkilani.bingetv.domain.usecase.profile.ProfileUseCase
import com.arbelkilani.bingetv.domain.usecase.profile.StatisticsUseCase
import com.arbelkilani.bingetv.presentation.viewmodel.BaseViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val statisticsUseCase: StatisticsUseCase,
    private val profileUseCase: ProfileUseCase
) : BaseViewModel() {

    companion object {
        private const val TAG = "ProfileViewModel"
    }

    private lateinit var googleSignInClient: GoogleSignInClient
    private var firebaseAuth: FirebaseAuth = Firebase.auth

    private val _statistics = MutableLiveData<StatisticsEntity>()
    val statistics: LiveData<StatisticsEntity>
        get() = _statistics

    private val _genres = MutableLiveData<List<GenreEntity>>()
    val genres: LiveData<List<GenreEntity>>
        get() = _genres

    private val _signInIntent = MutableLiveData<Intent>()
    val signInIntent: LiveData<Intent>
        get() = _signInIntent

    private val _firebaseUser = MutableLiveData<FirebaseUser>()
    val firebaseUser: LiveData<FirebaseUser>
        get() = _firebaseUser

    fun refresh() {
        getStatistics()
    }

    private fun getStatistics() {
        scope.launch(Dispatchers.IO) {
            _statistics.postValue(statisticsUseCase.getStatistics())
            _genres.postValue(statisticsUseCase.getGenres())
        }
    }

    fun onCreate(context: Context) {
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(context, googleSignInOptions)
        _signInIntent.postValue(googleSignInClient.signInIntent)

        firebaseAuth.currentUser?.let {
            _firebaseUser.postValue(it)
        }
    }

    fun getSignedInAccountFromIntent(data: Intent?) {
        profileUseCase.getSignedInAccountFromIntent(data)?.let { it ->
            firebaseAuth.signInWithCredential(it)
                .addOnSuccessListener {
                    scope.launch(Dispatchers.IO) { profileUseCase.saveUser(firebaseAuth.currentUser) }
                    _firebaseUser.postValue(firebaseAuth.currentUser)
                }.addOnFailureListener { exception ->
                    Log.i(TAG, "Exception : ${exception.localizedMessage}")
                }
        }
    }

    fun signOut() {
        firebaseAuth.signOut()
        googleSignInClient.signOut().addOnCompleteListener {
            _firebaseUser.postValue(null)
        }
    }
}