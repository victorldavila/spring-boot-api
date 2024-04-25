package com.finapp.api.products.data.config

import com.finapp.api.mountable_products.data.listener.MountableStepCascadeDeleteMongoEventListener
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ProductMongoConfig {

    @Bean
    fun productDeleteCascadeMongoEventListener(): MountableStepCascadeDeleteMongoEventListener = MountableStepCascadeDeleteMongoEventListener()
}