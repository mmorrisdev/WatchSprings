// WatchSprings 1.0 © 2025 Mark S. Morris. All rights reserved.
//
// https://www.watchsprings.com
//
// WatchSprings is Fan Content pursuant to the Epic Games Fan Content Policy. Portions of the materials used are trademarks and/or copyrighted works of Epic Games, Inc.

package copyright.markmorris.allrightsreserved.watchsprings

import android.view.HapticFeedbackConstants
import kotlinx.coroutines.*
import java.net.URL


class FortniteAPI
{
    private var function: Process = Process.FNERROR
    private var lastPath = ""

    val base_url: String = "legacy API provider no longer exists"
    val activeFunctions: MutableList<Process> = arrayListOf()

    init
    {
        MainActivity.instance.CMDLOG()

    }

    //----------------------------------------------------------------------------------------------------------------------------

    fun getUserID()
    {
        MainActivity.instance.CMDLOG()
        function = Process.FNgetUserID
    }

    fun getPlayerData()
    {
        MainActivity.instance.CMDLOG()
        function = Process.FNgetPlayerData
    }

    fun getNews(mode: String)  // "br" or "stw"
    {
        MainActivity.instance.CMDLOG()

        if (mode == "br")
        {
            function = Process.FNgetNewsBR
            lastPath = "br_motd/get?language=en"
        }
        else
        {
            function = Process.FNgetNewsSTW
            lastPath = "stw_motd/get?language=en"
        }

        GlobalScope.launch { apiCall() }
    }

    fun getStatus()
    {
        MainActivity.instance.CMDLOG()

        function = Process.FNgetStatus
        lastPath = "status/fortnite_server_status"

        GlobalScope.launch { apiCall() }
    }

    fun getUsernameFromId()
    {
        MainActivity.instance.CMDLOG()
        function = Process.FNgetUsernameFromId
    }

    fun getLeaderboard()
    {
        MainActivity.instance.CMDLOG()
        function = Process.FNgetLeaderboard
    }

    fun getWeapons()
    {
        MainActivity.instance.CMDLOG()

        function = Process.FNgetWeapons
        lastPath = "weapons/get"

        GlobalScope.launch { apiCall() }
    }

    fun getChallenges()
    {
        MainActivity.instance.CMDLOG()

        function = Process.FNgetChallenges
        lastPath = "challenges/get?season=current"

        GlobalScope.launch { apiCall() }
    }

    fun getStore()
    {
        MainActivity.instance.CMDLOG()

        function = Process.FNgetStore
        lastPath = "store/get?language=en"

        GlobalScope.launch { apiCall() }
    }

    fun getUpcoming()
    {
        MainActivity.instance.CMDLOG()

        function = Process.FNgetUpcoming
        lastPath = "upcoming/get"

        GlobalScope.launch { apiCall() }
    }

    //----------------------------------------------------------------------------------------------------------------------------

    fun apiCall()
    {
        MainActivity.instance.CMDLOG()

        if (activeFunctions.contains(function))	// only ignores requests for functions that are already pending...
        {
            print("busy right now!")
            MainActivity.instance.titleLabel.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
            return
        }

        activeFunctions.add(function)

        var results: MutableMap<String, Any> = hashMapOf("function" to function)

        val fnurl = base_url + lastPath

        var jsonStr = "{ \"message\": \"No data\" }"

        /* legacy API provider no longer exists

        try {
            jsonStr = URL(fnurl).readText()
        } catch (e: Exception) {
            print(e.message)
            return
        } finally {
            activeFunctions.remove(function)

        }*/

        activeFunctions.remove(function)

        results["result"] = jsonStr

        val fnMsg = MainActivity.handleResultsNotification.obtainMessage(function.ordinal, results)

        MainActivity.handleResultsNotification.sendMessage(fnMsg)

    }

}