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
import proyek.andro.helper.StringHelper
import proyek.andro.model.User
import java.util.UUID

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val loginRedirector = findViewById<TextView>(R.id.loginRedirect)

        val etEmail = findViewById<TextInputEditText>(R.id.etEmail)
        val etPassword = findViewById<TextInputEditText>(R.id.etPassword)
        val etPasswordConfirm = findViewById<TextInputEditText>(R.id.etPasswordConfirm)
        val etName = findViewById<TextInputEditText>(R.id.etName)
        val etPhone = findViewById<TextInputEditText>(R.id.etPhone)
        val etAge = findViewById<TextInputEditText>(R.id.etAge)

        val tilEmail = findViewById<TextInputLayout>(R.id.tilEmail)
        val tilPassword = findViewById<TextInputLayout>(R.id.tilPassword)
        val tilPasswordConfirm = findViewById<TextInputLayout>(R.id.tilPasswordConfirm)
        val tilName = findViewById<TextInputLayout>(R.id.tilName)
        val tilPhone = findViewById<TextInputLayout>(R.id.tilPhone)
        val tilAge = findViewById<TextInputLayout>(R.id.tilAge)

        btnRegister.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            val passwordConfirm = etPasswordConfirm.text.toString()
            val name = etName.text.toString()
            val phone = etPhone.text.toString()
            val age = etAge.text.toString().toLong()

            if (email.length == 0) tilEmail.error = "Email is required"
            if (password.length == 0) tilPassword.error = "Password is required"
            if (passwordConfirm.length == 0) tilPasswordConfirm.error = "Password confirmation is required"
            if (name.length == 0) tilName.error = "Name is required"
            if (phone.length == 0) tilPhone.error = "Phone is required"
            if (age.toString().length == 0) tilAge.error = "Age is required"
            if (password != passwordConfirm) tilPasswordConfirm.error = "Password confirmation must match password"

            if (email.length > 0 &&
                password.length > 0 &&
                passwordConfirm.length > 0 &&
                name.length > 0 &&
                phone.length > 0 &&
                age.toString().length > 0 &&
                password == passwordConfirm
            ) {
                CoroutineScope(Dispatchers.Main).launch {
                    // check if email already exists
                    val userExists = User().get<User>(
                        filter = Filter.equalTo("email", email),
                        order = arrayOf(arrayOf("email", "asc"))
                    ).size > 0

                    if (!userExists) {
                        val user = User(
                            id = UUID.randomUUID().toString(),
                            email = email,
                            password = BCrypt.hashpw(password, BCrypt.gensalt()),
                            name = name,
                            phone = phone,
                            remember_token = StringHelper().generateRandomString(60),
                            role = 0,
                            age = age
                        )

                        // make new user
                        user.insertOrUpdate()
                        startActivity(Intent(this@Register, Login::class.java))
                    }

                    else {
                        Snackbar.make(
                            btnRegister,
                            R.string.accountExist,
                            Snackbar.LENGTH_SHORT
                        ).apply {
                            setBackgroundTint(resources.getColor(R.color.light, null))
                            setTextColor(resources.getColor(R.color.black, null))
                        }.show()
                    }
                }
            }
        }

        loginRedirector.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
        }
    }
}