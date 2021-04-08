package com.srs.myshop.ui.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.srs.myshop.R
import com.srs.myshop.utils.Constants


class MainActivity : AppCompatActivity() {
    lateinit var tv_user_id: TextView
    lateinit var tv_email_id:TextView
    lateinit var btn_logout: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        //This call the parent constructor
        super.onCreate(savedInstanceState)
        // This is used to align the xml view to this class
        setContentView(R.layout.activity_main)

        // TODO Step 11: Get the user id and email through intent and set them in UI.
        // START

        val sharedPreferences =
            getSharedPreferences(Constants.MYSHOPPAL_PREFERENCES, Context.MODE_PRIVATE)

        val userId = sharedPreferences.getString(Constants.LOGGED_IN_USERID, "")!!
        val emailId = sharedPreferences.getString(Constants.LOGGED_IN_EMAIL, "")!!
        tv_user_id=findViewById(R.id.tv_user_id)
        tv_email_id=findViewById(R.id.tv_email_id)
        btn_logout=findViewById(R.id.btn_logout)
        tv_user_id.text = "User ID :: $userId"
        tv_email_id.text = "Email ID :: $emailId"
        // END

        // TODO Step 12: Assign the logout button click event and add the feature to logout fom the application.
        // START
        btn_logout.setOnClickListener {
            // Logout from app.
            FirebaseAuth.getInstance().signOut()

            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            finish()
        }
        // END
    }
}