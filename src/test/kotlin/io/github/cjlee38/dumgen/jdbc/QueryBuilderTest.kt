package io.github.cjlee38.dumgen.jdbc

import io.github.cjlee38.dumgen.generator.Table
import io.github.cjlee38.dumgen.generator.Tuple
import io.github.cjlee38.dumgen.scheme.Attribute
import io.github.cjlee38.dumgen.scheme.Relation
import org.junit.jupiter.api.Test

class QueryBuilderTest {

    @Test
    fun test() {
        val eachInserter = BatchQueryBuilder()
        val relation = Relation(
            "team", listOf(
                Attribute("id", "bigint", "NO", null, null, null),
                Attribute("team_name", "varchar(255)", "NO", null, null, null)
            )
        )
        val table = Table(listOf(Tuple(1, "hello"), Tuple(2, "world")))
        eachInserter.build(relation, table)
    }
}
