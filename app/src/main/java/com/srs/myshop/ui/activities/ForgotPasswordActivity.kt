package com.srs.myshop.ui.activities

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.srs.myshop.R
import com.srs.myshop.utils.MSPEditText
import androidx.appcompat.widget.Toolbar
// TODO Step 1: Create a Forgot Password Activity.
// START
/**
 * Forgot Password Screen of the application.
 */
class ForgotPasswordActivity : BaseActivity() {

    /**
     * This function is auto created by Android when the Activity Class is created.
     */
    lateinit var btn_submit: Button
    lateinit var et_email_forgot_pwd:MSPEditText
    lateinit var toolbar_forgot_password_activity: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        //This call the parent constructor
        super.onCreate(savedInstanceState)
        // This is used to align the xml view to this class
        setContentView(R.layout.activity_forgot_password)

        // TODO Step 7: Call the setup action bar function.
        // START
        setupActionBar()
        btn_submit=findViewById(R.id.btn_submit)

        toolbar_forgot_password_activity=findViewById(R.id.toolbar_forgot_password_activity)
        btn_submit.setOnClickListener {
            et_email_forgot_pwd=findViewById(R.id.et_email_forgot_pwd)
            // Get the email id from the input field.
            val email: String = et_email_forgot_pwd.text.toString().trim { it <= ' ' }

            // Now, If the email entered in blank then show the error message or else continue with the implemented feature.
            if (email.isEmpty()) {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email), true)
            } else {

                // Show the progress dialog.
                showProgressDialog(resources.getString(R.string.please_wait))

                // This piece of code is used to send the reset password link to the user's email id if the user is registered.
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->

                        // Hide the progress dialog
                        hideProgressDialog()

                        if (task.isSuccessful) {
                            // Show the toast message and finish the forgot password activity to go back to the login screen.
                            Toast.makeText(
                                this@ForgotPasswordActivity,
                                resources.getString(R.string.email_sent_success),
                                Toast.LENGTH_LONG
                            ).show()

                            finish()
                        } else {
                            showErrorSnackBar(task.exception!!.message.toString(), true)
                        }
                    }
            }
        }

    }


    // TODO Step 6: Create a function to setup the action bar.
    // START
    /**
     * A function for actionBar Setup.
     */
    private fun setupActionBar() {

        setSupportActionBar(toolbar_forgot_password_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        }

        toolbar_forgot_password_activity.setNavigationOnClickListener { onBackPressed() }
    }
    // END
}
// END