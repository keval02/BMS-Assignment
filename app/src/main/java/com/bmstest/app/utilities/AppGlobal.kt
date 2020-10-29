import java.text.SimpleDateFormat

object AppGlobal {
    const val KEY_MOVIE_ID = "movieId"
    private const val DISPLAY_DATE_PATTERN = "dd, MMM YYYY"
    private const val RECEIVE_DATE_PATTERN = "yyyy-MM-dd"


    /**
     * FUNCTION COMMENT
     *
     * The function will use to convert the date format.
     * @param releaseDate parameter will get the date from api.
     * @return
     */
    fun changeDateFormat(releaseDate: String): String {
        val displayDateFormat = SimpleDateFormat(DISPLAY_DATE_PATTERN)
        val receivedDateFormat = SimpleDateFormat(RECEIVE_DATE_PATTERN)
        var parseDateInDisplayFormat = ""
        parseDateInDisplayFormat = try {
            val parseDate = receivedDateFormat.parse(releaseDate)
            displayDateFormat.format(parseDate)
        } catch (e: Exception) {
            ""
        }
        return parseDateInDisplayFormat
    }
}