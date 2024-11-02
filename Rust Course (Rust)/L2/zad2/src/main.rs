fn main() {
}

fn longest(a1: &str, a2: &str) -> String {
    "abcdefghijklmnopqrstuvwxyz".chars()
                                .filter(|&c| a1.contains(c) || a2.contains(c))
                                .collect()
}

#[cfg(test)]
    mod tests {
    use super::*;
   
    fn testing(s1: &str, s2: &str, exp: &str) -> () {
        assert_eq!(&longest(s1, s2), exp)
    }

    #[test]
    fn basic_tests1() {
        testing("aretheyhere", "yestheyarehere", "aehrsty");
    }

    #[test]
    fn basic_tests2() {
        testing("loopingisfunbutdangerous", "lessdangerousthancoding", "abcdefghilnoprstu");
    }

    #[test]
    fn basic_tests3() {
        testing("itsatest", "numberthree", "abehimnrstu");
    }

    #[test]
    fn basic_tests4() {
        testing("thisexcercise", "isveryfun", "cefhinrstuvxy");
    }

    #[test]
    fn basic_tests5() {
        testing("ohno", "nothisagain", "aghinost");
    }
}
