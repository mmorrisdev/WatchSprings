// WatchSprings 1.0 © 2025 Mark S. Morris. All rights reserved.
//
// https://www.watchsprings.com
//
// WatchSprings is Fan Content pursuant to the Epic Games Fan Content Policy. Portions of the materials used are trademarks and/or copyrighted works of Epic Games, Inc.


import WatchKit
import Foundation


class AboutViewController: WKInterfaceController {
    
    @IBOutlet var versionLabel: WKInterfaceLabel!
    @IBOutlet var legalText: WKInterfaceLabel!

    override func awake(withContext context: Any?) {
        super.awake(withContext: context)
        
        // Configure interface objects here.
        
        var version: String = Bundle.main.infoDictionary?["CFBundleShortVersionString"] as! String
        
        #if DEBUG
        version += " DEBUG"
        #endif
        
        versionLabel.setText("Version \(version)")
        
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
