// WatchSprings 1.0 © 2025 Mark S. Morris. All rights reserved.
//
// https://www.watchsprings.com
//
// WatchSprings is Fan Content pursuant to the Epic Games Fan Content Policy. Portions of the materials used are trademarks and/or copyrighted works of Epic Games, Inc.

import WatchKit

class TopTenRowController: ItemRowController {
    
    @IBOutlet var rowTitle: WKInterfaceLabel!
    @IBOutlet var rowBody: WKInterfaceLabel!
    
    var rowItem: NSDictionary? {
        didSet {
            guard let rowItem = rowItem else { return }
            
            rowTitle.setText((rowItem["rank"] as! NSNumber).stringValue)
            rowBody.setText(rowItem["username"] as? String)
        
        }
    }
    
}
