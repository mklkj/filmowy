package io.github.mklkj.filmowy.api.scrapper.response

import pl.droidsonroids.jspoon.annotation.Selector

class FilmSeasonEpisodesResponse {

    @Selector(".episode")
    var episodes: List<Episode> = emptyList()

    class Episode {

        @Selector(".episode", attr = "data-id")
        var id: Int = 0

        @Selector(".episode", attr = "data-season")
        var season: Int = 0

        @Selector(".episode__title", regex = "([0-9]*) -")
        var number: Int = 0

        @Selector(".episode", attr = "data-date")
        var timestamp: Long = 0

        @Selector(".episode__title", regex = "- (.*)")
        var title: String = ""

        @Selector(".episode__communityRate")
        var averageRate: String = "-1.0"
    }
}
