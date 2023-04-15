package io.github.cjlee38.database

import mu.KotlinLogging
import org.springframework.stereotype.Component
import javax.sql.DataSource

@Component
class DatabaseDetector(dataSource: DataSource) {
    private val logger = KotlinLogging.logger { }

    val database: String

    init {
        database = dataSource.connection.catalog
        logger.info { "database defined by DETECTION : $database" }
    }
}
