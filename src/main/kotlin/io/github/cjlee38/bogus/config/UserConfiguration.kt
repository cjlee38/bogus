package io.github.cjlee38.bogus.config

import mu.KotlinLogging
import org.springframework.util.ResourceUtils
import org.yaml.snakeyaml.Yaml
import java.io.FileInputStream

object UserConfiguration {
    private val logger = KotlinLogging.logger {}

    private val config: Map<String, Any>

    init {
        val yaml = Yaml()
        config = yaml.load(FileInputStream(ResourceUtils.getFile("classpath:bogus.yml")))
    }

    fun <T> get(path: String): T? {
        var cnf = config
        val split = path.split("/").filter { it.isNotEmpty() }
        for (i in 0 until split.size - 1) {
            val next = cnf[split[i]] ?: return null
            cnf = next as Map<String, Any>
        }

        return cnf[split.last()] as T::class
    }

    fun getRelationConfig(relationName: String): RelationConfig {
//        val useAutoIncrement = UserConfiguration.get<Boolean>("bogus/relations/${relation.name}/use_auto_increment") ?: true
//        val globalConfig = RelationConfig()
//        val map = get<Map<String, Any>>("bogus/relations/$relationName")
//        map["count"] ?:
        return RelationConfig()
    }
}
