// WatchSprings 1.0 © 2025 Mark S. Morris. All rights reserved.
//
// https://www.watchsprings.com
//
// WatchSprings is Fan Content pursuant to the Epic Games Fan Content Policy. Portions of the materials used are trademarks and/or copyrighted works of Epic Games, Inc.

import WatchKit
import Foundation

func print(_ items: Any...) {
    #if DEBUG
    Swift.print("DEBUG",items[0])
    #endif
}


var mainInterface: MainInterfaceController!

class MainInterfaceController: WKInterfaceController {
    
    @IBOutlet var titleImage: WKInterfaceImage!
    @IBOutlet var statusLabel: WKInterfaceLabel!
    @IBOutlet var titleLabel: WKInterfaceLabel!
    
    @IBOutlet var newsButton: WKInterfaceButton!
    @IBOutlet var stwNewsButton: WKInterfaceButton!
    @IBOutlet var challengesButton: WKInterfaceButton!
    @IBOutlet var storeButton: WKInterfaceButton!
    @IBOutlet var upcomingButton: WKInterfaceButton!
    @IBOutlet var topTenButton: WKInterfaceButton!
    
    let BRnewsModel = FunctionModel()
    let STWnewsModel = FunctionModel()
    let toptenModel = FunctionModel()
    let statusModel = FunctionModel()
    let challengesModel = FunctionModel()
    let upcomingModel = FunctionModel()
    let storeModel = FunctionModel()

    let fortniteAPI = FortniteAPI()
   
    override func awake(withContext context: Any?) {
        super.awake(withContext: context)
        
        CMDLOG()
        
        // Configure interface objects here.
  
        let img = #imageLiteral(resourceName: "Title.jpg")
        titleImage.setImage(img)
        
        NotificationCenter.default.addObserver(self, selector: #selector(handleResultsNotification(_:)),
                                               name: NSNotification.Name(rawValue: APIResultsNotification),
                                               object: nil)
    
        mainInterface = self
        
    }
    
    override func willActivate() {
        // This method is called when watch view controller is about to be visible to user
        super.willActivate()
        
        CMDLOG()
        
        updateServerStatus()
        
        // ?flashScrollIndicators()
    }
    
    override func didAppear() {
        super.didAppear()
        
        CMDLOG()
        
        // ?flashScrollIndicators()
    }
    
    
    override func didDeactivate() {
        // This method is called when watch view controller is no longer visible
        super.didDeactivate()
    }
    
    func updateServerStatus()
    {
        CMDLOG()
        
        statusLabel.setText("Fortnite Server Status:")
        
        if (statusModel.isModelLoaded)
        {
            // create attributed string
            let atext = statusModel.ItemJSON["message"] as! String
            let status = statusModel.ItemJSON["status"] as! String
            
            var attributes: [NSAttributedString.Key: Any] = [
                .backgroundColor: UIColor.init(red: 0.13, green: 0.54, blue: 0.13, alpha: 1.0) ,
                .foregroundColor: UIColor.white ]
            
            if (status.lowercased() != "up")
            {
                WKInterfaceDevice.current().play(WKHapticType.failure)
                attributes = [ .backgroundColor: UIColor.red, .foregroundColor: UIColor.white ]
            }
            
            let aString = NSAttributedString(string: atext, attributes: attributes)
            titleLabel.setAttributedText(aString)
        }
        else
        {
            titleLabel.setText("Getting status...")
            fortniteAPI.getStatus()
        }
    }
    
    //---------------------------------------------------------------------------------------------------------------------------

    @IBAction public func newsAction(sender: AnyObject)
    {
        CMDLOG()
        
        if (BRnewsModel.isModelLoaded)
        {
            pushController(withName: "NewsTableController", context: BRnewsModel)
            return
        }
        statusLabel.setText("Loading BR News...")
        fortniteAPI.getNews(mode: "br")
    }
    
    //------    ------    ------    ------    ------    ------    ------    ------    ------    ------    ------    ------
    
    @IBAction public func stwNewsAction(sender: AnyObject)
    {
        CMDLOG()
        
        if (STWnewsModel.isModelLoaded)
        {
            pushController(withName: "NewsTableController", context: STWnewsModel)
            return
        }
        scroll(to: titleImage, at: WKInterfaceScrollPosition.top, animated: true)
        statusLabel.setText("Loading STW News...")
        fortniteAPI.getNews(mode: "stw")
        
    }
    
