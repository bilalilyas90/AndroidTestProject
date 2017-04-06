package com.project.coresol.facbookloginapi;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private LoginButton loginButton;
    private CallbackManager callbackManager;
    TextView textView_name;
    ProfilePictureView imageView_profilePic;
    Button btn_share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.project.coresol.facbookloginapi",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }*/
        btn_share = (Button)findViewById(R.id.btn_share);
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton)findViewById(R.id.login_button);
        textView_name = (TextView)findViewById(R.id.textView_name);
        imageView_profilePic = (ProfilePictureView)findViewById(R.id.imageView_profilePic);
        loginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email", "user_birthday", "user_friends"));

//        callbackManager = CallbackManager.Factory.create();
        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Log.d("loginresult",loginResult.toString());
                Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_LONG).show();

                final AccessToken accessToken = loginResult.getAccessToken();
//                final FBUser fbUser = new FBUser();

                /*GraphRequestAsyncTask request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject user, GraphResponse graphResponse) {
                        Profile profile = Profile.getCurrentProfile();
                        if (profile != null) {
                            String fname = profile.getFirstName() + " " + profile.getLastName();

                        }
                        Log.d("logindata",user.toString());
                        // Launch main activity
                        Intent intent = new Intent(LoginActivity.this,
                                MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).executeAsync();*/
            }

            @Override
            public void onCancel() {
                // App code
                Toast.makeText(getApplicationContext(),"cancel",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Toast.makeText(getApplicationContext(),exception.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        // Progre

        Profile profile = Profile.getCurrentProfile();

        ;
        if (profile != null) {
            String fname = profile.getFirstName() + " " + profile.getLastName();
            textView_name.setText(fname);
            imageView_profilePic.setProfileId(profile.getId());

            String p_image = "http://graph.facebook.com/"+profile.getId()+"/picture?width=200&height=200";
            Log.i("Image",p_image);
//            Picasso.with(getApplicationContext()).load(p_image).into(imageView_profilePic);
            Log.v("Facebook Profile Data", "Logged, user name=" + profile.getFirstName() + " " + profile.getLastName());
        }

        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ShareDialog shareDialog = new ShareDialog(MainActivity.this);
                shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
                    @Override
                    public void onSuccess(Sharer.Result result) {
                        Toast.makeText(MainActivity.this,"successs",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(MainActivity.this,"cancel",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Toast.makeText(MainActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
                final String titlee = "Post Title";
                final String detailNews = "Detail Description";
                final String newsSummary = "Content description";
                final String newsUrl = "http://coresol.com.pk";
                final String newsImgUrl = "http://api.androidhive.info/music/images/adele.png";
                if (ShareDialog.canShow(ShareLinkContent.class)) {
                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
                            .setContentTitle(titlee)
                            .setContentDescription(
                                    newsSummary)
                            .setContentUrl(Uri.parse(newsUrl))
                            .setImageUrl(Uri.parse(newsImgUrl))
                            .build();

                    shareDialog.show(linkContent);
                }
            }
        });
        
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
