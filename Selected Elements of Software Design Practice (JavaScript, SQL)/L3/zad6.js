function fib() {
    var n1 = 1, n2 = 0
    return {

        next: function () {
            var cur = n2
            n2 = n1
            n1 = n1 + cur
            return {
                value: cur,
                done: false
            }
        }
    }
}

function* fib() {
    [n1, n2] = [0, 1]
    while (true) {
        yield n1;
        [n1, n2] = [n2, n1 + n2]
    }
}

var _it = fib();
for (var _result; _result = _it.next(), !_result.done;) {
    console.log(_result.value);
}

for (var i of fib()) {
    console.log(i);
}

//działa dla generatora