package io.github.mklkj.filmowy.api.scrapper.response

import pl.droidsonroids.jspoon.annotation.Selector

class SettingsResponse {

    @Selector(".mainSettings__groupItemStateContent")
    var nick: String = ""

    @Selector(".user__name")
    var name: String = ""

    @Selector(".userAvatar__image", attr = "data-image")
    var avatar: String = ""
}
