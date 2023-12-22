package proyek.andro.userActivity

import android.app.AlertDialog
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.firestore.Filter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import proyek.andro.Login
import proyek.andro.R
import proyek.andro.model.UserFavorite
import kotlin.math.log

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFr.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFr : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var parent : UserActivity
    lateinit var user_name : TextView
    lateinit var delAcc : ConstraintLayout
    lateinit var logout : ConstraintLayout
    lateinit var changePass : ConstraintLayout
    lateinit var changeEmail : ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        parent = super.requireActivity() as UserActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        user_name = view.findViewById(R.id.user_name)
        delAcc = view.findViewById(R.id.delAcc)
        logout = view.findViewById(R.id.logout)
        changePass = view.findViewById(R.id.changePass)
        changeEmail = view.findViewById(R.id.changeEmail)
        val sp = parent.getSharedPreferences("login_session", MODE_PRIVATE)

        user_name.text = parent.getUser()?.name

        logout.setOnClickListener {
            sp.edit().clear().apply()

            val intent = Intent(parent, Login::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        delAcc.setOnClickListener {
            val builder = MaterialAlertDialogBuilder(parent)
                .setTitle("Delete Account")
                .setMessage("Are you sure you want to delete your account?")
                .setNegativeButton("Cancel") { dialog, which ->
                    dialog.dismiss()
                }
                .setPositiveButton("Delete") { dialog, which ->
                    CoroutineScope(Dispatchers.Main).launch {
                        val user = parent.getUser()

                        UserFavorite().get<UserFavorite>(
                            filter = Filter.equalTo("user", user!!.id),
                            limit = 10,
                            order = arrayOf(arrayOf("user", "desc"))
                        ).forEach {
                            it.delete(it.id)
                        }
                        user?.delete(user.id)
                        sp.edit().clear().apply()

                        startActivity(Intent(parent, Login::class.java))
                    }
                }

            val dialog = builder.create()
            dialog.show()

            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(resources.getColor(R.color.black))
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(resources.getColor(R.color.red))
        }

        changeEmail.setOnClickListener {
            val intent = Intent(parent, ResetCredentials::class.java)
            intent.putExtra("type", "email")
            intent.putExtra("user_id", parent.getUser()?.id)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
        }

        changePass.setOnClickListener {
            val intent = Intent(parent, ResetCredentials::class.java)
            intent.putExtra("type", "password")
            intent.putExtra("user_id", parent.getUser()?.id)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFr.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFr().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}