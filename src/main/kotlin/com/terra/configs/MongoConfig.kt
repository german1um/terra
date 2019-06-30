package com.terra.configs

import com.mongodb.MongoClient
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.AbstractMongoConfiguration

@Configuration
class MongoConfig : AbstractMongoConfiguration() {

    override fun getDatabaseName(): String {
        return "terra_db"
    }

    override fun mongoClient(): MongoClient {
        return MongoClient("46.101.71.181", 27017)
    }
}
