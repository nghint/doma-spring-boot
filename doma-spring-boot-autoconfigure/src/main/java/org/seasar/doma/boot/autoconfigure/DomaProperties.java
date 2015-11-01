package org.seasar.doma.boot.autoconfigure;

import static org.seasar.doma.boot.autoconfigure.DomaProperties.DOMA_PREFIX;

import java.util.function.Supplier;

import org.seasar.doma.jdbc.GreedyCacheSqlFileRepository;
import org.seasar.doma.jdbc.NoCacheSqlFileRepository;
import org.seasar.doma.jdbc.SqlFileRepository;
import org.seasar.doma.jdbc.dialect.*;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * {@link ConfigurationProperties} for configuring Doma.
 * @author Toshiaki Maki
 */
@ConfigurationProperties(prefix = DOMA_PREFIX)
public class DomaProperties {
    public static final String DOMA_PREFIX = "doma";

    /**
     * Dialect of database used by Doma.
     */
    private DialectType dialect = DialectType.H2;

    /**
     * Type of {@link SqlFileRepository}.
     */
    private SqlFileRepositoryType sqlFileRepository = SqlFileRepositoryType.NO_CACHE;

    /**
     * Whether convert {@link org.seasar.doma.jdbc.JdbcException} into {@link org.springframework.dao.DataAccessException}.
     */
    private boolean exceptionTranslationEnabled = true;

    public DialectType getDialect() {
        return dialect;
    }

    public void setDialect(DialectType dialect) {
        this.dialect = dialect;
    }

    public SqlFileRepositoryType getSqlFileRepository() {
        return sqlFileRepository;
    }

    public void setSqlFileRepository(SqlFileRepositoryType sqlFileRepository) {
        this.sqlFileRepository = sqlFileRepository;
    }

    public boolean isExceptionTranslationEnabled() {
        return exceptionTranslationEnabled;
    }

    public void setExceptionTranslationEnabled(
            boolean exceptionTranslationEnabled) {
        this.exceptionTranslationEnabled = exceptionTranslationEnabled;
    }

    public static enum DialectType {
        SQLITE(SqliteDialect::new), DB2(Db2Dialect::new), MSSQL(
                MssqlDialect::new), MYSQL(MysqlDialect::new), POSTGRES(
                        PostgresDialect::new), ORACLE(OracleDialect::new), H2(
                                H2Dialect::new), HSQL(HsqldbDialect::new);

        private final Supplier<Dialect> constructor;

        DialectType(Supplier<Dialect> constructor) {
            this.constructor = constructor;
        }

        public Dialect create() {
            return this.constructor.get();
        }
    }

    public static enum SqlFileRepositoryType {
        NO_CACHE(NoCacheSqlFileRepository::new), GREEDY_CACHE(
                GreedyCacheSqlFileRepository::new);
        private final Supplier<SqlFileRepository> constructor;

        SqlFileRepositoryType(Supplier<SqlFileRepository> constructor) {
            this.constructor = constructor;
        }

        public SqlFileRepository create() {
            return this.constructor.get();
        }
    }

    @Override
    public String toString() {
        return "DomaProperties{" + "dialect=" + dialect + ", sqlFileRepository="
                + sqlFileRepository + ", exceptionTranslationEnabled="
                + exceptionTranslationEnabled + '}';
    }
}
