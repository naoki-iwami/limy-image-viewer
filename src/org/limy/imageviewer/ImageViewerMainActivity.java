package org.limy.imageviewer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ImageViewerMainActivity extends Activity {
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        new Thread() {
            @Override
            public void run() {
                
                String url = "http://lockerz.com/s/172612648";
                System.out.println(url);
                try {
                    
                    if (url.startsWith("http://lightbox.com/")) {
                        String html = LimyAndroidUtils.getResourceString(url);
                        Pattern pattern = Pattern.compile("<img src=\"(http://lightbox-photos[^\"]+)\"", Pattern.DOTALL | Pattern.MULTILINE);
                        Matcher matcher = pattern.matcher(html);
                        while (matcher.find()) {
                            String imageUrl = matcher.group(1);
                            showImage(imageUrl);
                            break;
                        }
                    }

                    if (url.startsWith("http://my365.in/")) {
                        String html = LimyAndroidUtils.getResourceString(url);
                        Pattern pattern = Pattern.compile(".*<img src=\"(http://my365.in/image/photo/[^\"]+)\".*", Pattern.DOTALL | Pattern.MULTILINE);
                        Matcher matcher = pattern.matcher(html);
                        if (matcher.matches()) {
                            String imageUrl = matcher.group(1);
                            showImage(imageUrl);
                        }
                    }

                    if (url.startsWith("http://photozou.jp/photo/show/")) {
                        int pos = url.lastIndexOf('/');
                        String photoId = url.substring(pos + 1);
                        String html = LimyAndroidUtils.getResourceString("http://api.photozou.jp/rest/photo_info?photo_id=" + photoId);
                        
                        Pattern pattern = Pattern.compile(".*<image_url>([^<]+)<.*", Pattern.DOTALL | Pattern.MULTILINE);
                        Matcher matcher = pattern.matcher(html);
                        if (matcher.matches()) {
                            String imageUrl = matcher.group(1);
                            showImage(imageUrl);
                        }
                    }

                    if (url.startsWith("http://p.twipple.jp/")) {
                        int pos = url.lastIndexOf('/');
                        String photoId = url.substring(pos + 1);
                        String imageUrl = "http://p.twpl.jp/show/orig/" + photoId;
                        showImage(imageUrl);
                    }
                    
                    if (url.startsWith("http://4sq.com/")) {
                        String html = LimyAndroidUtils.getResourceString(url);
                        Pattern pattern = Pattern.compile(".*(https://img-s\\.foursquare\\.com/pix/[^']+)'.*", Pattern.DOTALL | Pattern.MULTILINE);
                        Matcher matcher = pattern.matcher(html);
                        if (matcher.matches()) {
                            String imageUrl = matcher.group(1);
                            showImage(imageUrl);
                            return;
                        }
                    }
                    
                    if (url.startsWith("http://lockerz.com/s/")) {
                        int pos = url.lastIndexOf('/');
                        String photoId = url.substring(pos + 1);
                        String html = LimyAndroidUtils.getResourceString("http://api.plixi.com/api/tpapi.svc/photos/" + photoId);
                        Pattern pattern = Pattern.compile(".*<MediumImageUrl>([^<]+)<.*", Pattern.DOTALL | Pattern.MULTILINE);
                        Matcher matcher = pattern.matcher(html);
                        if (matcher.matches()) {
                            String imageUrl = matcher.group(1);
                            showImage(imageUrl);
                            return;
                        }
                    }

                    finish();

                } catch (ImageViewerException e) {
                    LimyAndroidUtils.handleException(getApplicationContext(), e);
                    finish();
                }
            }
        }.start();
    }

    protected void showImage(String imageUrl) throws ImageViewerException {
        
        final ImageView imgView = (ImageView)findViewById(R.id.image_main);
        final Bitmap bitmap = LimyAndroidUtils.getBitmap(imageUrl);
        imgView.post(new Runnable() {
            public void run() {
                findViewById(R.id.progressBar_main).setVisibility(View.GONE);
                imgView.setImageBitmap(bitmap);
                imgView.setVisibility(View.VISIBLE);
            }
        });
    }

}
