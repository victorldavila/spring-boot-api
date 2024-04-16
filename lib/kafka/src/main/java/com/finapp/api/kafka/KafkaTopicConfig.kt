package com.finapp.api.kafka

//@Configuration
//@EnableKafkaStreams
//class KafkaTopicConfig {
//
//    @Bean
//    fun setTestKafkaTopic(): NewTopic {
//        LOGGER.info("Creating kafka topic test in configuration")
//
//        return TopicBuilder
//            .name("test")
//            .build()
//    }
//
//    @Bean
//    fun setTest2KafkaTopic(): NewTopic {
//        LOGGER.info("Creating kafka topic test2 in configuration")
//
//        return TopicBuilder
//            .name("test2")
//            .build()
//    }
//
//    companion object {
//        private val LOGGER = LoggerFactory.getLogger(KafkaTopicConfig::class.java)
//    }
//}