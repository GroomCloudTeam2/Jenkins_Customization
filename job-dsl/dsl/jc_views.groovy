def ROOT_FOLDER = "monorepo"

folder(ROOT_FOLDER) {
    views {
        listView("All") {
            description("All jobs under ${ROOT_FOLDER}")
            jobs {
                regex(".*")   // 폴더 아래 전부
            }
            columns {
                status()
                weather()
                name()
                lastSuccess()
                lastFailure()
                lastDuration()
                buildButton()
            }
        }
    }
}
