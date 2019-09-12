package io.github.mklkj.filmowy.api.scrapper.response

import pl.droidsonroids.jspoon.annotation.Selector

class ForumThreadsList {

    @Selector(".forumMain .topics-list > li")
    var threadList: List<ForumThread> = emptyList()

    class ForumThread {

        @Selector("h3")
        var topic: String = ""

        @Selector("h3 a", attr = "href")
        var topicUrl: String = ""

        @Selector(".userNameLink", attr = "rel", defValue = "0")
        var authorId: Int = 0

        @Selector(".userNameLink")
        var authorName: String = ""

        @Selector(".userNameLink", attr = "href")
        var authorProfileUrl: String = ""

        @Selector(".userImg img", attr = "src")
        var authorAvatarUrl: String = ""

        @Selector(".cap", attr = "title")
        var date: String = ""

        @Selector(".topicInfo ul li:nth-child(3)", defValue = "0", regex = "na: ([0-9]*)")
        var rating: Int = 0

        @Selector("p.text")
        var content: String = ""

        @Selector(".plusCount")
        var thumbsUp: String = "0"

        @Selector(".topicAnswers a", regex = "([0-9]+) ", defValue = "0")
        var topicAnswers: Int = 0

        @Selector(".topicAnswers + li a:last-child", attr = "href", defValue = "")
        var lastReplayUrl: String = ""

        @Selector(".userLink")
        var lastReplayUser: String = ""

        @Selector(".userLink", attr = "rel", defValue = "0")
        var lastReplayUserId: Int = 0

        @Selector(".topicAnswers + li a:last-child span", attr = "title", defValue = "")
        var lastReplayDate: String = ""
    }
}
