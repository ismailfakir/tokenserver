package net.cloudcentrik.autolink.tokenserver.db;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MongoDBConfiguration {

    @Bean
    public MongoCustomConversions customConversions(){
        List<Converter<?, ?>> converterList = new ArrayList<Converter<?, ?>>();
        converterList.add(new RoleReadingConverter());
        converterList.add(new RoleWritingConverter());
        return new MongoCustomConversions(converterList);
    }
}