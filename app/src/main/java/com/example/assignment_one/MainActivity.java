package com.example.assignment_one;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    EditText editTextPhone;
    EditText editTextPassword;




    Button button;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        editTextPassword=findViewById(R.id.editTextPassword);


        editTextPhone= findViewById(R.id.editTextPhone);
        button= findViewById(R.id.button);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendDataToApi();
            }
        });




    }

    private void sendDataToApi() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://192.168.29.80/membershipApp/login.php"; // Replace with your actual API endpoint URL

        // Get the data from the EditText fields
        final String phoneNumber = editTextPhone.getText().toString().trim();

        final String password = editTextPassword.getText().toString().trim();




        // Create a StringRequest
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            // Convert the response string to a JSON object
                            JSONObject jsonResponse = new JSONObject(response);

                            // Check the 'status' key in the JSON response
                            boolean status = jsonResponse.getBoolean("status");

                            if (status) {
                                // Handle the response from the server
                                Toast.makeText(getApplicationContext(), "Successfully data send to server " + response, Toast.LENGTH_SHORT).show();


                            } else {
                                Toast.makeText(getApplicationContext(), "Response: Data not inserted in the database.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error parsing JSON response.", Toast.LENGTH_SHORT).show();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                        // Handle errors
                        Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Set the parameters to be sent to the server
                Map<String, String> params = new HashMap<>();
                params.put("mobileNo", phoneNumber);
                params.put("password",password);

                return params;
            }
        };




        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}