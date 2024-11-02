function forEach(a, f) {
    for (var _i = 0, a_1 = a; _i < a_1.length; _i++) {
        var n = a_1[_i];
        f(n);
    }
}
function map(a, f) {
    var result = [];
    for (var _i = 0, a_2 = a; _i < a_2.length; _i++) {
        var n = a_2[_i];
        result.push(f(n));
    }
    return result;
}
function filter(a, f) {
    var result = [];
    for (var _i = 0, a_3 = a; _i < a_3.length; _i++) {
        var n = a_3[_i];
        if (f(n))
            result.push(n);
    }
    return result;
}
console.log(filter([1, 2, 3, 4], function (x) {
    return (x < 3);
}));
console.log(map([1, 2, 3, 4], function (x) {
    return (x * 2);
}));
forEach([1, 2, 3, 4], function (x) {
    console.log(x);
});
