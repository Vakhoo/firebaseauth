package ge.msda.firebaseauth

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*




class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        auth = FirebaseAuth.getInstance()

        if (auth.currentUser == null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "Already logged in", Toast.LENGTH_LONG).show()
        }

        setContentView(R.layout.activity_main)



        logoutBtn.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        updatePasswordBtn.setOnClickListener {
            val intent = Intent(this, UpdatePasswordActivity::class.java)
            startActivity(intent)
        }


        init()

        clear.setOnClickListener {
            val n: String=""
            val p: String=""
            val m: String=""
            val a: String=""
            contactInfo(n,p,m,a)
        }

        saveBtn.setOnClickListener {

            val n: String = inputFullName.text.toString()
            val p: String = inputPhone.text.toString()
            val m: String = inputMail.text.toString()
            val a: String = inputAddress.text.toString()

            if (TextUtils.isEmpty(n)) {
                Toast.makeText(this, "Empty name!", Toast.LENGTH_LONG).show()
            } else {
                contactInfo(n, p,m,a)
            }

        }

    }

    private fun init() {

        auth = FirebaseAuth.getInstance()
        db = FirebaseDatabase.getInstance().getReference("UserInfo")

        addUserInfoChangeListener()

    }

    private fun contactInfo(name: String, phone: String?,mail: String?,address: String?) {
        val userInfo = UserInfo(name, phone, mail, address)
        db.child(auth.currentUser?.uid!!).setValue(userInfo)
    }

    private fun addUserInfoChangeListener() {

        db.child(auth.currentUser?.uid!!)
            .addValueEventListener(object : ValueEventListener {

                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(snap: DataSnapshot) {

                    val userInfo: UserInfo = snap.getValue(UserInfo::class.java) ?: return

                    showFullName.text = userInfo.name
                    showPhone.text = userInfo.mobile ?: ""
                    showAddress.text = userInfo.address ?: ""
                    showMail.text = userInfo.mail ?: ""

                    inputFullName.setText("")
                    inputPhone.setText("")
                    inputAddress.setText("")
                    inputMail.setText("")

                }

            })

    }

}

