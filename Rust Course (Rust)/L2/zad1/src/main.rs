fn main() {
    println!("Hello, world!");
}

fn get_count(string: &str) -> usize {
    string.chars()
          .filter(|&c| "aeiou".contains(c))
          .count()
}

#[cfg(test)]
mod tests {
    use super::get_count;

    #[test]
    fn my_test1() {
        assert_eq!(get_count("abracadabra"), 5);
    }
    
    #[test]
    fn my_tests2() {
        assert_eq!(get_count("informatyka"), 4);
    }
    
    #[test]
    fn my_tests3() {
        assert_eq!(get_count("konstantynopolitanczykowianeczka"), 11);
    }

    #[test]
    fn my_tests4() {
        assert_eq!(get_count("qwrtpsdfghjklzxcvbnm"), 0);
    }

    #[test]
    fn my_tests5() {
        assert_eq!(get_count("sri dzajawardanapura kotte"), 10);
    }
}
