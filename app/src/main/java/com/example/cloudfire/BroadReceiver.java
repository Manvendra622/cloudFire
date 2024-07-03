package com.example.cloudfire;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.Settings;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class BroadReceiver extends BroadcastReceiver {
    MediaPlayer mediaPlayer;

    Random random=new Random();

    @Override
    public void onReceive(Context context, Intent intent) {

        TextView text=MainActivity.getInstance().getWeatherState();
        String[] defaultSongs={"https://firebasestorage.googleapis.com/v0/b/weatherdetails-a6a91.appspot.com/o/Coldplay%20Default1.mp3?alt=media&token=5d58292e-bb9d-4bb3-be4e-067da61d30d8"};

        String[] sunshineSongs={"https://firebasestorage.googleapis.com/v0/b/weatherdetails-a6a91.appspot.com/o/sunIsShining3.mp3?alt=media&token=669190ef-466e-465a-8e8b-a760ee63bafe","https://firebasestorage.googleapis.com/v0/b/weatherdetails-a6a91.appspot.com/o/summerOf69Sunshine2.mp3?alt=media&token=05a5c903-1477-4424-91b9-94ba52be109e","https://firebasestorage.googleapis.com/v0/b/weatherdetails-a6a91.appspot.com/o/one%20Republic-Sunshine1.mp3?alt=media&token=87eff502-950b-4415-a79f-05f9346174ec","https://firebasestorage.googleapis.com/v0/b/weatherdetails-a6a91.appspot.com/o/happierSunshine4.mp3?alt=media&token=f21624fc-15fe-4cfc-899c-07f4994663d6","https://firebasestorage.googleapis.com/v0/b/weatherdetails-a6a91.appspot.com/o/californiaGirlsSunshine5.mp3?alt=media&token=b786dd1a-8954-4c8d-bb38-9d35422a01b1"};

        String[] rainSongs={"https://firebasestorage.googleapis.com/v0/b/weatherdetails-a6a91.appspot.com/o/Bastille%20-PompeiiRain1.mp3?alt=media&token=cf7f30d6-7c0c-46fb-aa9c-1ce2cdc5af1a","https://firebasestorage.googleapis.com/v0/b/weatherdetails-a6a91.appspot.com/o/Beautiful%20In%20My%20Eyes-Rain2.mp3?alt=media&token=ffacba7c-3806-4414-bf02-1e637d9a88f8","https://firebasestorage.googleapis.com/v0/b/weatherdetails-a6a91.appspot.com/o/Bruno%20Mars-Rain5.mp3?alt=media&token=922365f4-8c84-4828-aba5-8ad797ba468e","https://firebasestorage.googleapis.com/v0/b/weatherdetails-a6a91.appspot.com/o/Poets%20of%20the%20Fall-Rain4.mp3?alt=media&token=5e76bfd2-96d8-46f5-8633-2a5b2dbe7a15","https://firebasestorage.googleapis.com/v0/b/weatherdetails-a6a91.appspot.com/o/Rihanna%20-Umbrella-Rain3.mp3?alt=media&token=4fb65658-5205-4a59-9704-c84146582a73"};

        String[] snowSongs={"https://firebasestorage.googleapis.com/v0/b/weatherdetails-a6a91.appspot.com/o/Justin%20Bieber-Snow1.mp3?alt=media&token=d0ac15d5-ad25-407d-b6ad-59d3583d1b43","https://firebasestorage.googleapis.com/v0/b/weatherdetails-a6a91.appspot.com/o/Let%20It%20Go%20(from%20Frozen)Snow2.mp3?alt=media&token=113bf52f-8203-4778-acd7-e83a3445df8b","https://firebasestorage.googleapis.com/v0/b/weatherdetails-a6a91.appspot.com/o/Let%20It%20Snow!Snow3.mp3?alt=media&token=f5bd621d-859d-4893-ba58-beb9780fd250","https://firebasestorage.googleapis.com/v0/b/weatherdetails-a6a91.appspot.com/o/Cold%20As%20Ice%20Snow4.mp3?alt=media&token=c181fb7b-a59c-4d68-a5e7-05b7e74816a1","https://firebasestorage.googleapis.com/v0/b/weatherdetails-a6a91.appspot.com/o/Sia%20-%20Snowman-Snow5.mp3?alt=media&token=3273ea26-0ea9-4f24-8da8-88bb8cfa48cd"};

        if(text.getText().toString().equalsIgnoreCase("Clouds") || text.getText().toString().equalsIgnoreCase("Rain")){
            mediaPlayer=new MediaPlayer();
            try {
                mediaPlayer.setDataSource(rainSongs[random.nextInt(rainSongs.length)]);
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.start();
                    }
                });
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (text.getText().toString().equalsIgnoreCase("Sunny") || text.getText().toString().equalsIgnoreCase("Clear")){
            mediaPlayer=new MediaPlayer();
            try {
                mediaPlayer.setDataSource(sunshineSongs[random.nextInt(sunshineSongs.length)]);
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.start();
                    }
                });
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (text.getText().toString().equalsIgnoreCase("Snow") || text.getText().toString().equalsIgnoreCase("Snowfall")){
            mediaPlayer=new MediaPlayer();
            try {
                mediaPlayer.setDataSource(snowSongs[random.nextInt(snowSongs.length)]);
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.start();
                    }
                });
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            mediaPlayer=new MediaPlayer();
            try {
                mediaPlayer.setDataSource(defaultSongs[random.nextInt(defaultSongs.length)]);
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.start();
                    }
                });
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


//        mediaPlayer.start();
    }

}
