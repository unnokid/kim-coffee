package com.example.kimcoffee.model;

import org.springframework.util.Assert;

import java.util.Objects;
import java.util.regex.Pattern;

public class Email {
    //이메일에 대한 포맷
    private final String email;

    public Email(String email) {
        Assert.notNull(email, "이메일은 null 이면 안됩니다.");
        Assert.isTrue(email.length() >= 4 && email.length() <= 50, "이메일의 길이는 4~50로 입력해주세요.");
        Assert.isTrue(checkAddress(email), "이메일의 형식에 맞게 입력해주세요.");
        this.email = email;
    }

    //https://regexr.com/ 정규표현식 참고사이트
    private static boolean checkAddress(String address) {
        return Pattern.matches("\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b", address);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return Objects.equals(this.email, email.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public String toString() {
        return email;
    }

    public String getEmail() {
        return email;
    }
}
