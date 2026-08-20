package org.test.app.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.*;
import java.util.Date;

/**
 * @author zhanghaowei
 * @since 1.0
 */
@Data
public class TestDto implements Serializable {
    private static final long serialVersionUID = 7187994574401493029L;

    @NotBlank
    private String name;

    private String message;

    private Date date;

    private LocalDateTime localDateTime;

    private LocalTime localTime;

    private Year year;

    private YearMonth yearMonth;

    private LocalDate localDate;

    private BigDecimal bigDecimal;
}
