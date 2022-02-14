package org.bgamard.fsdtracker.service;

import io.quarkus.hibernate.orm.panache.Panache;
import org.bgamard.fsdtracker.dto.TripCondition;
import org.bgamard.fsdtracker.dto.TripDateData;
import org.bgamard.fsdtracker.dto.TripType;

import javax.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class TripService {
    public List<TripDateData> dateQuery(TripCondition condition, TripType type) {
        String sqlType = type.name().toLowerCase();
        String sqlCondition = condition == null ? "" : "where condition = '" + condition.name() + "'";
        List<?> resultList = Panache.getEntityManager().createNativeQuery("""
                        select date_trunc('week', date) AS week,
                        sum(%sdistance) / nullif(sum(%ssimplefailure), 0) kmSimpleFailure,
                        sum(%sdistance) / nullif(sum(%scriticalfailure), 0) kmCriticalFailure
                        from trip
                        %s
                        group by week
                        order by week""".formatted(sqlType, sqlType, sqlType, sqlType, sqlCondition))
                .getResultList();

        List<TripDateData> dataList = new ArrayList<>();
        for (Object object : resultList) {
            Object[] result = (Object[]) object;
            var lootInfo = new TripDateData();
            lootInfo.date = ((Timestamp) result[0]).toLocalDateTime().toLocalDate();
            if (result[1] != null) {
                lootInfo.kmSimpleFailure = ((BigDecimal) result[1]).intValue();
            }
            if (result[2] != null) {
                lootInfo.kmCriticalFailure = ((BigDecimal) result[2]).intValue();
            }
            dataList.add(lootInfo);
        }

        return dataList;
    }
}
