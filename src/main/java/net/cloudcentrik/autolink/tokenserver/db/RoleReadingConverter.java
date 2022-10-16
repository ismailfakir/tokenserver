package net.cloudcentrik.autolink.tokenserver.db;

import net.cloudcentrik.autolink.tokenserver.model.Role;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class RoleReadingConverter implements Converter<String, Role> {

    @Override
    public Role convert(String source) {
        return Role.fromValue(source);
    }
}
