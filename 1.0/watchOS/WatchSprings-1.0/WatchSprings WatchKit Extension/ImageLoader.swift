// WatchSprings 1.0 © 2025 Mark S. Morris. All rights reserved.
//
// https://www.watchsprings.com
//
// WatchSprings is Fan Content pursuant to the Epic Games Fan Content Policy. Portions of the materials used are trademarks and/or copyrighted works of Epic Games, Inc.

import WatchKit

class ImageLoader: NSObject
{
     // let img = #imageLiteral(resourceName: "Loading.jpg")
    private static let cache = NSCache<NSString, NSData>()
    
    class func image(for url: URL, completionHandler: @escaping(_ image: UIImage?) -> ()) {
        
        DispatchQueue.global(qos: DispatchQoS.QoSClass.background).async {
            
            if let data = self.cache.object(forKey: url.absoluteString as NSString) {
                DispatchQueue.main.async { completionHandler(UIImage(data: data as Data)) }
                return
            }
            
            var request = URLRequest(url: url)
            request.cachePolicy = URLRequest.CachePolicy.reloadIgnoringLocalCacheData
            
            let task = URLSession.shared.dataTask(with: request) {(data, response, error)  in
                if ((error != nil) || (data == nil)) {
                    DispatchQueue.main.async { completionHandler(nil) }
                }
                self.cache.setObject(data! as NSData, forKey: url.absoluteString as NSString)
                DispatchQueue.main.async { completionHandler(UIImage(data: data!)) }
            }
            task.resume()
            
        }
    }
}

