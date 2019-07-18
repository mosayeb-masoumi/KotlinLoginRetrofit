package com.example.kotlinlogin.activities


import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.kotlinlogin.R
import com.example.kotlinlogin.api.RetrofitClient
import com.example.kotlinlogin.models.LoginResponse
import com.example.kotlinlogin.storage.SharedPrefManager
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        buttonLogin.setOnClickListener {

            val email = editTextEmail.text.toString().trim()
            val  password = editTextPassword.text.toString().trim()


            if(email.isEmpty()){
                editTextEmail.setError("email required")
                editTextEmail.requestFocus()
                return@setOnClickListener
            }
            if(password.isEmpty()){
                editTextPassword.setError("password required")
                editTextPassword.requestFocus()
                return@setOnClickListener
            }


            RetrofitClient.instance.userLogin(email,password)
                    .enqueue(object :Callback<LoginResponse> {
                        override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                            Toast.makeText(applicationContext,""+t.message,Toast.LENGTH_SHORT).show()
                        }

                        override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {

                            if(!response.body()?.error!!){

                              SharedPrefManager.getInstance(applicationContext).saveUser(response.body()?.user!!)

                               val intent = Intent(applicationContext,ProfileActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)



                            }else{
                                Toast.makeText(applicationContext, response.body()?.message,Toast.LENGTH_SHORT).show()
                            }

                        }

                    })





        }
    }



    // if we logged in before then goto profile actiity or else
    override fun onStart() {
        super.onStart()
        if(SharedPrefManager.getInstance(this).isLoggedIn){
            val intent = Intent(applicationContext,ProfileActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }


}
