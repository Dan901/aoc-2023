package day5;

record Range(long start, long end) {

    long length() {
        return end - start + 1;
    }
}
