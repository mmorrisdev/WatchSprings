// WatchSprings 2.0 © 2025 Mark S. Morris. All rights reserved.
//
// https://www.watchsprings.com
//
// WatchSprings is Fan Content pursuant to the Epic Games Fan Content Policy. Portions of the materials used are trademarks and/or copyrighted works of Epic Games, Inc.

import Foundation
import WatchKit
import UIKit
import SwiftUI

@Observable
class MapViewModel
{
    let mapSizeH = 2048.0
    let mapSizeW = 2048.0
    let speed = 7.0

    var screenSize = CGSize.zero
    var offset = CGSize(width: -1024, height: -1024)
    
    init()
    {
        screenSize = WKInterfaceDevice.current().screenBounds.size
    }
    
    func updateOffset(_ value: DragGesture.Value)
    {
        var translationW = value.translation.width / speed
        var translationH = value.translation.height / speed
        
        if offset.width + translationW > 0
        {
            translationW = 0
        }
        
        if offset.width + translationW < -(mapSizeW - screenSize.width)
        {
            translationW = 0
        }
        
        if offset.height + translationH > 0
        {
            translationH = 0
        }
        
        if offset.height + translationH < -(mapSizeH - screenSize.height)
        {
            translationH = 0
        }
        
        offset = CGSize(width: offset.width + translationW, height: offset.height + translationH)
        
    }
}
