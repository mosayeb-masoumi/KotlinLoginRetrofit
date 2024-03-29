package com.example.kotlinlogin.activities


import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.kotlinlogin.R
import com.example.kotlinlogin.api.RetrofitClient
import com.example.kotlinlogin.models.DefaultResponse
import com.example.kotlinlogin.storage.SharedPrefManager
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewLogin.setOnClickListener {

            startActivity(Intent(this@MainActivity,LoginActivity::class.java))

    }



        buttonSignUp.setOnClickListener{

            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString().trim()
            val name = editTextName.text.toString().trim()
            val school = editTextSchool.text.toString().trim()


            if(email.isEmpty()){
                editTextEmail.setError("Email Required")
                editTextEmail.requestFocus()
                return@setOnClickListener   // do nothing
            }

            if(password.isEmpty()){
                editTextPassword.setError("Password Required")
                editTextPassword.requestFocus()
                return@setOnClickListener
            }
            if(name.isEmpty()){
                editTextName.setError("Name Required")
                editTextName.requestFocus()
                return@setOnClickListener
            }

            if(school.isEmpty()){
                editTextSchool.setError("School Required")
                editTextSchool.requestFocus()
                return@setOnClickListener
            }


            RetrofitClient.instance.createUser(email,name,password,school)
          //                      import retrofit2.Callback
                    .enqueue(object: Callback<DefaultResponse> {
                        override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                            Toast.makeText(applicationContext,""+t.message,Toast.LENGTH_SHORT).show()
                        }

                        override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                            Toast.makeText(applicationContext,response.body()?.message,Toast.LENGTH_SHORT).show()
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
