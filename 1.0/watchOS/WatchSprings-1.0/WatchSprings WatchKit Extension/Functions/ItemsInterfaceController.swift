// WatchSprings 1.0 © 2025 Mark S. Morris. All rights reserved.
//
// https://www.watchsprings.com
//
// WatchSprings is Fan Content pursuant to the Epic Games Fan Content Policy. Portions of the materials used are trademarks and/or copyrighted works of Epic Games, Inc.

import WatchKit
import Foundation

//general table controller for all button function results

class ItemsInterfaceController: WKInterfaceController {
    
    var itemsTable: WKInterfaceTable!
    var model: FunctionModel!
 
    override func awake(withContext context: Any?) {
		CMDLOG()
  
      super.awake(withContext: context)
        
        // Configure interface objects here.
        
        model = context as? FunctionModel
        
        var numRows = model.ItemEntries.count
        
        if (numRows > 15)
        {
            numRows = 15
        }
        
        itemsTable.setNumberOfRows(numRows, withRowType: model.ItemRowID)
        
        for index in 0..<itemsTable.numberOfRows
        {
            if (model.ItemRowID == "NewsRow")
            {
                guard let controller = itemsTable.rowController(at: index) as? NewsRowController else { continue }
                controller.rowItem = model.ItemEntries[index] as? NSDictionary
                controller.rowNum = index
            }
            
            if (model.ItemRowID == "TopTenRow")
            {
                guard let controller = itemsTable.rowController(at: index) as? TopTenRowController else { continue }
                controller.rowItem = model.ItemEntries[index] as? NSDictionary
                controller.rowNum = index 
            }
            
            if (model.ItemRowID == "ChallengesRow")
            {
                guard let controller = itemsTable.rowController(at: index) as? ChallengesRowController else { continue }
                controller.rowItem = model.ItemEntries[index] as? NSDictionary
                controller.rowNum = index
            }
            
            if (model.ItemRowID == "UpcomingRow")
            {
                guard let controller = itemsTable.rowController(at: index) as? UpcomingRowController else { continue }
                controller.cellItem = model.ItemEntries[index] as? NSDictionary
                controller.rowNum = index
            }
            
            if (model.ItemRowID == "StoreRow")
            {
                guard let controller = itemsTable.rowController(at: index) as? StoreRowController else { continue }
                controller.cellItem = model.ItemEntries[index] as? NSDictionary
                controller.rowNum = index
            }
        }
    }

    override func willActivate() {
        // This method is called when watch view controller is about to be visible to user
	CMDLOG()
        super.willActivate()
    }

    override func didDeactivate() {
        // This method is called when watch view controller is no longer visible
 	CMDLOG()
       super.didDeactivate()
    }
    
    override func table(_ table: WKInterfaceTable, didSelectRowAt rowIndex: Int)
    {
        CMDLOG()
        if (model.ItemDetailID == "NoDetail") { return }
        let entry = model.ItemEntries[rowIndex]
        pushController(withName: model.ItemDetailID, context: entry)
    }

}
