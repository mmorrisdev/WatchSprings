// WatchSprings 1.0 © 2025 Mark S. Morris. All rights reserved.
//
// https://www.watchsprings.com
//
// WatchSprings is Fan Content pursuant to the Epic Games Fan Content Policy. Portions of the materials used are trademarks and/or copyrighted works of Epic Games, Inc.

package copyright.markmorris.allrightsreserved.watchsprings.functions.store

import android.os.Bundle
import copyright.markmorris.allrightsreserved.watchsprings.*
import copyright.markmorris.allrightsreserved.watchsprings.functions.ItemsActivity
import kotlinx.android.synthetic.main.activity_dailyitems.*
import kotlinx.android.synthetic.main.item_table_row.view.*
import android.graphics.Bitmap
import android.util.Log
import org.json.JSONArray
import org.json.JSONObject

class DailyItemsActivity : ItemsActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dailyitems)

        currentTable = dailyItemsTable
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        dailyItemsTitle.text = "Daily Items"

        var items = JSONArray()

        try
        {
            items = currentModel.ItemJSON["items"] as JSONArray
        }
        catch (e: Exception)
        {
            dailyItemsTitle.text = e.message
            return
        }

        numRows = items.length() - 1

        for (i in 0..numRows)
        {
            var row = balloon.inflate(R.layout.item_table_row, dailyIemsLayout, false)

            val cellItem = items[i] as JSONObject
            val cellItemItem = cellItem["item"] as JSONObject

            row.itemTitle.text = cellItem["name"] as String

            var allText: String = ""
            var keyStr: String = ""
            var valueStr: String = ""

            keyStr = "Cost"
            valueStr = (cellItem["cost"] as String)
            allText += (keyStr + ": " + valueStr + "\n")

            keyStr = "Type"
            valueStr = (cellItemItem["type"] as String).capitalize()
            allText += (keyStr + ": " + valueStr + "\n")

            keyStr = "Rarity"
            valueStr = (cellItemItem["rarity"] as String).capitalize()
            allText += (keyStr + ": " + valueStr)

            row.itemBody.text = allText

            val images = cellItemItem["images"] as JSONObject

            dailyItemsTable.addView(row)

            val urlstr = images["background"] as String
            itemImageViews.add(i, row.itemImage)

            try
            {
                val bmp: Bitmap? = MainActivity.instance.bmpCache[urlstr]
                if (bmp != null)
                    row.itemImage.setImageBitmap(bmp)
                else
                    AsyncTaskLoadImage(i).execute(urlstr)
            }
            catch (e: Exception)
            {
                Log.e("onPostCreate", e.message)
            }
        }
    }
}

