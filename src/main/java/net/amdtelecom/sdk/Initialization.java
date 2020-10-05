package net.amdtelecom.sdk;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Initialization {

    private static RequestQueue queue;
    private static Context initializedContext;

    public static void init (Context context){
        queue = Volley.newRequestQueue(context);
        initializedContext = context;
    }

    public static void getRequest(){
        // Request a string response from the provided URL.
        String requestUrl = getString("api_endpoint") + getString("waymore_implementation_id");

        final Map<String, String> params = new HashMap<String, String>();
        params.put("Content-Type", "application/json");
        params.put("Origin", getString("web_application_domain"));

        StringRequest stringRequest = new StringRequest(Request.Method.GET, requestUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.v("Init Function","Response is: " + response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("Init Error","This did not work " + error.getMessage());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public static String getString(String id){
        try {
            ApplicationInfo ai = initializedContext.getPackageManager().getApplicationInfo( initializedContext.getPackageName(), PackageManager.GET_META_DATA );
            return (String)ai.metaData.get( id );
        } catch ( PackageManager.NameNotFoundException e ) {
            return "";
        }
    }
}
