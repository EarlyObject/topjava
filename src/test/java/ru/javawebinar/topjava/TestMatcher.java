package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Role;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class TestMatcher<T> {
    private final String[] fieldsToIgnore;

    private TestMatcher(String... fieldsToIgnore) {
        this.fieldsToIgnore = fieldsToIgnore;
    }

    public static <T> TestMatcher<T> usingFieldsComparator(String... fieldsToIgnore) {
        return new TestMatcher<>(fieldsToIgnore);
    }

    public void assertMatch(T actual, T expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, fieldsToIgnore);
    }

    public void assertMatch(Iterable<T> actual, T... expected) {
        assertMatch(actual, List.of(expected));
    }

    public void assertMatch(Iterable<T> actual, Iterable<T> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields(fieldsToIgnore).isEqualTo(expected);
    }

    public void assertMatch(Set<Role> roles, Set<Role> roles1) {
        assertThat(roles).isEqualTo(roles1);
    }


}
