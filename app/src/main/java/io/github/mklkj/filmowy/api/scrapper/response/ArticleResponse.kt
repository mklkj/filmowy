package io.github.mklkj.filmowy.api.scrapper.response

import org.jsoup.nodes.Element
import pl.droidsonroids.jspoon.annotation.Selector

class ArticleResponse {

    @Selector("html")
    lateinit var root: Element

    @Selector("h1.newsHeaderSection__title")
    var title: String = ""

    @Selector(".newsHeaderSection__source")
    var source: String = ""

    @Selector(".newsHeaderSection__author")
    var author: String = ""

    @Selector(".newsHeaderSection__date.tooltip__parent", attr = "data-datetime")
    var date: Long = 0

    @Selector("meta[property='og:description']", attr = "content")
    var lead: String = ""

    @Selector("meta[property='og:image']", attr = "content")
    var newsImageUrl: String = ""

    @Selector(".newsMainSection__news.page__container .page__text")
    lateinit var content: Element
}
