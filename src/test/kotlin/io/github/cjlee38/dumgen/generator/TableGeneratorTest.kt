package io.github.cjlee38.dumgen.generator

import io.github.cjlee38.dumgen.scheme.Attribute
import io.github.cjlee38.dumgen.scheme.Relation
import org.assertj.core.api.Assertions.*

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class TableGeneratorTest {
    private val tableGenerator = TableGenerator(StringTypeGenerator(), IntegerTypeGenerator())

    @Test
    fun test() {
        val relation = Relation("team", listOf(
            Attribute("id", "bigint", "NO", null, null, null),
            Attribute("team_name", "varchar(255)", "NO", null, null, null)
        ))
        val result = tableGenerator.generate(relation, 100)
        println("result = ${result.joinToString(separator = "\n")}")
    }
}
