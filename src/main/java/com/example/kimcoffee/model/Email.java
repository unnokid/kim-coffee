package com.example.kimcoffee.model;

import org.springframework.util.Assert;

import java.util.Objects;
import java.util.regex.Pattern;

public class Email {
    //이메일에 대한 포맷
    private final String address;

    public Email(String address) {
        Assert.notNull(address, "이메일은 null 이면 안됩니다.");
        Assert.isTrue(address.length() >= 4 && address.length() <= 50, "이메일의 길이는 4~50로 입력해주세요.");
        Assert.isTrue(checkAddress(address), "이메일의 형식에 맞게 입력해주세요.");
        this.address = address;
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
        return Objects.equals(address, email.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address);
    }

    @Override
    public String toString() {
        return "Email{" +
                "address='" + address + '\'' +
                '}';
    }
}
