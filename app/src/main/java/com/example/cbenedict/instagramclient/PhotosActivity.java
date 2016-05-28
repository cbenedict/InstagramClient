package com.example.cbenedict.instagramclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class PhotosActivity extends AppCompatActivity {
public static final String CLIENT_ID="e05c462ebd86446ea48a5af73769b602";
    private ArrayList<InstagramPhoto> photos;
    private InstagramPhotosAdapter aPhotos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        // SEND OUT API REQUEST TO POPULAR PHOTOS
        photos=new ArrayList<>();
        //Create the adapter link into the source
        aPhotos=new InstagramPhotosAdapter(this,photos);
        //Find the listView from the layout
        ListView lvPhotos = (ListView) findViewById(R.id.lvPhotos);
        //Fetch the popular photo
        lvPhotos.setAdapter(aPhotos);
        fetchPopularPhotos();

    }

    // Trigger API request
    public void fetchPopularPhotos() {
     /*   URLs : https://api.instagram.com/v1/media/popular?access_token=ACCESS-TOKEN
        - Response
            - Type:{"data" => [x]=>"type"}("image" or "video")
            - URLs :{"data" => [x]=>"images" => "standard_resolution" => "url"}
            - Caption :{"data" => [x]=>"caption" => "text"}
            - Author Name :{"data" => [x]=>"user" => "username"}
     */
        String url = "https://api.instagram.com/v1/media/popular?client_id="+CLIENT_ID;
      //Create the Network Client
        AsyncHttpClient client= new AsyncHttpClient();
        // Trigger the GET request
        client.get(url,null,new JsonHttpResponseHandler(){
            //On Success (200)
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //Expecting a JSON object
                // Type:{"data" => [x]=>"type"}("image" or "video")
                // URLs :{"data" => [x]=>"images" => "standard_resolution" => "url"}
                // Caption :{"data" => [x]=>"caption" => "text"}
                // Author Name :{"data" => [x]=>"user" => "username"}
               //Interate each of Photo items and decode the item into a java object
                JSONArray PhotosJSON =null;
                try {
                    PhotosJSON =response.getJSONArray("data");// array of post
                    //Iterate array of post
                    for (int i=0;i<PhotosJSON.length();i++){
                    // get the JSON object at that position
                        JSONObject PhotoJSON = PhotosJSON.getJSONObject(i);
                    // decade the attributes of the JSON into the data object
                        InstagramPhoto photo=new InstagramPhoto();
                        photo.username= PhotoJSON.getJSONObject("user").getString("username");
                        photo.caption= PhotoJSON.getJSONObject("caption").getString("text");
                        photo.imageUrl= PhotoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
                        photo.imageHeight= PhotoJSON.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
                        photo.likesCount= PhotoJSON.getJSONObject("likes").getInt("count");


                        photos.add(photo);
                    }



                }catch (JSONException e){
                    e.printStackTrace();
                }
                //Callback
                aPhotos .notifyDataSetChanged();
            }

            //On Failure
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                //DO SOMETHING
            }

        });

    }
}
