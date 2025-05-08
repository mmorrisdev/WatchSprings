// WatchSprings 1.0 © 2025 Mark S. Morris. All rights reserved.
//
// https://www.watchsprings.com
//
// WatchSprings is Fan Content pursuant to the Epic Games Fan Content Policy. Portions of the materials used are trademarks and/or copyrighted works of Epic Games, Inc.

package copyright.markmorris.allrightsreserved.watchsprings.functions.weapons

import android.graphics.Bitmap
import android.os.Bundle
import copyright.markmorris.allrightsreserved.watchsprings.*
import copyright.markmorris.allrightsreserved.watchsprings.functions.ItemsActivity
import kotlinx.android.synthetic.main.activity_weapons.*
import kotlinx.android.synthetic.main.item_table_row.view.*
import android.graphics.Color
import android.util.Log
import org.json.JSONArray
import org.json.JSONObject


class WeaponsActivity : ItemsActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weapons)

        currentTable = weaponsTable
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        weaponsTitle.text = "Weapons"

        itemImageViews.clear()

        var weapons = JSONArray()

        try
        {
            weapons = currentModel.ItemJSON["weapons"] as JSONArray
        }
        catch (e: Exception)
        {
            weaponsTitle.text = e.message
            return
        }

        numRows = weapons.length() - 1

        for (i in 0..numRows)
        {
            var row = balloon.inflate(R.layout.item_table_row, weaponsLayout, false)

            row.itemLinearLayout.setBackgroundColor(Color.parseColor("#CC2600"));

            val weapon = weapons[i] as JSONObject
                val wimages = weapon["images"] as JSONObject
                val wstats = weapon["stats"] as JSONObject
                    val wdamage = wstats["damage"] as JSONObject
                    val wmagazine = wstats["magazine"] as JSONObject

            row.itemTitle.text = weapon["name"] as String

            var allText: String = ""
            var keyStr: String = ""
            var valueStr: String = ""

            allText = String.format("Damage - body: %s\nDamage - head: %s\nDamage per second: %s\nFire rate: %s\nMagazine size: %s\nMagazine reload: %s secs\n",
                wdamage["body"], wdamage["head"], wstats["dps"], wstats["firerate"], wmagazine["size"], wmagazine["reload"])

            keyStr = "Rarity"
            valueStr = (weapon["rarity"] as String).capitalize()
            allText += (keyStr + ": " + valueStr)

            row.itemBody.text = allText

            weaponsTable.addView(row)

            val urlstr = wimages["background"] as String
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

