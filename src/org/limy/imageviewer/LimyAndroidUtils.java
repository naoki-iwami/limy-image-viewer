package org.limy.imageviewer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

public class LimyAndroidUtils {

    public static Bitmap getBitmap(String url) throws ImageViewerException {
        byte[] bs = getResourceByteArray(url);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bs, 0, bs.length);
        return bitmap;
    }

    public static String getResourceString(String url) throws ImageViewerException {
        return new String(getResourceByteArray(url));
    }

    public static byte[] getResourceByteArray(String url) throws ImageViewerException {
        try {
            byte[] bs = getByteArray(new URI(url).toURL().openStream());
            return bs;
        } catch (URISyntaxException e) {
            throw new ImageViewerException(e);
        } catch (IOException e) {
            throw new ImageViewerException(e);
        }
    }

    public static byte[] getByteArray(InputStream is) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[16384];

        while ((nRead = is.read(data, 0, data.length)) != -1) {
          buffer.write(data, 0, nRead);
        }
        buffer.flush();
        return buffer.toByteArray();
    }
    
    public static void handleException(Context context, Throwable e) {
        e.printStackTrace(); // for debug
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}
