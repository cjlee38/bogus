package io.github.cjlee38.bogus.scheme

import io.github.cjlee38.bogus.config.UserConfiguration
import mu.KotlinLogging
import org.springframework.stereotype.Component
import javax.sql.DataSource

@Component
class DatabaseDetector(
    userConfiguration: UserConfiguration,
    dataSource: DataSource,
) {
    private val logger = KotlinLogging.logger { }

    val database: String
    init {
        if (userConfiguration.databaseName != null) {
            database = userConfiguration.databaseName
            logger.info { "database defined by USER : $database" }
        } else {
            database = dataSource.connection.catalog
            logger.info { "database defined by DETECTION : $database" }
        }
    }
}
