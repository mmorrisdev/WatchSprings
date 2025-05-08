// WatchSprings 1.0 © 2025 Mark S. Morris. All rights reserved.
//
// https://www.watchsprings.com
//
// WatchSprings is Fan Content pursuant to the Epic Games Fan Content Policy. Portions of the materials used are trademarks and/or copyrighted works of Epic Games, Inc.

import WatchKit
import Foundation


class NewsDetailController: WKInterfaceController {
    
    @IBOutlet var newsText: WKInterfaceLabel!

    override func awake(withContext context: Any?) {
        super.awake(withContext: context)
        
        // Configure interface objects here.
        
        if let newsItem = context as? NSDictionary {
            self.newsItem = newsItem
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
    
    var newsItem: NSDictionary? {
        didSet {
            guard let newsItem = newsItem else { return }
            
            var allText: String = newsItem["title"] as? String ?? ""
            
            let body: String = newsItem["body"] as? String ?? ""
            allText += "\n\n" + body
            
            let time: Int = Int(newsItem["time"] as? String ?? "") ?? 0
            
            let date = Date(timeIntervalSince1970: TimeInterval(time))
            let dateFormatter = DateFormatter()
            dateFormatter.timeStyle = DateFormatter.Style.medium //Set time style
            dateFormatter.dateStyle = DateFormatter.Style.medium //Set date style
            dateFormatter.timeZone = NSTimeZone.system
            let localDate = dateFormatter.string(from: date)
            
            allText += "\n\n" + localDate
            
            newsText.setText(allText)
            
        }
    }

}
