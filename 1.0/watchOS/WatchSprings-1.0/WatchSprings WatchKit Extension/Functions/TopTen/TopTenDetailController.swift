// WatchSprings 1.0 © 2025 Mark S. Morris. All rights reserved.
//
// https://www.watchsprings.com
//
// WatchSprings is Fan Content pursuant to the Epic Games Fan Content Policy. Portions of the materials used are trademarks and/or copyrighted works of Epic Games, Inc.

import WatchKit
import Foundation


class TopTenDetailController: WKInterfaceController {
    
    @IBOutlet var playerDetailText: WKInterfaceLabel!
 
    override func awake(withContext context: Any?) {
        super.awake(withContext: context)
        
        // Configure interface objects here.
        
        if let thisPlayer = context as? NSDictionary {
            self.thisPlayer = thisPlayer
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
    
    var thisPlayer: NSDictionary? {
        didSet {
            guard let thisPlayer = thisPlayer else { return }
            
            var allText: String = ""
            var keyStr: String = ""
            var valueStr: String = ""
            
            keyStr = "Username"
            valueStr = (thisPlayer["username"] as! String)
            allText += "\(keyStr):\n\(valueStr)\n\n"
            
            keyStr = "Platform"
            valueStr = (thisPlayer["platform"] as! String)
            if (valueStr == "pc") { valueStr = "PC" }
            if (valueStr == "xb1") { valueStr = "XBOX" }
            if (valueStr == "ps4") { valueStr = "PLAYSTATION" }
            valueStr = valueStr.uppercased()
            allText += "\(keyStr):\n\(valueStr)\n\n"
            
            keyStr = "Wins average"
            let i1: Double = Double(thisPlayer["wins"] as! String)!
            let i2: Double = Double(thisPlayer["matches"] as! String)!
            let wa: Double = i1 / i2
            valueStr = String(format: "%.3f", wa)
            allText += "\(keyStr):\n\(valueStr)\n\n"
            
            keyStr = "Kills per death"
            valueStr = (thisPlayer["kd"] as! String)
            allText += "\(keyStr):\n\(valueStr)\n\n"
            
            playerDetailText.setText(allText)
            
        }
    }

}
