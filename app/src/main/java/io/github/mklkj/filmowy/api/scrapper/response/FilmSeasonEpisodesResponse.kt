package io.github.mklkj.filmowy.api.scrapper.response

import pl.droidsonroids.jspoon.annotation.Selector

class FilmSeasonEpisodesResponse {

    @Selector(".episodePreview")
    var episodes: List<Episode> = emptyList()

    class Episode {

        @Selector(".episodePreview", attr = "data-id")
        var id: Int = 0

        @Selector(".episodePreview", attr = "data-season-number")
        var season: Int = 0

        @Selector(".episodePreview", attr = "data-image")
        var image: String = ""

        @Selector("span[itemprop]")
        var number: Int = 0

        @Selector(".episodePreview", attr = "data-air-date")
        var airDate: String = ""

        @Selector(".episodePreview", attr = "data-premiere")
        var premiereDate: String = ""

        @Selector(".episodePreview__title")
        var title: String = ""

        @Selector(".episodePreview__rate")
        var averageRate: String = "-1,0"
    }
}
