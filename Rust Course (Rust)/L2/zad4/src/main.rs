fn main() {}

fn count_bits(n: i64) -> u32 
{
    n.count_ones()
}

#[cfg(test)]
mod tests {
    use super::count_bits;
#[test]
fn returns_expected1() {
    assert_eq!(count_bits(0), 0);
}

#[test]
fn returns_expected2() {
    assert_eq!(count_bits(4), 1);
}

#[test]
fn returns_expected3() {
    assert_eq!(count_bits(7), 3);
}

#[test]
fn returns_expected4() {
    assert_eq!(count_bits(9), 2);
}

#[test]
fn returns_expected5() {
    assert_eq!(count_bits(10), 2);
}
}