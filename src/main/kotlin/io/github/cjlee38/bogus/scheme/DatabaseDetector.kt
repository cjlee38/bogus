package io.github.cjlee38.bogus.scheme

import io.github.cjlee38.bogus.config.BogusConfiguration
import mu.KotlinLogging
import org.springframework.stereotype.Component
import javax.sql.DataSource

@Component
class DatabaseDetector(
    bogusConfiguration: BogusConfiguration,
    dataSource: DataSource,
) {
    private val logger = KotlinLogging.logger { }

    val database: String
    init {
        if (bogusConfiguration.databaseName != null) {
            database = bogusConfiguration.databaseName
            logger.info { "database defined by USER : $database" }
        } else {
            database = dataSource.connection.catalog
            logger.info { "database defined by DETECTION : $database" }
        }
    }
}
