// WatchSprings 1.0 © 2025 Mark S. Morris. All rights reserved.
//
// https://www.watchsprings.com
//
// WatchSprings is Fan Content pursuant to the Epic Games Fan Content Policy. Portions of the materials used are trademarks and/or copyrighted works of Epic Games, Inc.

package copyright.markmorris.allrightsreserved.watchsprings

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.wearable.activity.WearableActivity
import android.util.Log
import android.view.HapticFeedbackConstants
import android.view.View
import android.widget.ScrollView
import android.widget.TextView
import copyright.markmorris.allrightsreserved.watchsprings.functions.FunctionModel
import copyright.markmorris.allrightsreserved.watchsprings.functions.about.AboutActivity
import copyright.markmorris.allrightsreserved.watchsprings.functions.challenges.ChallengesActivity
import copyright.markmorris.allrightsreserved.watchsprings.functions.store.DailyItemsActivity
import copyright.markmorris.allrightsreserved.watchsprings.functions.news.NewsActivity
import copyright.markmorris.allrightsreserved.watchsprings.functions.upcoming.UpcomingItemsActivity
import copyright.markmorris.allrightsreserved.watchsprings.functions.weapons.WeaponsActivity
import org.json.JSONArray
import org.json.JSONObject
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap


public enum class Process
{
    FNgetUserID,
    FNgetPlayerData,
    FNgetNewsBR,
    FNgetNewsSTW,
    FNgetStatus,
    FNgetUsernameFromId,
    FNgetLeaderboard,
    FNgetWeapons,
    FNgetChallenges,
    FNgetStore,
    FNgetUpcoming,
    FNERROR
}

var currentModel: FunctionModel = FunctionModel()

var valid_until: String? = null     // null == don't check date

class MainActivity : WearableActivity() {

    val BRnewsModel = FunctionModel()
    val STWnewsModel = FunctionModel()
    val weaponsModel = FunctionModel()
    val statusModel = FunctionModel()
    val challengesModel = FunctionModel()
    val upcomingModel = FunctionModel()
    val storeModel = FunctionModel()

    val bmpCache: HashMap<String, Bitmap?> = hashMapOf()

    lateinit var statusLabel: TextView
    lateinit var titleLabel: TextView
    lateinit var mainScrollView: ScrollView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        instance = this

        statusLabel = findViewById<TextView>(R.id.statusLabel)
        titleLabel = findViewById<TextView>(R.id.titleLabel)
        mainScrollView = findViewById<ScrollView>(R.id.mainScrollView)

        if (valid_until != null)
        //null means don't check date
        {
            val sdf = SimpleDateFormat("dd/MM/yyyy")
            var strDate: Date? = null
            try {
                strDate = sdf.parse(valid_until)
            } catch (e: ParseException) {
                Log.d("Exit", "Expire date can't validate - please retry")
            }

            if (Date().after(strDate!!)) {
                Log.d("Exit", "This build has expired")
                finishAndRemoveTask()  //this build has expired!
            }
        }

        fortniteAPI = FortniteAPI()

        bmpCache.clear()


        // Enables Always-on
        setAmbientEnabled()

        setHandleResultsNotification()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()

        CMDLOG()

