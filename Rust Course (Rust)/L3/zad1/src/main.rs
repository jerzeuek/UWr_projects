fn main() {
}

fn row_sum_odd_numbers(n: i64) -> i64 {
    (1..)
        .filter(|&x| x % 2 == 1)
        .skip((0..n as usize).sum())
        .take(n as usize)
        .sum()
}

#[cfg(test)]
mod tests {
    use super::row_sum_odd_numbers;

    #[test]
    fn returns_expected1() {
        assert_eq!(row_sum_odd_numbers(1), 1);
    }

    #[test]
    fn returns_expected2() {
        assert_eq!(row_sum_odd_numbers(42), 74088);
    }

    #[test]
    fn returns_expected3() {
        assert_eq!(row_sum_odd_numbers(3), 27);
    }

    #[test]
    fn returns_expected4() {
        assert_eq!(row_sum_odd_numbers(100), 1000000);
    }

    #[test]
    fn returns_expected5() {
        assert_eq!(row_sum_odd_numbers(69), 328509);
    }

    #[test]
    fn returns_expected6() {
        assert_eq!(row_sum_odd_numbers(42), 74088);
    }

    #[test]
    fn returns_expected7() {
        assert_eq!(row_sum_odd_numbers(7), 343);
    }

    #[test]
    fn returns_expected8() {
        assert_eq!(row_sum_odd_numbers(31), 29791);
    }

    #[test]
    fn returns_expected9() {
        assert_eq!(row_sum_odd_numbers(16), 4096);
    }

    #[test]
    fn returns_expected10() {
        assert_eq!(row_sum_odd_numbers(420), 74088000);
    }

    
}
