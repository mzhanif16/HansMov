package com.movieapp.hansmov.sign.signin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.*
import com.movieapp.hansmov.home.HomeActivity
import com.movieapp.hansmov.R
import com.movieapp.hansmov.sign.signup.SignUpActivity
import com.movieapp.hansmov.utils.Preferences

    lateinit var edtusername: EditText
    lateinit var edtpassword: EditText
    lateinit var btnsignin: Button
    lateinit var btnsigup: Button
    lateinit var iUsername: String
    lateinit var iPassword: String
    lateinit var mDatabase: DatabaseReference

class SignInActivity : AppCompatActivity() {
    lateinit var preference: Preferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        mDatabase = FirebaseDatabase.getInstance().getReference("User")
        preference = Preferences(this)

        preference.setValues("onboarding","1")
        if(preference.getValues("status").equals("1")){
            finishAffinity()
            var goHome = Intent(this@SignInActivity, HomeActivity::class.java)
            startActivity(goHome)
        }

        edtpassword = findViewById(R.id.edt_password)
        edtusername = findViewById(R.id.edt_username)
        btnsignin = findViewById(R.id.btn_signin)
        btnsigup = findViewById(R.id.btn_daftar)


        btnsignin.setOnClickListener {
            iUsername = edtusername.text.toString().trim()
            iPassword = edtpassword.text.toString().trim()

            if(iUsername.equals("")){
                edtusername.error = "Silahkan tulis username anda!"
                edtusername.requestFocus()
            }else if(iPassword.equals("")){
            edtpassword.error = "Silahkan tulis password anda!"
            edtpassword.requestFocus()
        }else {
            pushLogin(iUsername, iPassword)
            }
        }

        btnsigup.setOnClickListener {
            var gosignup = Intent(this@SignInActivity, SignUpActivity::class.java)
            startActivity(gosignup)
        }
    }

    private fun pushLogin(iUsername: String, iPassword: String) {
        mDatabase.child(iUsername).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var user = dataSnapshot.getValue(User::class.java)
                if(user == null){
                    Toast.makeText(this@SignInActivity,"Username Tidak Ditemukan !",
                        Toast.LENGTH_LONG).show()
                }else {

                    if(user.password.equals(iPassword)){

                        preference.setValues("nama", user.nama.toString())
                        preference.setValues("email", user.email.toString())
                        preference.setValues("saldo", user.saldo.toString())
                        preference.setValues("url", user.url.toString())
                        preference.setValues("username", user.username.toString())
                        preference.setValues("status", "1")


                        var intent = Intent (this@SignInActivity, HomeActivity::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this@SignInActivity,"Password salah !",
                            Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@SignInActivity,databaseError.message,
                    Toast.LENGTH_LONG).show()
            }
        })
    }
}