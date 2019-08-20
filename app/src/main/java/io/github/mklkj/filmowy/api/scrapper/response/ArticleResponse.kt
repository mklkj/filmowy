package io.github.mklkj.filmowy.api.scrapper.response

import org.jsoup.nodes.Element
import pl.droidsonroids.jspoon.annotation.Selector

class ArticleResponse {

    @Selector(".newsContent > div")
    lateinit var content: Element
}
