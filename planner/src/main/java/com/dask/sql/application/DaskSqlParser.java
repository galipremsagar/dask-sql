package com.dask.sql.application;

import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;
import org.apache.calcite.sql.validate.SqlConformanceEnum;

/**
 * DaskSqlParser can turn a SQL string into a tree of SqlNodes. It uses the
 * SqlParser from calcite for this.
 */
public class DaskSqlParser {
    final boolean case_sensitive;

    private SqlParser.Config DEFAULT_CONFIG;

    public DaskSqlParser(final boolean case_sensitive_value) {
        case_sensitive = case_sensitive_value;

        DEFAULT_CONFIG = DaskSqlDialect.DEFAULT.configureParser(SqlParser.Config.DEFAULT)
            .withConformance(SqlConformanceEnum.DEFAULT)
            .withCaseSensitive(case_sensitive)
            .withParserFactory(new DaskSqlParserImplFactory());
    }

    public SqlNode parse(String sql) throws SqlParseException {
        final SqlParser parser = SqlParser.create(sql, DEFAULT_CONFIG);
        final SqlNode sqlNode = parser.parseStmt();
        return sqlNode;
    }
}
