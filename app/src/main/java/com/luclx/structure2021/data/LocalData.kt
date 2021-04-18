package com.sin.buildingInsights.data

import com.sin.buildingInsights.data.model.bean.inspection.LocalAssetLevel

object LocalData {
    var areaCode = 65
    var isSyncOfflineShowing: Boolean = false
    var asset: LocalAssetLevel? = null
    var needReloadFRList = false
}