package tn.dksoft.association.mappers;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
@Component
public class DateMapper {
public OffsetDateTime fromEntityToDto(Timestamp ts){
    if(ts!=null){
        return OffsetDateTime.of(ts.toLocalDateTime().getYear(),ts.toLocalDateTime().getMonthValue(), ts.toLocalDateTime().getDayOfMonth(),
                ts.toLocalDateTime().getHour(),ts.toLocalDateTime().getMinute(),ts.toLocalDateTime().getSecond(),ts.toLocalDateTime().getNano(), ZoneOffset.UTC);
    }
    else{
        return null;
    }
    }
    public Timestamp fromDtoToEntity (OffsetDateTime ts)
    {
        if(ts!=null)
        {
            return Timestamp.valueOf(ts.atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime());
        }
        else return null;
    }
}
