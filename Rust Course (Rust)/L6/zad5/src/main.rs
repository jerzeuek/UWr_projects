fn main() {
    println!("{}", descending_order(12124091479012));
}

fn descending_order(x: u64) -> u64 {
    let mut numbers = x.to_string().chars().collect::<Vec<char>>();
    numbers.sort_by(|a, b| b.cmp(a));
    
    numbers.into_iter().collect::<String>().parse().unwrap()
}

#[cfg(test)]
mod tests {
    use super::descending_order;
#[test]
fn returns_expected1() {
    assert_eq!(descending_order(0), 0);
}

#[test]
fn returns_expected2() {
    assert_eq!(descending_order(15), 51);
}

#[test]
fn returns_expected3() {
    assert_eq!(descending_order(1021), 2110);
}

#[test]
fn returns_expected4() {
    assert_eq!(descending_order(123456789), 987654321);
}

#[test]
fn returns_expected5() {
    assert_eq!(descending_order(123456789), 987654321);
}
}