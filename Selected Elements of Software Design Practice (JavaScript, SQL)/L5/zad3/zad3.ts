function forEach<T, K>(a: T[], f: (t: T) => K): void {
    for (var n of a) {
        f(n)
    }
}

function map<T>(a: T[], f: (t: T) => T): T[] {
    var result: T[] = []
    for (var n of a) {
        result.push(f(n))
    }

    return result;
}

function filter<T>(a: T[], f: (t: T) => boolean): T[] {
    var result: T[] = [];
    for (var n of a) {
        if (f(n))
            result.push(n);
    }
    return result;
}

console.log(filter([1, 2, 3, 4], function (x) {
    return (x < 3)
}))


console.log(map([1, 2, 3, 4], function (x) {
    return (x * 2)
}))

forEach([1, 2, 3, 4], function(x){
    console.log(x)
})



