package com.swarawan.try1.ui.login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.constraint.ConstraintSet
import android.transition.ChangeBounds
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.swarawan.corelibrary.base.CoreActivity
import com.swarawan.corelibrary.extensions.safeDispose
import com.swarawan.corelibrary.extensions.showSnackbar
import com.swarawan.corelibrary.firebase.FirebaseConstant
import com.swarawan.corelibrary.firebase.auth.FirebaseAuthConfigService
import com.swarawan.corelibrary.utils.TextUtils
import com.swarawan.try1.R
import com.swarawan.try1.RosidFoodApplication
import com.swarawan.try1.common.AppPreference
import com.swarawan.try1.ui.main.MainActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by Rio Swarawan on 8/23/18.
 */
class LoginActivity : CoreActivity() {

    @Inject
    lateinit var appPreference: AppPreference

    @Inject
    lateinit var authConfigService: FirebaseAuthConfigService

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    private var googleSignInOptions: GoogleSignInOptions? = null
    private var googleSignInClient: GoogleSignInClient? = null

    override fun initInjection() {
        (application as RosidFoodApplication).defaultComponent.inject(this@LoginActivity)
    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        initApplication()
        initGoogleSignIn()
        buttonGoogleSignIn.setOnClickListener { signIn() }
    }

    override fun setLayout() {
        setContentView(R.layout.activity_login)
    }

    private fun initApplication() {
        when {
            appPreference.accessToken.isEmpty() -> buttonGoogleSignIn.visibility = View.VISIBLE
            else -> startToApp()
        }
    }

    private fun initGoogleSignIn() {
        googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).apply {
            requestIdToken(getString(R.string.default_web_client_id))
            requestEmail()
            requestId()
            requestProfile()
        }.build()

        googleSignInOptions?.let {
            googleSignInClient = GoogleSignIn.getClient(this, it)
        }
    }

    private fun signIn() {
        val googleIntent = googleSignInClient?.signInIntent
        startActivityForResult(googleIntent, FirebaseConstant.SIGN_IN_RESULT_CODE)
    }

    private fun startToApp() {
        startActivity<MainActivity>()
        finish()
    }

    private fun authWithGoogle(account: GoogleSignInAccount?) {
        account?.let {
            authConfigService.signInWithCredentials(this@LoginActivity, firebaseAuth, account,
                    onSuccess = {
                        val user = firebaseAuth.currentUser
                        when (user) {
                            null -> Log.d("Auth With Google", "user null")
                            else -> {
                                appPreference.setCredential(it.displayName, it.id, it.email, "", it.idToken)
                                startToApp()
                            }
                        }
                    },
                    onFailed = {
                        rootLayout.showSnackbar(resources.getString(R.string.failed_signin_google))
                    })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            FirebaseConstant.SIGN_IN_RESULT_CODE -> {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    authWithGoogle(account)
                } catch (ex: ApiException) {
                    rootLayout.showSnackbar(resources.getString(R.string.failed_signin_google))
                }
            }
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }
}