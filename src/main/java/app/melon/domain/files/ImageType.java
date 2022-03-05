package app.melon.domain.files;

import java.util.Arrays;
import java.util.Optional;

public enum ImageType {
    JPG("jpg"), JPEG("jpeg"), PNG("png");

    private final String value;

    ImageType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    static ImageType fromValue(String v) {
        Optional<ImageType> type = Arrays.stream(ImageType.values())
                .filter(t -> t.value.equals(v)).findFirst();
        return type.orElse(null);
    }
}
