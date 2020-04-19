package io.github.mklkj.filmowy.api.scrapper.response

import org.jsoup.nodes.Element
import pl.droidsonroids.jspoon.annotation.Selector

class ArticleResponse {

    @Selector("html")
    lateinit var root: Element

    @Selector("h1.inline")
    var title: String = ""

    @Selector(".hdrWithAuthor .newsInfo li:nth-child(1)")
    var source: String = ""

    @Selector(".hdrWithAuthor .newsInfo li:nth-child(2)")
    var author: String = ""

    @Selector(".newsInfo script")
    lateinit var date: Element

    @Selector(".newsContent b:first-child")
    var lead: String = ""

    @Selector("meta[property='og:image']", attr = "content")
    var newsImageUrl: String = ""

    @Selector(".newsContent > div")
    lateinit var content: Element
}
