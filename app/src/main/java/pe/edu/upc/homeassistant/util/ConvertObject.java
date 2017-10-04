package pe.edu.upc.homeassistant.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

/**
 * Created by Fernando on 03/10/2017.
 */

public class ConvertObject {

    public static byte[] bitmapToByteArray(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public static Bitmap byteArrayToBitmap(byte[] bitmapdata){
        return BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);
    }
}
