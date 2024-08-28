package andrewafony.test.wallpaper_detail

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream


internal class ImageSaver {

    fun saveImageToGallery(context: Context, bitmap: Bitmap, fileName: String) {
        // todo При первом запросе нужно нажимать два раза
        if (checkStoragePermission(context)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                saveImageAndroidQ(context, bitmap, fileName)
            } else {
                saveImageLegacy(context, bitmap, fileName)
            }
        } else {
            requestStoragePermission(context)
        }
    }

    private fun checkStoragePermission(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestStoragePermission(context: Context) {
        ActivityCompat.requestPermissions(
            (context as Activity),
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            REQUEST_CODE_STORAGE_PERMISSION
        )
    }

    private fun saveImageAndroidQ(context: Context, bitmap: Bitmap, fileName: String) {
        val resolver = context.contentResolver
        val contentValues = ContentValues()
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
        contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)

        val imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        try {
            val outputStream = resolver.openOutputStream(imageUri!!)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream!!)
            outputStream.flush()
            outputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun saveImageLegacy(context: Context, bitmap: Bitmap, fileName: String) {
        val dirPath =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).absolutePath
        val dir = File(dirPath)
        if (!dir.exists()) {
            dir.mkdirs()
        }

        val file = File(dir, fileName)

        try {
            val outputStream: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.flush()
            outputStream.close()

            // Add the image to the system gallery
            MediaStore.Images.Media.insertImage(
                context.contentResolver,
                file.absolutePath,
                file.name,
                null
            )
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    companion object {

        private const val REQUEST_CODE_STORAGE_PERMISSION = 123
    }
}