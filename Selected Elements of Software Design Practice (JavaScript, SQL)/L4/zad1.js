function Tree(val, left, right) {
    this.left = left;
    this.right = right;
    this.val = val;
}
Tree.prototype[Symbol.iterator] = function* () {
    var queue = []
    queue.push(this);
    while (queue.length > 0) {
        var pomoc = queue[0]
        queue.splice(0, 1)
        if (pomoc.left != undefined) queue.push(pomoc.left);
        if (pomoc.right != undefined) queue.push(pomoc.right);
        yield pomoc.val
    }
}
var root = new Tree(1,
    new Tree(2, new Tree(3)), new Tree(4));

for (var e of root) {
    console.log(e);
}