package day2;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
class RevealedCubes {

    private int red;
    private int green;
    private int blue;

    void setCubeCount(int count, String color) {
        switch (color) {
            case "red" -> red = count;
            case "green" -> green = count;
            case "blue" -> blue = count;
        }
    }
}
