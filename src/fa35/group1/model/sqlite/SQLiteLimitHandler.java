package fa35.group1.model.sqlite;

import org.hibernate.dialect.pagination.AbstractLimitHandler;
import org.hibernate.dialect.pagination.LimitHelper;
import org.hibernate.engine.spi.RowSelection;

/**
 * An implementation of {@link org.hibernate.dialect.pagination.LimitHandler}
 * for the {@link fa35.group1.model.sqlite.SqliteDialect} based on
 * {@link org.hibernate.dialect.pagination.CUBRIDLimitHandler}.
 * copied from https://gist.github.com/jsumners/8849909
 */
public class SqliteLimitHandler extends AbstractLimitHandler {

    public SqliteLimitHandler(String sql, RowSelection selection) {
        super(sql, selection);
    }

    @Override
    public boolean supportsLimit() {
        return true;
    }

    @Override
    public String getProcessedSql() {
        String result;

        if (LimitHelper.useLimit(this, this.selection)) {
            final boolean useLimitOffset = LimitHelper.hasFirstRow(this.selection);
            result = this.sql + (useLimitOffset ? " limit ? offset ?" : " limit ?");
        } else {
            result = this.sql;
        }

        return result;
    }
}
