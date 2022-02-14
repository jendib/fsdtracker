package org.bgamard.fsdtracker.service;

import io.quarkus.hibernate.orm.panache.Panache;
import org.bgamard.fsdtracker.dto.CountData;
import org.bgamard.fsdtracker.dto.TripCondition;
import org.bgamard.fsdtracker.dto.TripDateData;
import org.bgamard.fsdtracker.dto.TripType;

import javax.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class TripService {
    public List<TripDateData> dateQuery(TripCondition condition, TripType type) {
        String sqlType = type.name().toLowerCase();
        String sqlCondition = condition == null ? "" : "where condition = '" + condition.name() + "'";
        // TODO Change to "week" when we have enough data
        List<?> resultList = Panache.getEntityManager().createNativeQuery("""
                        select date_trunc('day', date) AS week,
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

    public CountData count() {
        Object object = Panache.getEntityManager().createNativeQuery("""
                        select sum(highwaydistance) + sum(streetdistance) totaldistance,
                               sum(duration) totalduration,
                               sum(highwaycriticalfailure) + sum(streetcriticalfailure) totalcriticalfailure,
                               sum(highwaysimplefailure) + sum(streetsimplefailure) totalsimplefailure,
                               count(distinct version) versions
                        from trip""")
                .getSingleResult();
        Object[] result = (Object[]) object;
        var countData = new CountData();
        countData.distance = ((BigDecimal) result[0]).intValue();
        countData.duration = ((BigInteger) result[1]).intValue();
        countData.criticalFailure = ((BigInteger) result[2]).intValue();
        countData.simpleFailure = ((BigInteger) result[3]).intValue();
        countData.versions = ((BigInteger) result[4]).intValue();
        return countData;
    }
}
