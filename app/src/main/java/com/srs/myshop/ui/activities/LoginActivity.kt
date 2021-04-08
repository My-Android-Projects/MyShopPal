package com.srs.myshop.ui.activities


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.WindowManager
import com.srs.myshop.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.srs.myshop.firestore.FirestoreClass
import com.srs.myshop.models.User
import com.srs.myshop.utils.*

@Suppress("DEPRECATION")
class LoginActivity : BaseActivity() {

    /**
     * This function is auto created by Android when the Activity Class is created.
     */

    lateinit var tv_register: MSPTextViewBold
    lateinit var btn_login: MSPButton
   lateinit var tv_forgot_password: MSPTextView
    lateinit var  et_email: MSPEditText
    lateinit var   et_password:MSPEditText
    override fun onCreate(savedInstanceState: Bundle?) {
        //This call the parent constructor
        super.onCreate(savedInstanceState)
        // This is used to align the xml view to this class
        setContentView(R.layout.activity_login)

        // This is used to hide the status bar and make the login screen as a full screen activity.
        // It is deprecated in the API level 30. I will update you with the alternate solution soon.
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )



        // TODO Step 7: Assign a onclick event to the register text to launch the register activity.
        // START
        tv_register=findViewById(R.id.tv_register)
        et_email=findViewById(R.id.et_email)
        et_password=findViewById(R.id.et_password)
        tv_register.setOnClickListener {


            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)

        }
        btn_login=findViewById(R.id.btn_login)
        btn_login.setOnClickListener()
        {

            logInRegisteredUser()
        }
        tv_forgot_password=findViewById(R.id.tv_forgot_password)
        tv_forgot_password.setOnClickListener()
        {
            val intent = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }
    }


    private fun validateLoginDetails(): Boolean {
        return when {
            TextUtils.isEmpty(et_email.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email), true)
                false
            }

            TextUtils.isEmpty(et_password.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password), true)
                false
            }

            else -> {

              true
            }
        }
    }
    private fun logInRegisteredUser()
    {
        if(validateLoginDetails())
        {
            showProgressDialog(resources.getString(R.string.please_wait))
            val email=et_email.text.toString().trim { it <= ' ' }
            val password=et_password.text.toString().trim { it <= ' ' }
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password).addOnCompleteListener(
                OnCompleteListener<AuthResult>
                { task ->

                    if (task.isSuccessful) {
                       /* val firebaseUser: FirebaseUser = FirebaseAuth.getInstance().currentUser

                        showErrorSnackBar("You are logged in successfully.", false)
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        intent.flags=Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        intent.putExtra("user_id",firebaseUser.uid)
                        intent.putExtra("email_id",email)
                        startActivity(intent)
                        finish()*/
                        FirestoreClass().getUserDetails(this@LoginActivity)

                    }
                    else{
                        hideProgressDialog()
                        showErrorSnackBar(task.exception!!.message.toString(), true)

                    }
                }
            )
        }

    }
    fun userLoggedInSuccess(user: User) {

        // Hide the progress dialog.
        hideProgressDialog()

        // Print the user details in the log as of now.
        Log.i("First Name: ", user.firstName)
        Log.i("Last Name: ", user.lastName)
        Log.i("Email: ", user.email)

        // Redirect the user to Main Screen after log in.
       if(user.profileCompleted==0) {
           var intent = Intent(this@LoginActivity, UserProfileActivity::class.java)
           intent.putExtra(Constants.EXTRA_USER_DETAILS,user)
           startActivity(intent)
       }
       else
        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        finish()
    }
    // END
}
