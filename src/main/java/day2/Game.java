package day2;

import java.util.List;
import java.util.function.ToIntFunction;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@ToString
class Game {

    @Getter
    private final int id;
    private final List<RevealedCubes> revealedCubes;

    boolean isPossible(int red, int green, int blue) {
        return revealedCubes.stream()
                            .allMatch(revealed -> revealed.getRed() <= red
                                    && revealed.getGreen() <= green
                                    && revealed.getBlue() <= blue);
    }

    int calculatePowerOfCubeSets() {
        var maxReds = getMaxCubes(RevealedCubes::getRed);
        var maxGreens = getMaxCubes(RevealedCubes::getGreen);
        var maxBlues = getMaxCubes(RevealedCubes::getBlue);
        return maxReds * maxGreens * maxBlues;
    }

    private int getMaxCubes(ToIntFunction<RevealedCubes> cubeCountGetter) {
        return revealedCubes.stream()
                            .mapToInt(cubeCountGetter)
                            .max()
                            .getAsInt();
    }
}
