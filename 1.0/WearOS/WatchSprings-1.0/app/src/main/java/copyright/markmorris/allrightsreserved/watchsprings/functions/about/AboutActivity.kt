// WatchSprings 1.0 © 2025 Mark S. Morris. All rights reserved.
//
// https://www.watchsprings.com
//
// WatchSprings is Fan Content pursuant to the Epic Games Fan Content Policy. Portions of the materials used are trademarks and/or copyrighted works of Epic Games, Inc.

package copyright.markmorris.allrightsreserved.watchsprings.functions.about

import android.content.res.Resources
import android.os.Bundle
import copyright.markmorris.allrightsreserved.watchsprings.*
import android.support.wearable.activity.WearableActivity
import android.widget.LinearLayout
import android.widget.TextView

class AboutActivity : WearableActivity() {

    private val factor = 0.146467f // c = a * sqrt(2)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        val manager = applicationContext.getPackageManager()
        val info = manager.getPackageInfo(applicationContext.getPackageName(), 0)
        val version = findViewById<TextView>(R.id.version)

        var versionText = "Version " + info?.versionName
        val versionCode = info.versionCode

        var versionExpires = valid_until

        if (versionExpires != null)
        {
            versionText += "\n TEST Build $versionCode expires " + versionExpires
        }

        if (BuildConfig.DEBUG) {
            version.setText(versionText + "\n DEBUG")
        } else {
            version.setText(versionText)
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        adjustInset()
    }

    private fun adjustInset() {
        if (applicationContext.resources.configuration.isScreenRound) {
            val inset = (factor * Resources.getSystem().displayMetrics.widthPixels).toInt()
            val aboutLayout = findViewById<LinearLayout>(R.id.aboutLayout)
            aboutLayout.setPadding(inset, inset, inset, inset)
        }
    }
}
