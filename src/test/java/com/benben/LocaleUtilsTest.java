package com.benben;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

public class LocaleUtilsTest {
    private static final String EMPTY = "";
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void should_return_null_when_null_to_locale() {
        //given
        LocaleUtils localeUtils = new LocaleUtils();

        //when
        Locale result = localeUtils.toLocale(null);

        //then
        assertThat(result).isEqualTo(null);
    }

    @Test
    public void should_return_empty_when_empty_to_locale() {
        //given
        LocaleUtils localeUtils = new LocaleUtils();

        //when
        Locale result = localeUtils.toLocale("");

        //then
        assertThat(result).isEqualTo(new Locale(EMPTY, EMPTY));
    }

    @Test
    public void should_throw_exception_when_string_contains_number_sign_to_locale(){
        //given
        LocaleUtils localeUtils = new LocaleUtils();
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Invalid locale format: a#b");

        //when
        localeUtils.toLocale("a#b");
    }

    @Test
    public void should_throw_exception_when_string_length_less_than_three(){
        //given
        LocaleUtils localeUtils = new LocaleUtils();
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Invalid locale format: ab");

        //when
        localeUtils.toLocale("ab");
    }

    @Test
    public void should_throw_exception_when_input_begin_with_underline_and_length_less_than_three(){
        //given
        LocaleUtils localeUtils = new LocaleUtils();
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Invalid locale format: _b");

        //when
        localeUtils.toLocale("_b");
    }

    @Test
    public void should_throw_exception_when_input_begin_with_underline_and_second_char_is_not_upper_case(){
        //given
        LocaleUtils localeUtils = new LocaleUtils();
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Invalid locale format: _bQ");

        //when
        localeUtils.toLocale("_bQ");
    }

    @Test
    public void should_throw_exception_when_input_begin_with_underline_and_third_char_is_not_upper_case(){
        //given
        LocaleUtils localeUtils = new LocaleUtils();
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Invalid locale format: _Bq");

        //when
        localeUtils.toLocale("_Bq");
    }

    @Test
    public void should_return_except_underline_when_input_begin_with_underline_and_second_third_char_is_upper_case_and_length_is_three(){
        //given
        LocaleUtils localeUtils = new LocaleUtils();

        //when
        Locale result = localeUtils.toLocale("_BQ");

        //then
        assertThat(result).isEqualTo(new Locale(EMPTY, "BQ"));
    }

    @Test
    public void should_throw_exception_when_input_begin_with_underline_and_length_is_four(){
        //given
        LocaleUtils localeUtils = new LocaleUtils();
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Invalid locale format: _BQ_");

        //when
        localeUtils.toLocale("_BQ_");
    }

    @Test
    public void should_throw_exception_when_input_begin_with_underline_and_length_more_than_five_and_fourth_is_not_underline(){
        //given
        LocaleUtils localeUtils = new LocaleUtils();
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Invalid locale format: _BQAAA");

        //when
        localeUtils.toLocale("_BQAAA");
    }

    @Test
    public void should_return_except_top_two_underline_when_input_begin_with_underline_and_has_underline(){
        //given
        LocaleUtils localeUtils = new LocaleUtils();

        //when
        Locale result = localeUtils.toLocale("_BQ_ABC");

        //then
        assertThat(result).isEqualTo(new Locale(EMPTY, "BQ", "ABC"));
    }

    @Test
    public void should_return_string_when_isISO639LanguageCode(){
        //given
        LocaleUtils localeUtils = new LocaleUtils();

        //when
        Locale result = localeUtils.toLocale("_BQ_ABC");

        //then
        assertThat(result).isEqualTo(new Locale(EMPTY, "BQ", "ABC"));
    }
}