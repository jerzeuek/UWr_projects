for (var i = 1; i <= 100000; i++) {
    var div = true;
    var x = i;
    var suma = 0;
    while (x > 0) {
        if (i % (x % 10) != 0) {
            div = false;
            break;
        }
        suma += (x % 10);
        x = (x - (x % 10)) / 10;
    }

    if (i % suma == 0 && div == true) console.log(i);

}