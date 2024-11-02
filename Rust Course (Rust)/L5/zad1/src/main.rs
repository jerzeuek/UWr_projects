fn main() {
    println!("{}", count_odd_pentafib(2));
}

// pierwsza - parzysta
// nastepne mają cykl - 2 nieparzyste, 4 parzyste... 
// p n n p p p p n n ... 

fn count_odd_pentafib(n: u16) -> u16 {
    if n < 2 {
        n
    } else {
        ((n - 1) / 6) + ((n - 2) / 6) + 1
    }
}

#[cfg(test)]
mod tests {
    use super::count_odd_pentafib;

    #[test]
    fn basic_tests1() {
        assert_eq!(count_odd_pentafib(1), 1);
    }

    #[test]
    fn basic_tests2() {
        assert_eq!(count_odd_pentafib(5), 1);
    }

    #[test]
    fn basic_tests3() {
        assert_eq!(count_odd_pentafib(15), 5);
    }

    #[test]
    fn basic_tests4() {
        assert_eq!(count_odd_pentafib(45), 15);
    }

    #[test]
    fn basic_tests5() {
        assert_eq!(count_odd_pentafib(68), 23);
    }
}