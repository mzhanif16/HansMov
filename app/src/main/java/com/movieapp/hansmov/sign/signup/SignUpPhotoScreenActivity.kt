package com.movieapp.hansmov.sign.signup

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.movieapp.hansmov.home.HomeActivity
import com.movieapp.hansmov.R
import com.movieapp.hansmov.utils.Preferences
import java.util.UUID

val REQUEST_IMAGE_CAPTURE = 1
    var statusAdd:Boolean = false
    lateinit var filePath: Uri

    lateinit var storage: FirebaseStorage
    lateinit var storagereferensi: StorageReference
    lateinit var preferences: Preferences
    lateinit var tv_hello: TextView
    lateinit var iv_add: ImageView
    lateinit var iv_profile:ImageView
    lateinit var btn_simpan: Button
    lateinit var btn_uploadnanti: Button
class SignUpPhotoScreenActivity : AppCompatActivity(), PermissionListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_photo_screen)

        preferences = Preferences(this)
        storage = FirebaseStorage.getInstance()
        storagereferensi = storage.getReference()
        tv_hello = findViewById(R.id.tv_hello)
        iv_add = findViewById(R.id.add)
        iv_add = findViewById(R.id.iv_photoprofile)
        btn_simpan = findViewById(R.id.btn_simpan)
        btn_uploadnanti = findViewById(R.id.btn_uploadnanti)

        tv_hello.text = "Selamat Datang\n" + intent.getStringExtra("nama")
        iv_add.setOnClickListener {
            if (statusAdd) {
                statusAdd = false
                btn_simpan.visibility = View.VISIBLE
                iv_add.setImageResource(R.drawable.ic_baseline_add_24)
                iv_profile.setImageResource(R.drawable.photo)
            } else{
                Dexter.withActivity(this)
                    .withPermission(Manifest.permission.CAMERA)
                    .withListener(this)
                    .check()

//                ImagePicker.with(this)
//                    .cameraOnly()	//User can only capture image using Camera
//                    .start()
            }
        }

        btn_uploadnanti.setOnClickListener {
            finishAffinity()

            var goHomeActivity = Intent(this@SignUpPhotoScreenActivity, HomeActivity::class.java)
            startActivity(goHomeActivity)
        }

        btn_simpan.setOnClickListener {
            if(filePath != null){
                var progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Uploading ...")
                progressDialog.show()

                var ref = storagereferensi.child("image/" + UUID.randomUUID().toString())
                ref.putFile(filePath)
                    .addOnSuccessListener {
                        progressDialog.dismiss()
                        Toast.makeText(this,"Upload Berhasil",Toast.LENGTH_SHORT).show()

                        ref.downloadUrl.addOnSuccessListener{
                            preferences.setValues("url", it.toString())
                        }

                        finishAffinity()
                        var goHomeActivity = Intent(this@SignUpPhotoScreenActivity, HomeActivity::class.java)
                        startActivity(goHomeActivity)
                    }

                    .addOnFailureListener{
                        progressDialog.dismiss()
                        Toast.makeText(this,"Upload Gagal",Toast.LENGTH_SHORT).show()
                    }
                    .addOnProgressListener {
                        TaskSnapshot -> var progress = 100.0 * TaskSnapshot.bytesTransferred / TaskSnapshot.totalByteCount
                        progressDialog.setMessage("Upload" + progress.toInt()+" %")
                    }
            }else{
//                Dexter.withActivity(this)
//                    .withPermission(android.Manifest.permission.CAMERA)
//                    .withListener(this)
//                    .check()
            }
        }

    }

    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also {
            takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
        Toast.makeText(this,"Anda tidak bisa menambahkan foto profil",Toast.LENGTH_SHORT).show()
    }

    override fun onPermissionRationaleShouldBeShown(
        permission: PermissionRequest?,
        token: PermissionToken?
    ) {

    }

    override fun onBackPressed() {
        Toast.makeText(this,"Tergesah? klik tombol upload nanti aja", Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK){
            var bitmap = data?.extras?.get("data") as Bitmap
            statusAdd = true

            filePath = data.getData()!!
            Glide.with(this)
                .load(bitmap)
                .apply(RequestOptions.circleCropTransform())
                .into(iv_profile)

            btn_simpan.visibility = View.VISIBLE
            iv_add.setImageResource(R.drawable.ic_baseline_delete_24)
        }
    }
}