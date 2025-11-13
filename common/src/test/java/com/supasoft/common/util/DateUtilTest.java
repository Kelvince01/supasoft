package com.supasoft.common.util;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class DateUtilTest {

    @Test
    public void testFormatDate() {
        assertEquals("2025-01-01", DateUtil.formatDate(LocalDate.of(2025, 1, 1)));
    }
}