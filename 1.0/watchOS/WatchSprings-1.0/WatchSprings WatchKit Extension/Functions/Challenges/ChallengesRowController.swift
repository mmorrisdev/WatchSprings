// WatchSprings 1.0 © 2025 Mark S. Morris. All rights reserved.
//
// https://www.watchsprings.com
//
// WatchSprings is Fan Content pursuant to the Epic Games Fan Content Policy. Portions of the materials used are trademarks and/or copyrighted works of Epic Games, Inc.

import WatchKit

class ChallengesRowController: ItemRowController {
    
    @IBOutlet var rowText: WKInterfaceLabel!
    
    var rowItem: NSDictionary? {
        didSet {
            guard let rowItem = rowItem else { return }
            
            let s1 = rowItem.allKeys[0] as! String
            let i1: Int = Int(s1.dropFirst(4))!
            
            rowText.setText("Week \(i1)")
        }
    }
    
}
