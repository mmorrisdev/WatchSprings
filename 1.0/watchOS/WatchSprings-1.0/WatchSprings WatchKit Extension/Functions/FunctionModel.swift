// WatchSprings 1.0 © 2025 Mark S. Morris. All rights reserved.
//
// https://www.watchsprings.com
//
// WatchSprings is Fan Content pursuant to the Epic Games Fan Content Policy. Portions of the materials used are trademarks and/or copyrighted works of Epic Games, Inc.

import WatchKit

class FunctionModel: NSObject
{
    var isModelLoaded: Bool = false
    
    var ItemEntries: NSMutableArray = []
    var ItemDetailID: String = "NoDetail"
    var ItemRowID: String = ""
    var ItemType: String = ""
    var ItemJSON: [String: Any] = [:]

}
