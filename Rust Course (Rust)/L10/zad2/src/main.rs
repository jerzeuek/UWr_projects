fn main() {
}

fn dig_pow(n: i64, p: i32) -> i64 {
    let n_pow: i64 = n.to_string()
                           .chars()
                           .enumerate()
                           .map(|(i, c)| (c.to_digit(10)
                                                        .unwrap() as i64)
                                                        .pow(p as u32 + i as u32))
                           .sum();
    
    if n_pow % n == 0 {
        return n_pow / n;
    }
    else{
        return -1;
    }
}

#[cfg(test)]
    mod tests {
    use super::dig_pow;
    
    #[test]
    fn basic_tests1() {
        assert_eq!(dig_pow(89, 1), 1);
        
    }

    #[test]
    fn basic_tests2() {
        assert_eq!(dig_pow(92, 1), -1);
        
    }

    #[test]
    fn basic_tests3() {
        assert_eq!(dig_pow(46288, 3), 51);
        
    }

    #[test]
    fn basic_tests4() {
        assert_eq!(dig_pow(42, 2), -1);
        
    }

    #[test]
    fn basic_tests5() {
        assert_eq!(dig_pow(2137, 7), -1);
    }
}
