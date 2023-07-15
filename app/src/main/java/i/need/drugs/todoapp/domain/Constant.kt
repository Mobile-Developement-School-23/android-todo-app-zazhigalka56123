package i.need.drugs.todoapp.domain

object Constant {
    //code
    const val NOT_FOUND_ERROR = 404
    const val OK = 200
    const val UNKNOWN_ERROR = 401
    const val REVISION_ERROR = 400

    //api
    const val TOKEN = "appressorium"
    const val REVISION_HEADER = "X-Last-Known-Revision"
    const val TOKEN_HEADER = "Authorization"

    //sp
    const val SP_REVISION = "revision"
    const val SP_THEME = "theme"
    const val SP_NAME = "prefs"

    //db
    const val DB_NAME = "app_database"

    //notification
    const val EXTRA_ID = "EXTRA_ID"
    const val CHANNEL_ID = "CHANNEL_TODO_APP"
    const val CHANNEL_NAME = "TODO APP NOTIFICATION"

    //theme
    const val THEME_LIGHT = 0
    const val THEME_SYSTEM = 1
    const val THEME_DARK = 2
}