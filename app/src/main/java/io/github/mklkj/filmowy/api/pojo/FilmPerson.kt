package io.github.mklkj.filmowy.api.pojo

data class FilmPerson(
    val filmId: Int,
    val assocType: AssocType,
    val personId: Long,
    val assocName: String?,
    val assocAttributes: String?,
    val personName: String,
    val personImagePath: String?
) {
    enum class AssocType(val id: Int) {
        UNKNOWN(0),
        DIRECTOR(1),
        SCREENWRITER(2),
        MUSIC(3),
        CINEMATOGRAPHER(4),
        ORIGINAL_MATERIALS(5),
        ACTOR(6),
        SELF(7),
        VOICE(8),
        PRODUCER(9),
        MONTAGE(10),
        COSTUME_DESIGNER(11),
        ARCHIVAL_MATERIALS(12),
        GUEST(13);

        companion object {
            private val values = values()
            fun getById(id: Int) = values.firstOrNull { it.id == id } ?: UNKNOWN
        }
    }
}
