package io.github.mklkj.filmowy.api.scrapper.response

import pl.droidsonroids.jspoon.annotation.Selector

class ForumThreadsList {

    @Selector(".forumSection__list > li")
    var threadList: List<ForumThread> = emptyList()

    class ForumThread {

        @Selector(".forumSection__topicTitle")
        var topic: String = ""

        @Selector(".forumSection__itemLink", attr = "href")
        var topicUrl: String = ""

        @Selector(".forumSection__authorName", attr = "rel", defValue = "0")
        var authorId: Int = 0

        @Selector(".forumSection__authorName")
        var authorName: String = ""

        @Selector(".avatar__link", attr = "href")
        var authorProfileUrl: String = ""

        @Selector(".avatar__link .avatar__image", attr = "data-src")
        var authorAvatarUrl: String = ""

        @Selector(".forumSection__date")
        var date: String = ""

        @Selector(".forumSection__starsNo", defValue = "0")
        var rating: String = ""

        @Selector(".forumSection__topicText")
        var content: String = ""

        @Selector(".plusMinusWidget__count")
        var thumbsUp: String = "0"

        @Selector(".forumSection__commentsCount", defValue = "0")
        var topicAnswers: Int = 0

        @Selector(".forumSection__lastLink", attr = "href", defValue = "")
        var lastReplayUrl: String = ""

        @Selector(".forumSection__lastWho")
        var lastReplayUser: String = ""

        @Selector(".forumSection__lastWho .userLink", attr = "rel", defValue = "0")
        var lastReplayUserId: Int = 0

        @Selector(".forumSection__lastDate", attr = "datetime", defValue = "")
        var lastReplayDate: String = ""
    }
}
