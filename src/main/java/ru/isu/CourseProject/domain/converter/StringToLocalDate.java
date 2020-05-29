package ru.isu.CourseProject.domain.converter;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;

public class StringToLocalDate implements Converter<String, LocalDate> {
    @Override
    public LocalDate convert( String s ) {
        Integer year = Integer.parseInt( s.split( "-" )[0] );
        Integer month = Integer.parseInt( s.split( "-" )[1] );
        Integer day = Integer.parseInt( s.split( "-" )[2] );
        return LocalDate.of( year, month, day );
    }
}
