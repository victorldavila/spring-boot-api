package com.finapp.api.products_combination.data.config

import com.finapp.api.products_combination.data.listener.ProductsCombinationCascadeDeleteMongoEventListener
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ProductsCombinationMongoConfig {

    @Bean
    fun productsCombinationDeleteCascadeMongoEventListener(): ProductsCombinationCascadeDeleteMongoEventListener =
        ProductsCombinationCascadeDeleteMongoEventListener()
}