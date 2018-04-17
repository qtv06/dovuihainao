package com.example.dell.dovuihainao;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import java.util.Random;

public class CongratulationActivity extends AppCompatActivity {

    private Button btnShare;
    private TextView tvPraise;
    private CallbackManager callbackManager;
    private ShareDialog shareDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congratulation);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        tvPraise = findViewById(R.id.tvKhen);
        btnShare = findViewById(R.id.btnShare);

        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
                    @Override
                    public void onSuccess(Sharer.Result result) {
                        Toast.makeText(CongratulationActivity.this, "Share successful!", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(CongratulationActivity.this, "Share cancel!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Toast.makeText(CongratulationActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                ShareLinkContent linkContent = new ShareLinkContent.Builder()
                        .setQuote("This is useful link")
                        .setContentUrl(Uri.parse("https://youtube.com"))
                        .build();
                if(ShareDialog.canShow(ShareLinkContent.class)){
                    shareDialog.show(linkContent);
                }
            }
        });

        String[] praises = getResources().getStringArray(R.array.praises);
        Random random = new Random();
        int n = random.nextInt(praises.length - 1);
        tvPraise.setText(praises[n]);
    }

    public void continute(View view){
        Intent intent = new Intent();
        setResult(RESULT_OK);
        finish();
    }
}
