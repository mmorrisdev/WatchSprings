// WatchSprings 1.0 © 2025 Mark S. Morris. All rights reserved.
//
// https://www.watchsprings.com
//
// WatchSprings is Fan Content pursuant to the Epic Games Fan Content Policy. Portions of the materials used are trademarks and/or copyrighted works of Epic Games, Inc.

import WatchKit
import Foundation

//general table controller for all button function results

class ChallengesTableController: ItemsInterfaceController
{
    
    @IBOutlet var challengesTable: WKInterfaceTable!
   
    override func awake(withContext context: Any?)
    {
        CMDLOG()
        
        itemsTable = challengesTable
        
        super.awake(withContext: context)
        
        self.setTitle("Season \(model.ItemJSON["season"] ?? "Current")")
    }
}
