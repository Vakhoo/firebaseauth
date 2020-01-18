package ge.msda.firebaseauth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        auth = FirebaseAuth.getInstance()

        signUpBtn.setOnClickListener {

            val email: String = email.text.toString()
            val password: String = password.text.toString()

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {

                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_LONG).show()

            } else {

                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, OnCompleteListener { task ->
                        if (task.isSuccessful) {

                            Toast.makeText(this, "Successfully Registered", Toast.LENGTH_LONG)
                                .show()
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()

                        } else {

                            Toast.makeText(this, "Registration Failed", Toast.LENGTH_LONG).show()

                        }
                    })

            }

        }

        loginBtn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }


}