package za.co.seanix.androidstudiosandbox.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

import za.co.seanix.androidstudiosandbox.R;
import za.co.seanix.androidstudiosandbox.Utils.RequestQueueSingleton;

/**
 * Created by Sean on 2/3/2017.
 */

public class AndroidVersionsActivity extends FragmentActivity {

    private ListView listView;
    private ImageView mImageView;
    String[] android_versions;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_layout);
        FetchImage();
    }



    private void OnJSONFetch(String response) {
        Toast.makeText(this, response, Toast.LENGTH_LONG).show();
    }


    private void FetchImage() {
        mImageView = (ImageView) findViewById(R.id.imageView);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnImageSelect();
            }
        });

        String url = "http://www.seanix.co.za/android/raccoon.jpg";

        ImageRequest request = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        mImageView.setImageBitmap(response);
                    }
                }, 0, 0, null,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });

        request.setTag("FetchImage");

        RequestQueueSingleton.getInstance(this).addToRequestQueue(request);
    }

    public void OnImageSelect() {
        startActivity(new Intent(this, GoogleSignInActivity.class));
    }
}



