package com.movieapp.hansmov.sign.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.*
import com.movieapp.hansmov.R
import com.movieapp.hansmov.sign.signin.User


lateinit var edt_username: EditText
    lateinit var edt_password: EditText
    lateinit var edt_namalengkap: EditText
    lateinit var edt_email: EditText
    lateinit var btn_lanjutkan: Button
    lateinit var iUsername: String
    lateinit var iPassword: String
    lateinit var iNama: String
    lateinit var iEmail: String

    lateinit var mFirebaseInstance: FirebaseDatabase
    lateinit var mDatabase: DatabaseReference
    lateinit var mDatabaseReference: DatabaseReference

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        mFirebaseInstance = FirebaseDatabase.getInstance()
        mDatabase = FirebaseDatabase.getInstance().getReference()
        mDatabaseReference = mFirebaseInstance.getReference("User")

        edt_username = findViewById(R.id.edt_usernamesignup)
        edt_password = findViewById(R.id.edt_passwordsignup)
        edt_namalengkap = findViewById(R.id.edt_name)
        edt_email = findViewById(R.id.edt_email)
        btn_lanjutkan = findViewById(R.id.btn_lanjutkandaftar)

        btn_lanjutkan.setOnClickListener {
            iUsername = edt_username.text.toString()
            iPassword = edt_password.text.toString()
            iNama = edt_namalengkap.text.toString()
            iEmail = edt_email.text.toString()

            if(iUsername.equals("")){
                edt_username.error = "Silahkan isi username anda !"
                edt_username.requestFocus()
            }else if (iPassword.equals("")){
                edt_password.error = "Silahkan isi password anda !"
                edt_password.requestFocus()
            }else if (iNama.equals("")){
                edt_namalengkap.error = "Silahkan isi nama anda !"
                edt_namalengkap.requestFocus()
            }else if (iEmail.equals("")){
                edt_email.error = "Silahkan isi email anda !"
                edt_email.requestFocus()
            }else{
                saveUsername (iUsername, iPassword, iNama, iEmail)
            }

        }
    }

    private fun saveUsername(iUsername: String, iPassword: String, iNama: String, iEmail: String) {
        var user = User()
        user.email = iEmail
        user.username = iUsername
        user.nama = iNama
        user.password = iPassword


        if (iUsername != null) {
            CheckingUsername(iUsername, user)
        }
    }

    private fun CheckingUsername(iUsername: String, data: User) {
        mDatabaseReference.child(iUsername).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var user = dataSnapshot.getValue(User:: class.java)
                if (user == null){
                    mDatabaseReference.child(iUsername).setValue(data)

                    var goSignUpPhotoScreen = Intent(this@SignUpActivity,
                        SignUpPhotoScreenActivity::class.java).putExtra("nama", data.nama)
                    startActivity(goSignUpPhotoScreen)

                } else{
                    Toast.makeText(this@SignUpActivity,"User Sudah Digunakan", Toast.LENGTH_LONG).show()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@SignUpActivity,""+databaseError.message, Toast.LENGTH_LONG).show()
            }

        })
    }
}