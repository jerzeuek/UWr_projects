for (var i = 2; i <= 100000; i++) {
    var prime = true
    for (var j = 2; j * j <= i; j++) {
        if (i % j == 0) {
            prime = false;
            break;
        }
    }
    if (prime == true) console.log(i);
}