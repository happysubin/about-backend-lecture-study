package tobyspring.splearn.domain.shared;


import java.util.regex.Pattern;

// @Embeddable  // JPA 3.2 부터는 record에 Embeddable 사용 가능
public record Email(String address) {

    private static final Pattern EMAIL_PATTERN =  Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");

    public Email {
        if(!EMAIL_PATTERN.matcher(address).matches()) {
            throw new IllegalArgumentException("이메일 형식이 바르지 않습니다: " + address);
        }
    }
}
