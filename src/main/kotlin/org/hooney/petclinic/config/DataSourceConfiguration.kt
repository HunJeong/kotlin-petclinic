package org.hooney.petclinic.config

import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource
import org.springframework.transaction.support.TransactionSynchronizationManager
import javax.sql.DataSource


const val PRIMARY = "primary"
const val SECONDARY = "secondary"

@Configuration
class DataSourceConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    fun primaryDataSource(): DataSource = DataSourceBuilder.create().type(HikariDataSource::class.java).build()

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.secondary")
    fun secondaryDataSource(): DataSource = DataSourceBuilder.create().type(HikariDataSource::class.java).build()

    @Bean
    fun routingDataSource(
        @Qualifier("primaryDataSource") primaryDataSource: DataSource,
        @Qualifier("secondaryDataSource") secondaryDataSource: DataSource
    ) = ReplicationRoutingDataSource()
            .also { it.setTargetDataSources(mutableMapOf<Any, Any>(PRIMARY to primaryDataSource, SECONDARY to secondaryDataSource)) }
            .also { it.setDefaultTargetDataSource(primaryDataSource) }

    @Primary
    @Bean
    fun dataSource(@Qualifier("routingDataSource") routingDataSource: DataSource): DataSource = LazyConnectionDataSourceProxy(routingDataSource)
}

class ReplicationRoutingDataSource: AbstractRoutingDataSource() {
    override fun determineCurrentLookupKey() = if(TransactionSynchronizationManager.isCurrentTransactionReadOnly()) SECONDARY else PRIMARY
}