// WatchSprings 1.0 © 2025 Mark S. Morris. All rights reserved.
//
// https://www.watchsprings.com
//
// WatchSprings is Fan Content pursuant to the Epic Games Fan Content Policy. Portions of the materials used are trademarks and/or copyrighted works of Epic Games, Inc.

package copyright.markmorris.allrightsreserved.watchsprings.functions

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.wearable.activity.WearableActivity
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TableLayout
import copyright.markmorris.allrightsreserved.watchsprings.MainActivity
import java.io.IOException
import java.io.InputStream
import java.net.URL

abstract class ItemsActivity : WearableActivity() {

    lateinit var currentTable: TableLayout
    lateinit var balloon: LayoutInflater

    val itemImageViews: MutableList<ImageView> = arrayListOf()

    var numRows = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MainActivity.instance.CMDLOG()

        setAmbientEnabled()     // Enables Always-on
        balloon = LayoutInflater.from(applicationContext)
        setCacheResultsNotification()

        itemImageViews.clear()

        numRows = 0

    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        adjustInset()
    }

    private fun adjustInset() {
        if (applicationContext.resources.configuration.isScreenRound) {
            val inset = (FACTOR * Resources.getSystem().displayMetrics.widthPixels).toInt()
            currentTable.setPadding(inset, inset, inset, inset)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        MainActivity.instance.CMDLOG()

        ItemsActivity.wcacheResultsNotification.removeCallbacksAndMessages(null)
    }


    //-----------------------------------------------------------------------------------------------------------------------------------------


    fun setCacheResultsNotification()
    {
        ItemsActivity.wcacheResultsNotification = Handler(object: Handler.Callback {
            override fun handleMessage(msg: Message): Boolean {

                try
                {
                    itemImageViews[msg.what].setImageBitmap(MainActivity.instance.bmpCache[msg.obj])
                    itemImageViews[msg.what].invalidate()
                }
                catch (e: Exception)
                {
                    Log.e("ResultsNotification", e.message)
                }
                return true
            }
        })
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------

    public fun getBitmapFromAsset(strName: String): Bitmap? {
        val assetManager = applicationContext.assets
        var istr: InputStream? = null
        try {
            istr = assetManager.open(strName)
        } catch (e: IOException) {
            return null
        }
        return BitmapFactory.decodeStream(istr)
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------


    public class AsyncTaskLoadImage(private val arowNum: Int) : AsyncTask<String, String, Bitmap>()
    {
        var urlstr = "Nothing"

        override fun doInBackground(vararg params: String): Bitmap?
        {
            val wcache = MainActivity.instance.bmpCache
            urlstr = params[0]

            if (wcache.containsKey(urlstr)) {
                return wcache.getValue(urlstr)
            }

            var bitmap: Bitmap? = null
            try {
                val url: URL? = URL(urlstr)
                if (url != null) {
                    bitmap = BitmapFactory.decodeStream(url.content as InputStream)
                }
            } catch (e: IOException) {
                Log.e(TAG, e.message)
            }
            bitmap?.density = 72
            wcache.put(urlstr, bitmap)
            return bitmap
        }

        override fun onPostExecute(bitmap: Bitmap?) {
            val fnMsg = ItemsActivity.wcacheResultsNotification.obtainMessage(arowNum, urlstr)
            ItemsActivity.wcacheResultsNotification.sendMessage(fnMsg)
        }

        companion object {
            private val TAG = "AsyncTaskLoadImage"
        }
    }

    //----------------------------------------------------------------------------------------------------------------------------------------

    companion object
    {
        lateinit var wcacheResultsNotification: Handler

        private const val FACTOR = 0.146467f // c = a * sqrt(2)

    }
}
