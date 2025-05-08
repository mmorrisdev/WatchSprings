// WatchSprings 1.0 © 2025 Mark S. Morris. All rights reserved.
//
// https://www.watchsprings.com
//
// WatchSprings is Fan Content pursuant to the Epic Games Fan Content Policy. Portions of the materials used are trademarks and/or copyrighted works of Epic Games, Inc.

import WatchKit

class NewsRowController: ItemRowController {
    
    @IBOutlet var rowImage: WKInterfaceImage!       
    @IBOutlet var rowTitle: WKInterfaceLabel!
    @IBOutlet var rowBody: WKInterfaceLabel!
    
    var rowItem: NSDictionary? {
        didSet {
            guard let rowItem = rowItem else { return }
            
             // let img = #imageLiteral(resourceName: "Loading.jpg")
            let defaultImage = #imageLiteral(resourceName: "Loading.jpg")
            rowImage.setImage(defaultImage)
            
            rowTitle.setText(rowItem["title"] as? String)
            rowBody.setText(rowItem["body"] as? String)
            
            let imageURL = URL(string: ((rowItem["image"] as? String)!))
            
            ImageLoader.image(for: imageURL!) { [weak self] image in
                if (image == nil) {
                    self?.rowImage.setImage(defaultImage)
                } else {
                    self?.rowImage.setImage(image)
                }
            }
        }
    }
}
