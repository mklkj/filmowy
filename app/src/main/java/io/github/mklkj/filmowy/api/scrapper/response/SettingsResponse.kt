package io.github.mklkj.filmowy.api.scrapper.response

import pl.droidsonroids.jspoon.annotation.Selector

class SettingsResponse {

    @Selector(".userAvatar", attr = "data-id")
    var id: Long = 0

    @Selector(".userAvatar", attr = "data-nick")
    var nick: String = ""

    @Selector(".user__name")
    var name: String = ""

    @Selector(".userAvatar__image", attr = "data-image")
    var avatar: String = ""

    @Selector("#form-male", attr = "checked")
    var sex: String = ""
}
