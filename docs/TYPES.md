# Currently Supported Types

## MySQL (8.0)

### Numeric Types

- `unsigned` ✅

|      type       |  sub_type   | supported | 
|:---------------:|:-----------:|:---------:|
|    `Integer`    |  `bigint`   |     ✅     |
|                 |    `int`    |     ✅     |
|                 | `mediumint` |     ✅     |
|                 | `smallint`  |     ✅     |
|                 |  `tinyint`  |     ✅     |
|  `FixedPoint`   |  `decimal`  |     ✅     |
|                 |  `numeric`  |     ✅     |
| `FloatingPoint` |   `float`   |     ✅     |
|                 |  `double`   |     ✅     |
|      `Bit`      |             |    ☑️     |

### String Types

|     type      | sub_type  | supported | 
|:-------------:|:---------:|:---------:|
|  `Character`  |  `char`   |     ✅     |
|               | `varchar` |     ✅     |
|   `Binary`    |           |    ☑️     |
| `Blob & Text` |           |    ☑️     |
|    `Enum`     |           |    ☑️     |
|     `Set`     |           |    ☑️     |

