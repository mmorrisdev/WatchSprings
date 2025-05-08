// WatchSprings 1.0 © 2025 Mark S. Morris. All rights reserved.
//
// https://www.watchsprings.com
//
// WatchSprings is Fan Content pursuant to the Epic Games Fan Content Policy. Portions of the materials used are trademarks and/or copyrighted works of Epic Games, Inc.

import WatchKit

let APIResultsNotification = "APIResultsNotification"

class FortniteAPI: NSObject
{
    let api_key: String = "legacy API provider no longer exists"
    let base_url: String = "legacy API provider no longer exists"
    
    var activeFunctions: NSMutableArray = []
    
    override init()
    {
        super.init()
        
        print("FortniteAPI is awake")
        
    }
    
    deinit
    {
        CMDLOG()
        
    }
    
    //----------------------------------------------------------------------------------------------------------------------------

    func getUserID()
    {
        CMDLOG()
    }
    
    func getPlayerData()
    {
        CMDLOG()
    }
    
    func getNews(mode: String)  // "br" or "stw"
    {
        CMDLOG()
        
        let function = #function
        
        let lastPath = mode + "_motd"
        let HTTPbody = "language=en"
        
        let params = ["function": function, "path": lastPath, "body": HTTPbody]
        
        api_call(parameters: params)
        
    }
    
    func getStatus()
    {
        CMDLOG()
        
        let function = #function
        let lastPath = "status/fortnite_server_status"
        let params = ["function": function, "path": lastPath]
        
        api_get(parameters: params)
        
    }
    
    func getUsernameFromId()
    {
        CMDLOG()
    }
    
    func getLeaderboard()
    {
        CMDLOG()
    }
    
    func getTopTen()
    {
        CMDLOG()
        
        let function = #function
        
        let lastPath = "leaderboards"
        let HTTPbody = "------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"window\"\r\n\r\ntop_10_kills\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW--"
        
        let params = ["function": function, "path": lastPath, "body": HTTPbody]
        
        api_call(parameters: params)
    }
    
    func getChallenges()
    {
        CMDLOG()
        
        let function = #function
        
        let lastPath = "challenges"
        
        let HTTPbody = "------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"season\"\r\n\r\ncurrent\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"language\"\r\n\r\nen\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW--"
        
        let params = ["function": function, "path": lastPath, "body": HTTPbody]
        
        api_call(parameters: params)
    }
    
    func getStore()
    {
        CMDLOG()
        
        let function = #function
        
        let lastPath = "store"
        
        let params = ["function": function, "path": lastPath]
        
        api_call(parameters: params)
       
    }
    
    func getUpcoming()
    {
        CMDLOG()
        
        let function = #function
        
        let lastPath = "upcoming"
        
        let params = ["function": function, "path": lastPath]
        
        api_call(parameters: params)
    }
    
    //----------------------------------------------------------------------------------------------------------------------------

    func api_call(parameters: Dictionary<String, String>)
    {
        CMDLOG()

        let fn: String = parameters["function"]!
        
        if (activeFunctions.contains(fn))	//make smarter, so it only ignores requests for functions that are already pending...
        {
            print("busy right now!")
            WKInterfaceDevice.current().play(WKHapticType.failure)
            return
        }
        
        activeFunctions.add(fn)
        
        var results: Dictionary<String, Any> = ["function": parameters["function"] as Any]
        
        //declare parameter as a dictionary which contains string as key and value combination. considering inputs are valid
        
        //create the url with URL
        let url = URL(string: base_url + parameters["path"]! + "/get")! //change the url
        
        //create the session object
        let session = URLSession.shared
        
        //now create the URLRequest object using the url object
        var request = URLRequest(url: url)
        request.httpMethod = "POST" //set http method as POST
        request.httpBody = parameters["body"]?.data(using: String.Encoding.ascii)
        request.addValue("multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW", forHTTPHeaderField: "Content-Type")
        request.addValue(api_key, forHTTPHeaderField: "Authorization")
        request.cachePolicy = .reloadIgnoringLocalCacheData

        //create dataTask using the session object to send data to the server
        let task = session.dataTask(with: request as URLRequest, completionHandler: { data, response, error in
            self.activeFunctions.remove(fn)
            guard error == nil else {
                results["function"] = "ERROR: " + error!.localizedDescription
                print(error!.localizedDescription)
                NotificationCenter.default.post(name: NSNotification.Name(rawValue: APIResultsNotification), object: results)
                return
            }
            guard let data = data else {
                results["function"] = "ERROR: No data"
                print(error!.localizedDescription)
                NotificationCenter.default.post(name: NSNotification.Name(rawValue: APIResultsNotification), object: results)
                return
            }
            do {
                defer
                {
                    NotificationCenter.default.post(name: NSNotification.Name(rawValue: APIResultsNotification), object: results)
                }
                //create json object from data
                if let json = try JSONSerialization.jsonObject(with: data, options: .mutableContainers) as? [String: Any] {
                    print(json)
                    results["result"] = json
                }
            } catch let error {
                results["function"] = "ERROR" + error.localizedDescription
                print(error.localizedDescription)
            }
            
        })
        task.resume()
    }
    
    //----------------------------------------------------------------------------------------------------------------------------
    
    func api_get(parameters: Dictionary<String, String>)
    {
        CMDLOG()
        
        var results: Dictionary<String, Any> = ["function": parameters["function"] as Any]
        
        let fn: String = parameters["function"]!
        
        if (activeFunctions.contains(fn))    //make smarter, so it only ignores requests for functions that are already pending...
        {
            print("busy right now!")
            WKInterfaceDevice.current().play(WKHapticType.failure)
            return
        }
        
        activeFunctions.add(fn)
        
        //declare parameter as a dictionary which contains string as key and value combination. considering inputs are valid
        
        //create the url with URL
        let url = URL(string: base_url + parameters["path"]!)! //change the url
        
        //create the session object
        let session = URLSession.shared
        
        //now create the URLRequest object using the url object
        var request = URLRequest(url: url)
        request.httpMethod = "GET"
        request.cachePolicy = .reloadIgnoringLocalCacheData
        
        //create dataTask using the session object to send data to the server
        let task = session.dataTask(with: request as URLRequest, completionHandler: { data, response, error in
            self.activeFunctions.remove(fn)
            guard error == nil else {
                results["function"] = "ERROR: " + error!.localizedDescription
                print(error!.localizedDescription)
                 NotificationCenter.default.post(name: NSNotification.Name(rawValue: APIResultsNotification), object: results)
                return
            }
            guard let data = data else {
                results["function"] = "ERROR: No data"
                print(error!.localizedDescription)
               NotificationCenter.default.post(name: NSNotification.Name(rawValue: APIResultsNotification), object: results)
                return
            }
            
            do {
                defer
                {
                    NotificationCenter.default.post(name: NSNotification.Name(rawValue: APIResultsNotification), object: results)
                }
                //create json object from data
                if let json = try JSONSerialization.jsonObject(with: data, options: .mutableContainers) as? [String: Any] {
                    print(json)
                    results["result"] = json
                }
            } catch let error {
                results["function"] = "ERROR: " + error.localizedDescription
                print(error.localizedDescription)
            }
            
        })
        task.resume()
    }
    
    //----------------------------------------------------------------------------------------------------------------------------

}



