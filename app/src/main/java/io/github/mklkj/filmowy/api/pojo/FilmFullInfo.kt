package io.github.mklkj.filmowy.api.pojo

import pl.droidsonroids.jspoon.annotation.Selector
import java.io.Serializable
import java.time.LocalDate

class FilmFullInfo : Serializable {

    var filmId: Long = 0

    var url: String = ""

    @Selector("h1.filmCoverSection__title")
    var title: String = ""

    @Selector(".filmRating--hasPanel", attr = "data-rate")
    var avgRate: Double = .0

    @Selector(".filmRating--hasPanel", attr = "data-count")
    var votesCount: Int = 0

    var wantSee: Int = 0

    @Selector(".filmCoverSection__year")
    var year: Int? = null

    @Selector(".filmCoverSection__filmTime", attr = "data-duration")
    var duration: Int? = null

    @Selector("#filmPoster", attr = "src")
    var imagePath: String? = null

    @Selector(".filmCoverSection__orginalTitle")
    var originalTitle: String = ""


    var genres: String = ""
    var commentsCount: String = ""

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
    var filmType: Int = 0

    @Selector(".filmInfo__info--seasons a:first-child", attr = "data-number", defValue = "0")
    var seasonsCount: Int = 0

    @Selector(".dataSource[data-source=allEpisodesCount]")
    var episodesCount: Int = 0
    var countriesString: String? = null

    @Selector(".filmPosterSection__plot")
    var synopsis: String? = null

    var recommends: Boolean = false
    var premiereWorldPublic: LocalDate? = null
    var premiereCountryPublic: LocalDate? = null
}
