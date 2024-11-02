fn main(){
}

fn encode(msg: String, n: i32) -> Vec<i32>{
    let vec_digits: Vec<i32> = n.to_string()
                                .chars()
                                .map(|c| c.to_digit(10)
                                                .unwrap() as i32)
                                .collect();
    msg.chars()
       .enumerate()
       .map(|(i, c)| c as i32 - 96 + vec_digits[i % vec_digits.len()])
       .collect()
}

#[cfg(test)]
mod tests {
    use super::encode;
    
    #[test]
    fn fixed_tests1() {
        assert_eq!(encode("scout".to_string(), 1939), vec![20, 12, 18, 30, 21]);
    }

    #[test]
    fn fixed_tests2() {
        assert_eq!(encode("masterpiece".to_string(), 1939), vec![14, 10, 22, 29, 6, 27, 19, 18, 6, 12, 8]);
    }

    #[test]
    fn fixed_tests3() {
        assert_eq!(encode("informatyka".to_string(), 2137), vec![11, 15, 9, 22, 20, 14, 4, 27, 27, 12, 4]);
    }

    #[test]
    fn fixed_tests4() {
        assert_eq!(encode("rustisthebest".to_string(), 42), vec![22, 23, 23, 22, 13, 21, 24, 10, 9, 4, 9, 21, 24]);
    }

    #[test]
    fn fixed_tests5() {
        assert_eq!(encode("verygoodtest".to_string(), 31122), vec![25, 6, 19, 27, 9, 18, 16, 5, 22, 7, 22, 21]);
    }
}