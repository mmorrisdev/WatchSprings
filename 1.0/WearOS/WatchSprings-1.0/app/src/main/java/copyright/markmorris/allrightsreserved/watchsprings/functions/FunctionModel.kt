// WatchSprings 1.0 © 2025 Mark S. Morris. All rights reserved.
//
// https://www.watchsprings.com
//
// WatchSprings is Fan Content pursuant to the Epic Games Fan Content Policy. Portions of the materials used are trademarks and/or copyrighted works of Epic Games, Inc.

package copyright.markmorris.allrightsreserved.watchsprings.functions

import org.json.JSONArray
import org.json.JSONObject

class FunctionModel
{
    var isModelLoaded: Boolean = false

    lateinit var ItemEntries: JSONArray
    lateinit var ItemEntriesList: MutableList<Pair<String, JSONArray>>
    lateinit var ItemDetailID: String
    lateinit var ItemRowID: String
    lateinit var ItemType: String
    lateinit var ItemJSON: JSONObject

}


