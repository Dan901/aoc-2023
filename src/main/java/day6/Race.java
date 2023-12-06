package day6;

import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
class Race {

    private final long time;
    private final long recordDistance;

    int waysToWin() {
        var D = Math.sqrt(Math.pow(time, 2) - 4 * recordDistance);
        var min = (time - D) / 2;
        var max = (time + D) / 2;
        return (int) (Math.floor(max) - Math.ceil(min)) + 1;
    }
}
