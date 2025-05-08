// WatchSprings 1.0 © 2025 Mark S. Morris. All rights reserved.
//
// https://www.watchsprings.com
//
// WatchSprings is Fan Content pursuant to the Epic Games Fan Content Policy. Portions of the materials used are trademarks and/or copyrighted works of Epic Games, Inc.

import WatchKit
import Foundation


class ChallengesDetailTableController: WKInterfaceController {
    
    @IBOutlet var challengesDetailTable: WKInterfaceTable!
    
    var challengeEntries: NSArray!
    
    var weekItem: NSDictionary? {
        didSet {
            guard let weekItem = weekItem else { return }
            
            let s1 = weekItem.allKeys[0] as! String
            let i1: Int = Int(s1.dropFirst(4))!
            
            self.setTitle("Week \(i1)")
            challengeEntries = weekItem.allValues[0] as? NSArray
        }
    }
    
    override func awake(withContext context: Any?) {
        super.awake(withContext: context)
        
        // Configure interface objects here.
        
        if let weekItem = context as? NSDictionary {
            self.weekItem = weekItem
        }
        
        let numRows = challengeEntries.count
        
        challengesDetailTable.setNumberOfRows(numRows, withRowType: "ChallengesDetailRow")
        
        for index in 0..<challengesDetailTable.numberOfRows
        {
            guard let controller = challengesDetailTable.rowController(at: index) as? ChallengesDetailRowController else { continue }
            controller.detailRowItem = challengeEntries[index] as? NSDictionary
            controller.rowNum = index
        }
    }

    override func willActivate() {
        // This method is called when watch view controller is about to be visible to user
        super.willActivate()
    }

    override func didDeactivate() {
        // This method is called when watch view controller is no longer visible
        super.didDeactivate()
    }
}
