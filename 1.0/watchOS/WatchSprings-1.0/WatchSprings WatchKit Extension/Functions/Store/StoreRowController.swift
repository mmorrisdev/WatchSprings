// WatchSprings 1.0 © 2025 Mark S. Morris. All rights reserved.
//
// https://www.watchsprings.com
//
// WatchSprings is Fan Content pursuant to the Epic Games Fan Content Policy. Portions of the materials used are trademarks and/or copyrighted works of Epic Games, Inc.

import WatchKit

class StoreRowController: ItemRowController {
    
    @IBOutlet var cellImage: WKInterfaceImage!
    @IBOutlet var cellTitle: WKInterfaceLabel!
    @IBOutlet var cellBody: WKInterfaceLabel!
    
    var cellItem: NSDictionary? {
        didSet {
            guard let cellItem = cellItem else { return }
            
            let defaultImage = #imageLiteral(resourceName: "Loading.jpg")
            cellImage.setImage(defaultImage)
            
            cellTitle.setText(cellItem["name"] as? String)
            
            let item = cellItem["item"] as! NSDictionary
            
            var allText: String = ""
            var keyStr: String = ""
            var valueStr: String = ""
                
            keyStr = "Cost"
            valueStr = (cellItem["cost"] as! String)
            allText += "\(keyStr): \(valueStr)\n"

            keyStr = "Type"
            valueStr = (item["type"] as! String).capitalized
            allText += "\(keyStr): \(valueStr)\n"
            
            keyStr = "Rarity"
            valueStr = (item["rarity"] as! String).capitalized
            allText += "\(keyStr): \(valueStr)"
            
            cellBody.setText(allText)
            
            let images = item["images"] as! NSDictionary

            let imageURL = URL(string: ((images["background"] as? String)!))
            
            ImageLoader.image(for: imageURL!) { [weak self] image in
                if (image == nil) {
                    self?.cellImage.setImage(defaultImage)
                } else {
                    self?.cellImage.setImage(image)
                }
            }
           
        }
    }
    
}
