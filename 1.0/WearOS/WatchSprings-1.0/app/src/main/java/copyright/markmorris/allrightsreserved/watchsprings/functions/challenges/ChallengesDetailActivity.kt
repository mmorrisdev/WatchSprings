// WatchSprings 1.0 © 2025 Mark S. Morris. All rights reserved.
//
// https://www.watchsprings.com
//
// WatchSprings is Fan Content pursuant to the Epic Games Fan Content Policy. Portions of the materials used are trademarks and/or copyrighted works of Epic Games, Inc.

package copyright.markmorris.allrightsreserved.watchsprings.functions.challenges

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TextView
import android.widget.ImageView
import copyright.markmorris.allrightsreserved.watchsprings.*
import copyright.markmorris.allrightsreserved.watchsprings.functions.ItemsActivity
import org.json.JSONObject

class ChallengesDetailActivity : ItemsActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_challengesdetail)

        currentTable = findViewById<TableLayout>(R.id.challengesDetailTable)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        var wtext = "ERROR"

        if (currentWeek.first == "week10")
        {
            wtext = String.format("Week %s", "10")
        } else {
            wtext = String.format("Week %s", currentWeek.first.last())
        }

        val challengesDetailTitle = findViewById<TextView>(R.id.challengesDetailTitle)
        val challengesDetailLayout = findViewById<LinearLayout>(R.id.challengesDetailLayout)
        val challengesDetailTable = findViewById<TableLayout>(R.id.challengesDetailTable)

        challengesDetailTitle.text = wtext

        val numRows = currentWeek.second.length() - 1

        for (i in 0..numRows)
        {
            val row = balloon.inflate(R.layout.challengesdetail_table_row, challengesDetailLayout, false)
            val detailRowItem  = currentWeek.second[i] as JSONObject

            val detailRowTitle = row.findViewById<TextView>(R.id.detailRowTitle)
            val detailRowImage = row.findViewById<ImageView>(R.id.detailRowImage)
            val detailRowBody = row.findViewById<TextView>(R.id.detailRowBody)

            detailRowTitle.text = detailRowItem["challenge"] as String

            val starfile = String.format("star%d.png", detailRowItem["stars"])
            val bmp = getBitmapFromAsset(starfile)
            detailRowImage.setImageBitmap(bmp)

            val body = String.format("Difficulty: %s", detailRowItem["difficulty"] as String)
            detailRowBody.text = body

            challengesDetailTable.addView(row)
        }
    }

}

