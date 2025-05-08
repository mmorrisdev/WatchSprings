// WatchSprings 1.0 © 2025 Mark S. Morris. All rights reserved.
//
// https://www.watchsprings.com
//
// WatchSprings is Fan Content pursuant to the Epic Games Fan Content Policy. Portions of the materials used are trademarks and/or copyrighted works of Epic Games, Inc.

package copyright.markmorris.allrightsreserved.watchsprings.functions.challenges

import android.content.Intent
import android.os.Bundle
import android.view.HapticFeedbackConstants
import android.view.View
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TextView
import copyright.markmorris.allrightsreserved.watchsprings.*
import copyright.markmorris.allrightsreserved.watchsprings.functions.ItemsActivity
import org.json.JSONArray

lateinit var currentWeek: Pair<String, JSONArray>

class ChallengesActivity : ItemsActivity() {

    lateinit var challengesTable: TableLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_challenges)

        challengesTable = findViewById<TableLayout>(R.id.challengesTable)
        val challengesTitle = findViewById<TextView>(R.id.challengesTitle)

        currentTable = challengesTable

        challengesTitle.text = String.format("Season %d", currentModel.ItemJSON["season"])

    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        var numRows = currentModel.ItemEntriesList.size - 1

        val challengesLayout = findViewById<LinearLayout>(R.id.challengesLayout)

        for (i in 0..numRows)
        {
            var row = balloon.inflate(R.layout.challenges_table_row, challengesLayout, false)
            row.setOnClickListener{this.onClick(row)}

            val week: Pair<String, JSONArray> = currentModel.ItemEntriesList[i]

            var wtext = "ERROR"

            val rowText = row.findViewById<TextView>(R.id.rowText)

            if (week.first == "week10")
            {
                wtext = String.format(" Week %s  ", "10")
            } else {
                wtext = String.format("   Week %s   ", week.first.last())
            }
            rowText.text = wtext

            challengesTable.addView(row)

        }
    }

    // Implement the OnClickListener callback
    fun onClick(v: View) {
        // do something when the button is clicked
        MainActivity.instance.CMDLOG()

        val rowIndex = challengesTable.indexOfChild(v)

	    currentWeek = currentModel.ItemEntriesList[rowIndex]

        v.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK);

        val intent = Intent(applicationContext, ChallengesDetailActivity::class.java)
        startActivity(intent)

    }


}

