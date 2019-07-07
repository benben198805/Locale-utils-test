package com.benben;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

public class LocaleUtilsTest {
    private static final String EMPTY = "";
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private LocaleUtils localeUtils;

    @Before
    public void setUp() {
        localeUtils = new LocaleUtils();
    }

    @Test
    public void should_return_null_when_null_to_locale() {
        //given
        //when
        Locale result = localeUtils.toLocale(null);

        //then
        assertThat(result).isEqualTo(null);
    }

    @Test
    public void should_return_empty_when_empty_to_locale() {
        //given
        //when
        Locale result = localeUtils.toLocale("");

        //then
        assertThat(result).isEqualTo(new Locale(EMPTY, EMPTY));
    }

    @Test
    public void should_throw_exception_when_string_contains_number_sign_to_locale() {
        //given
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Invalid locale format: a#b");

        //when
        localeUtils.toLocale("a#b");
    }

    @Test
    public void should_throw_exception_when_string_length_less_than_three() {
        //given
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Invalid locale format: _ab");

        //when
        localeUtils.toLocale("_ab");
    }

    @Test
    public void should_throw_exception_when_input_begin_with_underline_and_length_less_than_three() {
        //given
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Invalid locale format: _b");

        //when
        localeUtils.toLocale("_b");
    }

    @Test
    public void should_throw_exception_when_input_begin_with_underline_and_second_char_is_not_upper_case() {
        //given
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Invalid locale format: _bQ");

        //when
        localeUtils.toLocale("_bQ");
    }

    @Test
    public void should_throw_exception_when_input_begin_with_underline_and_third_char_is_not_upper_case() {
        //given
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Invalid locale format: _Bq");

        //when
        localeUtils.toLocale("_Bq");
    }

    @Test
    public void should_return_except_underline_when_input_begin_with_underline_and_second_third_char_is_upper_case_and_length_is_three() {
        //given
        //when
        Locale result = localeUtils.toLocale("_BQ");

        //then
        assertThat(result).isEqualTo(new Locale(EMPTY, "BQ"));
    }

    @Test
    public void should_throw_exception_when_input_begin_with_underline_and_length_is_four() {
        //given
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Invalid locale format: _BQ_");

        //when
        localeUtils.toLocale("_BQ_");
    }

    @Test
    public void should_throw_exception_when_input_begin_with_underline_and_length_more_than_five_and_fourth_is_not_underline() {
        //given
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Invalid locale format: _BQAAA");

        //when
        localeUtils.toLocale("_BQAAA");
    }

    @Test
    public void should_return_language_and_country_when_input_is_right_format() {
        // given
        // when
        Locale result = localeUtils.toLocale("_CN_CHN");
        // then
        assertThat(result).isEqualTo(new Locale(EMPTY, "CN", "CHN"));
    }

    @Test
    public void should_return_language_when_input_is_language_string_and_length_is_two() {
        // given
        // when
        Locale result = localeUtils.toLocale("ab");
        // then
        assertThat(result).isEqualTo(new Locale("ab"));
    }

    @Test
    public void should_return_language_when_input_is_language_string_and_length_is_three() {
        // given
        // when
        Locale result = localeUtils.toLocale("abc");
        // then
        assertThat(result).isEqualTo(new Locale("abc"));
    }

    @Test
    public void should_return_language_and_country_when_input_language_underline_country_format() {
        // given
        // when
        Locale result = localeUtils.toLocale("cn_CN");
        // then
        assertThat(result).isEqualTo(new Locale("cn", "CN"));
    }

    @Test
    public void should_return_language_and_country_when_input_is_language_underline_country_code_format() {
        // given
        // when
        Locale result = localeUtils.toLocale("cn_123");
        // then
        assertThat(result).isEqualTo(new Locale("cn", "123"));
    }

    @Test
    public void should_throw_exception_when_with_invalid_country_code() {
        // given
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Invalid locale format: cn_abc");
        // when
        localeUtils.toLocale("cn_abc");
        // then
    }

    @Test
    public void should_throw_exception_when_with_empty_country_code() {
        // given
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Invalid locale format: cn_");
        // when
        localeUtils.toLocale("cn_");
        // then
    }

    @Test
    public void should_return_language_country_variant_when_input_is_language_underline_country_code_underline_variant_format() {
        // given
        // when
        Locale result = localeUtils.toLocale("cn_123_ab");
        // then
        assertThat(result).isEqualTo(new Locale("cn", "123", "ab"));
    }

    @Test
    public void should_return_language_country_variant_when_input_is_language_underline_upper_case_country_code_underline_variant_format() {
        // given
        // when
        Locale result = localeUtils.toLocale("cn_AB_ab");
        // then
        assertThat(result).isEqualTo(new Locale("cn", "AB", "ab"));
    }


    @Test
    public void should_return_language_variant_when_input_is_language_underline__underline_variant_format() {
        // given
        // when
        Locale result = localeUtils.toLocale("cn__ab");
        // then
        assertThat(result).isEqualTo(new Locale("cn", "", "ab"));
    }

    @Test
    public void should_throw_exception_when_with_empty_variant_code() {
        // given
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Invalid locale format: ab_AB_");
        // when
        localeUtils.toLocale("ab_AB_");
        // then
    }

    @Test
    public void should_throw_exception_when_input_language_code_is_not_ISO639() {
        // given
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Invalid locale format: AB_abc_a");
        // when
        localeUtils.toLocale("AB_abc_a");
        // then
    }

    @Test
    public void should_throw_exception_when_input_has_country_is_invalid() {
        // given
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Invalid locale format: ab_abc_ab");
        // when
        localeUtils.toLocale("ab_abc_ab");
        // then
    }

    @Test
    public void should_throw_exception_when_input_language_is_not_ISO639_language_code() {
        // given
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Invalid locale format: AB_abc");
        // when
        localeUtils.toLocale("AB_abc");
        // then
    }

    @Test
    public void should_throw_exception_when_with_invalid_format() {
        // given
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Invalid locale format: AB_abc_ab_78");
        // when
        localeUtils.toLocale("AB_abc_ab_78");
        // then
    }

}