        updateServerStatus()
    }

    fun updateServerStatus()
    {
        CMDLOG()

        statusLabel.text = "Fortnite Server Status:"

        if (statusModel.isModelLoaded)
        {
            var atext: String = "ERROR"
            var status: String = "ERROR"

            try {
                atext = statusModel.ItemJSON["message"] as String
                status = statusModel.ItemJSON["status"] as String
            } catch (e: Exception) {
                print(e.message)
            }

            titleLabel.setBackgroundResource(R.color.greenstatusbg);

            if (status.toLowerCase() != "up")
            {
                titleLabel.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                titleLabel.setBackgroundResource(R.color.redtatusbg);
            }

            titleLabel.text = atext.trim()
        }
        else
        {
            titleLabel.text = "Getting status..."
            fortniteAPI.getStatus()
        }
    }

    //-----------------------------------------------------------------------------------------------------------------------------------------

    fun newsAction(v: View?)
    {
        CMDLOG()

        if (BRnewsModel.isModelLoaded)
        {
            currentModel = BRnewsModel
            val intent = Intent(applicationContext, NewsActivity::class.java)
            applicationContext.startActivity(intent)
            return
        }
        statusLabel.text = "Loading BR News..."
        fortniteAPI.getNews("br")
    }

    fun stwNewsAction(v: View?)
    {
        CMDLOG()

        if (STWnewsModel.isModelLoaded)
        {
            currentModel = STWnewsModel
            val intent = Intent(applicationContext, NewsActivity::class.java)
            startActivity(intent)
            return
        }
        mainScrollView.smoothScrollTo(0,0)
        statusLabel.text = "Loading STW News..."
        fortniteAPI.getNews("stw")

    }

    fun challengesAction(v: View?)
    {
        CMDLOG()

        if (challengesModel.isModelLoaded)
        {
            currentModel = challengesModel
            val intent = Intent(applicationContext, ChallengesActivity::class.java)
            startActivity(intent)
            return
        }
        mainScrollView.smoothScrollTo(0,0)
        statusLabel.text = "Loading Challenges..."
        fortniteAPI.getChallenges()
    }

    fun storeAction(v: View?)
    {
        CMDLOG()

        if (storeModel.isModelLoaded)
        {
 	        currentModel = storeModel
            val intent = Intent(applicationContext, DailyItemsActivity::class.java)
            startActivity(intent)
            return
        }
        mainScrollView.smoothScrollTo(0,0)
        statusLabel.text = "Loading daily items..."
        fortniteAPI.getStore()
    }

    fun upcomingAction(v: View?)
    {
        CMDLOG()

        if (upcomingModel.isModelLoaded)
        {
            currentModel = upcomingModel
            val intent = Intent(applicationContext, UpcomingItemsActivity::class.java)
            startActivity(intent)
            return
        }
        mainScrollView.smoothScrollTo(0,0)
        statusLabel.text = "Loading upcoming items..."
        fortniteAPI.getUpcoming()
    }

    fun weaponsAction(v: View?)
    {
        CMDLOG()

        if (weaponsModel.isModelLoaded)
        {
  	        currentModel = weaponsModel
            val intent = Intent(applicationContext, WeaponsActivity::class.java)
            startActivity(intent)
            return
        }
        mainScrollView.smoothScrollTo(0,0)
        statusLabel.text = "Loading weapons list..."
        fortniteAPI.getWeapons()

    }

    fun reloadAction(v: View?)
    {
        CMDLOG()

        BRnewsModel.isModelLoaded = false
        STWnewsModel.isModelLoaded = false
        weaponsModel.isModelLoaded = false
        statusModel.isModelLoaded = false
        challengesModel.isModelLoaded = false
        upcomingModel.isModelLoaded = false
        storeModel.isModelLoaded = false

        bmpCache.clear()

        mainScrollView.smoothScrollTo(0,0)

        updateServerStatus()
    }

    fun aboutAction(v: View?)
    {
        CMDLOG()

        val intent = Intent(applicationContext, AboutActivity::class.java)
        startActivity(intent)

    }

    //-----------------------------------------------------------------------------------------------------------------------------------------

    fun setHandleResultsNotification()
    {
        handleResultsNotification = Handler(object:Handler.Callback
        {
            override fun handleMessage(msg: Message) : Boolean
            {
                var itemEntries = JSONArray()

                if (msg.what == Process.FNERROR.ordinal)
                {
                    //do something error related here
                    statusLabel.text = "Unable to connect"
                    titleLabel.text = "Try Reload All"

                    titleLabel.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                    return false
                }

                titleLabel.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK);

                @Suppress("UNCHECKED_CAST")
                val results = msg.obj as Map<String, Any>

                if (!results.containsKey("result"))
                {
                    return false
                }

                val json = JSONObject(results["result"] as String)

                if (json.has("entries") )
                {
                    itemEntries = json["entries"] as JSONArray
                }

                when (msg.what)
                {
                    Process.FNgetNewsBR.ordinal ->
                    {
                        BRnewsModel.ItemEntries = itemEntries
                        BRnewsModel.ItemDetailID = "NewsDetail"
                        BRnewsModel.ItemRowID = "NewsRow"
                        BRnewsModel.ItemType = "BR News"

                        BRnewsModel.isModelLoaded = true

                        currentModel = BRnewsModel
                        val intent = Intent(applicationContext, NewsActivity::class.java)
                        startActivity(intent)
                    }

                    Process.FNgetNewsSTW.ordinal ->
                    {
                        STWnewsModel.ItemEntries = itemEntries
                        STWnewsModel.ItemDetailID = "NewsDetail"
                        STWnewsModel.ItemRowID = "NewsRow"
                        STWnewsModel.ItemType = "STW News"

                        STWnewsModel.isModelLoaded = true

                        currentModel = STWnewsModel
                        val intent = Intent(applicationContext, NewsActivity::class.java)
                        startActivity(intent)
                    }

                    Process.FNgetUpcoming.ordinal ->
                    {
 		            	upcomingModel.ItemEntries = itemEntries
                        upcomingModel.ItemDetailID = "UpcomingItems"
                        upcomingModel.ItemRowID = "Item"
                        upcomingModel.ItemType = "Upcoming Items"
                        upcomingModel.ItemJSON = json

                        upcomingModel.isModelLoaded = true
                
			            currentModel = upcomingModel
                        val intent = Intent(applicationContext, UpcomingItemsActivity::class.java)
                        startActivity(intent)

                    }

                    Process.FNgetStore.ordinal ->
                    {
                        storeModel.ItemEntries = itemEntries
                        storeModel.ItemDetailID = "DailyItems"
                        storeModel.ItemRowID = "Item"
                        storeModel.ItemType = "Daily Items"
                        storeModel.ItemJSON = json

                        storeModel.isModelLoaded = true
                
			            currentModel = storeModel
                        val intent = Intent(applicationContext, DailyItemsActivity::class.java)
                        startActivity(intent)

                    }

                    Process.FNgetWeapons.ordinal ->
                    {
                        weaponsModel.ItemEntries = itemEntries
                        weaponsModel.ItemDetailID = "Weaoibs"
                        weaponsModel.ItemRowID = "Item"
                        weaponsModel.ItemType = "Weapons"
                        weaponsModel.ItemJSON = json

                        weaponsModel.isModelLoaded = true
                
		            	currentModel = weaponsModel
                        val intent = Intent(applicationContext, WeaponsActivity::class.java)
                        startActivity(intent)  
                   }

                    Process.FNgetStatus.ordinal ->
                    {
                        Log.d("CMDLOG", "FNgetStatus")

                        currentModel = statusModel

                        statusModel.ItemJSON = json
                        statusModel.isModelLoaded = true
                        updateServerStatus()
                    }

                    Process.FNgetChallenges.ordinal ->
                    {
                        challengesModel.ItemJSON = json
                        challengesModel.isModelLoaded = true
                        challengesModel.ItemRowID = "ChallengesRow"
                        challengesModel.ItemDetailID = "ChallengesDetailTable"

                        var season: kotlin.Any

                        try {
                            season = json["challenges"] as JSONObject
                        } catch (e: Exception) {
                            print(e.message)
                            return false
                        }

                        val weeks: MutableList<Pair<String, JSONArray>> = arrayListOf()

                        for (key in season.keys())
                        {
                            val value = season.get(key) as JSONArray
                            if (value.length() != 0)
                            {
                                val week = Pair<String, JSONArray>(key, value)
                                weeks.add(week)
                            }
                        }
                        weeks.reverse()

                        challengesModel.ItemEntriesList = weeks
                        currentModel = challengesModel

                        val intent = Intent(applicationContext, ChallengesActivity::class.java)
                        startActivity(intent)
                    }
                }

                return true
            }
        })


    }

    fun CMDLOG()
    {
        if (BuildConfig.DEBUG) {
            val ste = Thread.currentThread().stackTrace
            Log.d("CMDLOG", "On thread: " + Thread.currentThread().name)
            Log.d("CMDLOG", ste[3].className + " " + ste[3].methodName)
        }
    }

    //----------------------------------------------------------------------------------------------------------------------------------------

    companion object
    {
        lateinit var instance: MainActivity
        lateinit var handleResultsNotification: Handler
        lateinit var fortniteAPI: FortniteAPI
    }

}