    //------    ------    ------    ------    ------    ------    ------    ------    ------    ------    ------    ------
    
    @IBAction public func challengesAction(sender: AnyObject)
    {
        CMDLOG()
        
        if (challengesModel.isModelLoaded)
        {
            pushController(withName: "ChallengesTableController", context: challengesModel)
            return
        }
        scroll(to: titleImage, at: WKInterfaceScrollPosition.top, animated: true)
        statusLabel.setText("Loading Challenges...")
        fortniteAPI.getChallenges()
        
    }
    
    //------    ------    ------    ------    ------    ------    ------    ------    ------    ------    ------    ------
    
    @IBAction public func storeAction(sender: AnyObject)
    {
        CMDLOG()
        
        if (storeModel.isModelLoaded)
        {
            pushController(withName: "StoreTableController", context: storeModel)
            return
        }
        scroll(to: titleImage, at: WKInterfaceScrollPosition.top, animated: true)
        statusLabel.setText("Loading daily items...")
        fortniteAPI.getStore()
        
    }
    
    //------    ------    ------    ------    ------    ------    ------    ------    ------    ------    ------    ------
    
    
    @IBAction public func upcomingAction(sender: AnyObject)
    {
        CMDLOG()
        
        if (upcomingModel.isModelLoaded)
        {
            pushController(withName: "UpcomingTableController", context: upcomingModel)
            return
        }
        scroll(to: titleImage, at: WKInterfaceScrollPosition.top, animated: true)
        statusLabel.setText("Loading upcoming...")
        fortniteAPI.getUpcoming()
        
    }
    
    //------    ------    ------    ------    ------    ------    ------    ------    ------    ------    ------    ------
    
    
    @IBAction public func topTenAction(sender: AnyObject)
    {
        CMDLOG()
        
        if (toptenModel.isModelLoaded)
        {
            pushController(withName: "TopTenTableController", context: toptenModel)
            return
        }
        scroll(to: titleImage, at: WKInterfaceScrollPosition.top, animated: true)
        statusLabel.setText("Loading Top 10...")
        fortniteAPI.getTopTen()
    }
  
    //------    ------    ------    ------    ------    ------    ------    ------    ------    ------    ------    ------
    
    
    @IBAction public func reloadAction(sender: AnyObject)
    {
        CMDLOG()
        
        BRnewsModel.isModelLoaded = false
        STWnewsModel.isModelLoaded = false
        toptenModel.isModelLoaded = false
        statusModel.isModelLoaded = false
        challengesModel.isModelLoaded = false
        upcomingModel.isModelLoaded = false
        storeModel.isModelLoaded = false
        
        scroll(to: titleImage, at: WKInterfaceScrollPosition.top, animated: true)
        
        updateServerStatus()
        
    }
    
    //------    ------    ------    ------    ------    ------    ------    ------    ------    ------    ------    ------
    
    @IBAction public func aboutAction(sender: AnyObject)
    {
        CMDLOG()
        
        pushController(withName: "AboutViewController", context: nil)
    }
    
    //---------------------------------------------------------------------------------------------------------------------------
    
