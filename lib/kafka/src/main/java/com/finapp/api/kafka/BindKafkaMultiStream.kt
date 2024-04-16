package com.finapp.api.kafka

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import reactor.core.publisher.Flux
import reactor.util.function.Tuple2
import reactor.util.function.Tuples
import java.util.function.Function

@Configuration
class BindKafkaMultiStream {

    @Bean
    fun multiInMultiOut(): Function<
        Tuple2<Flux<InventoryInfo>, Flux<ProductMessage>>,
        Tuple2<Flux<String>, Flux<String>>
    > {
        return Function { stream ->
            val item1 = stream.t1.map { item -> item.name }
                .zipWith(stream.t2.map { item -> item.name }) { inventory, product ->
                    inventory + product
                }

            val item2 = stream.t1.map { item -> item.name }
                    .zipWith(stream.t2.map { item -> item.name + "2" }) { inventory, product ->
                        inventory + product
                    }

            Tuples.of(item1, item2)
        }
    }
}