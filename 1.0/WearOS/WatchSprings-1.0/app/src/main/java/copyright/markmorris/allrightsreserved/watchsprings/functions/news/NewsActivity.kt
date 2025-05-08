// WatchSprings 1.0 © 2025 Mark S. Morris. All rights reserved.
//
// https://www.watchsprings.com
//
// WatchSprings is Fan Content pursuant to the Epic Games Fan Content Policy. Portions of the materials used are trademarks and/or copyrighted works of Epic Games, Inc.

package copyright.markmorris.allrightsreserved.watchsprings.functions.news

import android.os.Bundle
import android.widget.ImageView
import copyright.markmorris.allrightsreserved.watchsprings.*
import copyright.markmorris.allrightsreserved.watchsprings.functions.ItemsActivity
import org.json.JSONObject
import android.graphics.Bitmap
import android.util.Log
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TextView
import java.text.DateFormat.getDateTimeInstance
import java.util.*

class NewsActivity : ItemsActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        val newsTable = findViewById<TableLayout>(R.id.newsTable)
        val newsTitle = findViewById<TextView>(R.id.newsTitle)

        currentTable = newsTable
        newsTitle.text = currentModel.ItemType
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        val newsLayout = findViewById<ViewGroup>(R.id.newsLayout)
        val newsTable = findViewById<TableLayout>(R.id.newsTable)

        numRows = currentModel.ItemEntries.length() - 1

        for (i in 0..numRows) {
            val row = layoutInflater.inflate(R.layout.news_table_row, newsLayout, false)

            val rowTitle = row.findViewById<TextView>(R.id.rowTitle)
            val rowBody = row.findViewById<TextView>(R.id.rowBody)
            val rowImage = row.findViewById<ImageView>(R.id.rowImage)

            val entry = currentModel.ItemEntries[i] as JSONObject

            val title = entry["title"] as String
            rowTitle.text = title

            val sdf = getDateTimeInstance()
            val netDate = Date((entry["time"] as String).toLong() * 1000L)
            val datestr = sdf.format(netDate)

            val body = "${entry["body"] as String}\n\n$datestr"
            rowBody.text = body

            newsTable.addView(row)

            val urlstr = entry["image"] as String
            itemImageViews.add(i, rowImage)

            try {
                val bmp: Bitmap? = MainActivity.instance.bmpCache[urlstr]
                if (bmp != null) {
                    rowImage.setImageBitmap(bmp)
                } else {
                    AsyncTaskLoadImage(i).execute(urlstr)
                }
            } catch (e: Exception) {
                Log.e("onPostCreate", e.message, e)
            }
        }
    }
}



