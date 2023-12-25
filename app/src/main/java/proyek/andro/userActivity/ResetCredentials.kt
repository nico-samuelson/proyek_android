package proyek.andro.userActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.text.method.PasswordTransformationMethod
import android.widget.ImageView
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.mindrot.jbcrypt.BCrypt
import proyek.andro.Login
import proyek.andro.R
import proyek.andro.model.User

class ResetCredentials : AppCompatActivity() {
    lateinit var etCurrent : TextInputEditText
    lateinit var etNew : TextInputEditText
    lateinit var etNewConfirm : TextInputEditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_credentials)

        val type = intent.getStringExtra("type")
        val user_id = intent.getStringExtra("user_id")

        etCurrent = findViewById(R.id.etCurrent)
        etNew = findViewById(R.id.etNew)
        etNewConfirm = findViewById(R.id.etNewConfirm)

        val btnBack : ImageView = findViewById(R.id.btnBack)
        val btnSave : MaterialButton = findViewById(R.id.btnSave)

        var user : User? = null

        if (type == "password") {
            val ilCurrent : TextInputLayout = findViewById(R.id.ilCurrent)
            val ilNew : TextInputLayout = findViewById(R.id.ilNew)
            val ilNewConfirm : TextInputLayout = findViewById(R.id.ilNewConfirm)

            ilCurrent.setHint("Current Password")
            etCurrent.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
            etCurrent.setTransformationMethod(PasswordTransformationMethod.getInstance())

            ilNew.setHint("New Password")
            etNew.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
            etNew.setTransformationMethod(PasswordTransformationMethod.getInstance())

            ilNewConfirm.setHint("Confirm New Password")
            etNewConfirm.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
            etNewConfirm.setTransformationMethod(PasswordTransformationMethod.getInstance())
        }

        btnBack.setOnClickListener {
            startActivity(Intent(this@ResetCredentials, UserActivity::class.java))
        }

        CoroutineScope(Dispatchers.Main).launch {
            user = User().find(user_id ?: "")
        }.invokeOnCompletion {
            btnSave.setOnClickListener {
                if (validate()) {
                    if (type == "password") {
                        if (BCrypt.checkpw(etCurrent.text.toString(), user?.password ?: "")) {
                            if (etNew.text.toString() == etNewConfirm.text.toString()) {

                                CoroutineScope(Dispatchers.Main).launch {
                                    user?.password = BCrypt.hashpw(etNew.text.toString(), BCrypt.gensalt())
                                    user?.insertOrUpdate()

                                    val intent = Intent(this@ResetCredentials, Login::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    startActivity(intent)
                                }
                            } else {
                                Snackbar.make(
                                    it,
                                    "New Password and Confirm New Password must be the same",
                                    Snackbar.LENGTH_SHORT
                                )
                                    .apply {
                                        setBackgroundTint(resources.getColor(R.color.light, null))
                                        setTextColor(resources.getColor(R.color.black, null))
                                    }.show()
                            }
                        } else {
                            Snackbar.make(it, "Wrong Password", Snackbar.LENGTH_SHORT).apply {
                                setBackgroundTint(resources.getColor(R.color.light, null))
                                setTextColor(resources.getColor(R.color.black, null))
                            }.show()
                        }
                    }
                    else if (type == "email") {
                        if (etCurrent.text.toString() == user?.email) {
                            if (etNew.text.toString() == etNewConfirm.text.toString()) {
                                CoroutineScope(Dispatchers.Main).launch {
                                    user?.email = etNew.text.toString()
                                    user?.insertOrUpdate()

                                    val intent = Intent(this@ResetCredentials, Login::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    startActivity(intent)
                                }
                            } else {
                                Snackbar.make(
                                    it,
                                    "New Email and Confirm New Email must be the same",
                                    Snackbar.LENGTH_SHORT
                                )
                                    .apply {
                                        setBackgroundTint(resources.getColor(R.color.light, null))
                                        setTextColor(resources.getColor(R.color.black, null))
                                    }.show()
                            }
                        }
                    }
                }
                else {
                    Snackbar.make(it, "Please fill all the fields", Snackbar.LENGTH_SHORT).apply {
                        setBackgroundTint(resources.getColor(R.color.light, null))
                        setTextColor(resources.getColor(R.color.black, null))
                    }.show()
                }
            }
        }
    }

    fun validate() : Boolean {
        if (etCurrent.text.toString().isEmpty() || etNew.text.toString().isEmpty() || etNewConfirm.text.toString().isEmpty()) {
            return false
        }

        return true
    }
}