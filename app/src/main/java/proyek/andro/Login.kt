package proyek.andro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.firestore.Filter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.mindrot.jbcrypt.BCrypt
import proyek.andro.adminActivity.AdminActivity
import proyek.andro.helper.StringHelper
import proyek.andro.model.User
import proyek.andro.userActivity.UserActivity
import java.time.LocalDate

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val etEmail = findViewById<TextInputEditText>(R.id.etEmail)
        val etPassword = findViewById<TextInputEditText>(R.id.etPassword)
        val signUpRedirector = findViewById<TextView>(R.id.signUpRedirect)
        val tilEmail = findViewById<TextInputLayout>(R.id.tilEmail)
        val tilPassword = findViewById<TextInputLayout>(R.id.tilPassword)

        val sp = getSharedPreferences("login_session", MODE_PRIVATE)

        val timedOut = intent.getBooleanExtra("timed_out", false)

        if (timedOut) {
            Snackbar.make(
                btnLogin,
                R.string.timedOut,
                Snackbar.LENGTH_SHORT
            ).apply {
                setBackgroundTint(resources.getColor(R.color.light, null))
                setTextColor(resources.getColor(R.color.black, null))

            }.show()
        }

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            lateinit var user : ArrayList<User>

            // reset error state
            tilEmail.isErrorEnabled = false
            tilPassword.isErrorEnabled = false

            // check for error
            if (email.length == 0) tilEmail.error = "Email is required"
            if (password.length == 0) tilPassword.error = "Password is required"

            if (email.length > 0 && password.length > 0) {
                var job = CoroutineScope(Dispatchers.Main).launch {
                    user = User().get(filter = Filter.equalTo("email", email), order = arrayOf(arrayOf("email", "ASC")))
                }

                job.invokeOnCompletion {
                    // if email not found
                    if (user.size == 0) {
                        Snackbar.make(
                            btnLogin,
                            R.string.emailError,
                            Snackbar.LENGTH_SHORT
                        ).apply {
                            setBackgroundTint(resources.getColor(R.color.light, null))
                            setTextColor(resources.getColor(R.color.black, null))

                        }.show()
                    }

                    // login as user
                    else if (user.get(0).role == 0L) {
                        // check user password
                        val authenticated = BCrypt.checkpw(password, user.get(0).password)

                        if (authenticated) {
                            val remember_token = StringHelper().generateRandomString(60)

                            sp.edit()
                                .putString("email", user.get(0).email)
                                .putString("remember_token", remember_token)
                                .putString("expires_at", LocalDate.now().plusDays(30).toString())
                                .putInt("role", 0)
                                .apply()

                            user.get(0).remember_token = remember_token

                            CoroutineScope(Dispatchers.Main).launch {
                                user.get(0).insertOrUpdate()
                            }.invokeOnCompletion {
                                startActivity(Intent(this, UserActivity::class.java))
                            }
                        }
                        else {
                            Snackbar.make(
                                btnLogin,
                                R.string.passError,
                                Snackbar.LENGTH_SHORT
                            ).apply {
                                setBackgroundTint(resources.getColor(R.color.light, null))
                                setTextColor(resources.getColor(R.color.black, null))

                            }.show()
                        }
                    }

                    // login as admin
                    else if (user.get(0).role == 1L) {
                        // check user password
                        val authenticated = BCrypt.checkpw(password, user.get(0).password)

                        if (authenticated) {
                            val remember_token = StringHelper().generateRandomString(60)

                            sp.edit()
                                .putString("email", user.get(0).email)
                                .putString("remember_token", remember_token)
                                .putString("expires_at", LocalDate.now().plusDays(30).toString())
                                .putInt("role", 1)
                                .apply()

                            user.get(0).remember_token = remember_token

                            CoroutineScope(Dispatchers.Main).launch {
                                user.get(0).insertOrUpdate()
                            }.invokeOnCompletion {
                                startActivity(Intent(this, AdminActivity::class.java))
                            }
                        }
                        else {
                            Snackbar.make(
                                btnLogin,
                                R.string.passError,
                                Snackbar.LENGTH_SHORT
                            ).apply {
                                setBackgroundTint(resources.getColor(R.color.light, null))
                                setTextColor(resources.getColor(R.color.black, null))

                            }.show()
                        }
                    }
                }
            }
        }

        signUpRedirector.setOnClickListener {
            startActivity(Intent(this, Register::class.java))
        }
    }
}