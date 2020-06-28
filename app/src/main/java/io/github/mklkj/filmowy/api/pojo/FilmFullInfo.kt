package io.github.mklkj.filmowy.api.pojo

import org.jsoup.nodes.Element
import pl.droidsonroids.jspoon.annotation.Selector
import java.io.Serializable
import java.time.LocalDate

class FilmFullInfo : Serializable {

    var filmId: Long = 0

    var url: String = ""

    @Selector("h1.filmCoverSection__title")
    var title: String = ""

    @Selector(".filmRating__rateValue")
    var avgRate: String = ""

    @Selector(".filmRating--hasPanel", attr = "data-count", defValue = "0")
    var votesCount: Int? = null

    var wantSee: Int = 0

    @Selector(".filmCoverSection__year")
    var year: String? = null

    @Selector(".filmCoverSection__filmTime", attr = "data-duration", defValue = "0")
    var duration: Int? = null

    @Selector("link[rel=preload][as=image]", attr = "href", index = 1)
    var cover: String = ""

    @Selector("#filmPoster", attr = "src")
    var imagePath: String? = null

    @Selector(".filmCoverSection__orginalTitle")
    var originalTitle: String = ""

    @Selector(".filmPosterSection__info")
    var filmInfo: Element? = null

    var genres: String = ""
    var commentsCount: String = ""

    @Selector(".advertButton--netflix a", attr = "href")
    var netflixUrl: String? = null

    @Selector(".advertButton--hbo a", attr = "href")
    var hboUrl: String? = null

    @Selector(".forumSection h2 a", attr = "href")
    var forumUrl: String? = null
    var hasReview: Boolean = false
    var hasDescription: Boolean = false
    var videoImageUrl: String? = null
    var videoUrl: String? = null
    var videoHDUrl: String? = null
    var video480pUrl: String? = null
    var ageRestriction: Int? = null
    var premiereWorld: String? = null
    var premiereCountry: String? = null

    @Selector(".filmCoverSection__type")
    var filmType: String = ""

    @Selector(".filmPosterSection__serialSeasons .filmInfo__info--group a:not(.see-all)")
    var seasons: List<Element> = emptyList()

    @Selector(".filmInfo__info--seasons a:first-child", attr = "data-number", defValue = "0")
    var seasonsCount: Int = 0

    @Selector(".dataSource[data-source=allEpisodesCount]", defValue = "0")
    var episodesCount: Int = 0
    var countriesString: String? = null

    @Selector(".filmPosterSection__plot")
    var plot: String? = null

    var recommends: Boolean = false
    var premiereWorldPublic: LocalDate? = null
    var premiereCountryPublic: LocalDate? = null

    @Selector(".forumSection__item")
    var lastForumTopics: List<ForumTopic> = emptyList()

    class ForumTopic {

        @Selector(".forumSection__itemLink", attr = "href")
        var url: String = ""

        @Selector(".forumSection__topicTitle")
        var topic: String = ""

        @Selector(".forumSection__authorName", attr = "data-uname")
        var username: String = ""

        @Selector(".avatar__image", attr = "data-src")
        var avatar: String = ""

        @Selector(".forumSection__authorName", attr = "rel", defValue = "0")
        var userId: Int = 0

        @Selector(".forumSection__starsNo", defValue = "0")
        var vote: Int = 0

        @Selector(".forumSection__topicText")
        var content: String = ""
    }
}
