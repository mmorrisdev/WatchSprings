// WatchSprings 1.0 © 2025 Mark S. Morris. All rights reserved.
//
// https://www.watchsprings.com
//
// WatchSprings is Fan Content pursuant to the Epic Games Fan Content Policy. Portions of the materials used are trademarks and/or copyrighted works of Epic Games, Inc.

import WatchKit

class ChallengesDetailRowController: ItemRowController {
    
    @IBOutlet var detailRowImage: WKInterfaceImage!
    @IBOutlet var detailRowTitle: WKInterfaceLabel!
    @IBOutlet var detailRowBody: WKInterfaceLabel!
    
    var detailRowItem: NSDictionary? {
        didSet {
            guard let detailRowItem = detailRowItem else { return }
            
            let star = "star\(detailRowItem["stars"] ?? 1)"
            
            let img = UIImage(imageLiteralResourceName: star)
            detailRowImage.setImage(img)
            
            detailRowTitle.setText(detailRowItem["challenge"] as? String)
            detailRowBody.setText("Difficulty: \(detailRowItem["difficulty"] ?? "normal")")
        }
    }
}
