// WatchSprings 1.0 © 2025 Mark S. Morris. All rights reserved.
//
// https://www.watchsprings.com
//
// WatchSprings is Fan Content pursuant to the Epic Games Fan Content Policy. Portions of the materials used are trademarks and/or copyrighted works of Epic Games, Inc.


import UIKit

class ViewController: UIViewController {

    @IBOutlet var versionLabel: UILabel!

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        
        CMDLOG()
        
        var version: String = Bundle.main.infoDictionary?["CFBundleShortVersionString"] as! String
        
#if DEBUG
        version += " DEBUG"
#endif
        
        versionLabel.text = "Version \(version)"
    }
    
    @IBAction public func uplinktoButtonAction(sender: AnyObject)
    {
        guard let url = URL(string: "https://uplink.to") else { return }
        UIApplication.shared.open(url)
        
    }


}

