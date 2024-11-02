function memoizeFibonacci(index, cache) {
    if (cache[index]) {
        return cache[index];
    }
    else {
        if (index < 3)
            return 1;
        else {
            cache[index] = memoizeFibonacci(index - 1, cache) + memoizeFibonacci(index - 2, cache);
        }
    }
    return cache[index];
}
console.log(memoizeFibonacci(500));