    @objc func handleResultsNotification(_ notification: Notification)
    {
        CMDLOG()
        
        let results = notification.object as! Dictionary<String, Any>
        
        let fn = results["function"] as! String
        
        if (fn.starts(with: "ERROR"))
        {
            //do something error related here
            statusLabel.setText("Unable to connect")
            titleLabel.setText(fn)
            WKInterfaceDevice.current().play(WKHapticType.failure)
            return
        }
        
        WKInterfaceDevice.current().play(WKHapticType.success)
        
        let json = results["result"] as! [String: Any]
        let itemEntries = json["entries"] as? NSMutableArray
        
        //------    ------    ------    ------    ------    ------    ------    ------
        
        if (fn.starts(with: "getNews"))
        {
            let type = json["type"] as! String
            
            if (type == "battleroyale")
            {
                BRnewsModel.ItemEntries = itemEntries!
                BRnewsModel.ItemDetailID = "NewsDetail"
                BRnewsModel.ItemRowID = "NewsRow"
                BRnewsModel.ItemType = "BR News"
               
                BRnewsModel.isModelLoaded = true
                
                DispatchQueue.main.async
                {
                    self.pushController(withName: "NewsTableController", context: self.BRnewsModel)
                }
            }
            
            if (type == "savetheworld")
            {
                STWnewsModel.ItemEntries = itemEntries!
                STWnewsModel.ItemDetailID = "NewsDetail"
                STWnewsModel.ItemRowID = "NewsRow"
                STWnewsModel.ItemType = "STW News"

                STWnewsModel.isModelLoaded = true
                
                DispatchQueue.main.async
                {
                    self.pushController(withName: "NewsTableController", context: self.STWnewsModel)
                }
            }
        }
        
        //------    ------    ------    ------    ------    ------    ------    ------
        
        if (fn.starts(with: "getUpcoming"))
        {
            upcomingModel.ItemEntries = json["items"] as! NSMutableArray
            upcomingModel.ItemRowID = "UpcomingRow"
            
            upcomingModel.isModelLoaded = true
            
            DispatchQueue.main.async
            {
                self.pushController(withName: "UpcomingTableController", context: self.upcomingModel)
            }
        }
        
        //------    ------    ------    ------    ------    ------    ------    ------
        
        if (fn.starts(with: "getStore"))
        {
            storeModel.ItemEntries = json["items"] as! NSMutableArray
            storeModel.ItemRowID = "StoreRow"
            storeModel.ItemType = json["date"] as! String
            
            storeModel.isModelLoaded = true
            
            DispatchQueue.main.async
                {
                    self.pushController(withName: "StoreTableController", context: self.storeModel)
            }
        }

        
        //------    ------    ------    ------    ------    ------    ------    ------
        
        if (fn.starts(with: "getTopTen"))
        {
            toptenModel.ItemEntries = itemEntries!
            toptenModel.ItemDetailID = "TopTenDetail"
            toptenModel.ItemRowID = "TopTenRow"
            
            toptenModel.isModelLoaded = true
            
            DispatchQueue.main.async
            {
                self.pushController(withName: "TopTenTableController", context: self.toptenModel)
            }
        }
        
        //------    ------    ------    ------    ------    ------    ------    ------

        if (fn.starts(with: "getStatus"))
        {
            statusModel.ItemJSON = json
            statusModel.isModelLoaded = true
            updateServerStatus()
        }

        //------    ------    ------    ------    ------    ------    ------    ------

        if (fn.starts(with: "getChallenges"))
        {
            challengesModel.ItemJSON = json
            challengesModel.isModelLoaded = true
            challengesModel.ItemRowID = "ChallengesRow"
            challengesModel.ItemDetailID = "ChallengesDetailTable"
            
            let season = json["challenges"] as! NSDictionary
            let weeks: NSMutableArray = []
            
            for tuple in season
            {
                let week: NSDictionary = [tuple.key : tuple.value]
                if ((tuple.value as! NSArray).count != 0)
                {
                    weeks.add(week)
                }
            }
            
            let ordered: NSArray = weeks.sorted {
                let s1 = (($0 as! NSDictionary).allKeys[0]) as! String
                let s2 = (($1 as! NSDictionary).allKeys[0]) as! String
            
                let i1: Int = Int(s1.dropFirst(4))!
                let i2: Int = Int(s2.dropFirst(4))!
            
                return i1 > i2 } as NSArray
            
            challengesModel.ItemEntries = NSMutableArray(array: ordered)
            
            DispatchQueue.main.async
            {
                self.pushController(withName: "ChallengesTableController", context: self.challengesModel)
            }
            
        }
    }
}

//---------------------------------------------------------------------------------------------------------------------------
    
extension NSObject
{
    func CMDLOG(file: String = #file, line: Int = #line, function: String = #function)
    {
        print("\((file as NSString).lastPathComponent):\(line) : \(function)")
    }
}

//---------------------------------------------------------------------------------------------------------------------------

public extension WKInterfaceImage
{
    public func setUrlImage(imgurl: String)
    {
        let url = URL(string: imgurl)!
        
        let task = URLSession.shared.dataTask(with: url) {(data, response, error) in
            guard let data = data else {
                return
            }
            if let placeholder = UIImage(data: data)
            {
                DispatchQueue.main.async
                    {
                        self.setImage(placeholder)
                }
            }
        }
        task.resume()
    }
}
